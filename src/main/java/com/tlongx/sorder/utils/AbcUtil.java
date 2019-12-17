package com.tlongx.sorder.utils;

import com.abc.pay.client.JSON;
import com.abc.pay.client.TrxException;
import com.abc.pay.client.ebus.PaymentRequest;
import com.abc.pay.client.ebus.PaymentResult;
import com.abc.pay.client.ebus.RefundRequest;
import com.tlongx.common.ErrorEnum;
import com.tlongx.common.exception.CodeException;
import com.tlongx.sorder.config.AbcPayConfig;
import com.tlongx.sorder.order.pojo.Trade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.SimpleFormatter;

@Slf4j
public class AbcUtil {

    /**
     * 农行支付
     * @param trade
     * @return
     */
    public static Map abcPay(Trade trade){

        Map resultMap = new HashMap();

        log.info("农行支付接口请求参数开始初始化");

        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

        //1、生成订单对象
        PaymentRequest tPaymentRequest = new PaymentRequest();
        tPaymentRequest.dicOrder.put("PayTypeID", AbcPayConfig.payTypeId);                   //设定交易类型
        tPaymentRequest.dicOrder.put("OrderDate", sf.format(trade.getCrtime()));                  //设定订单日期 （必要信息 - YYYY/MM/DD）
        tPaymentRequest.dicOrder.put("OrderTime", formatter.format(trade.getCrtime()));                   //设定订单时间 （必要信息 - HH:MM:SS）
        tPaymentRequest.dicOrder.put("OrderNo", trade.getTid());                       //设定订单编号 （必要信息）
        tPaymentRequest.dicOrder.put("CurrencyCode", AbcPayConfig.currencyCode);             //设定交易币种
        tPaymentRequest.dicOrder.put("OrderAmount", trade.getPrice());      //设定交易金额
        tPaymentRequest.dicOrder.put("InstallmentMark", AbcPayConfig.installmentMark);       //分期标识

        //2、订单明细
        LinkedHashMap orderitem = new LinkedHashMap();

        tPaymentRequest.dicOrder.put("CommodityType", AbcPayConfig.commodityType);           //设置商品种类

        orderitem.put("ProductName", "餐品消费");//商品名称

        tPaymentRequest.orderitems.put(1, orderitem);

        //3、生成支付请求对象
        tPaymentRequest.dicRequest.put("PaymentType", AbcPayConfig.paymentType);            //设定支付类型
        tPaymentRequest.dicRequest.put("PaymentLinkType", AbcPayConfig.paymentLinkType);    //设定支付接入方式
        tPaymentRequest.dicRequest.put("NotifyType", AbcPayConfig.notifyType);              //设定通知方式
        tPaymentRequest.dicRequest.put("ResultNotifyURL", AbcPayConfig.resultNotifyURL);    //设定通知URL地址
        tPaymentRequest.dicRequest.put("IsBreakAccount", AbcPayConfig.isBreakAccount);      //设定交易是否分账

        String genSignatur = "";
        try {
            genSignatur = tPaymentRequest.genSignature(1);
            log.info("签名:{}"+genSignatur);
        } catch (TrxException e) {
            e.printStackTrace();
        }

        JSON json = tPaymentRequest.postRequest();
        String ReturnCode = json.GetKeyValue("ReturnCode");
        String ErrorMessage = json.GetKeyValue("ErrorMessage");

        if(!StringUtils.isEmpty(genSignatur)){
            log.info("农行支付接口请求成功");
            resultMap.put("PaymentURL",json.GetKeyValue("PaymentURL"));
        }else{
            log.info("农行支付接口请求失败");
            log.info("请求失败原因：{}"+ErrorMessage);
        }
        String tokenId = json.GetKeyValue("PaymentURL").split("=")[1];
        resultMap.put("OrderNo",tokenId);
        return resultMap;
    }


    public static Map refund(Trade trade,String tid){
        Map resultMap = new HashMap();

        log.info("----------进入农行退款接口-------------");

        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

        RefundRequest refundRequest = new RefundRequest();

        refundRequest.dicRequest.put("OrderDate",sf.format(trade.getCrtime()));
        refundRequest.dicRequest.put("OrderTime",formatter.format(trade.getCrtime()));
        refundRequest.dicRequest.put("OrderNo",trade.getTid());
        refundRequest.dicRequest.put("NewOrderNo",tid);
        refundRequest.dicRequest.put("CurrencyCode",AbcPayConfig.currencyCode);
        refundRequest.dicRequest.put("TrxAmount",trade.getPrice().toString());

        JSON json = refundRequest.postRequest();

        String ReturnCode = json.GetKeyValue("ReturnCode");
        String ErrorMessage = json.GetKeyValue("ErrorMessage");

        if(ReturnCode.equals("0000")){
            log.info("农行退款接口请求成功====>"+ErrorMessage);
            resultMap.put("code",ReturnCode);
            resultMap.put("msg",ErrorMessage);
        }else{
            log.info("农行退款接口请求失败");
            log.info("退款失败原因：{}"+ErrorMessage);
            throw new CodeException(ErrorEnum.CANCEL_ORDER_FAIL);
        }
        return resultMap;
    }

}
