package com.machine.service.data.tag.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.machine.service.data.tag.dao.mapper.entity.DataTagCategoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DataTagCategoryMapper extends BaseMapper<DataTagCategoryEntity> {

    /**
     * 根据父ID查询子分类列表
     */
    List<DataTagCategoryEntity> selectByParentId(@Param("parentId") String parentId);

    /**
     * 根据编码查询分类
     */
    DataTagCategoryEntity selectByCode(@Param("code") String code);

    /**
     * 查询所有分类（树形结构）
     */
    List<DataTagCategoryEntity> selectAll();
}

