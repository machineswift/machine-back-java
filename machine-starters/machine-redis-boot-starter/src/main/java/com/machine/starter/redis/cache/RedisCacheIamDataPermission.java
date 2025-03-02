package com.machine.starter.redis.cache;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.leaf.IDataLeaf4RedisClient;
import com.machine.client.iam.permission.IIamPermissionClient;
import com.machine.client.iam.permission.dto.output.PermissionDetailOutputDto;
import com.machine.client.iam.permission.dto.output.PermissionTreeOutputDto;
import com.machine.client.iam.user.IIamUserPermissionClient;
import com.machine.client.iam.user.dto.input.DataPermission4ManageInputDto;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.envm.iam.PermissionResourceTypeEnum;
import com.machine.sdk.common.exception.iam.IamPermissionBusinessException;
import com.machine.sdk.common.model.dto.iam.DataPermissionDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.tool.TreeUtil;
import com.machine.starter.redis.function.CustomerRedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.machine.starter.redis.constant.RedisConstant.REDIS_KEY_SEPARATOR_COLON;
import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Iam.IAM_USER_APP_DATA_PERMISSION_LOCK;
import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Iam.IAM_USER_MANAGE_DATA_PERMISSION_LOCK;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserManageDataPermission.IAM_USER_MANAGE_DATA_PERMISSION_DATA;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserManageDataPermission.IAM_USER_MANAGE_DATA_PERMISSION_KEY;
import static com.machine.starter.redis.constant.RedisPrefix4IamConstant.UserSuperAppDataPermission.*;

@Slf4j
@Component
public class RedisCacheIamDataPermission {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private CustomerRedisCommands customerRedisCommands;

    @Autowired
    private RedisCacheIamPermission redisCacheIamPermission;

    @Autowired
    private IDataLeaf4RedisClient leaf4RedisClient;

    @Autowired
    private IIamPermissionClient permissionClient;

    @Autowired
    private IIamUserPermissionClient userPermissionClient;

    public DataPermissionDto dataPermission4manage() {
        return dataPermission4manage("DEFAULT");
    }

    public DataPermissionDto dataPermission4manage(String moduleCode) {
        AppContext appContext = AppContext.getContext();
        String permissionCode = appContext.getPermissionCode();
        if (StrUtil.isBlank(permissionCode)) {
            throw new IamPermissionBusinessException("iam.permission.data.nullPermissionCode", "权限编码为空");
        }

        //获取数据权限编码
        String dataPermissionCode = appContext.getDataPermissionCode();
        if (StrUtil.isBlank(dataPermissionCode)) {
            PermissionDetailOutputDto permissionDetailOutputDto = permissionClient.detailByCode(new IdRequest(permissionCode));
            if (permissionDetailOutputDto == null) {
                throw new IamPermissionBusinessException("iam.permission.data.permissionNotExists", "权限不存在");
            }

            if (PermissionResourceTypeEnum.APP == permissionDetailOutputDto.getResourceType() ||
                    PermissionResourceTypeEnum.MODULE == permissionDetailOutputDto.getResourceType()) {
                throw new IamPermissionBusinessException("iam.permission.data.permissionTypeWrong", "权限类型错误");
            }

            if (PermissionResourceTypeEnum.MENU == permissionDetailOutputDto.getResourceType()) {
                appContext.setDataPermissionCode(permissionCode);
                dataPermissionCode = permissionCode;
            } else {
                PermissionTreeOutputDto rootTreeOutputDto = redisCacheIamPermission.treeAll();
                PermissionTreeOutputDto targetNode = TreeUtil.findNode(rootTreeOutputDto, permissionDetailOutputDto.getId());
                String parentId = targetNode.getParentId();
                targetNode = TreeUtil.findNode(rootTreeOutputDto, parentId);
                appContext.setDataPermissionCode(targetNode.getCode());
                dataPermissionCode = targetNode.getCode();
            }
        }

        //获取缓存版本
        String userId = AppContext.getContext().getUserId();
        String keyCode = customerRedisCommands.get(IAM_USER_MANAGE_DATA_PERMISSION_KEY);
        if (StrUtil.isEmpty(keyCode)) {
            //缓存击穿
            RLock lock = redissonClient.getLock(IAM_USER_MANAGE_DATA_PERMISSION_LOCK);
            try {
                lock.lock();

                keyCode = customerRedisCommands.get(IAM_USER_MANAGE_DATA_PERMISSION_KEY);
                if (StrUtil.isEmpty(keyCode)) {
                    keyCode = leaf4RedisClient.iamUserAppDataPermissionPermission();
                    customerRedisCommands.set(IAM_USER_MANAGE_DATA_PERMISSION_KEY, keyCode, 6 * 60 * 60);
                }
            } finally {
                lock.unlock();
            }
        }


        //获取数据权限
        String dataPermission = customerRedisCommands.get(IAM_USER_MANAGE_DATA_PERMISSION_DATA + keyCode
                + REDIS_KEY_SEPARATOR_COLON + userId
                + REDIS_KEY_SEPARATOR_COLON + dataPermissionCode
                + REDIS_KEY_SEPARATOR_COLON + moduleCode);
        if (StrUtil.isNotEmpty(dataPermission)) {
            return JSONUtil.toBean(dataPermission, DataPermissionDto.class);
        } else {
            //缓存击穿
            RLock lock = redissonClient.getLock(IAM_USER_MANAGE_DATA_PERMISSION_LOCK
                    + REDIS_KEY_SEPARATOR_COLON + userId
                    + REDIS_KEY_SEPARATOR_COLON + dataPermissionCode
                    + REDIS_KEY_SEPARATOR_COLON + moduleCode);
            try {
                lock.lock();

                dataPermission = customerRedisCommands.get(IAM_USER_MANAGE_DATA_PERMISSION_DATA + keyCode
                        + REDIS_KEY_SEPARATOR_COLON + userId
                        + REDIS_KEY_SEPARATOR_COLON + dataPermissionCode
                        + REDIS_KEY_SEPARATOR_COLON + moduleCode);
                if (StrUtil.isNotEmpty(dataPermission)) {
                    return JSONUtil.toBean(dataPermission, DataPermissionDto.class);
                }

                //查询功能权限信息
                DataPermissionDto dataPermissionDto = userPermissionClient.dataPermission4SuperManage(
                        new DataPermission4ManageInputDto(moduleCode, dataPermissionCode));

                //用户权限信息存储到 redis
                customerRedisCommands.set(IAM_USER_MANAGE_DATA_PERMISSION_DATA + keyCode
                                + REDIS_KEY_SEPARATOR_COLON + userId
                                + REDIS_KEY_SEPARATOR_COLON + dataPermissionCode
                                + REDIS_KEY_SEPARATOR_COLON + moduleCode,
                        JSONUtil.toJsonStr(dataPermissionDto), 6 * 60 * 60 + 60);

                return dataPermissionDto;
            } finally {
                lock.unlock();
            }
        }
    }

