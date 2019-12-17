package com.tlongx.sorder.config;

import com.tlongx.sorder.utils.PropertiesUtil;

public interface AbcPayConfig {

    String payTypeId = PropertiesUtil.getProperty("abc_pay_paytype_Id");

    String currencyCode = PropertiesUtil.getProperty("abc_pay_currency_code");

    String installmentMark = PropertiesUtil.getProperty("abc_pay_installment_mark");

    String commodityType = PropertiesUtil.getProperty("abc_pay_commodity_type");

    String paymentType = PropertiesUtil.getProperty("abc_pay_payment_type");

    String paymentLinkType = PropertiesUtil.getProperty("abc_pay_payment_link_type");

    String notifyType = PropertiesUtil.getProperty("abc_pay_notify_type");

    String resultNotifyURL = PropertiesUtil.getProperty("abc_pay_result_notify_url");

    String isBreakAccount = PropertiesUtil.getProperty("abc_pay_is_bank_account");

}
