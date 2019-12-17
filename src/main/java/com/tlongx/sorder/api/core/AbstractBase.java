package com.tlongx.sorder.api.core;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;

/**
 * <p>
 * <title></title>
 * </p>
 * <p>
 * windows 7  旗舰版
 *
 * @author 雅诗兰黛
 * @ClassName: AbstractBase
 * @create <h>巴黎欧莱雅，你值得拥有</h>
 * @since 2019/12/17 0017 上午 10:32
 */

public  class   AbstractBase {

    /**
     * 测试商户号
     */
    protected static String memberId = "030147446000004022";

    /**
     * 测试分账串,测试金额0.02
     */
    protected static String accSplitBunch = "{\"acctInfos\":[{\"divRate\":\"0.50\",\"memberId\":\"030147446000004022\"},{\"divRate\":\"0.50\",\"memberId\":\"030147443000002370\"}],\"divCusCount\":\"2\",\"feeMemberId\":\"030147446000004022\"}";

    /**
     * 测试机具号
     */
    protected static String devsId = "030147441118000151381";

    /**
     * 测试机具产品编号
     */
    protected static String prodId = "nspos";

    /**
     * 测试机具系统编号
     */
    protected static String sysId = "nspos";

    /**
     * 订单描述
     */
    protected static String goodsDesc = "描述";

    /**
     * 订单金额
     */
    protected static String ordAmt = "0.01";

    /**
     *
     */
    protected static String isRaw = "1";

    /**
     *
     */
    protected static String appId = "123";

    /**
     *
     */
    protected static String openId = "4213";

    /**
     * 操作员号
     */
    protected static String merOperId = "wzq020308";


    protected static String apiVersion = "2.0.0.1";


    /**
     * 生成长度小于等于14位时，直接生成随机数
     * 生成长度大于14位时,由14位年月日时分秒+(length-14)位随机数组成
     *
     * @return String
     */
    public static String generateSeq(int length) {
        String seq = "";
        if (length <= 14) {
            seq = String.valueOf((long) (1 + Math.random() * Math.pow(10, length + 5))).substring(0, length);
        } else {
            BigInteger random = new BigDecimal(1)
                    .add(new BigDecimal(Math.random()).multiply(new BigDecimal(10).pow(length - 14 + 5)))
                    .toBigInteger();
            seq = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                    + random.toString().substring(0, length - 14);
        }
        return seq;
    }

    /**
     * 商户扩展域、json串格式
     * @return
     */
    public static String getMerPriv()
    {
        JSONObject merPriv = new JSONObject();
        merPriv.put("Ic" , "2253425259");
        merPriv.put("callType" , "04");
        merPriv.put("merNoticeUrl" , "https://amtqrcp.testpnr.com/qrcp/virgoReceive");
        return merPriv.toJSONString();
    }
}
