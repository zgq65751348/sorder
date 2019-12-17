package com.tlongx.sorder.order.pojo;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tl_recharge")
public class Recharge {
    @Id
    private Integer id;

    private BigDecimal price;

    private String photo;

    /**
     * 2微信 3支付宝 4农行
     */
    private Integer type;

    private Date crtime;

    private Date uptime;

    private Integer sid;

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
     * @return price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * @param photo
     */
    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
    }

    /**
     * 获取2微信 3支付宝 4农行
     *
     * @return type - 2微信 3支付宝 4农行
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置2微信 3支付宝 4农行
     *
     * @param type 2微信 3支付宝 4农行
     */
    public void setType(Integer type) {
        this.type = type;
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
     * @return uptime
     */
    public Date getUptime() {
        return uptime;
    }

    /**
     * @param uptime
     */
    public void setUptime(Date uptime) {
        this.uptime = uptime;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }
}