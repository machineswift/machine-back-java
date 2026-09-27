package com.machine.app.admin.data.brand.business.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.machine.app.admin.data.brand.business.IDataBrandBusiness;
import com.machine.app.admin.data.brand.controller.vo.request.*;
import com.machine.app.admin.data.brand.controller.vo.response.DataBrandDetailResponseVo;
import com.machine.app.admin.data.brand.controller.vo.response.DataBrandExpandListResponseVo;
import com.machine.app.admin.data.brand.controller.vo.response.DataBrandSimpleListResponseVo;
import com.machine.client.data.brand.IDataBrandClient;
import com.machine.client.data.brand.dto.input.DataBrandCreateInputDto;
import com.machine.client.data.brand.dto.input.DataBrandQueryPageInputDto;
import com.machine.client.data.brand.dto.input.DataBrandQuerySimplePageInputDto;
import com.machine.client.data.brand.dto.input.DataBrandUpdateInputDto;
import com.machine.client.data.brand.dto.input.DataBrandUpdateLogoAttachmentIdInputDto;
import com.machine.client.data.brand.dto.input.DataBrandUpdateParentIdInputDto;
import com.machine.client.data.brand.dto.input.DataBrandUpdateStatusInputDto;
import com.machine.client.data.brand.dto.output.DataBrandDetailOutputDto;
import com.machine.client.data.brand.dto.output.DataBrandListOutputDto;
import com.machine.client.data.filecenter.attachment.IDataAttachmentClient;
import com.machine.client.data.filecenter.attachment.IDataAttachmentVersionClient;
import com.machine.client.data.filecenter.attachment.IDataFileTempClient;
import com.machine.client.data.filecenter.attachment.dto.DataFileTempCreateDto;
import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentCreateInputDto;
import com.machine.client.data.filecenter.attachment.dto.input.DataAttachmentVersionUpdateInputDto;
import com.machine.client.data.filecenter.attachment.dto.output.DataFileTempDetailOutputDto;
import com.machine.client.iam.biam.user.IBIamUserClient;
import com.machine.client.iam.biam.user.dto.output.BIamUserDetailOutputDto;
import com.machine.sdk.base.envm.base.ModuleEntityEnum;
import com.machine.sdk.base.envm.base.ModuleEnum;
import com.machine.sdk.base.envm.base.audit.OperateSourceEnum;
import com.machine.sdk.base.envm.data.filecenter.DataFileTypeEnum;
import com.machine.sdk.base.envm.data.filecenter.attachment.DataAttachmentOperationTypeEnum;
import com.machine.sdk.base.exception.data.DataBusinessException;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.sdk.base.model.response.PageResponse;
import com.machine.sdk.base.model.tree.TreeNode;
import com.machine.starter.obs.operateLog.AttachmentOperationLogPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static com.machine.sdk.base.constant.CommonDataConstant.Brand.DATA_BRAND_ROOT_PARENT_ID;
import static com.machine.starter.obs.constant.ObsFileConstant.ATTACHMENT_DEFAULT_GROUP;

@Slf4j
@Component
public class DataBrandBusinessImpl implements IDataBrandBusiness {

    @Autowired
    private IBIamUserClient userClient;

    @Autowired
    private IDataBrandClient brandClient;

    @Autowired
    private IDataFileTempClient dataFileTempClient;

    @Autowired
    private IDataAttachmentClient dataAttachmentClient;

    @Autowired
    private IDataAttachmentVersionClient dataAttachmentVersionClient;

    @Autowired
    private AttachmentOperationLogPublisher attachmentOperationLogPublisher;

    @Override
    public String create(DataBrandCreateRequestVo request) {
        request.setName(request.getName().trim());

        //验证LOGO临时文件
        DataFileTempDetailOutputDto fileTempDto = dataFileTempClient.getById(new IdRequest(request.getLogoFile().getFileId()));
        if (null == fileTempDto) {
            throw new DataBusinessException("data.brand.business.create.logoFileNotExists", "LOGO文件不存在");
        }
        if (DataFileTypeEnum.IMAGE != fileTempDto.getFileType()) {
            throw new DataBusinessException("data.brand.business.create.wrongLogoFileType", "LOGO必须是图片");
        }

        DataBrandCreateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataBrandCreateInputDto.class);
        String brandId = brandClient.create(inputDto);

