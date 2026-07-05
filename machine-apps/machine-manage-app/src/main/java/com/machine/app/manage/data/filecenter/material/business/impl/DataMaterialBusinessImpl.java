package com.machine.app.manage.data.filecenter.material.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.manage.data.filecenter.material.business.IDataMaterialBusiness;
import com.machine.app.manage.data.filecenter.material.controller.vo.response.DataMaterialDetailResponseVo;
import com.machine.app.manage.data.filecenter.material.controller.vo.response.DataMaterialExpandListResponseVo;
import com.machine.app.manage.data.filecenter.material.controller.vo.resquest.DataMaterialCreateRequestVo;
import com.machine.app.manage.data.filecenter.material.controller.vo.resquest.DataMaterialQueryPageRequestVo;
import com.machine.app.manage.data.filecenter.material.controller.vo.resquest.DataMaterialUpdateCategoryRequestVo;
import com.machine.app.manage.data.filecenter.material.controller.vo.resquest.DataMaterialUpdateRequestVo;
import com.machine.client.data.filecenter.attachment.IDataAttachmentClient;
import com.machine.client.data.filecenter.attachment.IDataAttachmentOperationLogClient;
import com.machine.client.data.filecenter.attachment.IDataAttachmentVersionClient;
import com.machine.client.data.filecenter.attachment.IDataFileTempClient;
import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentCreateInputDto;
import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentOperationLogCreateInputDto;
import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentVersionUpdateInputDto;
import com.machine.client.data.filecenter.attachment.dto.output.DataAttachmentDetailOutputDto;
import com.machine.client.data.filecenter.attachment.dto.output.DataAttachmentWithCurrentFileInfoOutputDto;
import com.machine.client.data.filecenter.attachment.dto.output.DataFileTempDetailOutputDto;
import com.machine.client.data.filecenter.material.IDataMaterialCategoryRelationClient;
import com.machine.client.data.filecenter.material.IDataMaterialClient;
import com.machine.client.data.filecenter.material.dto.input.*;
import com.machine.client.data.filecenter.material.dto.output.DataMaterialCategoryRelationOutputDto;
import com.machine.client.data.filecenter.material.dto.output.DataMaterialDetailOutputDto;
import com.machine.client.data.filecenter.material.dto.output.DataMaterialListOutputDto;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.dto.output.IamUserDetailOutputDto;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.data.filecenter.DataFileTypeEnum;
import com.machine.sdk.base.envm.data.filecenter.attachment.DataAttachmentOperationResultEnum;
import com.machine.sdk.base.envm.data.filecenter.attachment.DataAttachmentOperationTypeEnum;
import com.machine.sdk.base.exception.data.DataBusinessException;
import com.machine.sdk.base.model.dto.base.ClientEnvironmentInfo;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.model.response.PageResponse;
import com.machine.sdk.base.tool.ClientEnvironmentUtil;
import com.machine.starter.obs.service.ObsFileService;
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
    private ObsFileService obsFileService;

    @Autowired
    private IIamUserClient userClient;

    @Autowired
    private IDataFileTempClient dataFileTempClient;

    @Autowired
    private IDataMaterialClient dataMaterialClient;

    @Autowired
    private IDataAttachmentClient dataAttachmentClient;

    @Autowired
    private IDataAttachmentVersionClient dataAttachmentVersionClient;

    @Autowired
    private IDataAttachmentOperationLogClient dataAttachmentOperationLogClient;

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
        ClientEnvironmentInfo environmentInfo = ClientEnvironmentUtil.buildInfo(servletRequest);
        DataAttachmentDetailOutputDto attachmentDetail = dataAttachmentClient.getById(new IdRequest(attachmentId));
        DataAttachmentOperationLogCreateInputDto logCreateInputDto = new DataAttachmentOperationLogCreateInputDto();
        logCreateInputDto.setAttachmentId(attachmentId);
        logCreateInputDto.setVersionId(attachmentDetail.getCurrentVersionId());
        logCreateInputDto.setOperationType(DataAttachmentOperationTypeEnum.UPLOAD);
        logCreateInputDto.setOperationResult(DataAttachmentOperationResultEnum.SUCCESS);
        logCreateInputDto.setIpAddress(environmentInfo.getIpAddress());
        logCreateInputDto.setPlatform(environmentInfo.getPlatform());
        logCreateInputDto.setUserAgent(environmentInfo.getUserAgent());
        dataAttachmentOperationLogClient.create(logCreateInputDto);

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
        if (null != request.getFileTemp()) {
            DataAttachmentVersionUpdateInputDto versionUpdateInputDto = new DataAttachmentVersionUpdateInputDto();
            versionUpdateInputDto.setEntity(ModuleEntityEnum.DATA_MATERIAL);
            versionUpdateInputDto.setEntityId(request.getId());
            versionUpdateInputDto.setAttachmentGroup(ATTACHMENT_DEFAULT_GROUP);
            versionUpdateInputDto.setChangeDesc("修改素材");
            versionUpdateInputDto.setFileTempList(List.of(request.getFileTemp()));
            dataAttachmentVersionClient.update(versionUpdateInputDto);

            // 记录操作日志
            ClientEnvironmentInfo environmentInfo = ClientEnvironmentUtil.buildInfo(servletRequest);
            DataAttachmentDetailOutputDto attachmentDetail = dataAttachmentClient.getById(new IdRequest(materialOutputDto.getAttachmentId()));

            DataAttachmentOperationLogCreateInputDto logCreateInputDto = new DataAttachmentOperationLogCreateInputDto();
            logCreateInputDto.setAttachmentId(materialOutputDto.getAttachmentId());
            logCreateInputDto.setVersionId(attachmentDetail.getCurrentVersionId());
            logCreateInputDto.setOperationType(DataAttachmentOperationTypeEnum.UPDATE);
            logCreateInputDto.setOperationResult(DataAttachmentOperationResultEnum.SUCCESS);
            logCreateInputDto.setIpAddress(environmentInfo.getIpAddress());
            logCreateInputDto.setPlatform(environmentInfo.getPlatform());
            logCreateInputDto.setUserAgent(environmentInfo.getUserAgent());
            dataAttachmentOperationLogClient.create(logCreateInputDto);
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
        Map<String, IamUserDetailOutputDto> userMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
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
        Map<String, IamUserDetailOutputDto> userMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
        for (DataMaterialExpandListResponseVo vo : records) {
            vo.setCreateName(userMap.get(vo.getCreateBy()).getName());
            vo.setUpdateName(userMap.get(vo.getUpdateBy()).getName());
        }
    }

    @Override
    public String getDownloadUrl(IdRequest request) {
        DataMaterialDetailOutputDto materialDetail = dataMaterialClient.getById(request);
        if (materialDetail == null) {
            throw new DataBusinessException("data.material.business.getPresignedDownloadUrl.notFound", "素材不存在");
        }
        String attachmentId = materialDetail.getAttachmentId();
        if (StrUtil.isBlank(attachmentId)) {
            throw new DataBusinessException("data.material.business.getPresignedDownloadUrl.attachmentNotFound", "附件ID不存在");
        }

        // 查询附件文件信息
        DataAttachmentWithCurrentFileInfoOutputDto attachment = dataAttachmentClient.getCurrentByAttachmentId(new IdRequest(attachmentId));
        if (attachment == null) {
            throw new DataBusinessException("data.material.business.getPresignedDownloadUrl.attachmentNotFound", "附件不存在");
        }
        if (CollectionUtil.isEmpty(attachment.getFileInfoList())) {
            throw new DataBusinessException("data.material.business.getPresignedDownloadUrl.fileNotFound", "附件文件不存在");
        }

        DataAttachmentWithCurrentFileInfoOutputDto.DataFileInfo dataFileInfo = attachment.getFileInfoList().getFirst();

        // 生成预签名 URL
        // 图片/视频 → inline 预览；其他类型（PDF 等）→ 强制下载
        boolean forceDownload = materialDetail.getFileType() != DataFileTypeEnum.IMAGE
                && materialDetail.getFileType() != DataFileTypeEnum.VIDEO;
        String presignedUrl = obsFileService.generatePresignedUrl(dataFileInfo.getFileInfo(), 300, forceDownload);
        if (StrUtil.isBlank(presignedUrl)) {
            throw new DataBusinessException("data.material.business.getPresignedDownloadUrl.urlGenerateFailed", "生成访问地址失败");
        }

        return presignedUrl;
    }

}
