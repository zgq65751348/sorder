package com.tlongx.sorder.order.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tl_temporary")
public class Temporary {
    @Id
    private Integer id;

    private Integer wfid;

    /**
     * 当天供应量
     */
    @Column(name = "daily_supply")
    private Integer dailySupply;

    /**
     * 已卖
     */
    @Column(name = "sale_num")
    private Integer saleNum;

    /**
     * 预定时间
     */
    @Column(name = "sub_time")
    private Date subTime;

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
     * @return wfid
     */
    public Integer getWfid() {
        return wfid;
    }

    /**
     * @param wfid
     */
    public void setWfid(Integer wfid) {
        this.wfid = wfid;
    }

    /**
     * 获取当天供应量
     *
     * @return daily_supply - 当天供应量
     */
    public Integer getDailySupply() {
        return dailySupply;
    }

    /**
     * 设置当天供应量
     *
     * @param dailySupply 当天供应量
     */
    public void setDailySupply(Integer dailySupply) {
        this.dailySupply = dailySupply;
    }

    /**
     * 获取已卖
     *
     * @return sale_num - 已卖
     */
    public Integer getSaleNum() {
        return saleNum;
    }

    /**
     * 设置已卖
     *
     * @param saleNum 已卖
     */
    public void setSaleNum(Integer saleNum) {
        this.saleNum = saleNum;
    }

    /**
     * 获取预定时间
     *
     * @return sub_time - 预定时间
     */
    public Date getSubTime() {
        return subTime;
    }

    /**
     * 设置预定时间
     *
     * @param subTime 预定时间
     */
    public void setSubTime(Date subTime) {
        this.subTime = subTime;
    }
}