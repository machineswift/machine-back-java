package com.machine.service.iam.biam.user.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.machine.sdk.base.envm.biam.user.BIamUserConfigKeyEnum;
import com.machine.service.iam.biam.user.dao.IBIamUserConfigDao;
import com.machine.service.iam.biam.user.dao.mapper.BIamUserConfigMapper;
import com.machine.service.iam.biam.user.dao.mapper.entity.BIamUserConfigEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BIamUserConfigDaoImpl implements IBIamUserConfigDao {

    @Autowired
    private BIamUserConfigMapper userConfigMapper;

    @Override
    public void insertOrUpdate(String userId,
            BIamUserConfigKeyEnum configKey,
            String configValue) {
        BIamUserConfigEntity exist = selectByUserIdAndKey(userId, configKey);
        if (exist != null) {
            exist.setConfigValue(configValue);
            userConfigMapper.updateById(exist);
            return;
        }

        BIamUserConfigEntity entity = new BIamUserConfigEntity();
        entity.setUserId(userId);
        entity.setConfigKey(configKey);
        entity.setConfigValue(configValue);
        userConfigMapper.insert(entity);
    }

    @Override
    public BIamUserConfigEntity selectByUserIdAndKey(String userId,
                                                     BIamUserConfigKeyEnum configKey) {
        Wrapper<BIamUserConfigEntity> wrapper = new LambdaQueryWrapper<BIamUserConfigEntity>()
                .eq(BIamUserConfigEntity::getUserId, userId)
                .eq(BIamUserConfigEntity::getConfigKey, configKey);
        return userConfigMapper.selectOne(wrapper);
    }
}
