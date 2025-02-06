package com.machine.service.data.label.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.label.dto.input.*;
import com.machine.client.data.label.dto.output.DataLabelOptionDetailOutputDto;
import com.machine.client.data.label.dto.output.DataLabelOptionListOutputDto;
import com.machine.client.data.leaf.IDataLeaf4CodeClient;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.exception.iam.IamBusinessException;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.service.data.label.dao.ILabelOptionDao;
import com.machine.service.data.label.dao.mapper.entity.LabelOptionEntity;
import com.machine.service.data.label.service.ILabelOptionService;
import com.machine.service.data.shop.dao.IShopLabelOptionRelationDao;
import com.machine.service.data.shop.dao.mapper.entity.ShopLabelOptionRelationEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.machine.sdk.common.constant.CommonConstant.Shop.BIAO_QIAN_PREFIX;

@Slf4j
@Service
public class LabelOptionServiceImpl implements ILabelOptionService {

    @Autowired
    private IDataLeaf4CodeClient leafClient;

    @Autowired
    private ILabelOptionDao labelOptionDao;

    @Autowired
    private IShopLabelOptionRelationDao shopLabelOptionRelationDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(DataLabelOptionCreateInputDto inputDto) {
        //验证名称在标签Id下面是否存在
        LabelOptionEntity entityByName = labelOptionDao.getByLabelIdAndName(inputDto.getLabelId(), inputDto.getName());
        if (null != entityByName) {
            throw new IamBusinessException("data.labelOption.create.nameAlreadyExists", "标签选项已经存在");
        }

        LabelOptionEntity insertEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), LabelOptionEntity.class);
        insertEntity.setStatus(StatusEnum.ENABLE);
        //生成编码
        insertEntity.setCode(leafClient.dataBiaoQianBq());
        return labelOptionDao.create(insertEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(IdRequest request) {
        LabelOptionEntity dbEntity = labelOptionDao.getById(request.getId());
        if (null == dbEntity) {
            return 0;
        }

        if (StatusEnum.ENABLE == dbEntity.getStatus()) {
            throw new IamBusinessException("data.labelOption.delete.enableStatus", "启用状态不能删除");
        }

        //校验标签是否关联门店
        List<ShopLabelOptionRelationEntity> shopLabelOptionRelationEntityList = shopLabelOptionRelationDao.listByLabelOptionId(dbEntity.getId());
        if (CollectionUtil.isNotEmpty(shopLabelOptionRelationEntityList)) {
            throw new IamBusinessException("data.labelOption.delete.associationShop", "标签关联门店不能删除");
        }

        return labelOptionDao.deleteById(request.getId(), dbEntity.getLabelId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(DataLabelOptionUpdateInputDto inputDto) {
        LabelOptionEntity dbEntity = labelOptionDao.getById(inputDto.getId());
        if (null == dbEntity) {
            return 0;
        }

        if (!dbEntity.getCode().startsWith(BIAO_QIAN_PREFIX)) {
            throw new IamBusinessException("data.labelOption.updateStatus.defaultLabelOption", "默认标签选项，不能修改名称");
        }

        //验证名称在标签Id下面是否存在
        LabelOptionEntity entityByName = labelOptionDao.getByLabelIdAndName(dbEntity.getLabelId(), inputDto.getName());
        if (null != entityByName && !entityByName.getId().equals(dbEntity.getId())) {
            throw new IamBusinessException("data.labelOption.updateStatus.nameAlreadyExists", "标签选项已经存在");
        }

        LabelOptionEntity updateEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), LabelOptionEntity.class);
        return labelOptionDao.update(dbEntity.getLabelId(), updateEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateStatus(DataLabelOptionUpdateStatusInputDto inputDto) {
        LabelOptionEntity dbEntity = labelOptionDao.getById(inputDto.getId());
        if (null == dbEntity) {
            return 0;
        }

        if (dbEntity.getStatus() == inputDto.getStatus()) {
            return 0;
        }

        if (!dbEntity.getCode().startsWith(BIAO_QIAN_PREFIX)) {
            throw new IamBusinessException("data.labelOption.updateStatus.defaultLabelOption", "默认标签选选项，不能修改状态");
        }

        return labelOptionDao.updateStatus(inputDto.getId(), dbEntity.getLabelId(), inputDto.getStatus());
    }

    @Override
    public DataLabelOptionDetailOutputDto detail(IdRequest request) {
        LabelOptionEntity entity = labelOptionDao.getById(request.getId());
        if (null == entity) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entity), DataLabelOptionDetailOutputDto.class);
    }

    @Override
    public List<DataLabelOptionListOutputDto> listByIdSet(IdSetRequest request) {
        List<LabelOptionEntity> entityList = labelOptionDao.listByIdSet(request.getIdSet());
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), DataLabelOptionListOutputDto.class);
    }

    @Override
    public List<DataLabelOptionListOutputDto> listByOffset(DataLabelOptionQueryListOffsetInputDto inputDto) {
        List<LabelOptionEntity> entityList = labelOptionDao.listByOffset(inputDto);
        if (CollectionUtil.isEmpty(entityList)) {
            return List.of();
        }
        return JSONUtil.toList(JSONUtil.toJsonStr(entityList), DataLabelOptionListOutputDto.class);
    }

    @Override
    public Map<String, DataLabelOptionListOutputDto> mapByIdSet(IdSetRequest request) {
        List<LabelOptionEntity> entityList = labelOptionDao.listByIdSet(request.getIdSet());
        if (CollectionUtil.isEmpty(entityList)) {
            return Map.of();
        }
        return entityList.stream()
                .collect(Collectors.toMap(LabelOptionEntity::getId, user ->
                        JSONUtil.toBean(JSONUtil.toJsonStr(user), DataLabelOptionListOutputDto.class)));
    }

    @Override
    public Page<DataLabelOptionListOutputDto> page(DataLabelOptionQueryPageInputDto inputDto) {
        Page<LabelOptionEntity> page = labelOptionDao.selectPage(inputDto);
        Page<DataLabelOptionListOutputDto> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        pageResult.setRecords(JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), DataLabelOptionListOutputDto.class));
        return pageResult;
    }
}
