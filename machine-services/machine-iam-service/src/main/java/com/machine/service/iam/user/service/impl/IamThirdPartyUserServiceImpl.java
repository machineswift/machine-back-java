package com.machine.service.iam.user.service.impl;

import cn.hutool.json.JSONUtil;
import com.machine.client.iam.user.dto.input.IamThirdPartyUserBindInputDto;
import com.machine.client.iam.user.dto.input.IamThirdPartyUserCreateInputDto;
import com.machine.service.iam.user.dao.IamThirdPartyUserDao;
import com.machine.service.iam.user.dao.IamUserThirdPartyUserRelationDao;
import com.machine.service.iam.user.dao.mapper.entity.IamThirdPartyUserEntity;
import com.machine.service.iam.user.dao.mapper.entity.IamUserThirdPartyUserRelationEntity;
import com.machine.service.iam.user.service.IIamThirdPartyUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class IamThirdPartyUserServiceImpl implements IIamThirdPartyUserService {

    @Autowired
    private IamThirdPartyUserDao thirdPartyUserDao;

    @Autowired
    private IamUserThirdPartyUserRelationDao userThirdPartyUserRelationDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(IamThirdPartyUserCreateInputDto inputDto) {
        IamThirdPartyUserEntity insertEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), IamThirdPartyUserEntity.class);
        return thirdPartyUserDao.insert(insertEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bind(IamThirdPartyUserBindInputDto inputDto) {
        IamUserThirdPartyUserRelationEntity insertEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), IamUserThirdPartyUserRelationEntity.class);
        userThirdPartyUserRelationDao.insert(insertEntity);
    }
}
