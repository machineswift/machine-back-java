package com.machine.app.admin.data.filecenter.material.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.admin.data.filecenter.material.business.IDataMaterialBusiness;
import com.machine.app.admin.data.filecenter.material.controller.vo.response.DataMaterialDetailResponseVo;
import com.machine.app.admin.data.filecenter.material.controller.vo.response.DataMaterialExpandListResponseVo;
import com.machine.app.admin.data.filecenter.material.controller.vo.resquest.DataMaterialCreateRequestVo;
import com.machine.app.admin.data.filecenter.material.controller.vo.resquest.DataMaterialQueryPageRequestVo;
import com.machine.app.admin.data.filecenter.material.controller.vo.resquest.DataMaterialUpdateCategoryRequestVo;
import com.machine.app.admin.data.filecenter.material.controller.vo.resquest.DataMaterialUpdateRequestVo;
import com.machine.client.data.filecenter.attachment.IDataAttachmentClient;
import com.machine.client.data.filecenter.attachment.IDataAttachmentVersionClient;
import com.machine.client.data.filecenter.attachment.IDataFileTempClient;
import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentCreateInputDto;
import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentVersionUpdateInputDto;
import com.machine.client.data.filecenter.attachment.dto.output.DataFileTempDetailOutputDto;
import com.machine.client.data.filecenter.material.IDataMaterialCategoryRelationClient;
import com.machine.client.data.filecenter.material.IDataMaterialClient;
import com.machine.client.data.filecenter.material.dto.input.*;
import com.machine.client.data.filecenter.material.dto.output.DataMaterialCategoryRelationOutputDto;
import com.machine.client.data.filecenter.material.dto.output.DataMaterialDetailOutputDto;
import com.machine.client.data.filecenter.material.dto.output.DataMaterialListOutputDto;
import com.machine.client.iam.biam.user.IBIamUserClient;
import com.machine.client.iam.biam.user.dto.output.BIamUserDetailOutputDto;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.base.ModuleEnum;
import com.machine.sdk.base.envm.base.audit.OperateSourceEnum;
import com.machine.sdk.base.envm.data.filecenter.attachment.DataAttachmentOperationTypeEnum;
import com.machine.sdk.base.exception.data.DataBusinessException;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.model.response.PageResponse;
import com.machine.starter.obs.operateLog.AttachmentOperationLogPublisher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.machine.starter.obs.constant.ObsFileConstant.ATTACHMENT_DEFAULT_GROUP;

@Slf4j
@Component
public class DataMaterialBusinessImpl implements IDataMaterialBusiness {

    @Autowired
    private AttachmentOperationLogPublisher attachmentOperationLogPublisher;

    @Autowired
    private IBIamUserClient userClient;

    @Autowired
    private IDataFileTempClient dataFileTempClient;

    @Autowired
    private IDataMaterialClient dataMaterialClient;

    @Autowired
    private IDataAttachmentClient dataAttachmentClient;

    @Autowired
    private IDataAttachmentVersionClient dataAttachmentVersionClient;

    @Autowired
    private IDataMaterialCategoryRelationClient materialCategoryRelationClient;


    @Override
    public String create(DataMaterialCreateRequestVo request,
                         HttpServletRequest servletRequest) {
        // 校验附件
        DataFileTempDetailOutputDto fileTempDto = dataFileTempClient.getById(new IdRequest(request.getFileTemp().getFileId()));
        if (null == fileTempDto) {
            throw new DataBusinessException("data.material.business.create.fileNotExists", "附件不存在");
        }
        if (request.getFileType() != fileTempDto.getFileType()) {
            throw new DataBusinessException("data.material.business.create.wrongFileType", "附件类型错误");
        }

        DataMaterialCreateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataMaterialCreateInputDto.class);
        String materialId = dataMaterialClient.create(inputDto);

