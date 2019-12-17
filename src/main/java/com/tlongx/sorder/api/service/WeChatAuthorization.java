package com.tlongx.sorder.api.service;

import com.alibaba.fastjson.JSONObject;
import com.tlongx.common.TLResult;
import com.tlongx.sorder.api.core.WeChatAuthorizationConfig;
import com.tlongx.sorder.utils.UrlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * <title>微信授权登录</title>
 * </p>
 * <p>
 * windows 7  旗舰版
 *
 * @author 雅诗兰黛
 * @ClassName: WeChatAuthorization
 * @create <h>巴黎欧莱雅，你值得拥有</h>
 * @since 2019/12/17 0017 上午 10:42
 */
@Service
@Slf4j
public class WeChatAuthorization {
    /**
     *  微信授权登录
     * @param code
     * @return TLResult
     */
    public TLResult weChatLogin(String code){
        StringBuilder tokenUrl = new StringBuilder("https://api.weixin.qq.com/sns/oauth2/access_token");
        tokenUrl.append("?appid="+ WeChatAuthorizationConfig.APPID);
        tokenUrl.append("&secret="+WeChatAuthorizationConfig.APPSECRET);
        tokenUrl.append("&code="+code);
        tokenUrl.append("&grant_type=authorization_code");
        String json = UrlUtils.loadURL(tokenUrl.toString());
        Map<String,Object> tokenMap = new HashMap<>();
        tokenMap = JSONObject.parseObject(json,Map.class);
        String access_token = tokenMap.get("access_token").toString();
        String openId = tokenMap.get("openId").toString();
        Map<String,String> map = new HashMap<>();
        map.put ("open_id",openId);
        map.put("access_token",access_token);
        return  TLResult.ok(map);
    }

}
