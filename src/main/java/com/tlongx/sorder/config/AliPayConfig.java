package com.tlongx.sorder.config;

import com.tlongx.sorder.utils.PropertiesUtil;

public interface AliPayConfig {

    String url = PropertiesUtil.getProperty("alipay_gateway_url");
    String appId = PropertiesUtil.getProperty("alipay_appid");
    String privateKey = PropertiesUtil.getProperty("alipay_private_key");
    String format = PropertiesUtil.getProperty("alipay_format");
    String charset = PropertiesUtil.getProperty("alipay_charset");
    String publicKey = PropertiesUtil.getProperty("alipay_public_key");
    String signType = PropertiesUtil.getProperty("alipay_sign_type");
    String body = PropertiesUtil.getProperty("pay_body");
    String subject = PropertiesUtil.getProperty("pay_subject");
    String walletSubject = PropertiesUtil.getProperty("recharge_wallet_subject");
    String notify = PropertiesUtil.getProperty("alipay_notify");
}
