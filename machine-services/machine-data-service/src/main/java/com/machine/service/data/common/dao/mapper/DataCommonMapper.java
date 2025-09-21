package com.machine.service.data.common.dao.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

@Mapper
public interface DataCommonMapper {

    Date getUnixTimestamp();

}
