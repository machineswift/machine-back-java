package com.machine.service.iam.auth.dao.impl;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.machine.client.iam.auth.dto.input.OAuth2AuthorizationDto;
import com.machine.service.iam.auth.dao.IOauth2AuthorizationDao;
import com.machine.service.iam.auth.dao.mapper.Oauth2AuthorizationConsentMapper;
import com.machine.service.iam.auth.dao.mapper.Oauth2AuthorizationMapper;
import com.machine.service.iam.auth.dao.mapper.entity.Oauth2AuthorizationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Repository
public class Oauth2AuthorizationDaoImpl implements IOauth2AuthorizationDao {

    @Autowired
    private Oauth2AuthorizationMapper authorizationMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(Oauth2AuthorizationEntity entity) {
        return authorizationMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(Oauth2AuthorizationEntity entity) {
        return authorizationMapper.updateById(entity);
    }

    @Override
    public Oauth2AuthorizationEntity findById(String id) {
        return authorizationMapper.selectById(id);
    }

    @Override
    public Oauth2AuthorizationEntity findByClientId(String clientId) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(String id) {
        authorizationMapper.deleteById(id);
    }

    @Override
    public Oauth2AuthorizationEntity findByToken(Oauth2AuthorizationEntity entity) {
         try {
            Map<String, Object> map = entityToMap(entity);
            QueryWrapper<Oauth2AuthorizationEntity> queryWrapper = new QueryWrapper<>();

            if (map.size() == 1) {
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    queryWrapper.eq(entry.getKey(), entry.getValue());
                }
            } else if (map.size() > 1) {
                boolean first = true;
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    if (!first) {
                        queryWrapper.or();
                    }
                    queryWrapper.eq(entry.getKey(), entry.getValue());
                    first = false;
                }
            }

            return authorizationMapper.selectOne(queryWrapper);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to convert entity to map", e);
        }
    }

     public static Map<String, Object> entityToMap(Object entity) throws IllegalAccessException {
         Map<String, Object> map = new HashMap<>();
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(entity);
            if (value != null) {
                TableField tableField = field.getAnnotation(TableField.class);
                String key = tableField != null && !tableField.value().isEmpty() ? tableField.value() : field.getName();
                map.put(key, value);
            }
        }
        return map;
    }
}
