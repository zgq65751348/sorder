package com.tlongx.sorder.utils;

import com.google.gson.Gson;
import com.tlongx.common.ErrorEnum;
import com.tlongx.common.exception.CodeException;
import com.tlongx.sorder.config.WeChatConfig;
import com.tlongx.sorder.dto.OrderDto;
import com.tlongx.sorder.order.pojo.Order;
import com.tlongx.sorder.order.pojo.OrderItem;
import com.tlongx.sorder.order.pojo.Trade;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

/**
 * 微信支付工具类
 */
@Slf4j
public class WeChatPayUtil {

    static SortedMap<String, String> packageParams = null;

    private static String characterEncoding = "UTF-8";

    private static String returnXml;

    /**
     * 微信统一下单
     * @param trade
     * @return
     */
    public static Map<String, Object> unifiedorder(Trade trade) {
        Map resultMap = null;
        Integer fee = Integer.valueOf(trade.getPrice().multiply(new BigDecimal(100)).intValue());
        try {
            packageParams = new TreeMap();
            packageParams.put("out_trade_no", trade.getOutTradeNo());
            packageParams.put("appid", WeChatConfig.appid);
            packageParams.put("mch_id", WeChatConfig.mchId);
            if(trade.getTradeType().equals(1)){
                packageParams.put("body", WeChatConfig.rwtBody);
            }else{
                packageParams.put("body", WeChatConfig.payBody);
            }
            packageParams.put("notify_url", WeChatConfig.notify);
            packageParams.put("attach", URLEncoder.encode(trade.getTid(), "utf-8"));
            packageParams.put("trade_type", WeChatConfig.tradeType);
            packageParams.put("nonce_str", MyUtil.createUUID());
            if (fee != null) {
                packageParams.put("total_fee", fee.toString());
            } else {
                throw new CodeException(ErrorEnum.ILLEGAL_REQUEST);
            }
            String sign = createSign(packageParams, characterEncoding);
            log.info("生成签名:{}", sign);
            packageParams.put("sign", sign);
            Iterator it = packageParams.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                log.info("key= " + ((String) entry.getKey()) + " and value= " + ((String) entry.getValue()));
            }

            sendConnWithXML(WeChatConfig.wxpUnifiedorderPath, parseXML());
            log.info("微信返回的数据:" + returnXml);

            Document doc = null;
            doc = DocumentHelper.parseText(returnXml);
            Element rootElt = doc.getRootElement();
            String prepay_id = rootElt.elementText("prepay_id");
            String body = rootElt.elementText("body");

            log.info("body:{}",body);
            log.info(rootElt.elementText("return_code") + "/*/*/*/");
            log.info(rootElt.elementText("return_msg") + "/*/*/*/");

            resultMap = new HashMap();
            SortedMap packParams = new TreeMap();
            packParams.put("prepayid", prepay_id);
            packParams.put("appid", WeChatConfig.appid);
            packParams.put("partnerid", WeChatConfig.mchId);
            packParams.put("package", "Sign=WXPay");
            packParams.put("noncestr", MyUtil.createUUID());
            packParams.put("timestamp", System.currentTimeMillis() / 1000L);

            String pay_sign = createSign(packParams, characterEncoding);
            resultMap.put("sign", pay_sign);
            resultMap.put("out_trade_no", trade.getOutTradeNo());
            resultMap.put("prepay_id", prepay_id);
            resultMap.put("package", "Sign=WXPay");
            resultMap.put("noncestr", packParams.get("noncestr"));
            resultMap.put("timestamp", packParams.get("timestamp"));
            resultMap.put("partnerid", WeChatConfig.mchId);
            //resultMap.put("body",new String(body.getBytes("GBK"),"UTF-8"));
        } catch (Exception e) {
            log.info("==============微信支付异常=============");
            e.printStackTrace();
        }
        return resultMap;
    }

    public static String createSign(SortedMap<String, String> packageParams, String characterEncoding) {
        StringBuffer sb = new StringBuffer();
        Set es = packageParams.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = MyUtil.strObject(entry.getValue());
            if ((v == null) || ("".equals(v)) || ("sign".equals(k)) ||
                    ("sign_type".equals(k))) {
                continue;
            }
            sb.append(k + "=" + v + "&");
        }
        sb.append("key=" + WeChatConfig.key);
        String sign = null;
        sign = MD5Util.MD5Encode1(sb.toString(), characterEncoding).toUpperCase();
        return sign;
    }

    private static void sendConnWithXML(String path, String rqestXml) throws IOException {
        log.info("rqestXml:====" + rqestXml);
        HttpURLConnection conn = getConn(path);
        byte[] xmlbyte = rqestXml.getBytes("UTF-8");
        DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
        outStream.write(xmlbyte);
        outStream.flush();
        outStream.close();

        byte[] msgBody = null;
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("请求url失败");
        }
        InputStream is = conn.getInputStream();

        byte[] temp = new byte[1024];
        int n = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((n = is.read(temp)) != -1) {
            bos.write(temp, 0, n);
        }
        msgBody = bos.toByteArray();
        bos.close();
        is.close();
        returnXml = new String(msgBody, "UTF-8").trim();
        conn.disconnect();
    }

    private static HttpURLConnection getConn(String path)
            throws IOException {
        URL url = new URL(path);
        HttpURLConnection conn = null;
        conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("content-type", "text/xml; charset=UTF-8");
        conn.connect();
        return conn;
    }

    public static String getParameter(String parameter) {
        String s = (String) packageParams.get(parameter);
        return ((s == null) ? "" : s);
    }

    public static String parseXML() {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = packageParams.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if ((v == null) || ("".equals(v)) || ("key".equals(k))){
                continue;
            }
            //sb.append("<" + k + ">" + getParameter(k) + "</" + k + ">\n");
            sb.append("<" + k + ">" + getParameter(k) + "</" + k + ">");
        }
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * 退款
     * @param orderDto
     * @return
     * @throws Exception
     */
    public static Map<String, String> refund(OrderDto orderDto)
            throws Exception {
        Map<String,String> resultMap=new HashMap<>();
        String outRefundNo=MyUtil.createId("OR");
        packageParams = new TreeMap();
        packageParams.put("appid", WeChatConfig.appid);
        packageParams.put("mch_id", WeChatConfig.mchId);
        packageParams.put("nonce_str", MyUtil.createUUID());
        packageParams.put("out_trade_no", orderDto.getOutTradeNo());
        packageParams.put("out_refund_no", outRefundNo);
        packageParams.put("total_fee", String.valueOf(orderDto.getPayPrice().multiply(new BigDecimal(100)).intValue()));
        packageParams.put("refund_fee", String.valueOf(orderDto.getPayPrice().multiply(new BigDecimal(100)).intValue()));

        String sign = createSign(packageParams, characterEncoding);
        packageParams.put("sign", sign);

        String returnXml = WeChatCertUtil.ssl(WeChatConfig.wxpRefundPath, parseXML());

        Document doc = null;
        doc = DocumentHelper.parseText(returnXml);
        Element rootElt = doc.getRootElement();
        String result_code = rootElt.elementText("result_code");
        String return_code = rootElt.elementText("return_code");
        if ((result_code.equals("SUCCESS")) && (return_code.endsWith("SUCCESS"))) {
            log.info("return_msg ==> " + rootElt.elementText("return_msg"));
            log.info("err_code ==> " + rootElt.elementText("err_code"));
            log.info("err_code_des==> " + rootElt.elementText("err_code_des"));

        } else if ((result_code.equals("FAIL")) || (return_code.endsWith("FAIL"))) {
            log.info("return_msg ==> " + rootElt.elementText("return_msg"));
            log.info("err_code ==> " + rootElt.elementText("err_code"));
            log.info("err_code_des==> " + rootElt.elementText("err_code_des"));
            throw new CodeException(20002, rootElt.elementText("err_code_des"));
        }
        resultMap.put("result_code",result_code);
        resultMap.put("return_code",return_code);
        return resultMap;
    }

    public static Map transfers(String tradeId, String openId, String amount)
            throws Exception
    {
        packageParams = new TreeMap();
        packageParams.put("mch_appid",WeChatConfig.appid);
        packageParams.put("mchid",WeChatConfig.mchId);
        packageParams.put("partner_trade_no", tradeId);
        packageParams.put("nonce_str", MyUtil.createUUID());
        packageParams.put("openid", openId);
        packageParams.put("check_name", "NO_CHECK");
        packageParams.put("amount", amount);
        packageParams.put("desc", "小圈圈提现");
        InetAddress inetAddress = InetAddress.getLocalHost();
        packageParams.put("spbill_create_ip", inetAddress.getHostAddress());

        String sign = createSign(packageParams, characterEncoding);
        packageParams.put("sign", sign);
        try {
            String returnXml = WeChatCertUtil.ssl(WeChatConfig.wxpTransfersPath, parseXML());
            System.out.println("===============" + returnXml);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        Gson gson = new Gson();
        Map map = new HashMap();
        map = (Map)gson.fromJson(returnXml, map.getClass());

        boolean flag = false;

        Document doc = null;

        doc = DocumentHelper.parseText(returnXml);
        Element rootElt = doc.getRootElement();
        String result_code = rootElt.elementText("result_code");
        String return_code = rootElt.elementText("return_code");
        if ((result_code.equals("SUCCESS")) && (return_code.endsWith("SUCCESS"))) {
            String partnerTradeNo = rootElt.elementText("partner_trade_no");
            if (partnerTradeNo.equals(tradeId))
                flag = true;
            else {
                flag = false;
            }
        }
        else if ((result_code.equals("FAIL")) || (return_code.endsWith("FAIL"))) {
            log.info("return_msg ==> " + rootElt.elementText("return_msg"));
            log.info("err_code ==> " + rootElt.elementText("err_code"));
            log.info("err_code_des==> " + rootElt.elementText("err_code_des"));
            flag = false;
        }
        return map;
    }

}
