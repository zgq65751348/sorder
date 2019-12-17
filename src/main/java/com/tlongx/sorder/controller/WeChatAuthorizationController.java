package com.tlongx.sorder.controller;

import com.tlongx.common.TLResult;
import com.tlongx.sorder.api.service.WeChatAuthorization;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * <title></title>
 * </p>
 * <p>
 * windows 7  旗舰版
 *
 * @author 雅诗兰黛
 * @ClassName: WeChatAuthorizationController
 * @create <h>巴黎欧莱雅，你值得拥有</h>
 * @since 2019/12/17 0017 上午 10:49
 */
@RestController
@RequestMapping(value = "/WeChat/auth")
public class WeChatAuthorizationController {

    @Resource
    private WeChatAuthorization weChatAuthorization;

    /**
     *  微信授权登录
     * @param code
     * @return
     */
    @GetMapping(value = "/login/{code}")
    public TLResult login(@PathVariable("code") String code){
        return weChatAuthorization.weChatLogin(code);
    }

}
