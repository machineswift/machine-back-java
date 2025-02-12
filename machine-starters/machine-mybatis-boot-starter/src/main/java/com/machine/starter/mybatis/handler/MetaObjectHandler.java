package com.machine.starter.mybatis.handler;

import com.machine.sdk.common.context.AppContext;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class MetaObjectHandler implements com.baomidou.mybatisplus.core.handlers.MetaObjectHandler {
    private static final String CREATE_BY_FIELD_NAME = "createBy";
    private static final String CREATE_TIME_FIELD_NAME = "createTime";
    private static final String UPDATE_BY_FIELD_NAME = "updateBy";
    private static final String UPDATE_TIME_FIELD_NAME = "updateTime";

    private static final Set<String> EXCLUDED_ENTITIES = Set.of(
            "com.machine.service.data.shop.dao.mapper.entity.ShopBigDataBusinessEntity"
    );

    /**
     * 使用mp实现添加操作,这个方法会执行
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        String entityClassName = getEntityClassName(metaObject);
        if (EXCLUDED_ENTITIES.contains(entityClassName)) {
            return;
        }

        //根据名称设置属性值
        Long currentTimeMillis = System.currentTimeMillis();
        String userId= AppContext.getContext().getUserId();

        this.setFieldValByName(CREATE_TIME_FIELD_NAME, currentTimeMillis, metaObject);
        this.setFieldValByName(UPDATE_TIME_FIELD_NAME, currentTimeMillis, metaObject);

        this.setFieldValByName(CREATE_BY_FIELD_NAME, userId, metaObject);
        this.setFieldValByName(UPDATE_BY_FIELD_NAME, userId, metaObject);
    }

    /**
     * 使用mp实现修改操作,这个方法会执行
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        String entityClassName = getEntityClassName(metaObject);
        if (EXCLUDED_ENTITIES.contains(entityClassName)) {
            return;
        }

        Long currentTimeMillis = System.currentTimeMillis();
        String userId= AppContext.getContext().getUserId();

        this.setFieldValByName(UPDATE_TIME_FIELD_NAME, currentTimeMillis, metaObject);
        this.setFieldValByName(UPDATE_BY_FIELD_NAME, userId, metaObject);
    }

    /**
     * 获取当前操作的实体类名
     */
    private String getEntityClassName(MetaObject metaObject) {
        Object originalObject = metaObject.getOriginalObject();
        return originalObject.getClass().getName();
    }
}
