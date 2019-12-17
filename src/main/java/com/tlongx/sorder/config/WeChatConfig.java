package com.tlongx.sorder.config;

import com.tlongx.sorder.utils.PropertiesUtil;

public interface WeChatConfig {

    String appid= PropertiesUtil.getProperty("wxp_appid");

    String mchId=PropertiesUtil.getProperty("wxp_mch_id");

    String rwtBody=PropertiesUtil.getProperty("recharge_wallet_subject");

    String payBody=PropertiesUtil.getProperty("pay_subject");

    String tradeType=PropertiesUtil.getProperty("wxp_trade_type");

    String notify=PropertiesUtil.getProperty("wxp_notify");

    String key=PropertiesUtil.getProperty("wxp_key");

    String wxpUnifiedorderPath=PropertiesUtil.getProperty("wxp_unifiedorder_path");

    String wxpCertPath=PropertiesUtil.getProperty("wxp_cert_path");

    String wxpRefundPath=PropertiesUtil.getProperty("wxp_refund_path");

    String wxpTransfersPath=PropertiesUtil.getProperty("wxp_transfers_path");
}
