package com.machine.service.iam.biam.user.dao;

import com.machine.sdk.base.envm.biam.user.BIamUserConfigKeyEnum;
import com.machine.service.iam.biam.user.dao.mapper.entity.BIamUserConfigEntity;

public interface IBIamUserConfigDao {

    void insertOrUpdate(String userId,
            BIamUserConfigKeyEnum configKey,
            String configValue);

    BIamUserConfigEntity selectByUserIdAndKey(String userId,
                                              BIamUserConfigKeyEnum configKey);
}
