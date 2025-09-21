package com.machine.app.openapi.wechat.miniapp.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.constant.WxMaConstants;
import cn.binarywang.wx.miniapp.util.WxMaConfigHolder;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Tag(name = "【微信/小程序模块】小程序临时素材接口")
@Slf4j
@RestController
@RequestMapping("openapi/wechat/miniapp/media")
public class WechatMiniAppMediaController {

    @Autowired
    private WxMaService wxMaService;

    @Operation(summary = "上传临时素材", description = "返回素材的media_id列表，实际上如果有的话，只会有一个")
    @PostMapping("upload")
    public List<String> uploadMedia(@RequestParam("appid") String appid,
                                    HttpServletRequest request) throws WxErrorException {
        if (!wxMaService.switchover(appid)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }

        String contentType = request.getContentType();
        if (null == contentType || !contentType.toLowerCase().startsWith("multipart/form-data")) {
            WxMaConfigHolder.remove();//清理ThreadLocal
            return Lists.newArrayList();
        }

        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        Iterator<String> it = multiRequest.getFileNames();
        List<String> result = Lists.newArrayList();
        while (it.hasNext()) {
            try {
                MultipartFile file = multiRequest.getFile(it.next());
                File newFile = new File(Files.createTempDir(), file.getOriginalFilename());
                log.info("filePath is ：" + newFile.toString());
                file.transferTo(newFile);
                WxMediaUploadResult uploadResult = wxMaService.getMediaService().uploadMedia(WxMaConstants.KefuMsgType.IMAGE, newFile);
                log.info("media_id ： " + uploadResult.getMediaId());
                result.add(uploadResult.getMediaId());
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        WxMaConfigHolder.remove();//清理ThreadLocal
        return result;
    }

    @Operation(summary = "下载临时素材")
    @GetMapping("download")
    public File getMedia(@RequestParam("appid") String appid,
                         @RequestParam("mediaId") String mediaId) throws WxErrorException {
        if (!wxMaService.switchover(appid)) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }
        File media = wxMaService.getMediaService().getMedia(mediaId);
        WxMaConfigHolder.remove();//清理ThreadLocal
        return media;
    }
}
