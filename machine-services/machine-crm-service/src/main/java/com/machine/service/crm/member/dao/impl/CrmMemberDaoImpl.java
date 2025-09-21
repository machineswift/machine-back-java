package com.machine.service.crm.member.dao.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.machine.client.crm.member.dto.input.CrmMemberQueryPageInputDto;
import com.machine.service.crm.member.dao.ICrmMemberDao;
import com.machine.service.crm.member.dao.mapper.CrmMemberMapper;
import com.machine.service.crm.member.dao.mapper.entity.CrmMemberEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CrmMemberDaoImpl implements ICrmMemberDao {

    @Autowired
    private CrmMemberMapper memberMapper;

    @Override
    public String insert(CrmMemberEntity entity) {
        memberMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public int delete(String id) {
        return memberMapper.deleteById(id);
    }

    @Override
    public int update(CrmMemberEntity entity) {
        return memberMapper.updateById(entity);
    }

    @Override
    public CrmMemberEntity getById(String id) {
        return memberMapper.selectById(id);
    }

    @Override
    public CrmMemberEntity getByPhone(String phone) {
        Wrapper<CrmMemberEntity> queryWrapper = new LambdaQueryWrapper<CrmMemberEntity>()
                .eq(CrmMemberEntity::getPhone, phone);
        return memberMapper.selectOne(queryWrapper);
    }

    @Override
    public CrmMemberEntity getByEmail(String email) {
        Wrapper<CrmMemberEntity> queryWrapper = new LambdaQueryWrapper<CrmMemberEntity>()
                .eq(CrmMemberEntity::getEmail, email);
        return memberMapper.selectOne(queryWrapper);
    }

    @Override
    public Page<CrmMemberEntity> selectPage(CrmMemberQueryPageInputDto inputDto) {
        IPage<CrmMemberEntity> page = new Page<>(inputDto.getCurrent(), inputDto.getSize());
        return memberMapper.selectPage(inputDto, page);
    }
}
