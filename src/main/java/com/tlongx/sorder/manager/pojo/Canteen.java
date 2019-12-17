package com.tlongx.sorder.manager.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tl_canteen")
@ApiModel
public class Canteen {
    @Id
    @ApiModelProperty(value = "食堂id",example = "2")
    private Integer cid;

    /**
     * 食堂名称
     */
    @ApiModelProperty(value = "食堂名称")
    private String cname;

    /**
     * 学校id
     */
    @ApiModelProperty(value = "学校id",example = "1")
    private Integer sid;

    /**
     * 学校名称
     */
    @ApiModelProperty(value = "学校名称")
    private String sname;

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
     * @return cid
     */
    public Integer getCid() {
        return cid;
    }

    /**
     * @param cid
     */
    public void setCid(Integer cid) {
        this.cid = cid;
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
     * 获取学校名称
     *
     * @return sname - 学校名称
     */
    public String getSname() {
        return sname;
    }

    /**
     * 设置学校名称
     *
     * @param sname 学校名称
     */
    public void setSname(String sname) {
        this.sname = sname == null ? null : sname.trim();
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