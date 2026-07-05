package com.machine.app.manage.data.filecenter.material.business.impl;

import cn.hutool.json.JSONUtil;
import com.machine.app.manage.data.filecenter.material.business.IDataMaterialCategoryBusiness;
import com.machine.app.manage.data.filecenter.material.controller.vo.response.DataMaterialCategoryDetailResponseVo;
import com.machine.app.manage.data.filecenter.material.controller.vo.response.DataMaterialCategorySimpleTreeResponseVo;
import com.machine.app.manage.data.filecenter.material.controller.vo.resquest.DataMaterialCategoryCreateRequestVo;
import com.machine.app.manage.data.filecenter.material.controller.vo.resquest.DataMaterialCategoryUpdateParentRequestVo;
import com.machine.app.manage.data.filecenter.material.controller.vo.resquest.DataMaterialCategoryUpdateRequestVo;
import com.machine.client.data.filecenter.material.IDataMaterialCategoryClient;
import com.machine.client.data.filecenter.material.dto.input.DataMaterialCategoryCreateInputDto;
import com.machine.client.data.filecenter.material.dto.input.DataMaterialCategoryUpdateInputDto;
import com.machine.client.data.filecenter.material.dto.input.DataMaterialCategoryUpdateParentInputDto;
import com.machine.client.data.filecenter.material.dto.output.DataMaterialCategoryDetailOutputDto;
import com.machine.client.data.filecenter.material.dto.output.DataMaterialCategoryTreeSimpleOutputDto;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.dto.output.IamUserDetailOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.starter.redis.cache.data.RedisDataMaterialCategoryCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Slf4j
@Component
public class DataMaterialCategoryBusinessImpl implements IDataMaterialCategoryBusiness {

    @Autowired
    private RedisDataMaterialCategoryCache materialCategoryCache;

    @Autowired
    private IDataMaterialCategoryClient materialCategoryClient;

    @Autowired
    private IIamUserClient userClient;

    @Override
    public String create(DataMaterialCategoryCreateRequestVo request) {
        DataMaterialCategoryCreateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataMaterialCategoryCreateInputDto.class);
        return materialCategoryClient.create(inputDto);
    }

    @Override
    public void delete(IdRequest request) {
        materialCategoryClient.delete(request);
    }

    @Override
    public void update(DataMaterialCategoryUpdateRequestVo request) {
        DataMaterialCategoryUpdateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataMaterialCategoryUpdateInputDto.class);
        materialCategoryClient.update(inputDto);
    }

    @Override
    public void updateParent(DataMaterialCategoryUpdateParentRequestVo request) {
        DataMaterialCategoryUpdateParentInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataMaterialCategoryUpdateParentInputDto.class);
        materialCategoryClient.updateParent(inputDto);
    }

    @Override
    public DataMaterialCategoryDetailResponseVo detail(IdRequest request) {
        DataMaterialCategoryDetailOutputDto outputDto = materialCategoryClient.detail(request);
        if (null == outputDto) {
            return null;
        }

        DataMaterialCategoryDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), DataMaterialCategoryDetailResponseVo.class);

        {// 填充修改人创建人信息
            Set<String> userIdSet = new HashSet<>();
            userIdSet.add(outputDto.getCreateBy());
            userIdSet.add(outputDto.getUpdateBy());
            Map<String, IamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));

            responseVo.setCreateName(userSimpleDetailMap.get(responseVo.getCreateBy()).getName());
            responseVo.setUpdateName(userSimpleDetailMap.get(responseVo.getUpdateBy()).getName());
        }

        return responseVo;
    }

    @Override
    public DataMaterialCategorySimpleTreeResponseVo treeAllSimple() {
        DataMaterialCategoryTreeSimpleOutputDto allTreeOutputDto = materialCategoryCache.treeAllSimple();
        return JSONUtil.toBean(JSONUtil.toJsonStr(allTreeOutputDto), DataMaterialCategorySimpleTreeResponseVo.class);
    }
}
