package com.tlongx.sorder.user.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tl_feedback")
@ApiModel
public class FeedBack {
    @Id
    private Integer id;

    /**
     * 反馈内容
     */
    @ApiModelProperty(value = "反馈内容")
    private String content;

    /**
     * 关联人uid
     */
    @ApiModelProperty(value = "关联人uid")
    private String uid;

    /**
     * 创建时间
     */
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
     * 获取反馈内容
     *
     * @return content - 反馈内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置反馈内容
     *
     * @param content 反馈内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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
}