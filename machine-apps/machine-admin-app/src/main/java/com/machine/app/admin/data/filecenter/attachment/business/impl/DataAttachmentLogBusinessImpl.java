package com.machine.app.admin.data.filecenter.attachment.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.admin.data.filecenter.attachment.business.IDataAttachmentLogBusiness;
import com.machine.app.admin.data.filecenter.attachment.controller.vo.request.DataAttachmentLogQueryPageRequestVo;
import com.machine.app.admin.data.filecenter.attachment.controller.vo.response.DataAttachmentLogDetailResponseVo;
import com.machine.app.admin.data.filecenter.attachment.controller.vo.response.DataAttachmentLogExpandListResponseVo;
import com.machine.client.data.filecenter.attachment.IDataAttachmentOperationLogClient;
import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentOperationLogQueryPageInputDto;
import com.machine.client.data.filecenter.attachment.dto.output.DataAttachmentOperationLogDetailOutputDto;
import com.machine.client.data.filecenter.attachment.dto.output.DataAttachmentOperationLogListOutputDto;
import com.machine.client.iam.biam.user.IBIamUserClient;
import com.machine.client.iam.biam.user.dto.output.BIamUserDetailOutputDto;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.model.dto.IdNameDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.model.response.PageResponse;
import com.machine.starter.obs.validate.ModuleEntityValidatorRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class DataAttachmentLogBusinessImpl implements IDataAttachmentLogBusiness {

    @Autowired
    private ModuleEntityValidatorRegistry moduleEntityValidatorRegistry;

    @Autowired
    private IBIamUserClient userClient;

    @Autowired
    private IDataAttachmentOperationLogClient attachmentOperationLogClient;

    @Override
    public DataAttachmentLogDetailResponseVo detail(IdRequest request) {
        DataAttachmentOperationLogDetailOutputDto outputDto = attachmentOperationLogClient.detail(request);
        if (null == outputDto) {
            return null;
        }

        DataAttachmentLogDetailResponseVo responseVo =
                JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), DataAttachmentLogDetailResponseVo.class);

        { // 填充操作人姓名
            Set<String> userIdSet = new HashSet<>();
            if (StrUtil.isNotBlank(outputDto.getCreateBy())) {
                userIdSet.add(outputDto.getCreateBy());
            }
            Map<String, BIamUserDetailOutputDto> userMap = mapUser(userIdSet);
            responseVo.setCreateName(userName(userMap, responseVo.getCreateBy()));
        }

        { // 填充实体信息
            ModuleEntityEnum moduleEntity = responseVo.getModuleEntity();
            String moduleEntityId = responseVo.getModuleEntityId();

            IdNameDto idNameDto = moduleEntityValidatorRegistry.getNameInfo(moduleEntity, moduleEntityId);
            if (null != idNameDto) {
                responseVo.setModuleEntityName(idNameDto.getName());
            }
        }
        return responseVo;
    }

    @Override
    public PageResponse<DataAttachmentLogExpandListResponseVo> pageExpand(DataAttachmentLogQueryPageRequestVo request) {
        DataAttachmentOperationLogQueryPageInputDto inputDto =
                JSONUtil.toBean(JSONUtil.toJsonStr(request), DataAttachmentOperationLogQueryPageInputDto.class);
        PageResponse<DataAttachmentOperationLogListOutputDto> page = attachmentOperationLogClient.selectPage(inputDto);

        PageResponse<DataAttachmentLogExpandListResponseVo> pageResponse =
                new PageResponse<>(page.getCurrent(), page.getSize(), page.getTotal());
        if (CollectionUtil.isEmpty(page.getRecords())) {
            return pageResponse;
        }
        pageResponse.setRecords(JSONUtil.toList(JSONUtil.toJsonStr(page.getRecords()), DataAttachmentLogExpandListResponseVo.class));

        { // 填充操作人姓名
            Set<String> userIdSet = new HashSet<>();
            for (DataAttachmentLogExpandListResponseVo responseVo : pageResponse.getRecords()) {
                if (StrUtil.isNotBlank(responseVo.getCreateBy())) {
                    userIdSet.add(responseVo.getCreateBy());
                }
            }
            Map<String, BIamUserDetailOutputDto> userMap = mapUser(userIdSet);
            for (DataAttachmentLogExpandListResponseVo responseVo : pageResponse.getRecords()) {
                responseVo.setCreateName(userName(userMap, responseVo.getCreateBy()));
            }
        }

        { // 填充实体信息
            Map<String, IdNameDto> entityInfoMap = new HashMap<>(pageResponse.getRecords().size());
            for (DataAttachmentLogExpandListResponseVo responseVo : pageResponse.getRecords()) {
                ModuleEntityEnum moduleEntity = responseVo.getModuleEntity();
                String moduleEntityId = responseVo.getModuleEntityId();
                String key = moduleEntity.getName() + moduleEntityId;

                if (!entityInfoMap.containsKey(key)) {
                    IdNameDto idNameDto = moduleEntityValidatorRegistry.getNameInfo(moduleEntity, moduleEntityId);
                    entityInfoMap.put(key, idNameDto);
                }

                IdNameDto idNameDto = entityInfoMap.get(key);
                if (null != idNameDto) {
                    responseVo.setModuleEntityName(idNameDto.getName());
                }
            }
        }

        return pageResponse;
    }

    private Map<String, BIamUserDetailOutputDto> mapUser(Set<String> userIdSet) {
        if (CollectionUtil.isEmpty(userIdSet)) {
            return Map.of();
        }
        return userClient.mapByIdSet(new IdSetRequest(userIdSet));
    }

    private String userName(Map<String, BIamUserDetailOutputDto> userMap, String userId) {
        BIamUserDetailOutputDto user = userMap.get(userId);
        return null == user ? null : user.getName();
    }
}
