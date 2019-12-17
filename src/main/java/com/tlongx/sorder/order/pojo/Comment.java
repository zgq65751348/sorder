package com.tlongx.sorder.order.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tl_comment")
@ApiModel
public class Comment {
    @Id
    private Integer id;

    /**
     * 评论人uid
     */
    @ApiModelProperty(name = "评论人uid")
    private String uid;

    /**
     * 订单条目id
     */
    @ApiModelProperty(name = "订单条目id",example = "1")
    private Integer oid;

    /**
     * 食品id
     */
    @ApiModelProperty(name = "食品id",example = "1")
    private Integer fid;

    /**
     * 食品名称
     */
    @Column(name = "food_name")
    @ApiModelProperty(name = "食品名称")
    private String foodName;

    /**
     * 1: 食堂  2:供应商
     */
    @Column(name = "food_type")
    @ApiModelProperty(name = "1: 食堂  2:供应商",example = "1")
    private Integer foodType;

    /**
     * 评论内容
     */
    @ApiModelProperty(name = "评论内容")
    private String remark;

    /**
     * 商家回复
     */
    @ApiModelProperty(name = "商家回复")
    private String reply;

    /**
     * 0不可见 1可见
     */
    @ApiModelProperty(name = "0不可见 1可见",example = "1")
    private Integer status;

    /**
     * 图片评论
     */
    @ApiModelProperty(name = "图片评论")
    private String photo;

    /**
     * 评分
     */
    @ApiModelProperty(name = "评分",example = "1")
    private BigDecimal score;

    /**
     * 是否匿名 0否1是
     */
    @ApiModelProperty(name = "是否匿名 0否1是",example = "1")
    private Integer anomy;

    /**
     * 食堂id
     */
    @ApiModelProperty(name = "食堂id",example = "1")
    private Integer cid;

    /**
     * 学校id
     */
    @ApiModelProperty(name = "学校id",example = "1")
    private Integer sid;

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
     * 获取评论人uid
     *
     * @return uid - 评论人uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * 设置评论人uid
     *
     * @param uid 评论人uid
     */
    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    /**
     * 获取订单条目id
     *
     * @return oid - 订单条目id
     */
    public Integer getOid() {
        return oid;
    }

    /**
     * 设置订单条目id
     *
     * @param oid 订单条目id
     */
    public void setOid(Integer oid) {
        this.oid = oid;
    }

    /**
     * 获取食品id
     *
     * @return fid - 食品id
     */
    public Integer getFid() {
        return fid;
    }

    /**
     * 设置食品id
     *
     * @param fid 食品id
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
     * 获取1: 食堂  2:供应商
     *
     * @return food_type - 1: 食堂  2:供应商
     */
    public Integer getFoodType() {
        return foodType;
    }

    /**
     * 设置1: 食堂  2:供应商
     *
     * @param foodType 1: 食堂  2:供应商
     */
    public void setFoodType(Integer foodType) {
        this.foodType = foodType;
    }

    /**
     * 获取评论内容
     *
     * @return remark - 评论内容
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置评论内容
     *
     * @param remark 评论内容
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 获取商家回复
     *
     * @return reply - 商家回复
     */
    public String getReply() {
        return reply;
    }

    /**
     * 设置商家回复
     *
     * @param reply 商家回复
     */
    public void setReply(String reply) {
        this.reply = reply == null ? null : reply.trim();
    }

    /**
     * 获取0不可见 1可见
     *
     * @return status - 0不可见 1可见
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0不可见 1可见
     *
     * @param status 0不可见 1可见
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取图片评论
     *
     * @return photo - 图片评论
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * 设置图片评论
     *
     * @param photo 图片评论
     */
    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    /**
     * 获取是否匿名 0否1是
     *
     * @return anomy - 是否匿名 0否1是
     */
    public Integer getAnomy() {
        return anomy;
    }

    /**
     * 设置是否匿名 0否1是
     *
     * @param anomy 是否匿名 0否1是
     */
    public void setAnomy(Integer anomy) {
        this.anomy = anomy;
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