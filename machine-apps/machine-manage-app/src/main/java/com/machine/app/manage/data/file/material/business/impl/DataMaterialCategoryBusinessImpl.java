package com.machine.app.manage.data.file.material.business.impl;

import cn.hutool.json.JSONUtil;
import com.machine.app.manage.data.file.material.business.IDataMaterialCategoryBusiness;
import com.machine.app.manage.data.file.material.controller.vo.response.DataMaterialCategoryDetailResponseVo;
import com.machine.app.manage.data.file.material.controller.vo.response.DataMaterialCategorySimpleTreeResponseVo;
import com.machine.app.manage.data.file.material.controller.vo.resquest.DataMaterialCategoryCreateRequestVo;
import com.machine.app.manage.data.file.material.controller.vo.resquest.DataMaterialCategoryUpdateParentRequestVo;
import com.machine.app.manage.data.file.material.controller.vo.resquest.DataMaterialCategoryUpdateRequestVo;
import com.machine.client.data.file.material.IDataMaterialCategoryClient;
import com.machine.client.data.file.material.dto.input.DataMaterialCategoryCreateInputDto;
import com.machine.client.data.file.material.dto.input.DataMaterialCategoryUpdateInputDto;
import com.machine.client.data.file.material.dto.input.DataMaterialCategoryUpdateParentInputDto;
import com.machine.client.data.file.material.dto.output.DataMaterialCategoryDetailOutputDto;
import com.machine.client.data.file.material.dto.output.DataMaterialCategoryTreeSimpleOutputDto;
import com.machine.sdk.common.model.request.IdRequest;
import com.machine.starter.redis.cache.data.RedisCacheDataMaterialCategory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class DataMaterialCategoryBusinessImpl implements IDataMaterialCategoryBusiness {

    @Autowired
    private RedisCacheDataMaterialCategory materialCategoryCache;

    @Autowired
    private IDataMaterialCategoryClient materialCategoryClient;

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

        return JSONUtil.toBean(JSONUtil.toJsonStr(outputDto), DataMaterialCategoryDetailResponseVo.class);
    }

    @Override
    public DataMaterialCategorySimpleTreeResponseVo treeAllSimple() {
        DataMaterialCategoryTreeSimpleOutputDto allTreeOutputDto = materialCategoryCache.treeAllSimple();
        return JSONUtil.toBean(JSONUtil.toJsonStr(allTreeOutputDto), DataMaterialCategorySimpleTreeResponseVo.class);
    }
}
