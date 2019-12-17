package com.tlongx.sorder.api.utils;

public class Constant {
    public static final String CHARSET_UTF8 = "UTF-8";

    public static final String MEM_PFX_FILE_PASSWORD = "888888";

	//商户应该换成自己的证书
    public static final String MEM_PFX_FILE_NAME = "100001.pfx";

	public static final String TRUST_CER_NAME = "CFCA_ACS_TEST_OCA31.cer";

    public static final String CASH_SALT = "CHINAPNR";

	//商户应该换成自己memberId的前16位
	public static final String VERIFY_MER_ID = "AS0381";

	public static final String MEM_PFX_FILE_PATH = Constant.class.getClassLoader().getResource(Constant.MEM_PFX_FILE_NAME).getPath();

	public static final String TRUST_CER_PATH = Constant.class.getClassLoader().getResource(Constant.TRUST_CER_NAME).getPath();;

    // 商户联调地址
    //public static final String QRCP_URL = "https://amtqrcp.testpnr.com/qrcp/";
    // 测试地址
    public static final String QRCP_URL = "https://testqrcp.cloudpnr.com/qrcp/";

    //商户信息查询、支付方式列表查询接口测试环境 :
    //public static final String NSPOSR_URL = "https://nspos.chinapnr.com/";
    public static final String NSPOSR_URL = "http://pttest.chinapnr.com/";

}
