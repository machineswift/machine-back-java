package com.machine.app.manage.data.tag.business.impl;

import cn.hutool.json.JSONUtil;
import com.machine.app.manage.data.tag.business.IDataTagCategoryBusiness;
import com.machine.app.manage.data.tag.controller.vo.request.DataTagCategoryCreateRequestVo;
import com.machine.app.manage.data.tag.controller.vo.request.DataTagCategoryTreeRequestVo;
import com.machine.app.manage.data.tag.controller.vo.request.DataTagCategoryUpdateParentRequestVo;
import com.machine.app.manage.data.tag.controller.vo.request.DataTagCategoryUpdateRequestVo;
import com.machine.app.manage.data.tag.controller.vo.response.DataTagCategoryDetailResponseVo;
import com.machine.client.data.tag.IDataTagCategoryClient;
import com.machine.client.data.tag.dto.input.DataTagCategoryCreateInputDto;
import com.machine.client.data.tag.dto.input.DataTagCategoryUpdateInputDto;
import com.machine.client.data.tag.dto.input.DataTagCategoryUpdateParentInputDto;
import com.machine.client.data.tag.dto.output.DataTagCategoryDetailOutputDto;
import com.machine.client.data.tag.dto.output.DataTagCategoryTreeSimpleOutputDto;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.dto.output.IamUserDetailOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.starter.redis.cache.data.RedisCacheDataTagCategory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class DataTagCategoryBusinessImpl implements IDataTagCategoryBusiness {

    @Autowired
    private RedisCacheDataTagCategory dataTagCategoryCache;

    @Autowired
    private IIamUserClient userClient;

    @Autowired
    private IDataTagCategoryClient tagCategoryClient;

    @Override
    public String create(DataTagCategoryCreateRequestVo request) {
        DataTagCategoryCreateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataTagCategoryCreateInputDto.class);
        return tagCategoryClient.create(inputDto);
    }

    @Override
    public void delete(IdRequest request) {
        tagCategoryClient.delete(request);
    }

    @Override
    public void update(DataTagCategoryUpdateRequestVo request) {
        DataTagCategoryUpdateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataTagCategoryUpdateInputDto.class);
        tagCategoryClient.update(inputDto);
    }

    @Override
    public void updateParent(DataTagCategoryUpdateParentRequestVo request) {
        DataTagCategoryUpdateParentInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataTagCategoryUpdateParentInputDto.class);
        tagCategoryClient.updateParent(inputDto);
    }

    @Override
    public DataTagCategoryDetailResponseVo detail(IdRequest request) {
        DataTagCategoryDetailOutputDto outputDto = tagCategoryClient.detail(request);
        DataTagCategoryDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), DataTagCategoryDetailResponseVo.class);

        {//填充修改人创建人信息
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
    public DataTagCategoryTreeSimpleOutputDto treeSimple(DataTagCategoryTreeRequestVo request) {
        return dataTagCategoryCache.treeAllSimple(request.getType());
    }

}
