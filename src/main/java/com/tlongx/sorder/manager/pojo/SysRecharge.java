package com.tlongx.sorder.manager.pojo;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tl_sys_recharge")
public class SysRecharge {
    @Id
    private Integer id;

    /**
     * 余额改变量
     */
    private BigDecimal price;

    /**
     * 1增加 2减少
     */
    private Integer ie;

    private Integer sid;

    private String uid;

    private Date crtime;

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
     * 获取余额改变量
     *
     * @return price - 余额改变量
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置余额改变量
     *
     * @param price 余额改变量
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取1增加 2减少
     *
     * @return ie - 1增加 2减少
     */
    public Integer getIe() {
        return ie;
    }

    /**
     * 设置1增加 2减少
     *
     * @param ie 1增加 2减少
     */
    public void setIe(Integer ie) {
        this.ie = ie;
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
     * @return uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * @param uid
     */
    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
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
}