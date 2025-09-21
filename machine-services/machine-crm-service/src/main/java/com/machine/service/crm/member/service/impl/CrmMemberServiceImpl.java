package com.machine.service.crm.member.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.crm.member.dto.input.CrmMemberCreateInputDto;
import com.machine.client.crm.member.dto.input.CrmMemberQueryPageInputDto;
import com.machine.client.crm.member.dto.input.CrmMemberUpdateInputDto;
import com.machine.client.crm.member.dto.output.CrmMemberDetailOutputDto;
import com.machine.client.crm.member.dto.output.CrmMemberListOutputDto;
import com.machine.client.data.leaf.IDataLeaf4CrmCodeClient;
import com.machine.sdk.common.exception.crm.CrmBusinessException;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.service.crm.member.dao.ICrmMemberDao;
import com.machine.service.crm.member.dao.mapper.entity.CrmMemberEntity;
import com.machine.service.crm.member.service.ICrmMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class CrmMemberServiceImpl implements ICrmMemberService {

    @Autowired
    private ICrmMemberDao memberDao;

    @Autowired
    private IDataLeaf4CrmCodeClient leaf4CrmCodeClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(CrmMemberCreateInputDto inputDto) {
        //验证手机号是否存在
        CrmMemberEntity entityByPhone = memberDao.getByPhone(inputDto.getPhone());
        if (null != entityByPhone) {
            throw new CrmBusinessException("crm.member.service.create.phoneAlreadyExists", "会员手机号已经存在");
        }

        //验证邮箱是否存在
        if (StrUtil.isBlank(inputDto.getEmail())) {
            CrmMemberEntity entityByEmail = memberDao.getByEmail(inputDto.getEmail());
            if (null != entityByEmail) {
                throw new CrmBusinessException("crm.member.service.create.emailAlreadyExists", "会员邮箱已经存在");
            }
        }

        CrmMemberEntity insertEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), CrmMemberEntity.class, true);
        insertEntity.setCode(leaf4CrmCodeClient.memberCode());
        return memberDao.insert(insertEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(IdRequest request) {
        return memberDao.delete(request.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(CrmMemberUpdateInputDto inputDto) {
        CrmMemberEntity updateEntity = JSONUtil.toBean(JSONUtil.toJsonStr(inputDto), CrmMemberEntity.class, true);
        return memberDao.update(updateEntity);
    }

    @Override
    public CrmMemberDetailOutputDto detail(IdRequest request) {
        CrmMemberEntity entityById = memberDao.getById(request.getId());
        if (null == entityById) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(entityById), CrmMemberDetailOutputDto.class);
    }

    @Override
    public Page<CrmMemberListOutputDto> selectPage(CrmMemberQueryPageInputDto inputDto) {
        Page<CrmMemberEntity> page = memberDao.selectPage(inputDto);
        Page<CrmMemberListOutputDto> pageResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        pageResult.setRecords(JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), CrmMemberListOutputDto.class));
        return pageResult;
    }
}
