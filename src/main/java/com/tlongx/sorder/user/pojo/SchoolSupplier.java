package com.tlongx.sorder.user.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;

@Table(name = "tl_school_supplier")
@ApiModel
public class SchoolSupplier {
    @Id
    private Integer id;

    /**
     * 学校id
     */
    @ApiModelProperty(value = "学校id",example = "1")
    private Integer sid;

    /**
     * 供应商id
     */
    @ApiModelProperty(value = "供应商id")
    private String uid;

    private String mid;

    /**
     * 学校是否启用该供应商状态  0停用  1启用
     */
    @ApiModelProperty(value = "学校是否启用该供应商状态  0停用  1启用",example = "1")
    private Integer status;

    private Date crtime;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public Date getCrtime() {
        return crtime;
    }

    public void setCrtime(Date crtime) {
        this.crtime = crtime;
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
     * 获取供应商id
     *
     * @return uid - 供应商id
     */
    public String getUid() {
        return uid;
    }

    /**
     * 设置供应商id
     *
     * @param uid 供应商id
     */
    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    /**
     * 获取学校是否启用该供应商状态  0停用  1启用
     *
     * @return status - 学校是否启用该供应商状态  0停用  1启用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置学校是否启用该供应商状态  0停用  1启用
     *
     * @param status 学校是否启用该供应商状态  0停用  1启用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}