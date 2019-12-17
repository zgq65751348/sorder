package com.tlongx.sorder.user.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tl_user_canteen")
@ApiModel
public class UserCanteen {
    @Id
    private String uid;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String uname;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

    /**
     * token
     */
    private String token;

    /**
     * 钱包余额
     */
    @ApiModelProperty(value = "钱包余额",example = "4.55")
    private Double balance;

    /**
     * 性别 1男 2女
     */
    private Integer sex;

    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像")
    private String portrait;

    /**
     * 用户真人照片
     */
    @ApiModelProperty(value = "用户真人照片")
    private String photo;

    /**
     * 职位
     */
    @ApiModelProperty(value = "职位")
    private String role;

    /**
     * 用户类型 1员工 2负责人
     */
    @ApiModelProperty(value = "用户类型 1员工 2负责人",example = "1")
    private Integer type;

    /**
     * 负责人id
     */
    @Column(name = "parent_id")
    @ApiModelProperty(value = "负责人id")
    private String parentId;

    /**
     * 学校id
     */
    @ApiModelProperty(value = "学校id",example = "2")
    private Integer sid;

    /**
     * 学校名称
     */
    @ApiModelProperty(value = "学校名称")
    private String sname;

    /**
     * 食堂id
     */
    @ApiModelProperty(value = "食堂id",example = "3")
    private Integer cid;

    /**
     * 食堂名称
     */
    @ApiModelProperty(value = "食堂名称")
    private String cname;

    /**
     * 窗口id
     */
    @ApiModelProperty(value = "窗口id",example = "4")
    private Integer wid;

    /**
     * 窗口姓名
     */
    @ApiModelProperty(value = "窗口姓名")
    private String wname;

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


    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    /**
     * @return uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * @param uid
     */
    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    /**
     * 获取姓名
     *
     * @return uname - 姓名
     */
    public String getUname() {
        return uname;
    }

    /**
     * 设置姓名
     *
     * @param uname 姓名
     */
    public void setUname(String uname) {
        this.uname = uname == null ? null : uname.trim();
    }

    /**
     * 获取手机号
     *
     * @return phone - 手机号
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置手机号
     *
     * @param phone 手机号
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取token
     *
     * @return token - token
     */
    public String getToken() {
        return token;
    }

    /**
     * 设置token
     *
     * @param token token
     */
    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    /**
     * 获取钱包余额
     *
     * @return balance - 钱包余额
     */
    public Double getBalance() {
        return balance;
    }

    /**
     * 设置钱包余额
     *
     * @param balance 钱包余额
     */
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    /**
     * 获取性别 1男 2女
     *
     * @return sex - 性别 1男 2女
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 设置性别 1男 2女
     *
     * @param sex 性别 1男 2女
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }


    /**
     * 获取用户真人照片
     *
     * @return photo - 用户真人照片
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * 设置用户真人照片
     *
     * @param photo 用户真人照片
     */
    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
    }

    /**
     * 获取职位
     *
     * @return role - 职位
     */
    public String getRole() {
        return role;
    }

    /**
     * 设置职位
     *
     * @param role 职位
     */
    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }

    /**
     * 获取用户类型 1员工 2负责人
     *
     * @return type - 用户类型 1员工 2负责人
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置用户类型 1员工 2负责人
     *
     * @param type 用户类型 1员工 2负责人
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取负责人id
     *
     * @return parent_id - 负责人id
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 设置负责人id
     *
     * @param parentId 负责人id
     */
    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
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
     * 获取窗口姓名
     *
     * @return wname - 窗口姓名
     */
    public String getWname() {
        return wname;
    }

    /**
     * 设置窗口姓名
     *
     * @param wname 窗口姓名
     */
    public void setWname(String wname) {
        this.wname = wname == null ? null : wname.trim();
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