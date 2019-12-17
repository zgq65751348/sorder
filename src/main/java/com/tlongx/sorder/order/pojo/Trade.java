package com.tlongx.sorder.order.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tl_trade")
@ApiModel
public class Trade {
    /**
     * 流水id
     */
    @Id
    private String tid;

    /**
     * 关联人uid
     */
    @ApiModelProperty(value = "关联人uid")
    private String uid;

    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单id")
    private String oid;
    @ApiModelProperty(value = "购物车ids")
    @Column(name = "cart_ids")
    private String cartIds;

    @ApiModelProperty(value = "是否使用补贴 0否 1是",example = "0")
    @Column(name = "use_subsidy")
    private Integer useSubsidy;

    /**
     * 商户订单号
     */
    @Column(name = "out_trade_no")
    @ApiModelProperty(value = "商户订单号")
    private String outTradeNo;

    /**
     * 流水类型
     */
    @Column(name = "trade_type")
    @ApiModelProperty(value = "流水类型 1钱包充值 2订单 3订单退款 4提现 5系统发放补贴 6单独发放补贴",example = "1")
    private Integer tradeType;

    /**
     * 交易类型 1钱包 2微信 3支付宝
     */
    @Column(name = "pay_type")
    @ApiModelProperty(value = "交易类型 0补贴 1钱包 2微信 3支付宝",example = "1")
    private Integer payType;

    /**
     * 交易金额
     */
    @ApiModelProperty(value = "交易金额",example = "1.5")
    private BigDecimal price;

    /**
     * 交易账号（微信/支付宝）
     */
    @ApiModelProperty(value = "交易账号（微信/支付宝）")
    private String account;

    /**
     * 交易状态（0待审核  1已完成  2待支付  3提现失败）
     */
    @ApiModelProperty(value = "交易状态（0待审核  1已完成  2待支付  3提现失败）",example = "1")
    private Integer status;

    /**
     * 提现失败原因
     */
    @ApiModelProperty(value = "提现失败原因")
    private String cause;

    /**
     * 收支（1收入 2支出）
     */
    @ApiModelProperty(value = "收支（1收入 2支出）",example = "1")
    private Integer ie;

    /**
     * 当前账户余额
     */
    @ApiModelProperty(value = "当前账户余额",example = "1.5")
    private BigDecimal balance;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date crtime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date uptime;

    @Column(name = "pay_order")
    @ApiModelProperty(value = "支付凭证 最多五条")
    private String payOrder;

    @Column(name = "mid")
    @ApiModelProperty(value="操作人id，平台添加付款记录时需要")
    private String mid;

    @Column(name = "subsidy_price")
    @ApiModelProperty(value = "补贴余额")
    private BigDecimal subsidyPrice;

    @Column(name = "subsidy")
    @ApiModelProperty(value = "补贴金额")
    private BigDecimal subsidy;

    @Column(name = "qr_code")
    private Integer qrCode;

    public Integer getUseSubsidy() {
        return useSubsidy;
    }

    public void setUseSubsidy(Integer useSubsidy) {
        this.useSubsidy = useSubsidy;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCartIds() {
        return cartIds;
    }

    public void setCartIds(String cartIds) {
        this.cartIds = cartIds;
    }

    /**
     * 获取流水id
     *
     * @return tid - 流水id
     */
    public String getTid() {
        return tid;
    }

    /**
     * 设置流水id
     *
     * @param tid 流水id
     */
    public void setTid(String tid) {
        this.tid = tid == null ? null : tid.trim();
    }

    /**
     * 获取关联人uid
     *
     * @return uid - 关联人uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * 设置关联人uid
     *
     * @param uid 关联人uid
     */
    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

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
     * 获取流水类型
     *
     * @return trade_type - 流水类型
     */
    public Integer getTradeType() {
        return tradeType;
    }

    /**
     * 设置流水类型
     *
     * @param tradeType 流水类型
     */
    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    /**
     * 获取交易类型 1钱包 2微信 3支付宝
     *
     * @return pay_type - 交易类型 1钱包 2微信 3支付宝
     */
    public Integer getPayType() {
        return payType;
    }

    /**
     * 设置交易类型 1钱包 2微信 3支付宝
     *
     * @param payType 交易类型 1钱包 2微信 3支付宝
     */
    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    /**
     * 获取交易金额
     *
     * @return price - 交易金额
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置交易金额
     *
     * @param price 交易金额
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取交易账号（微信/支付宝）
     *
     * @return account - 交易账号（微信/支付宝）
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置交易账号（微信/支付宝）
     *
     * @param account 交易账号（微信/支付宝）
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * 获取交易状态（0待审核  1已完成  2待支付  3提现失败）
     *
     * @return status - 交易状态（0待审核  1已完成  2待支付  3提现失败）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置交易状态（0待审核  1已完成  2待支付  3提现失败）
     *
     * @param status 交易状态（0待审核  1已完成  2待支付  3提现失败）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取提现失败原因
     *
     * @return cause - 提现失败原因
     */
    public String getCause() {
        return cause;
    }

    /**
     * 设置提现失败原因
     *
     * @param cause 提现失败原因
     */
    public void setCause(String cause) {
        this.cause = cause == null ? null : cause.trim();
    }

    /**
     * 获取收支（1收入 2支出）
     *
     * @return ie - 收支（1收入 2支出）
     */
    public Integer getIe() {
        return ie;
    }

    /**
     * 设置收支（1收入 2支出）
     *
     * @param ie 收支（1收入 2支出）
     */
    public void setIe(Integer ie) {
        this.ie = ie;
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

    public String getPayOrder() {
        return payOrder;
    }

    public void setPayOrder(String payOrder) {
        this.payOrder = payOrder;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public BigDecimal getSubsidyPrice() {
        return subsidyPrice;
    }

    public void setSubsidyPrice(BigDecimal subsidyPrice) {
        this.subsidyPrice = subsidyPrice;
    }

    public BigDecimal getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(BigDecimal subsidy) {
        this.subsidy = subsidy;
    }

    public Integer getQrCode() {
        return qrCode;
    }

    public void setQrCode(Integer qrCode) {
        this.qrCode = qrCode;
    }
}