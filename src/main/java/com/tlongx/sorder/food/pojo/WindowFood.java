package com.tlongx.sorder.food.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tl_window_food")
@ApiModel
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class WindowFood {
    @Id
    private Integer id;

    /**
     * 窗口id
     */
    @ApiModelProperty(value = "窗口id",example = "1")
    private Integer wid;

    /**
     * 食物id
     */
    @ApiModelProperty(value = "食物id",example = "1")
    private Integer fid;

    /**
     * 食堂供应价格
     */
    @ApiModelProperty(value = "食堂供应价格",example = "1.55")
    private BigDecimal price;

    /**
     * 食品类型 1.食堂自营 2.供应商
     */
    @ApiModelProperty(value = "食品类型 1.食堂自营 2.供应商",example = "1")
    private Integer type;

    /**
     * 是否上架 0否 1是
     */
    @ApiModelProperty(value = "是否上架 0否 1是",example = "1")
    private Integer status;

    /**
     * 评分
     */
//    @ApiModelProperty(value = "评分",example = "5.55")
//    private BigDecimal score;

    @ApiModelProperty(value = "当日供应量",example = "1")
    @Column(name = "daily_supply")
    private Integer dailySupply;


    @ApiModelProperty(value = "当日剩余供应量",example = "1")
    @Column(name = "residue_supply")
    private Integer residueSupply;

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

    public Integer getResidueSupply() {
        return residueSupply;
    }

    public void setResidueSupply(Integer residueSupply) {
        this.residueSupply = residueSupply;
    }

    public Integer getDailySupply() {
        return dailySupply;
    }

    public void setDailySupply(Integer dailySupply) {
        this.dailySupply = dailySupply;
    }

//    public BigDecimal getScore() {
//        return score;
//    }
//
//    public void setScore(BigDecimal score) {
//        this.score = score;
//    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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
     * 获取窗口id
     *
     * @return wid - 窗口id
     */
    public Integer getWid() {
        return wid;
    }

    /**
     * 设置窗口id
     *
     * @param wid 窗口id
     */
    public void setWid(Integer wid) {
        this.wid = wid;
    }

    /**
     * 获取食物id
     *
     * @return fid - 食物id
     */
    public Integer getFid() {
        return fid;
    }

    /**
     * 设置食物id
     *
     * @param fid 食物id
     */
    public void setFid(Integer fid) {
        this.fid = fid;
    }

    /**
     * 获取是否上架 0否 1是
     *
     * @return status - 是否上架 0否 1是
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置是否上架 0否 1是
     *
     * @param status 是否上架 0否 1是
     */
    public void setStatus(Integer status) {
        this.status = status;
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
}