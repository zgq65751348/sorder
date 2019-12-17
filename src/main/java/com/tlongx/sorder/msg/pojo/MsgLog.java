package com.tlongx.sorder.msg.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tl_msg_log")
@ApiModel
public class MsgLog {
    @Id
    private Integer id;


    /**
     * 消息id
     */
    @ApiModelProperty(value = "消息id",example = "1")
    private Integer mid;

    /**
     * 被推送人uid
     */
    @ApiModelProperty(value = "被推送人uid")
    private String uid;

    /**
     * 0未读 1已读
     */
    @ApiModelProperty(value = "0未读 1已读",example = "1")
    private Integer status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date crtime;

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
     * 获取消息id
     *
     * @return mid - 消息id
     */
    public Integer getMid() {
        return mid;
    }

    /**
     * 设置消息id
     *
     * @param mid 消息id
     */
    public void setMid(Integer mid) {
        this.mid = mid;
    }

    /**
     * 获取0未读 1已读
     *
     * @return status - 0未读 1已读
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0未读 1已读
     *
     * @param status 0未读 1已读
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}