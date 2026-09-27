package com.machine.service.data.brand.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.brand.dto.input.DataBrandCreateInputDto;
import com.machine.client.data.brand.dto.input.DataBrandQueryPageInputDto;
import com.machine.client.data.brand.dto.input.DataBrandQuerySimplePageInputDto;
import com.machine.client.data.brand.dto.input.DataBrandUpdateInputDto;
import com.machine.client.data.brand.dto.input.DataBrandUpdateLogoAttachmentIdInputDto;
import com.machine.client.data.brand.dto.input.DataBrandUpdateParentIdInputDto;
import com.machine.client.data.brand.dto.input.DataBrandUpdateStatusInputDto;
import com.machine.client.data.brand.dto.output.DataBrandDetailOutputDto;
import com.machine.client.data.brand.dto.output.DataBrandListOutputDto;
import com.machine.client.data.leaf.IDataLeaf4DataCodeClient;
import com.machine.sdk.base.envm.StatusEnum;
import com.machine.sdk.base.exception.data.DataBusinessException;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.service.data.brand.dao.IDataBrandDao;
import com.machine.service.data.brand.dao.mapper.entity.DataBrandEntity;
import com.machine.service.data.brand.service.IDataBrandService;
import com.machine.service.data.filecenter.attachment.dao.IDataAttachmentDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.machine.sdk.base.constant.CommonDataConstant.Brand.DATA_BRAND_ROOT_PARENT_ID;

@Slf4j
@Service
public class DataBrandServiceImpl implements IDataBrandService {

    @Autowired
    private IDataBrandDao brandDao;

    @Autowired
    private IDataAttachmentDao attachmentDao;