        //创建LOGO附件
        DataAttachmentCreateInputDto attachmentCreateInputDto = new DataAttachmentCreateInputDto();
        attachmentCreateInputDto.setEntity(ModuleEntityEnum.DATA_BRAND);
        attachmentCreateInputDto.setEntityId(brandId);
        attachmentCreateInputDto.setAttachmentGroup(ATTACHMENT_DEFAULT_GROUP);
        attachmentCreateInputDto.setExpireTime(Long.MAX_VALUE);
        attachmentCreateInputDto.setChangeDesc("新增品牌");
        attachmentCreateInputDto.setFileTempList(List.of(request.getLogoFile()));
        String attachmentId = dataAttachmentClient.create(attachmentCreateInputDto);

        //记录附件操作日志
        attachmentOperationLogPublisher.publish(attachmentId, DataAttachmentOperationTypeEnum.UPLOAD,
                OperateSourceEnum.ADMIN_APP, ModuleEnum.DATA);

        //回写品牌LOGO附件Id
        brandClient.updateLogoAttachmentId(new DataBrandUpdateLogoAttachmentIdInputDto(brandId, attachmentId));
        return brandId;
    }

    @Override
    public void delete(IdRequest request) {
        brandClient.delete(request);

    }

    @Override
    public void update(DataBrandUpdateRequestVo request) {
        request.setName(request.getName().trim());

        DataBrandDetailOutputDto brandOutputDto = brandClient.detail(new IdRequest(request.getId()));
        if (null == brandOutputDto) {
            throw new DataBusinessException("data.brand.business.update.brandNotExists", "品牌不存在");
        }

        //验证LOGO临时文件
        DataFileTempCreateDto logoFile = request.getLogoFile();
        if (null != logoFile) {
            DataFileTempDetailOutputDto fileTempDto = dataFileTempClient.getById(new IdRequest(logoFile.getFileId()));
            if (null == fileTempDto) {
                throw new DataBusinessException("data.brand.business.update.logoFileNotExists", "LOGO文件不存在");
            }
            if (DataFileTypeEnum.IMAGE != fileTempDto.getFileType()) {
                throw new DataBusinessException("data.brand.business.update.wrongLogoFileType", "LOGO必须是图片");
            }
        }

        DataBrandUpdateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataBrandUpdateInputDto.class);
        brandClient.update(inputDto);

        if (null == logoFile) {
            return;
        }

        //修改LOGO附件
        String attachmentId = brandOutputDto.getLogoAttachmentId();
        if (StrUtil.isNotBlank(attachmentId)) {
            DataAttachmentVersionUpdateInputDto versionUpdateInputDto = new DataAttachmentVersionUpdateInputDto();
            versionUpdateInputDto.setEntity(ModuleEntityEnum.DATA_BRAND);
            versionUpdateInputDto.setEntityId(request.getId());
            versionUpdateInputDto.setAttachmentGroup(ATTACHMENT_DEFAULT_GROUP);
            versionUpdateInputDto.setChangeDesc("修改品牌");
            versionUpdateInputDto.setFileTempList(List.of(logoFile));
            dataAttachmentVersionClient.update(versionUpdateInputDto);

            //记录附件操作日志
            attachmentOperationLogPublisher.publish(attachmentId, DataAttachmentOperationTypeEnum.UPDATE,
                    OperateSourceEnum.ADMIN_APP, ModuleEnum.DATA);
        }else {
            //历史数据没有LOGO附件，首次上传：新建附件并回写
            DataAttachmentCreateInputDto attachmentCreateInputDto = new DataAttachmentCreateInputDto();
            attachmentCreateInputDto.setEntity(ModuleEntityEnum.DATA_BRAND);
            attachmentCreateInputDto.setEntityId(request.getId());
            attachmentCreateInputDto.setAttachmentGroup(ATTACHMENT_DEFAULT_GROUP);
            attachmentCreateInputDto.setExpireTime(Long.MAX_VALUE);
            attachmentCreateInputDto.setChangeDesc("修改品牌");
            attachmentCreateInputDto.setFileTempList(List.of(logoFile));
            String newAttachmentId = dataAttachmentClient.create(attachmentCreateInputDto);

            //记录附件操作日志
            attachmentOperationLogPublisher.publish(newAttachmentId, DataAttachmentOperationTypeEnum.UPLOAD,
                    OperateSourceEnum.ADMIN_APP, ModuleEnum.DATA);

            //回写品牌LOGO附件Id
            brandClient.updateLogoAttachmentId(new DataBrandUpdateLogoAttachmentIdInputDto(request.getId(), newAttachmentId));
        }
    }

    @Override
    public void updateStatus(DataBrandUpdateStatusRequestVo request) {
        DataBrandUpdateStatusInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataBrandUpdateStatusInputDto.class);
        brandClient.updateStatus(inputDto);
    }

    @Override
    public void updateParent(DataBrandUpdateParentIdRequestVo request) {
        if (StrUtil.isBlank(request.getParentId())) {
            throw new DataBusinessException("data.brand.business.updateParent.parentIdIsNull", "父品牌ID不能为空");
        }

        DataBrandUpdateParentIdInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataBrandUpdateParentIdInputDto.class);
        brandClient.updateParent(inputDto);
    }

    @Override
    public DataBrandDetailResponseVo detail(IdRequest request) {
        DataBrandDetailOutputDto outputDto = brandClient.detail(request);
        if (null == outputDto) {
            return null;
        }

        DataBrandDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), DataBrandDetailResponseVo.class);

        //填充品牌全称（父品牌-子品牌）
        responseVo.setFullName(getFullName(outputDto));

        { //填充修改人创建人信息
            Set<String> userIdSet = new HashSet<>();
            userIdSet.add(outputDto.getCreateBy());
            userIdSet.add(outputDto.getUpdateBy());
            Map<String, BIamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
            responseVo.setCreateName(userSimpleDetailMap.get(responseVo.getCreateBy()).getName());
            responseVo.setUpdateName(userSimpleDetailMap.get(responseVo.getUpdateBy()).getName());
        }

        return responseVo;
    }

    @Override
    public PageResponse<DataBrandSimpleListResponseVo> childrenSimple(DataBrandQueryChildrenRequestVo request) {
        if (StrUtil.isBlank(request.getParentId())) {
            throw new DataBusinessException("data.brand.business.childrenSimple.parentIdIsNull", "父品牌ID不能为空");
        }

        DataBrandQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataBrandQueryPageInputDto.class);
        return buildChildrenSimple(brandClient.childrenPage(inputDto));
    }

    @Override
    public PageResponse<DataBrandExpandListResponseVo> childrenExpand(DataBrandQueryChildrenRequestVo request) {
        if (StrUtil.isBlank(request.getParentId())) {
            throw new DataBusinessException("data.brand.business.childrenExpand.parentIdIsNull", "父品牌ID不能为空");
        }

        DataBrandQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataBrandQueryPageInputDto.class);
        return buildChildrenExpand(brandClient.childrenPage(inputDto));
    }

    @Override
    public PageResponse<DataBrandSimpleListResponseVo> pageSimple(DataBrandQuerySimplePageRequestVo request) {
        DataBrandQuerySimplePageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataBrandQuerySimplePageInputDto.class);
        return buildSimplePage(brandClient.simplePage(inputDto));
    }

    @Override
    public PageResponse<DataBrandExpandListResponseVo> pageExpand(DataBrandQueryPageRequestVo request) {
        DataBrandQueryPageInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataBrandQueryPageInputDto.class);
        return buildPageExpandTree(brandClient.page(inputDto));
    }

    /**
     * page_simple（组件弹窗快速选择）：按 父品牌 / 关键字（name 或 code 模糊） / 状态 分页，扁平返回
     * 不补齐父链、不组树，也不与管理菜单的 page_expand（buildPageExpandTree）共用任何组装逻辑
     */
    private PageResponse<DataBrandSimpleListResponseVo> buildSimplePage(PageResponse<DataBrandListOutputDto> pageOutput) {
        if (CollectionUtil.isEmpty(pageOutput.getRecords())) {
            return new PageResponse<>(pageOutput.getCurrent(), pageOutput.getSize(), pageOutput.getTotal());
        }

        List<DataBrandSimpleListResponseVo> voList = JSONUtil.toList(JSONUtil.toJsonStr(pageOutput.getRecords()), DataBrandSimpleListResponseVo.class);
        fillSimpleHasChildren(voList);

        return new PageResponse<>(pageOutput.getCurrent(), pageOutput.getSize(), pageOutput.getTotal(), voList);
    }

    /**
     * page_expand：分页命中的品牌补齐父链后组装成树（相同节点合并）
     */
    private PageResponse<DataBrandExpandListResponseVo> buildPageExpandTree(PageResponse<DataBrandListOutputDto> pageOutput) {
        if (CollectionUtil.isEmpty(pageOutput.getRecords())) {
            return new PageResponse<>(pageOutput.getCurrent(), pageOutput.getSize(), pageOutput.getTotal());
        }

        List<DataBrandListOutputDto> nodeList = listWithParentChain(pageOutput.getRecords());
        List<DataBrandExpandListResponseVo> voList = JSONUtil.toList(JSONUtil.toJsonStr(nodeList), DataBrandExpandListResponseVo.class);
        fillExpandUserNames(voList);
        fillExpandHasChildren(voList);

        //最外层按创建时间倒序
        List<DataBrandExpandListResponseVo> treeList = buildTree(voList);
        treeList.sort((a, b) -> compareCreateTimeDesc(a.getCreateTime(), b.getCreateTime()));

        return new PageResponse<>(pageOutput.getCurrent(), pageOutput.getSize(), pageOutput.getTotal(), treeList);
    }

    /**
     * children_simple：子品牌分页（sort 大的在前），不组装树
     */
    private PageResponse<DataBrandSimpleListResponseVo> buildChildrenSimple(PageResponse<DataBrandListOutputDto> pageOutput) {
        if (CollectionUtil.isEmpty(pageOutput.getRecords())) {
            return new PageResponse<>(pageOutput.getCurrent(), pageOutput.getSize(), pageOutput.getTotal());
        }

        List<DataBrandSimpleListResponseVo> voList = JSONUtil.toList(JSONUtil.toJsonStr(pageOutput.getRecords()), DataBrandSimpleListResponseVo.class);
        fillSimpleHasChildren(voList);

        return new PageResponse<>(pageOutput.getCurrent(), pageOutput.getSize(), pageOutput.getTotal(), voList);
    }

    /**
     * children_expand：子品牌分页（sort 大的在前），不组装树
     */
    private PageResponse<DataBrandExpandListResponseVo> buildChildrenExpand(PageResponse<DataBrandListOutputDto> pageOutput) {
        if (CollectionUtil.isEmpty(pageOutput.getRecords())) {
            return new PageResponse<>(pageOutput.getCurrent(), pageOutput.getSize(), pageOutput.getTotal());
        }

        List<DataBrandExpandListResponseVo> voList = JSONUtil.toList(JSONUtil.toJsonStr(pageOutput.getRecords()), DataBrandExpandListResponseVo.class);
        fillExpandUserNames(voList);
        fillExpandHasChildren(voList);

        return new PageResponse<>(pageOutput.getCurrent(), pageOutput.getSize(), pageOutput.getTotal(), voList);
    }

    /**
     * 分页命中的品牌补齐父链：逐层向上查询到根节点，相同节点只保留一份
     */
    private List<DataBrandListOutputDto> listWithParentChain(List<DataBrandListOutputDto> matchedList) {
        Map<String, DataBrandListOutputDto> nodeMap = new LinkedHashMap<>();
        for (DataBrandListOutputDto matched : matchedList) {
            nodeMap.putIfAbsent(matched.getId(), matched);
        }

        Set<String> parentIdSet = listUnknownParentIdSet(nodeMap);
        while (CollectionUtil.isNotEmpty(parentIdSet)) {
            Map<String, DataBrandDetailOutputDto> parentMap = brandClient.mapByIdSet(new IdSetRequest(parentIdSet));

            int sizeBefore = nodeMap.size();
            for (DataBrandDetailOutputDto parent : parentMap.values()) {
                nodeMap.putIfAbsent(parent.getId(), JSONUtil.toBean(JSONUtil.toJsonStr(parent), DataBrandListOutputDto.class));
            }

            //父品牌查不到（脏数据）时跳出，避免死循环
            if (sizeBefore == nodeMap.size()) {
                break;
            }

            parentIdSet = listUnknownParentIdSet(nodeMap);
        }

        return new ArrayList<>(nodeMap.values());
    }

    /**
     * 收集还未加载的父品牌ID（根节点除外）
     */
    private Set<String> listUnknownParentIdSet(Map<String, DataBrandListOutputDto> nodeMap) {
        return nodeMap.values().stream()
                .map(DataBrandListOutputDto::getParentId)
                .filter(parentId -> StrUtil.isNotBlank(parentId) && !DATA_BRAND_ROOT_PARENT_ID.equals(parentId))
                .filter(parentId -> !nodeMap.containsKey(parentId))
                .collect(Collectors.toSet());
    }

    /**
     * 组装品牌树：相同节点合并，子节点按 sort 大的在前
     */
    private <T extends TreeNode<T>> List<T> buildTree(List<T> nodeList) {
        Map<String, T> nodeMap = new LinkedHashMap<>();
        for (T node : nodeList) {
            nodeMap.putIfAbsent(node.getId(), node);
        }

        List<T> rootList = new ArrayList<>();
        for (T node : nodeMap.values()) {
            T parentNode = StrUtil.isBlank(node.getParentId()) ? null : nodeMap.get(node.getParentId());
            if (null == parentNode) {
                //父节点不在结果集中，作为最外层节点
                rootList.add(node);
                continue;
            }

            parentNode.getChildren().add(node);
        }

        rootList.forEach(this::sortChildrenBySortDesc);
        return rootList;
    }

    /**
     * 子节点按 sort 大的在前（递归）
     */
    private <T extends TreeNode<T>> void sortChildrenBySortDesc(T node) {
        List<T> children = node.getChildren();
        if (CollectionUtil.isEmpty(children)) {
            return;
        }

        children.sort((a, b) -> compareSortDesc(a.getSort(), b.getSort()));
        for (T child : children) {
            sortChildrenBySortDesc(child);
        }
    }

    private int compareSortDesc(Long sortA, Long sortB) {
        if (null == sortA || null == sortB) {
            return 0;
        }
        return sortB.compareTo(sortA);
    }

    private int compareCreateTimeDesc(Long createTimeA, Long createTimeB) {
        if (null == createTimeA || null == createTimeB) {
            return 0;
        }
        return createTimeB.compareTo(createTimeA);
    }

    /**
     * 填充是否有子品牌（简单列表）
     */
    private void fillSimpleHasChildren(List<DataBrandSimpleListResponseVo> records) {
        Set<String> brandIdSet = records.stream().map(DataBrandSimpleListResponseVo::getId).collect(Collectors.toSet());
        Set<String> hasChildrenIdSet = brandClient.listHasChildrenIdSet(new IdSetRequest(brandIdSet));
        for (DataBrandSimpleListResponseVo vo : records) {
            vo.setHasChildren(CollectionUtil.isNotEmpty(hasChildrenIdSet) && hasChildrenIdSet.contains(vo.getId()));
        }
    }

    /**
     * 填充是否有子品牌（展开列表）
     */
    private void fillExpandHasChildren(List<DataBrandExpandListResponseVo> records) {
        Set<String> brandIdSet = records.stream().map(DataBrandExpandListResponseVo::getId).collect(Collectors.toSet());
        Set<String> hasChildrenIdSet = brandClient.listHasChildrenIdSet(new IdSetRequest(brandIdSet));
        for (DataBrandExpandListResponseVo vo : records) {
            vo.setHasChildren(CollectionUtil.isNotEmpty(hasChildrenIdSet) && hasChildrenIdSet.contains(vo.getId()));
        }
    }

    /**
     * 填充创建人、修改人姓名（展开列表）
     */
    private void fillExpandUserNames(List<DataBrandExpandListResponseVo> records) {
        Set<String> userIdSet = records.stream().map(DataBrandExpandListResponseVo::getCreateBy).collect(Collectors.toSet());
        userIdSet.addAll(records.stream().map(DataBrandExpandListResponseVo::getUpdateBy).collect(Collectors.toSet()));
        Map<String, BIamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
        for (DataBrandExpandListResponseVo vo : records) {
            vo.setCreateName(userSimpleDetailMap.get(vo.getCreateBy()).getName());
            vo.setUpdateName(userSimpleDetailMap.get(vo.getUpdateBy()).getName());
        }
    }

    /**
     * 获取品牌全称（父品牌-子品牌，不包含根节点）
     */
    private String getFullName(DataBrandDetailOutputDto outputDto) {
        List<DataBrandDetailOutputDto> ancestorList = brandClient.listAncestorById(new IdRequest(outputDto.getId()));
        if (CollectionUtil.isEmpty(ancestorList)) {
            return outputDto.getName();
        }

        return ancestorList.stream()
                .map(DataBrandDetailOutputDto::getName)
                .collect(Collectors.joining("-"));
    }
}
