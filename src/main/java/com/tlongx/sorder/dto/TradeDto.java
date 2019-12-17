package com.tlongx.sorder.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public class TradeDto {
    @ApiModelProperty(value = "日期")
    private String crtime;
    @ApiModelProperty(value = "年级")
    private Integer grade;
    @ApiModelProperty(value = "班级")
    private Integer classNum;
    @ApiModelProperty(value = "流水")
    private BigDecimal price;
    @ApiModelProperty(value = "收支")
    private Integer ie;
    @ApiModelProperty(value = "余额")
    private BigDecimal balance;
    @ApiModelProperty(value = "年级类型")
    private Integer type;
    @ApiModelProperty(value = "筛选开始时间")
    private String startTime;
    @ApiModelProperty(value = "筛选结束时间")
    private String endTime;
    @ApiModelProperty(value = "学校id")
    private Integer sid;
    @ApiModelProperty(value = "pageNum")
    private Integer pageNum;
    @ApiModelProperty(value = "pageSize")
    private  Integer pageSize;
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "补贴")
    private BigDecimal subSidyPrice;
    @ApiModelProperty(value = "补贴余额")
    private BigDecimal subsidy;
    @ApiModelProperty(value = "院校名称")
    private String sname;
    @ApiModelProperty(value = "交易类型")
    private Integer tradeType;
    @ApiModelProperty(value = "支付凭证 最多五条")
    private String payOrder;
    @ApiModelProperty(value = "交易账号（微信/支付宝/银行卡号）")
    private String account;
    @ApiModelProperty(value = "操作人id 后台-平台添加付款记录时添加")
    private String mid;
    @ApiModelProperty(value = "操作人姓名")
    private  String mname;
    @ApiModelProperty(value ="流水id")
    private String tid;

    public String getCrtime() {
        return crtime;
    }

    public void setCrtime(String crtime) {
        this.crtime = crtime;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getClassNum() {
        return classNum;
    }

    public void setClassNum(Integer classNum) {
        this.classNum = classNum;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getIe() {
        return ie;
    }

    public void setIe(Integer ie) {
        this.ie = ie;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getSubSidyPrice() {
        return subSidyPrice;
    }

    public void setSubSidyPrice(BigDecimal subSidyPrice) {
        this.subSidyPrice = subSidyPrice;
    }

    public BigDecimal getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(BigDecimal subsidy) {
        this.subsidy = subsidy;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    public String getPayOrder() {
        return payOrder;
    }

    public void setPayOrder(String payOrder) {
        this.payOrder = payOrder;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }
}
