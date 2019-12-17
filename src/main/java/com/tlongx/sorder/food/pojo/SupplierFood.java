package com.tlongx.sorder.food.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tl_supplier_food")
@ApiModel
public class SupplierFood {
    @Id
    @ApiModelProperty(value = "食品id",example = "1")
    private Integer fid;

    /**
     * 食品名称
     */
    @Column(name = "food_name")
    @ApiModelProperty(value = "食品名称")
    private String foodName;

    /**
     * 食品描述
     */
    @Column(name = "food_rmk")
    @ApiModelProperty(value = "食品描述")
    private String foodRmk;


    /**
     * 状态 是否可用 0不可用 1可用
     */
    @ApiModelProperty(value = "状态 是否可用 0不可用 1可用",example = "1")
    private Integer status;

    /**
     * 食品图片
     */
    @ApiModelProperty(value = "食品图片")
    private String photo;

    /**
     * 评分
     */
    @ApiModelProperty(value = "评分")
    private BigDecimal score;

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
     * 食品价格
     */
    @ApiModelProperty(value = "食品价格",example = "1.55")
    private BigDecimal price;

    /**
     * 供应商id
     */
    @Column(name = "uid")
    @ApiModelProperty(value = "供应商id")
    private String uid;

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * @return fid
     */
    public Integer getFid() {
        return fid;
    }

    /**
     * @param fid
     */
    public void setFid(Integer fid) {
        this.fid = fid;
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
     * 获取食品描述
     *
     * @return food_rmk - 食品描述
     */
    public String getFoodRmk() {
        return foodRmk;
    }

    /**
     * 设置食品描述
     *
     * @param foodRmk 食品描述
     */
    public void setFoodRmk(String foodRmk) {
        this.foodRmk = foodRmk == null ? null : foodRmk.trim();
    }


    /**
     * 获取是否可用 0不可用 1可用
     *
     * @return status - 是否可用 0不可用 1可用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置是否可用 0不可用 1可用
     *
     * @param status 是否可用 0不可用 1可用
     */
    public void setStatus(Integer status) {
        this.status = status;
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
     * 获取食品价格
     *
     * @return price - 食品价格
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置食品价格
     *
     * @param price 食品价格
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }


}