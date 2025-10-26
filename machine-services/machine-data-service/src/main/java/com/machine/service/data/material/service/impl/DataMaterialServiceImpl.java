package com.machine.service.data.material.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.material.dto.*;
import com.machine.client.data.material.dto.input.DataMaterialCreatePermanentInputDto;
import com.machine.client.data.material.dto.input.DataMaterialCreateTemporaryInputDto;
import com.machine.client.data.material.dto.input.DataMaterialQueryPageInputDto;
import com.machine.client.data.material.dto.input.DataMaterialUpdateInputDto;
import com.machine.client.data.material.dto.output.DataMaterialDetailOutputDto;
import com.machine.client.data.material.dto.output.DataMaterialListOutputDto;
import com.machine.sdk.common.envm.data.material.DataMaterIalTypeEnum;
import com.machine.sdk.common.envm.data.material.DataMaterialStatusEnum;
import com.machine.sdk.common.envm.system.SystemStorageTypeEnum;
import com.machine.sdk.common.exception.data.DataBusinessException;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.data.material.dao.*;
import com.machine.service.data.material.dao.mapper.entity.*;
import com.machine.service.data.material.service.IDataMaterialService;
import com.machine.starter.redis.cache.data.RedisCacheDataMaterialCategory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.machine.sdk.common.constant.CommonDataConstant.MaterialCategory.DATA_MATERIAL_CATEGORY_VIRTUAL_NODE;

@Slf4j
@Service
public class DataMaterialServiceImpl implements IDataMaterialService {

    /**
     * 30年
     */
    private static final long PERMANENT_EXPIRY_MILLISECONDS = 30 * 365 * 24 * 60 * 60 * 1000L;

    /**
     * 30天
     */
    private static final long TEMPORARY_EXPIRY_MILLISECONDS = 30 * 24 * 60 * 60 * 1000L;

    @Autowired
    private RedisCacheDataMaterialCategory materialCategoryCache;

    @Autowired
    private IDataMaterialDao materialDao;

    @Autowired
    private IDataMaterialTextDao materialTextDao;

    @Autowired
    private IDataMaterialImageDao materialImageDao;

    @Autowired
    private IDataMaterialAudioDao materialAudioDao;

    @Autowired
    private IDataMaterialVideoDao materialVideoDao;

    @Autowired
    private IDataMaterialDocumentDao materialDocumentDao;

    @Autowired
    private IDataMaterialFileDao materialFileDao;

