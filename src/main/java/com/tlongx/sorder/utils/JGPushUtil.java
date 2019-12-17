package com.tlongx.sorder.utils;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import com.tlongx.sorder.config.JiGuangConfig;
import com.tlongx.sorder.msg.pojo.Msg;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JGPushUtil {
    public static final String ALERT = "您有一条系统消息未读！";

    public static void main(String[] args) {
    }

    public static void sendPushMsg(Msg msg)
            throws APIConnectionException, APIRequestException {
        String TITLE = msg.getTitle();
        ClientConfig clientConfig = ClientConfig.getInstance();

        JPushClient jpushClient = null;
        if (msg.getPushObj().equals(1)) {
            jpushClient = new JPushClient(JiGuangConfig.masterSecret, JiGuangConfig.appKey, null, clientConfig);
        } else if ((msg.getPushObj().equals(2)) || (msg.getPushObj().equals(2))) {
            jpushClient = new JPushClient(JiGuangConfig.sMasterSecret, JiGuangConfig.sAppKey, null, clientConfig);
        }
        PushPayload payload = null;
        if (msg.getPushPlatform().equals(0)) {
            payload = buildPushObject_all_alias_alert(msg);
        } else if (msg.getPushPlatform().equals(1))
            payload = buildPushObject_ios_alias_alert(msg);
        else if (msg.getPushPlatform().equals(2)) {
            payload = buildPushObject_android_alertWithTitle(TITLE, msg);
        }

        jpushClient.sendPush(payload);
        log.info("推送成功");
    }

    public static PushPayload buildPushObject_all_alias_alert(Msg msg) {
        return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.all())
                .setNotification(Notification.alert("您有一条系统消息未读！"))
                .setAudience(Audience.alias(msg.getAlias())).build();
    }

    public static PushPayload buildPushObject_android_alertWithTitle(String TITLE, Msg msg) {
        return PushPayload.newBuilder().setPlatform(Platform.android()).setAudience(Audience.alias(msg.getAlias()))
                .setNotification(Notification.android("您有一条系统消息未读！", TITLE,
                        null)).build();
    }

    public static PushPayload buildPushObject_ios_alias_alert(Msg msg) {
        return PushPayload.newBuilder().setPlatform(Platform.ios()).setAudience(Audience.alias(msg.getAlias()))
                .setNotification(Notification.alert("您有一条系统消息未读！"))
                .build();
    }
}