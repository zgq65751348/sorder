package com.tlongx.sorder.order.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tl_order")
@ApiModel
public class Order {
    /**
     * 订单id
     */
    @Id
    @ApiModelProperty(value = "订单id")
    private String oid;

    /**
     * 购买人uid
     */
    @ApiModelProperty(value = "购买人id")
    private String uid;

    /**
     * 1:待付款 2:付款失败 3:付款成功=生成二维码 4:已完成 二维码已扫 5:退款 6.订单已评论  
     */
    @ApiModelProperty(value = "1:待付款 2:付款失败 3:付款成功=生成二维码 4:已完成 二维码已扫 5:退款 6.订单已评论")
    private Integer status;

    /**
     * 支付类型 1:钱包 2:微信 3:支付宝 4:农行
     */
    @ApiModelProperty(value = "1:钱包 2:微信 3:支付宝 4:农行")
    @Column(name = "pay_type")
    private Integer payType;

    /**
     * 实际支付金额
     */
    @ApiModelProperty(value = "实际支付金额")
    @Column(name = "pay_price")
    private BigDecimal payPrice;

    /**
     * 商户订单号
     */
    @ApiModelProperty(value = "商户订单号")
    @Column(name = "out_trade_no")
    private String outTradeNo;

    /**
     * 支付时间
     */
    @ApiModelProperty(value = "支付时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @Column(name = "pay_time")
    private Date payTime;



    /**
     * 优惠种类  (0:无 1: 满减 2:折扣 3:教师补贴)
     */
    @ApiModelProperty(value = "优惠种类  (0:无 1: 满减 2:折扣 3:教师补贴)")
    @Column(name = "discounts_type")
    private Integer discountsType;

    /**
     * 教师使用补贴金额
     */
    @ApiModelProperty(value = "教师使用补贴金额")
    @Column(name = "subsidy_price")
    private BigDecimal subsidyPrice;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date crtime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date uptime;



    /**
     * 获取订单id
     *
     * @return oid - 订单id
     */
    public String getOid() {
        return oid;
    }

    /**
     * 设置订单id
     *
     * @param oid 订单id
     */
    public void setOid(String oid) {
        this.oid = oid == null ? null : oid.trim();
    }

    /**
     * 获取购买人uid
     *
     * @return uid - 购买人uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * 设置购买人uid
     *
     * @param uid 购买人uid
     */
    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    /**
     * 获取1:待付款 2:付款失败 3:付款成功=生成二维码 4:已完成 二维码已扫 5:退款 6.订单已评论  
     *
     * @return status - 1:待付款 2:付款失败 3:付款成功=生成二维码 4:已完成 二维码已扫 5:退款 6.订单已评论  
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置1:待付款 2:付款失败 3:付款成功=生成二维码 4:已完成 二维码已扫 5:退款 6.订单已评论  
     *
     * @param status 1:待付款 2:付款失败 3:付款成功=生成二维码 4:已完成 二维码已扫 5:退款 6.订单已评论  
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取1:微信 2:支付宝 3:农行
     *
     * @return pay_type - 1:微信 2:支付宝 3:农行
     */
    public Integer getPayType() {
        return payType;
    }

    /**
     * 设置1:微信 2:支付宝 3:农行
     *
     * @param payType 1:微信 2:支付宝 3:农行
     */
    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    /**
     * 获取实际支付金额
     *
     * @return pay_price - 实际支付金额
     */
    public BigDecimal getPayPrice() {
        return payPrice;
    }

    /**
     * 设置实际支付金额
     *
     * @param payPrice 实际支付金额
     */
    public void setPayPrice(BigDecimal payPrice) {
        this.payPrice = payPrice;
    }

    /**
     * 获取商户订单号
     *
     * @return out_trade_no - 商户订单号
     */
    public String getOutTradeNo() {
        return outTradeNo;
    }

    /**
     * 设置商户订单号
     *
     * @param outTradeNo 商户订单号
     */
    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo == null ? null : outTradeNo.trim();
    }

    /**
     * 获取支付时间
     *
     * @return pay_time - 支付时间
     */
    public Date getPayTime() {
        return payTime;
    }

    /**
     * 设置支付时间
     *
     * @param payTime 支付时间
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /**
     * 获取优惠种类  (0:无 1: 满减 2:折扣 3:教师补贴)
     *
     * @return discounts_type - 优惠种类  (0:无 1: 满减 2:折扣 3:教师补贴)
     */
    public Integer getDiscountsType() {
        return discountsType;
    }

    /**
     * 设置优惠种类  (0:无 1: 满减 2:折扣 3:教师补贴)
     *
     * @param discountsType 优惠种类  (0:无 1: 满减 2:折扣 3:教师补贴)
     */
    public void setDiscountsType(Integer discountsType) {
        this.discountsType = discountsType;
    }

    /**
     * 获取教师使用补贴金额
     *
     * @return subsidy_price - 教师使用补贴金额
     */
    public BigDecimal getSubsidyPrice() {
        return subsidyPrice;
    }

    /**
     * 设置教师使用补贴金额
     *
     * @param subsidyPrice 教师使用补贴金额
     */
    public void setSubsidyPrice(BigDecimal subsidyPrice) {
        this.subsidyPrice = subsidyPrice;
    }

    /**
     * 获取创建时间
     *
     * @return crtime - 创建时间
     */
    public Date getCrtime() {
        return crtime;
    }

    /**
     * 设置创建时间
     *
     * @param crtime 创建时间
     */
    public void setCrtime(Date crtime) {
        this.crtime = crtime;
    }

    /**
     * 获取更新时间
     *
     * @return uptime - 更新时间
     */
    public Date getUptime() {
        return uptime;
    }

    /**
     * 设置更新时间
     *
     * @param uptime 更新时间
     */
    public void setUptime(Date uptime) {
        this.uptime = uptime;
    }
}