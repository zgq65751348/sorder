package com.tlongx.sorder.manager.pojo;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tl_funds_statistics")
public class FundsStatistics {
    @Id
    private Integer id;

    /**
     * 收入
     */
    private BigDecimal income;

    /**
     * 支出
     */
    private BigDecimal expand;

    /**
     * 学校id
     */
    private Integer sid;

    /**
     * 总额
     */
    @Column(name = "total_price")
    private BigDecimal totalPrice;

    private Date crtime;

    /**
     * 类型：1管理平台 2学校平台 3学生钱包
     */
    private Integer type;

    /**
     * 系统充值
     */
    @Column(name = "sys_recharge")
    private BigDecimal sysRecharge;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取收入
     *
     * @return income - 收入
     */
    public BigDecimal getIncome() {
        return income;
    }

    /**
     * 设置收入
     *
     * @param income 收入
     */
    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    /**
     * 获取支出
     *
     * @return expand - 支出
     */
    public BigDecimal getExpand() {
        return expand;
    }

    /**
     * 设置支出
     *
     * @param expand 支出
     */
    public void setExpand(BigDecimal expand) {
        this.expand = expand;
    }

    /**
     * 获取学校id
     *
     * @return sid - 学校id
     */
    public Integer getSid() {
        return sid;
    }

    /**
     * 设置学校id
     *
     * @param sid 学校id
     */
    public void setSid(Integer sid) {
        this.sid = sid;
    }

    /**
     * 获取总额
     *
     * @return total_price - 总额
     */
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    /**
     * 设置总额
     *
     * @param totalPrice 总额
     */
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * @return crtime
     */
    public Date getCrtime() {
        return crtime;
    }

    /**
     * @param crtime
     */
    public void setCrtime(Date crtime) {
        this.crtime = crtime;
    }

    /**
     * 获取类型：1管理平台 2学校平台 3学生钱包
     *
     * @return type - 类型：1管理平台 2学校平台 3学生钱包
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型：1管理平台 2学校平台 3学生钱包
     *
     * @param type 类型：1管理平台 2学校平台 3学生钱包
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取系统充值
     *
     * @return sys_recharge - 系统充值
     */
    public BigDecimal getSysRecharge() {
        return sysRecharge;
    }

    /**
     * 设置系统充值
     *
     * @param sysRecharge 系统充值
     */
    public void setSysRecharge(BigDecimal sysRecharge) {
        this.sysRecharge = sysRecharge;
    }
}