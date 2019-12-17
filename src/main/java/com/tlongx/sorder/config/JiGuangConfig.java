package com.tlongx.sorder.config;

import com.tlongx.sorder.utils.PropertiesUtil;

public interface JiGuangConfig {

    String masterSecret= PropertiesUtil.getProperty("masterSecret");

    String appKey=PropertiesUtil.getProperty("appKey");

    String sMasterSecret=PropertiesUtil.getProperty("s_masterSecret");

    String sAppKey=PropertiesUtil.getProperty("s_appKey");
}