    @Autowired
    private IDataMaterialCategoryRelationDao materialCategoryRelationDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createTemporary(DataMaterialCreateTemporaryInputDto inputDto) {
        DataMaterialEntity entity = new DataMaterialEntity();
        entity.setStatus(DataMaterialStatusEnum.PUBLISHED);
        entity.setType(inputDto.getType());
        entity.setStorageType(SystemStorageTypeEnum.TEMPORARY);
        entity.setTitle(inputDto.getName());
        entity.setName(inputDto.getName());
        entity.setFileInfo(inputDto.getFileInfo());
        entity.setSize(inputDto.getSize());
        entity.setExpireTime(System.currentTimeMillis() + TEMPORARY_EXPIRY_MILLISECONDS);
        return materialDao.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createPermanent(DataMaterialCreatePermanentInputDto inputDto) {
        String materialId = inputDto.getMaterialId();
        DataMaterialEntity dbEntity = materialDao.getById(materialId);
        if (dbEntity == null) {
            throw new DataBusinessException("data.material.service.createPermanent.materialNotExists", "素材不存在");
        }

        if (inputDto.getType() != dbEntity.getType()) {
            throw new DataBusinessException("data.material.service.createPermanent.wrongType", "素材类型错误");
        }

        DataMaterialEntity updateEntity = new DataMaterialEntity();
        updateEntity.setId(materialId);
        updateEntity.setStatus(DataMaterialStatusEnum.PUBLISHED);
        updateEntity.setTitle(inputDto.getTitle());
        updateEntity.setStorageType(SystemStorageTypeEnum.PERMANENT);
        if (null == inputDto.getExpireTime()) {
            updateEntity.setExpireTime(System.currentTimeMillis() + TEMPORARY_EXPIRY_MILLISECONDS);
        } else {
            inputDto.setExpireTime(System.currentTimeMillis() + PERMANENT_EXPIRY_MILLISECONDS);
        }
        materialDao.update(updateEntity);

        //素材元数据信息
        insertMaterialMetaInfo(inputDto.getType(), materialId, inputDto.getDescription(), inputDto.getFileMetaInfo());

        //素材与分类的关系
        insertMaterialCategoryRelation(materialId, inputDto.getCategoryIdSet());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePermanent(DataMaterialUpdateInputDto inputDto) {
        String materialId = inputDto.getId();
        DataMaterialEntity dbEntity = materialDao.getById(materialId);
        if (dbEntity == null) {
            throw new DataBusinessException("data.material.service.updatePermanent.materialNotExists", "素材不存在");
        }

        DataMaterialEntity updateEntity = new DataMaterialEntity();
        updateEntity.setId(materialId);
        updateEntity.setStatus(DataMaterialStatusEnum.PUBLISHED);
        updateEntity.setTitle(inputDto.getTitle());
        if (null == inputDto.getExpireTime()) {
            updateEntity.setExpireTime(System.currentTimeMillis() + TEMPORARY_EXPIRY_MILLISECONDS);
        } else {
            inputDto.setExpireTime(System.currentTimeMillis() + PERMANENT_EXPIRY_MILLISECONDS);
        }
        materialDao.update(updateEntity);

        //素材元数据信息
        deleteMaterialMetaInfo(dbEntity.getType(), materialId);
        insertMaterialMetaInfo(dbEntity.getType(), materialId, inputDto.getDescription(), inputDto.getFileMetaInfo());

        //素材与分类的关系
        materialCategoryRelationDao.deleteByMaterialId(materialId);
        insertMaterialCategoryRelation(materialId, inputDto.getCategoryIdSet());
    }

    @Override
    public DataMaterialDetailOutputDto getById(IdRequest request) {
        DataMaterialEntity dbEntity = materialDao.getById(request.getId());
        if (dbEntity == null) {
            return null;
        }

        DataMaterialDetailOutputDto outputDto = JSONUtil.toBean(JSONUtil.toJsonStr(dbEntity), DataMaterialDetailOutputDto.class);
        fillMetaInfo(outputDto);
        return outputDto;
    }

    @Override
    public List<DataMaterialDetailOutputDto> listByIdSet(IdSetRequest request) {
        Set<String> idSet = request.getIdSet();
        if (CollectionUtil.isEmpty(idSet)) {
            return List.of();
        }

        List<DataMaterialEntity> entityList = materialDao.selectByIdSet(idSet);
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }

        List<DataMaterialDetailOutputDto> outputDtoList = JSONUtil.toList(JSONUtil.toJsonStr(entityList), DataMaterialDetailOutputDto.class);
        batchFillMetaInfo(new ArrayList<>(outputDtoList));
        return outputDtoList;
    }

    @Override
    public Page<DataMaterialListOutputDto> selectPage(DataMaterialQueryPageInputDto inputDto) {
        Set<String> categoryIdSet = inputDto.getCategoryIdSet();

        // 处理素材分类参数
        if (CollectionUtil.isNotEmpty(categoryIdSet)) {
            if (categoryIdSet.contains(DATA_MATERIAL_CATEGORY_VIRTUAL_NODE)) {
                inputDto.setContainVirtualNode(true);
            }
            Set<String> recursionCategoryIdSet = materialCategoryCache.recursionListSubId(categoryIdSet);
            inputDto.setCategoryIdSet(recursionCategoryIdSet);
        }

        Page<DataMaterialEntity> entityPage = materialDao.selectPage(inputDto);
        Page<DataMaterialListOutputDto> pageResult = new Page<>(entityPage.getCurrent(), entityPage.getSize(), entityPage.getTotal());

        if (CollectionUtil.isEmpty(entityPage.getRecords())) {
            return pageResult;
        }

        List<DataMaterialListOutputDto> outputDtoList = JSONUtil.toList(JSONUtil.toJsonStr(entityPage.getRecords()), DataMaterialListOutputDto.class);
        batchFillMetaInfo(new ArrayList<>(outputDtoList));
        pageResult.setRecords(outputDtoList);
        return pageResult;
    }

    private <T> void insertMaterialMetaInfo(DataMaterIalTypeEnum type,
                                            String materialId,
                                            String description,
                                            T metaInfo) {
        if (DataMaterIalTypeEnum.TEXT.equals(type)) {
            if (metaInfo == null) {
                throw new DataBusinessException("data.material.service.createPermanent.metaInfoNotExists", "素材元数据不存在");
            }

            DataMaterialTextDto textDto = (DataMaterialTextDto) metaInfo;
            DataMaterialTextEntity entity = JSONUtil.toBean(JSONUtil.toJsonStr(metaInfo), DataMaterialTextEntity.class);
            entity.setMaterialId(materialId);
            entity.setWordCount(textDto.getContent().length());
            entity.setDescription(description);
            materialTextDao.insert(entity);
        } else if (DataMaterIalTypeEnum.IMAGE.equals(type)) {
            if (metaInfo == null) {
                throw new DataBusinessException("data.material.service.createPermanent.metaInfoNotExists", "素材元数据不存在");
            }

            DataMaterialImageEntity entity = JSONUtil.toBean(JSONUtil.toJsonStr(metaInfo), DataMaterialImageEntity.class);
            entity.setMaterialId(materialId);
            entity.setDescription(description);
            materialImageDao.insert(entity);
        } else if (DataMaterIalTypeEnum.AUDIO.equals(type)) {
            if (metaInfo == null) {
                throw new DataBusinessException("data.material.service.createPermanent.metaInfoNotExists", "素材元数据不存在");
            }

            DataMaterialAudioEntity entity = JSONUtil.toBean(JSONUtil.toJsonStr(metaInfo), DataMaterialAudioEntity.class);
            entity.setMaterialId(materialId);
            entity.setDescription(description);
            materialAudioDao.insert(entity);
        } else if (DataMaterIalTypeEnum.VIDEO.equals(type)) {
            if (metaInfo == null) {
                throw new DataBusinessException("data.material.service.createPermanent.metaInfoNotExists", "素材元数据不存在");
            }

            DataMaterialVideoEntity entity = JSONUtil.toBean(JSONUtil.toJsonStr(metaInfo), DataMaterialVideoEntity.class);
            entity.setMaterialId(materialId);
            entity.setDescription(description);
            materialVideoDao.insert(entity);
        } else if (DataMaterIalTypeEnum.DOCUMENT.equals(type)) {
            if (metaInfo == null) {
                throw new DataBusinessException("data.material.service.createPermanent.metaInfoNotExists", "素材元数据不存在");
            }

            DataMaterialDocumentEntity entity = JSONUtil.toBean(JSONUtil.toJsonStr(metaInfo), DataMaterialDocumentEntity.class);
            entity.setMaterialId(materialId);
            entity.setDescription(description);
            materialDocumentDao.insert(entity);
        } else if (DataMaterIalTypeEnum.FILE.equals(type)) {
            if (metaInfo == null) {
                throw new DataBusinessException("data.material.service.createPermanent.metaInfoNotExists", "素材元数据不存在");
            }

            DataMaterialFileEntity entity = JSONUtil.toBean(JSONUtil.toJsonStr(metaInfo), DataMaterialFileEntity.class);
            entity.setMaterialId(materialId);
            entity.setDescription(description);
            materialFileDao.insert(entity);
        }
    }

    private void deleteMaterialMetaInfo(DataMaterIalTypeEnum type,
                                        String materialId) {
        if (DataMaterIalTypeEnum.TEXT.equals(type)) {
            materialTextDao.deleteByMaterialId(materialId);
        } else if (DataMaterIalTypeEnum.IMAGE.equals(type)) {
            materialImageDao.deleteByMaterialId(materialId);
        } else if (DataMaterIalTypeEnum.AUDIO.equals(type)) {
            materialAudioDao.deleteByMaterialId(materialId);
        } else if (DataMaterIalTypeEnum.VIDEO.equals(type)) {
            materialVideoDao.deleteByMaterialId(materialId);
        } else if (DataMaterIalTypeEnum.DOCUMENT.equals(type)) {
            materialDocumentDao.deleteByMaterialId(materialId);
        } else if (DataMaterIalTypeEnum.FILE.equals(type)) {
            materialFileDao.deleteByMaterialId(materialId);
        }
    }

    private void insertMaterialCategoryRelation(String materialId,
                                                Set<String> categoryIdSet) {
        if (CollectionUtil.isNotEmpty(categoryIdSet)) {
            if (categoryIdSet.contains(DATA_MATERIAL_CATEGORY_VIRTUAL_NODE)) {
                throw new DataBusinessException("data.material.service.createPermanent.virtualNode", "不能选择【未分类】");
            }

            for (String categoryId : categoryIdSet) {
                DataMaterialCategoryRelationEntity relationEntity = new DataMaterialCategoryRelationEntity();
                relationEntity.setCategoryId(categoryId);
                relationEntity.setMaterialId(materialId);
                materialCategoryRelationDao.insert(relationEntity);
            }
        }
    }

    private void fillMetaInfo(DataMaterialDetailOutputDto outputDto) {
        if (SystemStorageTypeEnum.PERMANENT.equals(outputDto.getStorageType())) {
            if (DataMaterIalTypeEnum.TEXT.equals(outputDto.getType())) {
                DataMaterialTextEntity entity = materialTextDao.getByMaterialId(outputDto.getId());

                DataMaterialTextDto metaInfo = JSONUtil.toBean(JSONUtil.toJsonStr(entity), DataMaterialTextDto.class);
                outputDto.setDescription(entity.getDescription());
                outputDto.setTextMetaInfo(metaInfo);
            } else if (DataMaterIalTypeEnum.IMAGE.equals(outputDto.getType())) {
                DataMaterialImageEntity entity = materialImageDao.getByMaterialId(outputDto.getId());

                DataMaterialImageDto metaInfo = JSONUtil.toBean(JSONUtil.toJsonStr(entity), DataMaterialImageDto.class);
                outputDto.setDescription(entity.getDescription());
                outputDto.setImageMetaInfo(metaInfo);
            } else if (DataMaterIalTypeEnum.AUDIO.equals(outputDto.getType())) {
                DataMaterialAudioEntity entity = materialAudioDao.getByMaterialId(outputDto.getId());

                DataMaterialAudioDto metaInfo = JSONUtil.toBean(JSONUtil.toJsonStr(entity), DataMaterialAudioDto.class);
                outputDto.setDescription(entity.getDescription());
                outputDto.setAudioMetaInfo(metaInfo);
            } else if (DataMaterIalTypeEnum.VIDEO.equals(outputDto.getType())) {
                DataMaterialVideoEntity entity = materialVideoDao.getByMaterialId(outputDto.getId());

                DataMaterialVideoDto metaInfo = JSONUtil.toBean(JSONUtil.toJsonStr(entity), DataMaterialVideoDto.class);
                outputDto.setDescription(entity.getDescription());
                outputDto.setVideoMetaInfo(metaInfo);
            } else if (DataMaterIalTypeEnum.DOCUMENT.equals(outputDto.getType())) {
                DataMaterialDocumentEntity entity = materialDocumentDao.getByMaterialId(outputDto.getId());

                DataMaterialDocumentDto metaInfo = JSONUtil.toBean(JSONUtil.toJsonStr(entity), DataMaterialDocumentDto.class);
                outputDto.setDescription(entity.getDescription());
                outputDto.setDocumentMetaInfo(metaInfo);
            } else if (DataMaterIalTypeEnum.FILE.equals(outputDto.getType())) {
                DataMaterialFileEntity entity = materialFileDao.getByMaterialId(outputDto.getId());

                DataMaterialFileDto metaInfo = JSONUtil.toBean(JSONUtil.toJsonStr(entity), DataMaterialFileDto.class);
                outputDto.setDescription(entity.getDescription());
                outputDto.setFileMetaInfo(metaInfo);
            }
        }
    }

    private void batchFillMetaInfo(List<IDataMaterialOutputDto> outputDtoList) {
        if (CollectionUtil.isEmpty(outputDtoList)) {
            return;
        }

        Map<String, IDataMaterialOutputDto> outputDtoMap = outputDtoList.stream()
                .collect(Collectors.toMap(
                        IDataMaterialOutputDto::getId,
                        Function.identity()
                ));
        Map<DataMaterIalTypeEnum, List<IDataMaterialOutputDto>> typeOutputDtoMap = outputDtoList.stream()
                .filter(dto -> dto.getStorageType() != null
                        && !SystemStorageTypeEnum.TEMPORARY.equals(dto.getStorageType()))
                .collect(Collectors.groupingBy(IDataMaterialOutputDto::getType));


        for (Map.Entry<DataMaterIalTypeEnum, List<IDataMaterialOutputDto>> entry : typeOutputDtoMap.entrySet()) {
            DataMaterIalTypeEnum type = entry.getKey();
            List<IDataMaterialOutputDto> dtoList = entry.getValue();
            Set<String> materialIdSet = dtoList.stream().map(IDataMaterialOutputDto::getId).collect(Collectors.toSet());


            if (DataMaterIalTypeEnum.TEXT.equals(type)) {
                List<DataMaterialTextEntity> entityList = materialTextDao.selectByMaterialIdSet(materialIdSet);
                for (DataMaterialTextEntity entity : entityList) {
                    DataMaterialTextDto metaInfo = JSONUtil.toBean(JSONUtil.toJsonStr(entity), DataMaterialTextDto.class);

                    IDataMaterialOutputDto outputDto = outputDtoMap.get(entity.getMaterialId());
                    outputDto.setDescription(entity.getDescription());
                    outputDto.setTextMetaInfo(metaInfo);
                }
            } else if (DataMaterIalTypeEnum.IMAGE.equals(type)) {
                List<DataMaterialImageEntity> entityList = materialImageDao.selectByMaterialIdSet(materialIdSet);
                for (DataMaterialImageEntity entity : entityList) {
                    DataMaterialTextDto metaInfo = JSONUtil.toBean(JSONUtil.toJsonStr(entity), DataMaterialTextDto.class);

                    IDataMaterialOutputDto outputDto = outputDtoMap.get(entity.getMaterialId());
                    outputDto.setDescription(entity.getDescription());
                    outputDto.setTextMetaInfo(metaInfo);
                }
            } else if (DataMaterIalTypeEnum.AUDIO.equals(type)) {
                List<DataMaterialAudioEntity> entityList = materialAudioDao.selectByMaterialIdSet(materialIdSet);
                for (DataMaterialAudioEntity entity : entityList) {
                    DataMaterialTextDto metaInfo = JSONUtil.toBean(JSONUtil.toJsonStr(entity), DataMaterialTextDto.class);

                    IDataMaterialOutputDto outputDto = outputDtoMap.get(entity.getMaterialId());
                    outputDto.setDescription(entity.getDescription());
                    outputDto.setTextMetaInfo(metaInfo);
                }
            } else if (DataMaterIalTypeEnum.VIDEO.equals(type)) {
                List<DataMaterialVideoEntity> entityList = materialVideoDao.selectByMaterialIdSet(materialIdSet);
                for (DataMaterialVideoEntity entity : entityList) {
                    DataMaterialTextDto metaInfo = JSONUtil.toBean(JSONUtil.toJsonStr(entity), DataMaterialTextDto.class);

                    IDataMaterialOutputDto outputDto = outputDtoMap.get(entity.getMaterialId());
                    outputDto.setDescription(entity.getDescription());
                    outputDto.setTextMetaInfo(metaInfo);
                }
            } else if (DataMaterIalTypeEnum.DOCUMENT.equals(type)) {
                List<DataMaterialDocumentEntity> entityList = materialDocumentDao.selectByMaterialIdSet(materialIdSet);
                for (DataMaterialDocumentEntity entity : entityList) {
                    DataMaterialTextDto metaInfo = JSONUtil.toBean(JSONUtil.toJsonStr(entity), DataMaterialTextDto.class);

                    IDataMaterialOutputDto outputDto = outputDtoMap.get(entity.getMaterialId());
                    outputDto.setDescription(entity.getDescription());
                    outputDto.setTextMetaInfo(metaInfo);
                }
            } else if (DataMaterIalTypeEnum.FILE.equals(type)) {
                List<DataMaterialFileEntity> entityList = materialFileDao.selectByMaterialIdSet(materialIdSet);
                for (DataMaterialFileEntity entity : entityList) {
                    DataMaterialTextDto metaInfo = JSONUtil.toBean(JSONUtil.toJsonStr(entity), DataMaterialTextDto.class);

                    IDataMaterialOutputDto outputDto = outputDtoMap.get(entity.getMaterialId());
                    outputDto.setDescription(entity.getDescription());
                    outputDto.setTextMetaInfo(metaInfo);
                }
            }
        }


    }
}
