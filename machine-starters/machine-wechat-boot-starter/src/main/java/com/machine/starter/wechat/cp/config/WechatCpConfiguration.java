package com.machine.starter.wechat.cp.config;

import com.google.common.collect.Maps;
import com.machine.starter.wechat.cp.handler.*;
import jakarta.annotation.PostConstruct;
import lombok.val;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxRuntimeException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.config.impl.WxCpRedissonConfigImpl;
import me.chanjar.weixin.cp.constant.WxCpConsts;
import me.chanjar.weixin.cp.message.WxCpMessageRouter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 多实例配置
 */
@Configuration
@EnableConfigurationProperties(WechatCpProperties.class)
public class WechatCpConfiguration {

    private final WechatCpLogHandler wechatCpLogHandler;
    private final WechatCpNullHandler wechatCpNullHandler;
    private final WechatCpLocationHandler wechatCpLocationHandler;
    private final WechatCpMenuHandler wechatCpMenuHandler;
    private final WechatCpMsgHandler wechatCpMsgHandler;
    private final WechatCpContactChangeHandler wechatCpContactChangeHandler;
    private final WechatCpUnsubscribeHandler wechatCpUnsubscribeHandler;
    private final WechatCpSubscribeHandler wechatCpSubscribeHandler;
    private final WechatCpProperties properties;
    private final RedissonClient redissonClient;
    private final StringRedisTemplate stringRedisTemplate;

    private static Map<String, WxCpMessageRouter> routers = Maps.newHashMap();
    private static Map<String, WxCpService> cpServices = Maps.newHashMap();

    @Autowired
    public WechatCpConfiguration(WechatCpLogHandler wechatCpLogHandler,
                                 WechatCpNullHandler wechatCpNullHandler,
                                 WechatCpLocationHandler wechatCpLocationHandler,
                                 WechatCpMenuHandler wechatCpMenuHandler,
                                 WechatCpMsgHandler wechatCpMsgHandler,
                                 WechatCpContactChangeHandler wechatCpContactChangeHandler,
                                 WechatCpUnsubscribeHandler wechatCpUnsubscribeHandler,
                                 WechatCpSubscribeHandler wechatCpSubscribeHandler,
                                 WechatCpProperties properties,
                                 RedissonClient redissonClient,
                                 StringRedisTemplate stringRedisTemplate) {
        this.wechatCpLogHandler = wechatCpLogHandler;
        this.wechatCpNullHandler = wechatCpNullHandler;
        this.wechatCpLocationHandler = wechatCpLocationHandler;
        this.wechatCpMenuHandler = wechatCpMenuHandler;
        this.wechatCpMsgHandler = wechatCpMsgHandler;
        this.wechatCpContactChangeHandler = wechatCpContactChangeHandler;
        this.wechatCpUnsubscribeHandler = wechatCpUnsubscribeHandler;
        this.wechatCpSubscribeHandler = wechatCpSubscribeHandler;

        this.properties = properties;
        this.redissonClient = redissonClient;
        this.stringRedisTemplate = stringRedisTemplate;
    }


    public static Map<String, WxCpMessageRouter> getRouters() {
        return routers;
    }

    public static WxCpService getCpService(String corpId, Integer agentId) {
        WxCpService cpService = cpServices.get(corpId + agentId);
        return Optional.ofNullable(cpService).orElseThrow(() -> new WxRuntimeException("未配置此service"));
    }

    @PostConstruct
    public void initServices() {
        cpServices = this.properties.getAppConfigs().stream().map(a -> {

            /**
             * 第二种方式，请参考： todo
             * https://github.com/binarywang/weixin-java-mp-demo/blob/master/src/main/java/com/github/binarywang/demo/wx/mp/config/WxMpConfiguration.java
             */
//            WxRedisOps redisTemplateOps = new RedisTemplateWxRedisOps(stringRedisTemplate);
//            WxCpRedisConfigImpl redisConfig = new WxCpRedisConfigImpl(todo);

            WxCpRedissonConfigImpl config = new WxCpRedissonConfigImpl(redissonClient, "workRedis:");
            config.setCorpId(a.getCorpId());
            config.setAgentId(a.getAgentId());
            config.setCorpSecret(a.getSecret());
            config.setToken(a.getToken());
            config.setAesKey(a.getAesKey());

            val service = new WxCpServiceImpl();
            service.setWxCpConfigStorage(config);

            routers.put(a.getCorpId() + a.getAgentId(), this.newRouter(service));
            return service;
        }).collect(Collectors.toMap(service -> service.getWxCpConfigStorage().getCorpId() + service.getWxCpConfigStorage().getAgentId(), a -> a));
    }

    private WxCpMessageRouter newRouter(WxCpService wxCpService) {
        final val newRouter = new WxCpMessageRouter(wxCpService);

        // 记录所有事件的日志 （异步执行）
        newRouter.rule().handler(this.wechatCpLogHandler).next();

        // 自定义菜单事件
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.MenuButtonType.CLICK).handler(this.wechatCpMenuHandler).end();

        // 点击菜单链接事件（这里使用了一个空的处理器，可以根据自己需要进行扩展）
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.MenuButtonType.VIEW).handler(this.wechatCpNullHandler).end();

        // 关注事件
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.SUBSCRIBE).handler(this.wechatCpSubscribeHandler)
                .end();

        // 取消关注事件
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.UNSUBSCRIBE)
                .handler(this.wechatCpUnsubscribeHandler).end();

        // 上报地理位置事件
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.LOCATION).handler(this.wechatCpLocationHandler)
                .end();

        // 接收地理位置消息
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.LOCATION)
                .handler(this.wechatCpLocationHandler).end();

        // 扫码事件（这里使用了一个空的处理器，可以根据自己需要进行扩展）
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.SCAN).handler(this.wechatCpNullHandler).end();

        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxCpConsts.EventType.CHANGE_CONTACT).handler(this.wechatCpContactChangeHandler).end();

        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxCpConsts.EventType.ENTER_AGENT).handler(new WechatCpEnterAgentHandler()).end();

        // 默认
        newRouter.rule().async(false).handler(this.wechatCpMsgHandler).end();

        return newRouter;
    }

}