        // 创建附件
        DataAttachmentCreateInputDto attachmentCreateInputDto = new DataAttachmentCreateInputDto();
        attachmentCreateInputDto.setEntity(ModuleEntityEnum.DATA_MATERIAL);
        attachmentCreateInputDto.setEntityId(materialId);
        attachmentCreateInputDto.setAttachmentGroup(ATTACHMENT_DEFAULT_GROUP);
        attachmentCreateInputDto.setExpireTime(Long.MAX_VALUE);
        attachmentCreateInputDto.setChangeDesc("新增素材");
        attachmentCreateInputDto.setFileTempList(List.of(request.getFileTemp()));
        String attachmentId = dataAttachmentClient.create(attachmentCreateInputDto);

        // 记录操作日志
        attachmentOperationLogPublisher.publish(attachmentId, DataAttachmentOperationTypeEnum.UPLOAD,
                OperateSourceEnum.ADMIN_APP, ModuleEnum.DATA);
        // 修改关联的附件id
        dataMaterialClient.updateAttachmentId(new DataMaterialUpdateAttachmentIdInputDto(materialId, attachmentId));
        return materialId;
    }

    @Override
    public void update(DataMaterialUpdateRequestVo request,
                       HttpServletRequest servletRequest) {
        DataMaterialDetailOutputDto materialOutputDto = dataMaterialClient.getById(new IdRequest(request.getId()));
        if (null != request.getFileTemp()) {
            // 校验附件
            DataFileTempDetailOutputDto fileTempDto = dataFileTempClient.getById(new IdRequest(request.getFileTemp().getFileId()));
            if (null == fileTempDto) {
                throw new DataBusinessException("data.material.business.update.fileNotExists", "附件不存在");
            }
            if (materialOutputDto.getFileType() != fileTempDto.getFileType()) {
                throw new DataBusinessException("data.material.business.update.wrongFileType", "附件类型错误");
            }
        }

        DataMaterialUpdateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataMaterialUpdateInputDto.class);
        dataMaterialClient.update(inputDto);

        // 修改附件
        String attachmentId = materialOutputDto.getAttachmentId();
        if (null != request.getFileTemp()) {
            DataAttachmentVersionUpdateInputDto versionUpdateInputDto = new DataAttachmentVersionUpdateInputDto();
            versionUpdateInputDto.setEntity(ModuleEntityEnum.DATA_MATERIAL);
            versionUpdateInputDto.setEntityId(request.getId());
            versionUpdateInputDto.setAttachmentGroup(ATTACHMENT_DEFAULT_GROUP);
            versionUpdateInputDto.setChangeDesc("修改素材");
            versionUpdateInputDto.setFileTempList(List.of(request.getFileTemp()));
            dataAttachmentVersionClient.update(versionUpdateInputDto);

            // 记录操作日志
            attachmentOperationLogPublisher.publish(attachmentId,DataAttachmentOperationTypeEnum.UPDATE,
                    OperateSourceEnum.ADMIN_APP, ModuleEnum.DATA);
        }
    }

    @Override
    public void updateCategory(DataMaterialUpdateCategoryRequestVo request) {
        DataMaterialUpdateCategoryInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataMaterialUpdateCategoryInputDto.class);
        dataMaterialClient.updateCategory(inputDto);
    }

    @Override
    public DataMaterialDetailResponseVo detail(IdRequest request) {
        DataMaterialDetailOutputDto outputDto = dataMaterialClient.getById(request);
        if (outputDto == null) {
            return null;
        }

        DataMaterialDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), DataMaterialDetailResponseVo.class);
        fillDetailCategoryRelations(responseVo);
        fillDetailUserNames(outputDto, responseVo);
        return responseVo;
    }

    @Override
    public PageResponse<DataMaterialExpandListResponseVo> pageExpand(DataMaterialQueryPageRequestVo request) {
        DataMaterialQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataMaterialQueryPageInputDto.class);
        if (inputDto.getContainVirtualNode() == null) {
            inputDto.setContainVirtualNode(false);
        }
        PageResponse<DataMaterialListOutputDto> pageOutput = dataMaterialClient.selectPage(inputDto);

        if (CollectionUtil.isEmpty(pageOutput.getRecords())) {
            return new PageResponse<>(pageOutput.getCurrent(), pageOutput.getSize(), pageOutput.getTotal());
        }

        PageResponse<DataMaterialExpandListResponseVo> pageResponse = new PageResponse<>(
                pageOutput.getCurrent(),
                pageOutput.getSize(),
                pageOutput.getTotal(),
                JSONUtil.toList(JSONUtil.toJsonStr(pageOutput.getRecords()), DataMaterialExpandListResponseVo.class));
        fillPageCategoryRelations(pageResponse.getRecords());
        fillPageUserNames(pageResponse.getRecords());
        return pageResponse;
    }

    /**
     * 填充详情页的素材分类 ID 集合
     */
    private void fillDetailCategoryRelations(DataMaterialDetailResponseVo responseVo) {
        List<DataMaterialCategoryRelationOutputDto> relationList =
                materialCategoryRelationClient.listByMaterialId(new IdRequest(responseVo.getId()));
        if (CollectionUtil.isNotEmpty(relationList)) {
            Set<String> categoryIdSet = relationList.stream()
                    .map(DataMaterialCategoryRelationOutputDto::getCategoryId)
                    .collect(Collectors.toCollection(LinkedHashSet::new));
            responseVo.setCategoryIdSet(categoryIdSet);
        }
    }

    /**
     * 填充详情页的创建人、修改人姓名（空安全）
     */
    private void fillDetailUserNames(DataMaterialDetailOutputDto outputDto,
                                     DataMaterialDetailResponseVo responseVo) {
        Set<String> userIdSet = new HashSet<>();
        userIdSet.add(outputDto.getCreateBy());
        userIdSet.add(outputDto.getUpdateBy());
        Map<String, BIamUserDetailOutputDto> userMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
        responseVo.setCreateName(userMap.get(responseVo.getCreateBy()).getName());
        responseVo.setUpdateName(userMap.get(responseVo.getUpdateBy()).getName());
    }

    /**
     * 填充分页列表的素材分类 ID 集合
     */
    private void fillPageCategoryRelations(List<DataMaterialExpandListResponseVo> records) {
        Set<String> materialIdSet = records.stream().map(DataMaterialExpandListResponseVo::getId).collect(Collectors.toSet());
        List<DataMaterialCategoryRelationOutputDto> relationList =
                materialCategoryRelationClient.listByMaterialIdSet(new IdSetRequest(materialIdSet));
        Map<String, Set<String>> materialIdToCategoryIds = relationList.stream()
                .collect(Collectors.groupingBy(DataMaterialCategoryRelationOutputDto::getMaterialId,
                        Collectors.mapping(DataMaterialCategoryRelationOutputDto::getCategoryId, Collectors.toCollection(LinkedHashSet::new))));
        for (DataMaterialExpandListResponseVo vo : records) {
            vo.setCategoryIdSet(materialIdToCategoryIds.getOrDefault(vo.getId(), Set.of()));
        }
    }

    /**
     * 填充分页列表的创建人、修改人姓名（空安全）
     */
    private void fillPageUserNames(List<DataMaterialExpandListResponseVo> records) {
        Set<String> userIdSet = new HashSet<>();
        for (DataMaterialExpandListResponseVo vo : records) {
            if (vo.getCreateBy() != null) userIdSet.add(vo.getCreateBy());
            if (vo.getUpdateBy() != null) userIdSet.add(vo.getUpdateBy());
        }
        Map<String, BIamUserDetailOutputDto> userMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
        for (DataMaterialExpandListResponseVo vo : records) {
            vo.setCreateName(userMap.get(vo.getCreateBy()).getName());
            vo.setUpdateName(userMap.get(vo.getUpdateBy()).getName());
        }
    }

}
