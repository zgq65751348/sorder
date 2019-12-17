package com.tlongx.sorder.manager.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tl_discount_price")
@ApiModel
public class DiscountPrice {
    @Id
    private Integer id;

    /**
     * 学校id
     */
    @ApiModelProperty(value = "学校id",example = "1")
    private Integer sid;

    /**
     * 1:学生  2:班主任  3:普通教师
     */
    @ApiModelProperty(value = "角色 1:学生  2:班主任  3:普通教师",example = "2")
    private Integer role;

    /**
     * 1:满减 2:金额折扣
     */
    @ApiModelProperty(value = "类型 1:满减 2:金额折扣",example = "2")
    private Integer type;

    /**
     * 优惠价格
     */
    @Column(name = "discount_price")
    @ApiModelProperty(value = "优惠价格",example = "3.22")
    private BigDecimal discountPrice;

    /**
     * type=1 时  设置  满减金额
     */
    @ApiModelProperty(value = "status=1 时  设置  满减金额",example = "4.22")
    private BigDecimal price;

    /**
     * 开始时间
     */
    @Column(name = "start_time")
    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date startTime;

    /**
     * 结束时间
     */
    @Column(name = "end_time")
    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date endTime;

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
     * 获取1:学生  2:班主任  3:普通教师
     *
     * @return role - 1:学生  2:班主任  3:普通教师
     */
    public Integer getRole() {
        return role;
    }

    /**
     * 设置1:学生  2:班主任  3:普通教师
     *
     * @param role 1:学生  2:班主任  3:普通教师
     */
    public void setRole(Integer role) {
        this.role = role;
    }

    /**
     * 获取1:满减 2:金额折扣
     *
     * @return type - 1:满减 2:金额折扣
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置1:满减 2:金额折扣
     *
     * @param type 1:满减 2:金额折扣
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取优惠价格
     *
     * @return discount_price - 优惠价格
     */
    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    /**
     * 设置优惠价格
     *
     * @param discountPrice 优惠价格
     */
    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    /**
     * 获取status=1 时  设置  满减金额
     *
     * @return price - status=1 时  设置  满减金额
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置status=1 时  设置  满减金额
     *
     * @param price status=1 时  设置  满减金额
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取开始时间
     *
     * @return start_time - 开始时间
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 设置开始时间
     *
     * @param startTime 开始时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取结束时间
     *
     * @return end_time - 结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置结束时间
     *
     * @param endTime 结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}