package com.tlongx.sorder.manager.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tl_school")
@ApiModel
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class School {
    @Id
    @ApiModelProperty(value = "学校id",example = "1")
    private Integer sid;

    /**
     * 学校名称
     */
    @ApiModelProperty(value = "学校名称")
    private String sname;

    /**
     * 学校编号  限四位字母
     */
    @ApiModelProperty(value = "学校编号  限四位字母")
    private String snum;

    @ApiModelProperty(value = "余额")
    private BigDecimal balance;

    /**
     * 开通支付宝 0否 1是
     */
    @Column(name = "dredge_alipay")
    @ApiModelProperty(value = " 开通支付宝 0否 1是",example = "1")
    private Integer dredgeAlipay;
    /**
     * 开通微信 0否 1是
     */
    @Column(name = "dredge_wxpay")
    @ApiModelProperty(value = " 开通微信 0否 1是",example = "1")
    private Integer dredgeWxpay;
    /**
     * 开通人脸 0否 1是
     */
    @Column(name = "dredge_face")
    @ApiModelProperty(value = " 开通人脸 0否 1是",example = "1")
    private Integer dredgeFace;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date crtime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date uptime;

    /**
     * 是否启用补贴 0否 1是
     */
    @Column(name = "subsidy_status")
    @ApiModelProperty(value = "是否启用补贴 0否 1是",example = "1")
    private Integer subsidyStatus;

    /**
     * （教师）补贴金额
     */
    @Column(name = "subsidy_price")
    @ApiModelProperty(value = "（教师）补贴金额",example = "1.11")
    private BigDecimal subsidyPrice;

    /**
     * 补贴时间
     */
    @Column(name = "subsidy_time")
    @ApiModelProperty(value = "补贴时间")
    private Date subsidyTime;

    /**
     * 已提现金钱
     */
    @Column(name = "deposit_price")
    @ApiModelProperty(value = "已提现金钱",example = "1.23")
    private BigDecimal depositPrice;

    /**
     * 历史提现总金额
     */
    @Column(name = "total_deposit_price")
    @ApiModelProperty(value = "历史提现总金额",example = "1.54")
    private BigDecimal totalDepositPrice;

    @Column(name = "use_help")
    @ApiModelProperty(value = "使用帮助")
    private String useHelp;

    @Column(name = "about_us")
    @ApiModelProperty(value = "关于我们")
    private String aboutUs;

    @Column(name = "opening_bank")
    @ApiModelProperty(value = "开户行")
    private String openingBank;

    @Column(name = "bank_username")
    @ApiModelProperty(value = "户名")
    private String bankUserName;

    @Column(name = "bank_account")
    @ApiModelProperty(value = "银行账户")
    private String bankAccount;

    public String getUseHelp() {
        return useHelp;
    }

    public void setUseHelp(String useHelp) {
        this.useHelp = useHelp;
    }

    public String getAboutUs() {
        return aboutUs;
    }

    public void setAboutUs(String aboutUs) {
        this.aboutUs = aboutUs;
    }

    public Integer getDredgeAlipay() {
        return dredgeAlipay;
    }

    public void setDredgeAlipay(Integer dredgeAlipay) {
        this.dredgeAlipay = dredgeAlipay;
    }

    public Integer getDredgeWxpay() {
        return dredgeWxpay;
    }

    public void setDredgeWxpay(Integer dredgeWxpay) {
        this.dredgeWxpay = dredgeWxpay;
    }

    public Integer getDredgeFace() {
        return dredgeFace;
    }

    public void setDredgeFace(Integer dredgeFace) {
        this.dredgeFace = dredgeFace;
    }

    public String getSnum() {
        return snum;
    }

    public void setSnum(String snum) {
        this.snum = snum;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * @return sid
     */
    public Integer getSid() {
        return sid;
    }

    /**
     * @param sid
     */
    public void setSid(Integer sid) {
        this.sid = sid;
    }

    /**
     * 获取学校名称
     *
     * @return sname - 学校名称
     */
    public String getSname() {
        return sname;
    }

    /**
     * 设置学校名称
     *
     * @param sname 学校名称
     */
    public void setSname(String sname) {
        this.sname = sname == null ? null : sname.trim();
    }

    /**
     * 获取创建时间
     *
     * @return crtime - 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
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
     * 获取修改时间
     *
     * @return uptime - 修改时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Date getUptime() {
        return uptime;
    }

    /**
     * 设置修改时间
     *
     * @param uptime 修改时间
     */
    public void setUptime(Date uptime) {
        this.uptime = uptime;
    }

    /**
     * 获取是否启用补贴 0否 1是
     *
     * @return subsidy_status - 是否启用补贴 0否 1是
     */
    public Integer getSubsidyStatus() {
        return subsidyStatus;
    }

    /**
     * 设置是否启用补贴 0否 1是
     *
     * @param subsidyStatus 是否启用补贴 0否 1是
     */
    public void setSubsidyStatus(Integer subsidyStatus) {
        this.subsidyStatus = subsidyStatus;
    }

    /**
     * 获取（教师）补贴金额
     *
     * @return subsidy_price - （教师）补贴金额
     */
    public BigDecimal getSubsidyPrice() {
        return subsidyPrice;
    }

    /**
     * 设置（教师）补贴金额
     *
     * @param subsidyPrice （教师）补贴金额
     */
    public void setSubsidyPrice(BigDecimal subsidyPrice) {
        this.subsidyPrice = subsidyPrice;
    }

    /**
     * 获取补贴时间
     *
     * @return subsidy_time -补贴时间
     */
    public Date getSubsidyTime() {
        return subsidyTime;
    }

    /**
     * 设置重置时间
     *
     * @param subsidyTime 补贴时间
     */
    public void setSubsidyTime(Date subsidyTime) {
        this.subsidyTime = subsidyTime;
    }

    /**
     * 获取已提现金钱
     *
     * @return deposit_price - 已提现金钱
     */
    public BigDecimal getDepositPrice() {
        return depositPrice;
    }

    /**
     * 设置已提现金钱
     *
     * @param depositPrice 已提现金钱
     */
    public void setDepositPrice(BigDecimal depositPrice) {
        this.depositPrice = depositPrice;
    }

    /**
     * 获取历史提现总金额
     *
     * @return total_deposit_price - 历史提现总金额
     */
    public BigDecimal getTotalDepositPrice() {
        return totalDepositPrice;
    }

    /**
     * 设置历史提现总金额
     *
     * @param totalDepositPrice 历史提现总金额
     */
    public void setTotalDepositPrice(BigDecimal totalDepositPrice) {
        this.totalDepositPrice = totalDepositPrice;
    }

    public String getOpeningBank() {
        return openingBank;
    }

    public void setOpeningBank(String openingBank) {
        this.openingBank = openingBank;
    }

    public String getBankUserName() {
        return bankUserName;
    }

    public void setBankUserName(String bankUserName) {
        this.bankUserName = bankUserName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }
}