package com.machine.service.iam.biam.user.service.impl;

import cn.hutool.json.JSONUtil;
import com.machine.client.iam.biam.user.dto.input.BIamThirdPartyUserBindInputDto;
import com.machine.client.iam.biam.user.dto.input.BIamThirdPartyUserCreateInputDto;
import com.machine.service.iam.biam.user.dao.IBIamThirdPartyUserDao;
import com.machine.service.iam.biam.user.dao.IBIamUserThirdPartyUserRelationDao;
import com.machine.service.iam.biam.user.dao.mapper.entity.BIamThirdPartyUserEntity;
import com.machine.service.iam.biam.user.dao.mapper.entity.BIamUserThirdPartyUserRelationEntity;
import com.machine.service.iam.biam.user.service.IBIamThirdPartyUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class BIamThirdPartyUserServiceImpl implements IBIamThirdPartyUserService {

    @Autowired
    private IBIamThirdPartyUserDao thirdPartyUserDao;

    @Autowired
    private IBIamUserThirdPartyUserRelationDao userThirdPartyUserRelationDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(BIamThirdPartyUserCreateInputDto inputDto) {
        BIamThirdPartyUserEntity insertEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), BIamThirdPartyUserEntity.class);
        return thirdPartyUserDao.insert(insertEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bind(BIamThirdPartyUserBindInputDto inputDto) {
        BIamUserThirdPartyUserRelationEntity insertEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), BIamUserThirdPartyUserRelationEntity.class);
        userThirdPartyUserRelationDao.insert(insertEntity);
    }
}
