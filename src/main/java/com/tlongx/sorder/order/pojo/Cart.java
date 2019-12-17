package com.tlongx.sorder.order.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tl_cart")
@ApiModel
public class Cart {
    @Id
    private Long id;

    /**
     * 关联人uid
     */
    @ApiModelProperty(value = "关联人id")
    private String uid;

    /**
     * 窗口食品id
     */
    @ApiModelProperty(value = "窗口食品id",example = "1")
    private Integer wfid;

    /**
     * 食品数量
     */
    @ApiModelProperty(value = "食品数量",example = "2")
    private Integer num;

    /**
     * 预定时间 格式：2019-02-22
     */
    @Column(name = "sub_date")
    @ApiModelProperty(value = "预定时间 格式：2019-02-22")
    private String subDate;

    /**
     * 0无效 1有效
     */
    @ApiModelProperty(value = "状态 0无效 1有效",example = "1")
    private Integer status;

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
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取关联人uid
     *
     * @return uid - 关联人uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * 设置关联人uid
     *
     * @param uid 关联人uid
     */
    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    /**
     * 获取窗口食品id
     *
     * @return wfid - 窗口食品id
     */
    public Integer getWfid() {
        return wfid;
    }

    /**
     * 设置窗口食品id
     *
     * @param wfid 窗口食品id
     */
    public void setWfid(Integer wfid) {
        this.wfid = wfid;
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
     * 获取预定时间 格式：2019-02-22
     *
     * @return sub_date - 预定时间 格式：2019-02-22
     */
    public String getSubDate() {
        return subDate;
    }

    /**
     * 设置预定时间 格式：2019-02-22
     *
     * @param subDate 预定时间 格式：2019-02-22
     */
    public void setSubDate(String subDate) {
        this.subDate = subDate == null ? null : subDate.trim();
    }

    /**
     * 获取0无效 1有效
     *
     * @return status - 0无效 1有效
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0无效 1有效
     *
     * @param status 0无效 1有效
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取创建时间
     *
     * @return crtime - 创建时间
     */
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