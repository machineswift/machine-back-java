package com.machine.service.crm.member.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.crm.member.dto.input.CrmMemberQueryPageInputDto;
import com.machine.service.crm.member.dao.mapper.entity.CrmMemberEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CrmMemberMapper extends BaseMapper<CrmMemberEntity> {

    Page<CrmMemberEntity> selectPage(@Param("inputDto") CrmMemberQueryPageInputDto inputDto,
                                   IPage<CrmMemberEntity> page);

}
