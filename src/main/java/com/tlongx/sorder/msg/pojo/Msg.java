package com.tlongx.sorder.msg.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tl_msg")
@ApiModel
public class Msg {
    @Id
    private Integer mid;

    /**
     * 推送平台( 0:ALL 1:IOS  2:Android )
     */
    @Column(name = "push_platform")
    @ApiModelProperty(value = "推送平台( 0:ALL 1:IOS  2:Android )",example = "1")
    private Integer pushPlatform;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     * 消息推送对象 1学生端 2食堂 3 供应商
     */
    @Column(name = "push_obj")
    @ApiModelProperty(value = "消息推送对象 1学生端 2食堂 3 供应商",example = "1")
    private Integer pushObj;

    /**
     * 学校id
     */
    @ApiModelProperty(value = "学校id",example = "1")
    private Integer sid;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date crtime;

    /**
     * 内容
     */
    @ApiModelProperty(value = "推送内容")
    private String context;

    private String[] alias;

    public String[] getAlias() {
        return alias;
    }

    public void setAlias(String[] alias) {
        this.alias = alias;
    }

    /**
     * @return mid
     */
    public Integer getMid() {
        return mid;
    }

    /**
     * @param mid
     */
    public void setMid(Integer mid) {
        this.mid = mid;
    }

    /**
     * 获取推送平台( 0:ALL 1:IOS  2:Android )
     *
     * @return push_platform - 推送平台( 0:ALL 1:IOS  2:Android )
     */
    public Integer getPushPlatform() {
        return pushPlatform;
    }

    /**
     * 设置推送平台( 0:ALL 1:IOS  2:Android )
     *
     * @param pushPlatform 推送平台( 0:ALL 1:IOS  2:Android )
     */
    public void setPushPlatform(Integer pushPlatform) {
        this.pushPlatform = pushPlatform;
    }

    /**
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取消息推送对象 1学生端 2食堂 3 供应商
     *
     * @return push_obj - 消息推送对象 1学生端 2食堂 3 供应商
     */
    public Integer getPushObj() {
        return pushObj;
    }

    /**
     * 设置消息推送对象 1学生端 2食堂 3 供应商
     *
     * @param pushObj 消息推送对象 1学生端 2食堂 3 供应商
     */
    public void setPushObj(Integer pushObj) {
        this.pushObj = pushObj;
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
     * @return crtime
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
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
     * 获取内容
     *
     * @return context - 内容
     */
    public String getContext() {
        return context;
    }

    /**
     * 设置内容
     *
     * @param context 内容
     */
    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }
}