package com.tlongx.common.enums;


public enum PayType {
    补贴(0),
    钱包支付(1),
    微信(2),
    支付宝(3),
    农行(4),
    支付码(5),
    人脸支付(6)
    ;

    private Integer payType;

    public Integer getPayType() {
        return payType;
    }

    PayType(Integer payType) {
        this.payType = payType;
    }
}
