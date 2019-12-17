package com.tlongx.common;

import com.tlongx.sorder.utils.PropertiesUtil;

/**
 * @author xin.rf
 * @date 2019/2/12 16:43
 * @Description 静态常量类
 **/
public interface Constants {

    Integer SUCCESS_CODE=200;
    String SUCCESS_MSG="请求成功";
    Integer OPERATE_FAIL_CODE=401;
    String OPERATE_FAIL_MSG="操作失败";

    String TencentSecretId= PropertiesUtil.getProperty("tencentcloud_secret_id");
    String TencentSecretKey= PropertiesUtil.getProperty("tencentcloud_secret_key");
//    String serverName=PropertiesUtil.getProperty("server_name");
}