    public DataPermissionDto dataPermission4SuperApp() {
        //获取缓存版本
        String userId = AppContext.getContext().getUserId();
        String keyCode = customerRedisCommands.get(IAM_USER_SUPER_APP_DATA_PERMISSION_KEY);
        if (StrUtil.isEmpty(keyCode)) {
            //缓存击穿
            RLock lock = redissonClient.getLock(IAM_USER_APP_DATA_PERMISSION_LOCK);
            try {
                lock.lock();

                keyCode = customerRedisCommands.get(IAM_USER_SUPER_APP_DATA_PERMISSION_KEY);
                if (StrUtil.isEmpty(keyCode)) {
                    keyCode = leaf4RedisClient.iamUserAppDataPermissionPermission();
                    customerRedisCommands.set(IAM_USER_SUPER_APP_DATA_PERMISSION_KEY, keyCode, 6 * 60 * 60);
                }
            } finally {
                lock.unlock();
            }
        }

        //获取数据权限
        String functionPermission = customerRedisCommands.get(IAM_USER_SUPER_APP_DATA_PERMISSION_DATA + keyCode + REDIS_KEY_SEPARATOR_COLON + userId);
        if (StrUtil.isNotEmpty(functionPermission)) {
            return JSONUtil.toBean(functionPermission, DataPermissionDto.class);
        } else {
            //缓存击穿
            RLock lock = redissonClient.getLock(IAM_USER_APP_DATA_PERMISSION_LOCK + REDIS_KEY_SEPARATOR_COLON + userId);
            try {
                lock.lock();

                functionPermission = customerRedisCommands.get(IAM_USER_SUPER_APP_DATA_PERMISSION_DATA + keyCode + REDIS_KEY_SEPARATOR_COLON + userId);
                if (StrUtil.isNotEmpty(functionPermission)) {
                    return JSONUtil.toBean(functionPermission, DataPermissionDto.class);
                }

                //查询功能权限信息
                DataPermissionDto dataPermissionDto = userPermissionClient.dataPermission4SuperApp();

                //用户权限信息存储到 redis
                customerRedisCommands.set(
                        IAM_USER_SUPER_APP_DATA_PERMISSION_DATA + keyCode + REDIS_KEY_SEPARATOR_COLON + userId,
                        JSONUtil.toJsonStr(dataPermissionDto),
                        6 * 60 * 60 + 60);

                return dataPermissionDto;
            } finally {
                lock.unlock();
            }
        }
    }
}
