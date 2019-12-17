package com.tlongx.sorder.api.utils;

import com.huifu.module.common.sign.ShaUtil;
import com.huifu.saturn.cfca.CFCASignature;
import com.huifu.saturn.cfca.VerifyResult;

/**
 * <p>
 * <title></title>
 * </p>
 * <p>
 * windows 7  旗舰版
 *
 * @author 雅诗兰黛
 * @ClassName: SignUtil
 * @create <h>巴黎欧莱雅，你值得拥有</h>
 * @since 2019/12/16 0016 下午 5:37
 */

public class SignUtil {

    private static SignUtil instance = null;

    private SignUtil() {
    }

    public static SignUtil getInstance() {
        if (instance == null) {
            synchronized (SignUtil.class) {
                if (instance == null) {
                    instance = new SignUtil();
                }
            }
        }
        return instance;
    }

    /**
     * ApiVersion为"2.0.0.1"，使用该方法进行签名
     *
     * @param context 待签名的JsonData
     * @return
     */
    public String signByCFCA(String context) {

        return CFCASignature.signature(Constant.MEM_PFX_FILE_PATH, Constant.MEM_PFX_FILE_PASSWORD, context, Constant.CHARSET_UTF8).getSign();
    }

    public VerifyResult verifyByCFCA(String sign)
    {

        return CFCASignature.verifyChinaPnRSign(Constant.VERIFY_MER_ID , sign , Constant.CHARSET_UTF8 , Constant.TRUST_CER_PATH);
    }

    /**
     * @param salt 盐值
     * @return
     */
    public String signBySHA(String context,String salt) {
        return ShaUtil.encode(context, salt, Constant.CHARSET_UTF8);
    }

}
