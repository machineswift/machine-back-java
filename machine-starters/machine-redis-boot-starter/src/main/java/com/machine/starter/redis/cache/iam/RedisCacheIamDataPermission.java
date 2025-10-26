package com.machine.starter.redis.cache.iam;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.client.data.leaf.IDataLeaf4RedisClient;
import com.machine.client.iam.permission.IIamPermissionClient;
import com.machine.client.iam.permission.dto.output.IamPermissionDetailOutputDto;
import com.machine.client.iam.permission.dto.output.IamPermissionTreeOutputDto;
import com.machine.client.iam.user.IIamUserPermissionClient;
import com.machine.client.iam.user.dto.input.IamDataPermission4ManageInputDto;
import com.machine.sdk.common.context.AppContext;
import com.machine.sdk.common.envm.iam.IamPermissionResourceTypeEnum;
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

import static com.machine.sdk.common.constant.CommonConstant.REDIS_KEY_SEPARATOR_COLON;
import static com.machine.sdk.common.constant.CommonIamConstant.DATA_PERMISSION_DEFAULT_FUNCTION_CODE;
import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Iam.LOCK_IAM_USER_APP_DATA_PERMISSION;
import static com.machine.starter.redis.constant.RedisLockPrefixConstant.Iam.LOCK_IAM_USER_MANAGE_DATA_PERMISSION;
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
        return dataPermission4manage(DATA_PERMISSION_DEFAULT_FUNCTION_CODE);
    }

    public DataPermissionDto dataPermission4manage(String functionCode) {
        AppContext appContext = AppContext.getContext();
        String permissionCode = appContext.getPermissionCode();
        if (StrUtil.isBlank(permissionCode)) {
            throw new IamPermissionBusinessException("iam.permission.data.nullPermissionCode", "权限编码为空");
        }

        //获取数据权限编码
        String dataPermissionCode = appContext.getDataPermissionCode();
        if (StrUtil.isBlank(dataPermissionCode)) {
            IamPermissionDetailOutputDto permissionDetailOutputDto = permissionClient.detailByCode(new IdRequest(permissionCode));
            if (permissionDetailOutputDto == null) {
                throw new IamPermissionBusinessException("iam.permission.data.permissionNotExists", "权限不存在");
            }

            if (IamPermissionResourceTypeEnum.APP == permissionDetailOutputDto.getResourceType() ||
                    IamPermissionResourceTypeEnum.DIRECTORY == permissionDetailOutputDto.getResourceType()) {
                throw new IamPermissionBusinessException("iam.permission.data.permissionTypeWrong", "权限类型错误");
            }

            if (IamPermissionResourceTypeEnum.MENU == permissionDetailOutputDto.getResourceType()) {
                appContext.setDataPermissionCode(permissionCode);
                dataPermissionCode = permissionCode;
            } else {
                IamPermissionTreeOutputDto rootTreeOutputDto = redisCacheIamPermission.treeAll();
                IamPermissionTreeOutputDto targetNode = TreeUtil.findNode(rootTreeOutputDto, permissionDetailOutputDto.getId());
                while (targetNode != null && targetNode.getResourceType() != IamPermissionResourceTypeEnum.MENU) {
                    String parentId = targetNode.getParentId();
                    targetNode = TreeUtil.findNode(rootTreeOutputDto, parentId);
                }
                if (targetNode != null) {
                    appContext.setDataPermissionCode(targetNode.getCode());
                    dataPermissionCode = targetNode.getCode();
                } else {
                    throw new IamPermissionBusinessException("iam.permission.data.permissionCodeWrong", "权限编码不存在");
                }
            }
        }

        //获取缓存版本
        String userId = AppContext.getContext().getUserId();
        String keyCode = customerRedisCommands.get(IAM_USER_MANAGE_DATA_PERMISSION_KEY);
        if (StrUtil.isEmpty(keyCode)) {
            //缓存击穿
            RLock lock = redissonClient.getLock(LOCK_IAM_USER_MANAGE_DATA_PERMISSION);
            try {
                lock.lock();

                keyCode = customerRedisCommands.get(IAM_USER_MANAGE_DATA_PERMISSION_KEY);
                if (StrUtil.isEmpty(keyCode)) {
                    keyCode = leaf4RedisClient.iamUserDataPermissionPermission();
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
                + REDIS_KEY_SEPARATOR_COLON + functionCode);
        if (StrUtil.isNotEmpty(dataPermission)) {
            return JSONUtil.toBean(dataPermission, DataPermissionDto.class);
        } else {
            //缓存击穿
            RLock lock = redissonClient.getLock(LOCK_IAM_USER_MANAGE_DATA_PERMISSION
                    + REDIS_KEY_SEPARATOR_COLON + userId
                    + REDIS_KEY_SEPARATOR_COLON + dataPermissionCode
                    + REDIS_KEY_SEPARATOR_COLON + functionCode);
            try {
                lock.lock();

                dataPermission = customerRedisCommands.get(IAM_USER_MANAGE_DATA_PERMISSION_DATA + keyCode
                        + REDIS_KEY_SEPARATOR_COLON + userId
                        + REDIS_KEY_SEPARATOR_COLON + dataPermissionCode
                        + REDIS_KEY_SEPARATOR_COLON + functionCode);
                if (StrUtil.isNotEmpty(dataPermission)) {
                    return JSONUtil.toBean(dataPermission, DataPermissionDto.class);
                }

                //查询功能权限信息
                DataPermissionDto dataPermissionDto = userPermissionClient.dataPermission4Manage(
                        new IamDataPermission4ManageInputDto(permissionCode, functionCode, dataPermissionCode));

                //用户权限信息存储到 redis
                customerRedisCommands.set(IAM_USER_MANAGE_DATA_PERMISSION_DATA + keyCode
                                + REDIS_KEY_SEPARATOR_COLON + userId
                                + REDIS_KEY_SEPARATOR_COLON + dataPermissionCode
                                + REDIS_KEY_SEPARATOR_COLON + functionCode,
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
            RLock lock = redissonClient.getLock(LOCK_IAM_USER_APP_DATA_PERMISSION);
            try {
                lock.lock();

                keyCode = customerRedisCommands.get(IAM_USER_SUPER_APP_DATA_PERMISSION_KEY);
                if (StrUtil.isEmpty(keyCode)) {
                    keyCode = leaf4RedisClient.iamUserDataPermissionPermission();
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
            RLock lock = redissonClient.getLock(LOCK_IAM_USER_APP_DATA_PERMISSION + REDIS_KEY_SEPARATOR_COLON + userId);
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
