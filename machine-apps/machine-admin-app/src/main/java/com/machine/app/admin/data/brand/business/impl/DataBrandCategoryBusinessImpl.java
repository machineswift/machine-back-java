package com.machine.app.admin.data.brand.business.impl;

import cn.hutool.json.JSONUtil;
import com.machine.app.admin.data.brand.business.IDataBrandCategoryBusiness;
import com.machine.app.admin.data.brand.controller.vo.request.DataBrandCategoryCreateRequestVo;
import com.machine.app.admin.data.brand.controller.vo.request.DataBrandCategoryUpdateParentRequestVo;
import com.machine.app.admin.data.brand.controller.vo.request.DataBrandCategoryUpdateRequestVo;
import com.machine.app.admin.data.brand.controller.vo.response.DataBrandCategoryDetailResponseVo;
import com.machine.app.admin.data.brand.controller.vo.response.DataBrandCategorySimpleTreeResponseVo;
import com.machine.client.data.brand.IDataBrandCategoryClient;
import com.machine.client.data.brand.dto.input.DataBrandCategoryCreateInputDto;
import com.machine.client.data.brand.dto.input.DataBrandCategoryUpdateInputDto;
import com.machine.client.data.brand.dto.input.DataBrandCategoryUpdateParentInputDto;
import com.machine.client.data.brand.dto.output.DataBrandCategoryDetailOutputDto;
import com.machine.client.data.brand.dto.output.DataBrandCategoryTreeSimpleOutputDto;
import com.machine.client.iam.biam.user.IBIamUserClient;
import com.machine.client.iam.biam.user.dto.output.BIamUserDetailOutputDto;
import com.machine.sdk.base.model.request.IdRequest;
import com.machine.sdk.base.model.request.IdSetRequest;
import com.machine.starter.redis.cache.data.RedisDataBrandCategoryCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class DataBrandCategoryBusinessImpl implements IDataBrandCategoryBusiness {

    @Autowired
    private RedisDataBrandCategoryCache brandCategoryCache;

    @Autowired
    private IDataBrandCategoryClient brandCategoryClient;

    @Autowired
    private IBIamUserClient userClient;

    @Override
    public String create(DataBrandCategoryCreateRequestVo request) {
        DataBrandCategoryCreateInputDto inputDto =
                JSONUtil.toBean(JSONUtil.toJsonStr(request), DataBrandCategoryCreateInputDto.class);
        return brandCategoryClient.create(inputDto);
    }

    @Override
    public void delete(IdRequest request) {
        brandCategoryClient.delete(request);
    }

    @Override
    public void update(DataBrandCategoryUpdateRequestVo request) {
        DataBrandCategoryUpdateInputDto inputDto =
                JSONUtil.toBean(JSONUtil.toJsonStr(request), DataBrandCategoryUpdateInputDto.class);
        brandCategoryClient.update(inputDto);
    }

    @Override
    public void updateParent(DataBrandCategoryUpdateParentRequestVo request) {
        DataBrandCategoryUpdateParentInputDto inputDto =
                JSONUtil.toBean(JSONUtil.toJsonStr(request), DataBrandCategoryUpdateParentInputDto.class);
        brandCategoryClient.updateParent(inputDto);
    }

    @Override
    public DataBrandCategoryDetailResponseVo detail(IdRequest request) {
        DataBrandCategoryDetailOutputDto outputDto = brandCategoryClient.detail(request);
        if (outputDto == null) {
            return null;
        }

        DataBrandCategoryDetailResponseVo responseVo =
                JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), DataBrandCategoryDetailResponseVo.class);
        Set<String> userIdSet = new HashSet<>();
        if (outputDto.getCreateBy() != null) {
            userIdSet.add(outputDto.getCreateBy());
        }
        if (outputDto.getUpdateBy() != null) {
            userIdSet.add(outputDto.getUpdateBy());
        }
        if (!userIdSet.isEmpty()) {
            Map<String, BIamUserDetailOutputDto> userMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
            BIamUserDetailOutputDto createUser = userMap.get(responseVo.getCreateBy());
            BIamUserDetailOutputDto updateUser = userMap.get(responseVo.getUpdateBy());
            if (createUser != null) {
                responseVo.setCreateName(createUser.getName());
            }
            if (updateUser != null) {
                responseVo.setUpdateName(updateUser.getName());
            }
        }
        return responseVo;
    }

    @Override
    public DataBrandCategorySimpleTreeResponseVo treeAllSimple() {
        DataBrandCategoryTreeSimpleOutputDto tree = brandCategoryCache.treeAllSimple();
        return JSONUtil.toBean(JSONUtil.toJsonStr(tree), DataBrandCategorySimpleTreeResponseVo.class);
    }
}