    @Autowired
    private IDataLeaf4DataCodeClient leaf4DataCodeClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(DataBrandCreateInputDto inputDto) {
        String parentId = StrUtil.isBlank(inputDto.getParentId()) ? DATA_BRAND_ROOT_PARENT_ID : inputDto.getParentId();

        //验证父品牌是否存在
        if (!DATA_BRAND_ROOT_PARENT_ID.equals(parentId) && null == brandDao.getById(parentId)) {
            throw new DataBusinessException("data.brand.service.create.parentNotExists", "父品牌不存在");
        }

        //验证名称是否存在
        DataBrandEntity entityByName = brandDao.getByName(inputDto.getName());
        if (null != entityByName) {
            throw new DataBusinessException("data.brand.service.create.nameAlreadyExists", "名称已经存在");
        }

        DataBrandEntity insertEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), DataBrandEntity.class);
        insertEntity.setParentId(parentId);
        //新建默认禁用，启用时校验LOGO
        insertEntity.setStatus(StatusEnum.DISABLE);
        if (null == insertEntity.getSort()) {
            insertEntity.setSort(0L);
        }
        //品牌编码
        insertEntity.setCode(leaf4DataCodeClient.brandCode());

        return brandDao.insert(insertEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(IdRequest request) {
        DataBrandEntity entity = brandDao.getById(request.getId());
        if (null == entity) {
            return 0;
        }

        if (StatusEnum.ENABLE.equals(entity.getStatus())) {
            throw new DataBusinessException("data.brand.service.delete.enableStatus", "启用状态，不能删除");
        }

        //有子品牌不能删除（不级联删除）
        if (brandDao.existsByParentId(request.getId())) {
            throw new DataBusinessException("data.brand.service.delete.hasChildrenNode", "有子品牌不能删除");
        }

        return brandDao.delete(request.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(DataBrandUpdateInputDto inputDto) {
        DataBrandEntity entity = brandDao.getById(inputDto.getId());
        if (null == entity) {
            return 0;
        }

        //验证名称是否存在（全局唯一）
        DataBrandEntity entityByName = brandDao.getByName(inputDto.getName());
        if (null != entityByName && !entityByName.getId().equals(entity.getId())) {
            throw new DataBusinessException("data.brand.service.update.nameAlreadyExists", "名称已经存在");
        }

        DataBrandEntity updateEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), DataBrandEntity.class);
        updateEntity.setId(entity.getId());
        return brandDao.update(updateEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateParent(DataBrandUpdateParentIdInputDto inputDto) {
        DataBrandEntity entity = brandDao.getById(inputDto.getId());
        if (null == entity) {
            return 0;
        }

        String parentId = StrUtil.isBlank(inputDto.getParentId()) ? DATA_BRAND_ROOT_PARENT_ID : inputDto.getParentId();
        if (parentId.equals(entity.getParentId())) {
            //相同直接返回
            return 0;
        }

        //验证父品牌是否存在
        DataBrandEntity parentEntity = null;
        if (!DATA_BRAND_ROOT_PARENT_ID.equals(parentId)) {
            parentEntity = brandDao.getById(parentId);
            if (null == parentEntity) {
                throw new DataBusinessException("data.brand.service.updateParent.parentNotExists", "父品牌不存在");
            }
        }

        //验证父品牌不能是自己或自己的子品牌
        validateParentId(entity.getId(), parentId);

        //启用状态的品牌不能挂到禁用的父品牌下
        if (StatusEnum.ENABLE.equals(entity.getStatus())
                && null != parentEntity
                && StatusEnum.ENABLE != parentEntity.getStatus()) {
            throw new DataBusinessException("data.brand.service.updateParent.parentDisabled", "父品牌已禁用，请先启用父品牌");
        }

        return brandDao.updateParent(entity.getId(), parentId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateLogoAttachmentId(DataBrandUpdateLogoAttachmentIdInputDto inputDto) {
        if (null == brandDao.getById(inputDto.getId())) {
            throw new DataBusinessException("data.brand.service.updateLogoAttachmentId.brandNotExists", "品牌不存在");
        }

        if (null == attachmentDao.getById(inputDto.getLogoAttachmentId())) {
            throw new DataBusinessException("data.brand.service.updateLogoAttachmentId.attachmentNotExists", "附件不存在");
        }

        return brandDao.updateLogoAttachmentId(inputDto.getId(), inputDto.getLogoAttachmentId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateStatus(DataBrandUpdateStatusInputDto inputDto) {
        DataBrandEntity entity = brandDao.getById(inputDto.getId());
        if (null == entity) {
            return 0;
        }

        if (entity.getStatus() == inputDto.getStatus()) {
            return 0;
        }

        if (StatusEnum.ENABLE == inputDto.getStatus()) {
            //启用前必须已上传LOGO
            if (StrUtil.isBlank(entity.getLogoAttachmentId())) {
                throw new DataBusinessException("data.brand.service.updateStatus.logoNotUploaded", "请先上传品牌LOGO");
            }

            //启用前父品牌必须已启用
            String parentId = entity.getParentId();
            if (!DATA_BRAND_ROOT_PARENT_ID.equals(parentId)) {
                DataBrandEntity parentEntity = brandDao.getById(parentId);
                if (null != parentEntity && StatusEnum.ENABLE != parentEntity.getStatus()) {
                    throw new DataBusinessException("data.brand.service.updateStatus.parentDisabled", "父品牌已禁用，请先启用父品牌");
                }
            }
        } else {
            //禁用前必须没有已启用的子品牌
            if (brandDao.existsEnabledByParentId(entity.getId())) {
                throw new DataBusinessException("data.brand.service.updateStatus.hasEnabledChildrenNode", "存在已启用的子品牌，请先禁用子品牌");
            }
        }

        return brandDao.updateStatus(inputDto.getId(), inputDto.getStatus());
    }
    @Override
    public boolean exists(IdRequest request) {
        return brandDao.exists(request.getId());
    }

    @Override
    public DataBrandDetailOutputDto detail(IdRequest request) {
        DataBrandEntity entity = brandDao.getById(request.getId());
        if (null == entity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), DataBrandDetailOutputDto.class);
    }

    @Override
    public Set<String> listHasChildrenIdSet(IdSetRequest request) {
        return brandDao.selectHasChildrenIdSet(request.getIdSet());
    }

    @Override
    public List<DataBrandDetailOutputDto> listAncestorById(IdRequest request) {
        List<DataBrandEntity> entityList = brandDao.selectAncestorList(request.getId());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }

        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), DataBrandDetailOutputDto.class);
    }

    @Override
    public Map<String, DataBrandDetailOutputDto> mapByIdSet(IdSetRequest request) {
        List<DataBrandEntity> userEntityList = brandDao.selectByIdSet(request.getIdSet());
        return userEntityList.stream()
                .collect(Collectors.toMap(DataBrandEntity::getId, user ->
                        JSONUtil.toBean(JSONUtil.toJsonStr(user), DataBrandDetailOutputDto.class)));
    }

    @Override
    public Page<DataBrandListOutputDto> page(DataBrandQueryPageInputDto inputDto) {
        Page<DataBrandEntity> page = brandDao.selectPage(inputDto);
        Page<DataBrandListOutputDto> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        pageResult.setRecords(JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), DataBrandListOutputDto.class));
        return pageResult;
    }

    @Override
    public Page<DataBrandListOutputDto> childrenPage(DataBrandQueryPageInputDto inputDto) {
        Page<DataBrandEntity> page = brandDao.selectChildrenPage(inputDto);
        Page<DataBrandListOutputDto> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        pageResult.setRecords(JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), DataBrandListOutputDto.class));
        return pageResult;
    }

    @Override
    public Page<DataBrandListOutputDto> simplePage(DataBrandQuerySimplePageInputDto inputDto) {
        Page<DataBrandEntity> page = brandDao.selectSimplePage(inputDto);
        Page<DataBrandListOutputDto> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        pageResult.setRecords(JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), DataBrandListOutputDto.class));
        return pageResult;
    }


    /**
     * 验证父品牌不能是自己或自己的子品牌（从目标父级向上回溯到根节点）
     */
    private void validateParentId(String id,
                                  String parentId) {
        if (id.equals(parentId)) {
            throw new DataBusinessException("data.brand.service.updateParent.parentIsSelf", "父品牌不能是自己");
        }

        String currentId = parentId;
        Set<String> visitedIdSet = new HashSet<>();
        while (!DATA_BRAND_ROOT_PARENT_ID.equals(currentId)) {
            //数据异常成环时兜底
            if (!visitedIdSet.add(currentId)) {
                break;
            }

            if (id.equals(currentId)) {
                throw new DataBusinessException("data.brand.service.updateParent.parentIsChildren", "父品牌不能是自己的子品牌");
            }

            DataBrandEntity currentEntity = brandDao.getById(currentId);
            if (null == currentEntity) {
                break;
            }
            currentId = currentEntity.getParentId();
        }
    }
}
