package com.machine.service.plugin.config.handler;

import com.machine.sdk.common.envm.BaseEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(value = {BaseEnum.class})
public class EnumTypeHandler<E extends BaseEnum> extends BaseTypeHandler<E> {

    private final Class<E> type;

    public EnumTypeHandler(Class<E> type) {
        if (null == type) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, E e, JdbcType jdbcType) throws SQLException {
        preparedStatement.setObject(i, e.getName());
    }

    @Override
    public E getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        String columnValue = resultSet.getString(columnName);
        return resultSet.wasNull() ? null : this.getEnum(columnValue);
    }

    @Override
    public E getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        String columnValue = resultSet.getString(columnIndex);
        return resultSet.wasNull() ? null : this.getEnum(columnValue);
    }

    @Override
    public E getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        String columnValue = callableStatement.getString(columnIndex);
        return callableStatement.wasNull() ? null : this.getEnum(columnValue);
    }

    private E getEnum(String name) {
        for (E e : this.type.getEnumConstants()) {
            if (e.getName().equals(name)) {
                return e;
            }
        }
        throw new IllegalArgumentException("未知的枚举：" + name);
    }

}
