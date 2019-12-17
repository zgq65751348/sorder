package com.tlongx.sorder.order.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tl_order_item")
@ApiModel
public class OrderItem {
    @Id
    private Integer id;

    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单id")
    private String oid;

    @ApiModelProperty(value = "uid")
    private String uid;
    @ApiModelProperty(value = "窗口食品id",example = "1")
    private Integer wfid;
    @ApiModelProperty(value = "供应时段类型 1早餐 2午餐 3晚餐 4水果超市 5便利小超 6特色早餐 7特色午餐 8特色晚餐",example = "1")
    private Integer type;

    /**
     * 食品名称
     */
    @Column(name = "food_name")
    @ApiModelProperty(value = "食品名称")
    private String foodName;

    /**
     * 食堂名称
     */
    @ApiModelProperty(value = "食堂名称")
    private String cname;

    /**
     * 窗口名称
     */
    @ApiModelProperty(value = "窗口名称")
    private String wname;

    /**
     * 食品单价
     */
    @ApiModelProperty(value = "食品单价")
    private BigDecimal price;

    /**
     * 食品数量
     */
    @ApiModelProperty(value = "食品数量",example = "1")
    private Integer num;

    /**
     * 总价
     */
    @Column(name = "total_price")
    @ApiModelProperty(value = "总价",example = "1")
    private BigDecimal totalPrice;

    /**
     * 食品图片
     */
    @ApiModelProperty(value = "食品图片")
    private String photo;

    /**
     * 预定时间
     */
    @Column(name = "sub_time")
    @ApiModelProperty(value = "预定时间")
    private String subTime;

    /**
     * 退款时间
     */
    @ApiModelProperty(value = "退款时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @Column(name = "refund_time")
    private Date refundTime;


    /**
     * 0:订单已关闭 1:待付款 2:付款失败 3:付款成功=生成二维码 4:已完成 二维码已扫 5:退款 6.订单已评论  
     */
    @ApiModelProperty(value = "0:订单已关闭 1:待付款 2:付款失败 3:付款成功=生成二维码 4:已完成 二维码已扫 5:退款 6.订单已评论 ",example = "1")
    private Integer status;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date crtime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date uptime;

    public Date getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Date refundTime) {
        this.refundTime = refundTime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getWfid() {
        return wfid;
    }

    public void setWfid(Integer wfid) {
        this.wfid = wfid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

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
     * 获取食品名称
     *
     * @return food_name - 食品名称
     */
    public String getFoodName() {
        return foodName;
    }

    /**
     * 设置食品名称
     *
     * @param foodName 食品名称
     */
    public void setFoodName(String foodName) {
        this.foodName = foodName == null ? null : foodName.trim();
    }

    /**
     * 获取食堂名称
     *
     * @return cname - 食堂名称
     */
    public String getCname() {
        return cname;
    }

    /**
     * 设置食堂名称
     *
     * @param cname 食堂名称
     */
    public void setCname(String cname) {
        this.cname = cname == null ? null : cname.trim();
    }

    /**
     * 获取窗口名称
     *
     * @return wname - 窗口名称
     */
    public String getWname() {
        return wname;
    }

    /**
     * 设置窗口名称
     *
     * @param wname 窗口名称
     */
    public void setWname(String wname) {
        this.wname = wname == null ? null : wname.trim();
    }

    /**
     * 获取食品单价
     *
     * @return price - 食品单价
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置食品单价
     *
     * @param price 食品单价
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取食品数量
     *
     * @return num - 食品数量
     */
    public Integer getNum() {
        return num;
    }

    /**
     * 设置食品数量
     *
     * @param num 食品数量
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * 获取总价
     *
     * @return total_price - 总价
     */
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    /**
     * 设置总价
     *
     * @param totalPrice 总价
     */
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * 获取食品图片
     *
     * @return photo - 食品图片
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * 设置食品图片
     *
     * @param photo 食品图片
     */
    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
    }

    /**
     * 获取预定时间
     *
     * @return sub_time - 预定时间
     */
    public String getSubTime() {
        return subTime;
    }

    /**
     * 设置预定时间
     *
     * @param subTime 预定时间
     */
    public void setSubTime(String subTime) {
        this.subTime = subTime == null ? null : subTime.trim();
    }


    /**
     * 获取0:订单已关闭 1:待付款 2:付款失败 3:付款成功=生成二维码 4:已完成 二维码已扫 5:退款 6.订单已评论  
     *
     * @return status - 0:订单已关闭 1:待付款 2:付款失败 3:付款成功=生成二维码 4:已完成 二维码已扫 5:退款 6.订单已评论  
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0:订单已关闭 1:待付款 2:付款失败 3:付款成功=生成二维码 4:已完成 二维码已扫 5:退款 6.订单已评论  
     *
     * @param status 0:订单已关闭 1:待付款 2:付款失败 3:付款成功=生成二维码 4:已完成 二维码已扫 5:退款 6.订单已评论  
     */
    public void setStatus(Integer status) {
        this.status = status;
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
}