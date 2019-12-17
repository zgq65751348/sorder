package com.tlongx.common.enums;

/**
 * 流水枚举类
 */
public enum TradeType {
    钱包充值(1),
    订单(2),
    订单退款(3),
    提现(4),
    系统发放补贴(5),
    单独发放补贴(6)
    ;

    private Integer tradeType;

    TradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    public Integer getTradeType() {
        return tradeType;
    }
}
