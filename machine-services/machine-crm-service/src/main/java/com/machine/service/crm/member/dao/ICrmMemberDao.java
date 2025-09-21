package com.machine.service.crm.member.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.crm.member.dto.input.CrmMemberQueryPageInputDto;
import com.machine.service.crm.member.dao.mapper.entity.CrmMemberEntity;

public interface ICrmMemberDao {

    String insert(CrmMemberEntity entity);

    int delete(String id);

    int update(CrmMemberEntity entity);

    CrmMemberEntity getById(String id);

    CrmMemberEntity getByPhone(String phone);

    CrmMemberEntity getByEmail(String email);

    Page<CrmMemberEntity> selectPage(CrmMemberQueryPageInputDto inputDto);

}
