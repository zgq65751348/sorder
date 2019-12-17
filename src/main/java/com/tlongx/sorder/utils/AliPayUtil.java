package com.tlongx.sorder.utils;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayFundTransToaccountTransferModel;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.tlongx.common.ErrorEnum;
import com.tlongx.common.exception.CodeException;
import com.tlongx.sorder.config.AliPayConfig;
import com.tlongx.sorder.dto.OrderDto;
import com.tlongx.sorder.order.pojo.Order;
import com.tlongx.sorder.order.pojo.Trade;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AliPayUtil {

    private static AlipayClient alipayClient = null;

    public static Map<String, Object> alipay(Trade trade)
            throws UnsupportedEncodingException {
        Map result = new HashMap();
        AlipayTradeAppPayRequest requestData = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();

        alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", AliPayConfig.appId, AliPayConfig.privateKey, "json", AliPayConfig.charset, AliPayConfig.publicKey, "RSA2");

        model.setBody(AliPayConfig.body);
        if(trade.getTradeType().equals(1)){
            model.setSubject(AliPayConfig.walletSubject);
        }else{
            model.setSubject(AliPayConfig.subject);
        }
        model.setOutTradeNo(trade.getOutTradeNo());
        model.setTimeoutExpress("30m");
        model.setTotalAmount(trade.getPrice().toString());
        model.setPassbackParams(URLEncoder.encode(trade.getTid() , "UTF-8"));
        try {
            if (trade.getPrice() != null){
                model.setTotalAmount(trade.getPrice().toString());
            }
            else {
                throw new CodeException(ErrorEnum.ILLEGAL_REQUEST);
            }
            requestData.setNotifyUrl(AliPayConfig.notify);
            String orderStr = "";
            requestData.setBizModel(model);
            AlipayTradeAppPayResponse response = (AlipayTradeAppPayResponse) alipayClient.sdkExecute(requestData);
            orderStr = response.getBody();

            if (response.isSuccess()){
                log.info("支付三方调用成功");
            }
            else {
                log.info("支付三方调用失败");
            }
            result.put("orderStr", new String(orderStr.getBytes("GBK"),"UTF-8"));
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Map<String, String> refund(OrderDto orderDto)
            throws UnsupportedEncodingException {
        Map<String,String> resultMap=new HashMap<>();
        AlipayTradeRefundRequest requestData = new AlipayTradeRefundRequest();
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        model.setOutTradeNo(orderDto.getOutTradeNo());
        String outRequestNo=MyUtil.createId("OR");
        model.setOutRequestNo(outRequestNo);

        model.setRefundAmount(orderDto.getPayPrice().toString());
        requestData.setBizModel(model);

        alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", AliPayConfig.appId, AliPayConfig.privateKey, "json", AliPayConfig.charset, AliPayConfig.publicKey, "RSA2");
        try {
            AlipayTradeRefundResponse response = (AlipayTradeRefundResponse) alipayClient.execute(requestData);
            if (StringUtils.equals("10000", response.getCode())) {
                resultMap.put("code",response.getCode());
            } else {
                log.error(response.getMsg() + "退款失败");
                throw new CodeException(ErrorEnum.CANCEL_ORDER_FAIL);
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    public static Map<String,Object> transfer(Trade trade){
        Map<String,Object> resultMap = new HashMap<>();

        alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", AliPayConfig.appId, AliPayConfig.privateKey, "json", AliPayConfig.charset, AliPayConfig.publicKey, "RSA2");

        AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();

        AlipayFundTransToaccountTransferModel model = new AlipayFundTransToaccountTransferModel();

        String outBizNo = MyUtil.strObject(new Date().getTime() + MyUtil.random(6));

        model.setOutBizNo(outBizNo);
        model.setPayeeType("ALIPAY_LOGONID");
        model.setPayeeAccount(trade.getAccount());
        model.setAmount(trade.getPrice().toString());
        model.setRemark("测试提现");

        request.setBizModel(model);

        try {
            AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);
            if(response.isSuccess()){
                log.info("提现接口调用成功"+response.getBody());
                resultMap.put("code",response.getCode());
                resultMap.put("msg", response.getMsg());
                resultMap.put("payDate",response.getPayDate());
            } else {
                log.error(response.getMsg()+"提现接口调用失败");
                throw  new CodeException(ErrorEnum.WithDraw_Fail);
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return resultMap;
    }
}
