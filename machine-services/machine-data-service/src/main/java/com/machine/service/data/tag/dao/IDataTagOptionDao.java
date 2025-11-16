package com.machine.service.data.tag.dao;

import com.machine.service.data.tag.dao.mapper.entity.DataTagOptionEntity;

import java.util.List;

public interface IDataTagOptionDao {

    String insert(DataTagOptionEntity entity);

    int delete(String id);

    int update(DataTagOptionEntity entity);

    DataTagOptionEntity getById(String id);

    DataTagOptionEntity getByTagIdAndCode(String tagId, String code);

    DataTagOptionEntity getByTagIdAndName(String tagId, String name);

    List<DataTagOptionEntity> selectByTagId(String tagId);

}
