package com.machine.app.manage.data.area.business.impl;

import cn.hutool.json.JSONUtil;
import com.machine.app.manage.data.area.business.IDataAreaBusiness;
import com.machine.app.manage.data.area.controller.vo.request.DataAreaCreateRequestVo;
import com.machine.app.manage.data.area.controller.vo.request.DataAreaTreeRequestVo;
import com.machine.app.manage.data.area.controller.vo.request.DataAreaUpdateParentRequestVo;
import com.machine.app.manage.data.area.controller.vo.request.DataAreaUpdateRequestVo;
import com.machine.app.manage.data.area.controller.vo.response.DataAreaTreeExpandResponseVo;
import com.machine.app.manage.data.area.controller.vo.response.DataAreaTreeSimpleResponseVo;
import com.machine.app.manage.data.area.controller.vo.response.DataAreaDetailResponseVo;
import com.machine.client.data.area.IDataAreaClient;
import com.machine.client.data.area.dto.input.DataAreaCreateInputDto;
import com.machine.client.data.area.dto.input.DataAreaUpdateInputDto;
import com.machine.client.data.area.dto.input.DataAreaUpdateParentInputDto;
import com.machine.client.data.area.dto.output.DataAreaDetailOutputDto;
import com.machine.client.data.area.dto.output.DataAreaTreeOutputDto;
import com.machine.client.iam.user.IIamUserClient;
import com.machine.client.iam.user.dto.output.IamUserDetailOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.sdk.common.model.request.IdSetRequest;
import com.machine.sdk.common.tool.TreeUtil;
import com.machine.starter.redis.cache.RedisCacheDataArea;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DataAreaBusinessImpl implements IDataAreaBusiness {

    @Autowired
    private RedisCacheDataArea redisCacheDataArea;

    @Autowired
    private IIamUserClient userClient;

    @Autowired
    private IDataAreaClient areaClient;

    @Override
    public String create(DataAreaCreateRequestVo request) {
        DataAreaCreateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataAreaCreateInputDto.class);
        return areaClient.create(inputDto);
    }

    @Override
    public void delete(IdRequest request) {
        areaClient.delete(request);
    }

    @Override
    public void update(DataAreaUpdateRequestVo request) {
        DataAreaUpdateInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataAreaUpdateInputDto.class);
        areaClient.update(inputDto);
    }

    @Override
    public void updateParent(DataAreaUpdateParentRequestVo request) {
        DataAreaUpdateParentInputDto inputDto = JSONUtil.toBean(JSONUtil.toJsonStr(request), DataAreaUpdateParentInputDto.class);
        areaClient.updateParent(inputDto);
    }

    @Override
    public DataAreaDetailResponseVo detail(IdRequest request) {
        DataAreaDetailOutputDto outputDto = areaClient.detail(request);
        if (null == outputDto) {
            return null;
        }

        DataAreaDetailResponseVo responseVo = JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), DataAreaDetailResponseVo.class);
        { //填充修改人创建人信息
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
    public DataAreaTreeSimpleResponseVo treeSimple(DataAreaTreeRequestVo request) {
        DataAreaTreeOutputDto treeOutputDto = redisCacheDataArea.tree(request.getCountryCode());
        if (null == treeOutputDto) {
            return null;
        }
        return JSONUtil.toBean(JSONUtil.toJsonStr(treeOutputDto), DataAreaTreeSimpleResponseVo.class);
    }

    @Override
    public DataAreaTreeExpandResponseVo treeExpand(DataAreaTreeRequestVo request) {
        DataAreaTreeOutputDto treeOutputDto = redisCacheDataArea.tree(request.getCountryCode());
        if (null == treeOutputDto) {
            return null;
        }

        DataAreaTreeExpandResponseVo response = JSONUtil.toBean(JSONUtil.toJsonStr(treeOutputDto), DataAreaTreeExpandResponseVo.class);

        { //填充修改人创建人信息
            List<DataAreaTreeExpandResponseVo> voList = TreeUtil.collectAllNodes(response);
            Set<String> userIdSet = voList.stream().map(DataAreaTreeExpandResponseVo::getCreateBy).collect(Collectors.toSet());
            userIdSet.addAll(voList.stream().map(DataAreaTreeExpandResponseVo::getUpdateBy).collect(Collectors.toSet()));
            Map<String, IamUserDetailOutputDto> userSimpleDetailMap = userClient.mapByIdSet(new IdSetRequest(userIdSet));
            for (DataAreaTreeExpandResponseVo vo : voList) {
                vo.setCreateName(userSimpleDetailMap.get(vo.getCreateBy()).getName());
                vo.setUpdateName(userSimpleDetailMap.get(vo.getUpdateBy()).getName());
            }
        }

        return response;
    }
}
