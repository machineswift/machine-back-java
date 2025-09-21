package com.machine.service.data.label.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.sdk.common.envm.StatusEnum;
import com.machine.sdk.common.model.dto.IdDto;
import com.machine.sdk.common.model.dto.IdStatusDto;
import com.machine.sdk.self.envm.EventTypeEnum;
import com.machine.service.data.label.dao.IDataLabelOptionDao;
import com.machine.service.data.label.dao.mapper.DataLabelOptionMapper;
import com.machine.service.data.label.dao.mapper.entity.DataLabelOptionEntity;
import com.machine.starter.mq.function.CustomerStreamBridge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

import static com.machine.sdk.common.constant.CommonDataConstant.Shop.SHOP_LABEL_ID;

@Repository
public class DataLabelOptionDaoImpl implements IDataLabelOptionDao {

    @Autowired
    private CustomerStreamBridge customerStreamBridge;

    @Autowired
    private DataLabelOptionMapper labelOptionMapper;

    @Override
    public String create(DataLabelOptionEntity entity) {
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
        DataLabelOptionEntity entity = new DataLabelOptionEntity();
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
                      DataLabelOptionEntity entity) {

        if (SHOP_LABEL_ID.equals(labelId)) {
            //事件
            customerStreamBridge.sendWebHookEvent(EventTypeEnum.DATA_SHOP_LABEL_UPDATE_BASE, new IdDto(entity.getId()));
        }
        return labelOptionMapper.updateById(entity);
    }

    @Override
    public DataLabelOptionEntity getById(String id) {
        return labelOptionMapper.selectById(id);
    }

    @Override
    public DataLabelOptionEntity getByLabelIdAndName(String labelId,
                                                     String name) {
        Wrapper<DataLabelOptionEntity> queryWrapper = new LambdaQueryWrapper<DataLabelOptionEntity>()
                .eq(DataLabelOptionEntity::getLabelId, labelId)
                .eq(DataLabelOptionEntity::getName, name);
        return labelOptionMapper.selectOne(queryWrapper);
    }

    @Override
    public List<DataLabelOptionEntity> listByIdSet(Set<String> idSet) {
        Wrapper<DataLabelOptionEntity> queryWrapper = new LambdaQueryWrapper<DataLabelOptionEntity>()
                .in(DataLabelOptionEntity::getId, idSet);
        return labelOptionMapper.selectList(queryWrapper);
    }
}
