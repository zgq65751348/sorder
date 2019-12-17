package com.tlongx.sorder.manager.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tl_window")
@ApiModel
public class Window {
    /**
     * 窗口id
     */
    @Id
    @ApiModelProperty(value = "窗口id",example = "1")
    private Integer wid;

    /**
     * 窗口名称
     */
    @ApiModelProperty(value = "窗口名称")
    private String wname;

    /**
     * 学校id
     */
    @ApiModelProperty(value = "学校id",example = "1")
    private Integer sid;

    /**
     * 食堂id
     */
    @ApiModelProperty(value = "食堂id",example = "1")
    private Integer cid;

    /**
     * 窗口类型 1普通窗口 2水果超市 3便利小超
     */
    @ApiModelProperty(value = "窗口类型 1普通窗口 2水果超市 3便利小超",example = "1")
    private Integer type;

    /**
     * 是否显示窗口 0否 1是
     */
    @ApiModelProperty(value = "是否显示窗口 0否 1是",example = "1")
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
     * 获取食堂id
     *
     * @return cid - 食堂id
     */
    public Integer getCid() {
        return cid;
    }

    /**
     * 设置食堂id
     *
     * @param cid 食堂id
     */
    public void setCid(Integer cid) {
        this.cid = cid;
    }

    /**
     * 获取是否显示窗口 0否 1是
     *
     * @return status - 是否显示窗口 0否 1是
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置是否显示窗口 0否 1是
     *
     * @param status 是否显示窗口 0否 1是
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