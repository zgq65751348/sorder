package com.tlongx.sorder.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class TradeVo {

    private Integer pageNum;

    private Integer pageSize;

    private String uid;

    private Integer sid;

    private String username;

    private String phone;

    private String startTime;

    private String endTime;

    @ApiModelProperty(value = "供应时段类型", example = " 1早餐 2午餐 3晚餐 4水果超市 5便利小超 6特色早餐 7特色午餐 8特色晚餐")
    private Integer supType;

    /**
     * 流水id
     */
    private String tid;


    /**
     * 订单id
     */
    private String oid;

    private String cartIds;

    private Integer useSubsidy;

    /**
     * 商户订单号
     */
    private String outTradeNo;

    /**
     * 流水类型
     */
    @ApiModelProperty(value = "流水类型",example = " 1钱包充值 2订单 3订单退款 4提现 5系统发放补贴 6单独发放补贴")
    private Integer tradeType;

    /**
     * 交易类型 1钱包 2微信 3支付宝
     */
    @ApiModelProperty(value = "交易类型", example = "0补贴 1钱包 2微信 3支付宝 4农行 5支付码 6人脸")
    private Integer payType;

    /**
     * 交易金额
     */
    private BigDecimal price;

    /**
     * 交易账号（微信/支付宝）
     */
    private String account;

    /**
     * 交易状态（0待审核  1已完成  2待支付  3提现失败）
     */
    @ApiModelProperty(value = "交易状态", example = "0待审核  1已完成  2待支付  3提现失败 4审核拒绝")
    private Integer status;

    /**
     * 提现失败原因
     */
    private String cause;

    @ApiModelProperty(value = "收支类型",example = "1收入 2支出")
    private Integer ie;

    /**
     * 当前账户余额
     */
    private BigDecimal balance;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date crtime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date uptime;

    private String foodName;

    private String mtime;

    private Integer isPlat;
}
