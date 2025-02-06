package com.machine.service.data.label.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.data.label.dto.input.DataLabelOptionQueryListOffsetInputDto;
import com.machine.client.data.label.dto.input.DataLabelOptionQueryPageInputDto;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.model.dto.IdDto;
import com.machine.sdk.common.model.dto.IdStatusDto;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.data.label.dao.ILabelOptionDao;
import com.machine.service.data.label.dao.mapper.ILabelOptionMapper;
import com.machine.service.data.label.dao.mapper.entity.LabelOptionEntity;
import com.machine.starter.mq.function.CustomerStreamBridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

import static com.machine.sdk.common.constant.CommonConstant.Shop.SHOP_LABEL_ID;

@Repository
public class LabelOptionDaoImpl implements ILabelOptionDao {

    @Autowired
    private CustomerStreamBridge customerStreamBridge;

    @Autowired
    private ILabelOptionMapper labelOptionMapper;

    @Override
    public String create(LabelOptionEntity entity) {
        labelOptionMapper.insert(entity);

        if (SHOP_LABEL_ID.equals(entity.getLabelId())) {
            //事件
            customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_SHOP_LABEL_CREATE, new IdDto(entity.getId()));
        }

        return entity.getId();
    }

    @Override
    public int deleteById(String id,
                          String labelId) {

        if (SHOP_LABEL_ID.equals(labelId)) {
            //事件
            customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_SHOP_LABEL_DELETE, new IdDto(id));
        }

        return labelOptionMapper.deleteById(id);
    }

    @Override
    public int updateStatus(String id,
                            String labelId,
                            StatusEnum status) {
        LabelOptionEntity entity = new LabelOptionEntity();
        entity.setId(id);
        entity.setStatus(status);

        if (SHOP_LABEL_ID.equals(labelId)) {
            //事件
            customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_SHOP_LABEL_UPDATE_STATUS, new IdStatusDto(id, status));
        }

        return labelOptionMapper.deleteById(id);
    }

    @Override
    public int update(String labelId,
                      LabelOptionEntity entity) {

        if (SHOP_LABEL_ID.equals(labelId)) {
            //事件
            customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_SHOP_LABEL_UPDATE_BASE, new IdDto(entity.getId()));
        }
        return labelOptionMapper.updateById(entity);
    }

    @Override
    public LabelOptionEntity getById(String id) {
        return labelOptionMapper.selectById(id);
    }

    @Override
    public LabelOptionEntity getByLabelIdAndName(String labelId,
                                                 String name) {
        Wrapper<LabelOptionEntity> queryWrapper = new LambdaQueryWrapper<LabelOptionEntity>()
                .eq(LabelOptionEntity::getLabelId, labelId)
                .eq(LabelOptionEntity::getName, name);
        return labelOptionMapper.selectOne(queryWrapper);
    }

    @Override
    public List<LabelOptionEntity> listByIdSet(Set<String> idSet) {
        Wrapper<LabelOptionEntity> queryWrapper = new LambdaQueryWrapper<LabelOptionEntity>()
                .in(LabelOptionEntity::getId, idSet);
        return labelOptionMapper.selectList(queryWrapper);
    }

    @Override
    public List<LabelOptionEntity> listByOffset(DataLabelOptionQueryListOffsetInputDto inputDto) {
        return labelOptionMapper.listByOffset(inputDto);
    }

    @Override
    public Page<LabelOptionEntity> selectPage(DataLabelOptionQueryPageInputDto inputDto) {
        IPage<LabelOptionEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return labelOptionMapper.selectPage(inputDto, page);
    }
}
