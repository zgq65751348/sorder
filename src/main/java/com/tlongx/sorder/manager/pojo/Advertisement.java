package com.tlongx.sorder.manager.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tl_advertisement")
@ApiModel
public class Advertisement {
    @Id
    private Integer id;

    /**
     * 学校id
     */
    @ApiModelProperty(value = "学校id",example = "5")
    private Integer sid;

    /**
     * 图片地址
     */
    @ApiModelProperty(value = "图片地址")
    private String photo;

    @ApiModelProperty(value = "供应类型",example = "1")
    private Integer type;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date crtime;


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
     * 获取图片地址
     *
     * @return photo - 图片地址
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * 设置图片地址
     *
     * @param photo 图片地址
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
}