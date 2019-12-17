package com.tlongx.sorder.user.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tl_user")
@ApiModel
public class User {
    /**
     * 用户id
     */
    @Id
    private String uid;

    /**
     * 用户名/姓名
     */
    @ApiModelProperty(value = "用户名/姓名")
    private String username;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

    /**
     * token令牌
     */
    private String token;

    /**
     * 支付密码
     */
    @Column(name = "pay_password")
    @ApiModelProperty(value = "支付密码")
    private String payPassword;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别(1男 2女)
     */
    private Integer sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户类型（1学生 2教师 3班主任 4其他人员）
     */
    @ApiModelProperty(value = "用户类型（1学生 2教师 3班主任 4其他人员）",example = "1")
    private Integer type;

    /**
     * 状态（0未通过 1通过 2待审核）
     */
    @ApiModelProperty(value = "状态（0未通过 1通过 2待审核）",example = "0")
    private Integer status;

    /**
     * 学校id
     */
    @ApiModelProperty(value = "学校id",example = "2")
    private Integer sid;

    /**
     * 年级id
     */
    @ApiModelProperty(value = "年级id",example = "3")
    private Integer cid;

    /**
     * 钱包金额
     */
    @ApiModelProperty(value = "钱包金额",example = "5.55")
    private BigDecimal balance;

    /**
     * 学号
     */
    @Column(name = "stu_num")
    @ApiModelProperty(value = "学号")
    private String stuNum;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String icon;


    @ApiModelProperty(value = "口味")
    private String taste;
    @ApiModelProperty(value = "是否开通人脸 0未开通 1已开通")
    private Integer isFace;

    /**
     * 创建时间/注册时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date crtime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date uptime;
    

    /**
     * 补贴金额
     */
    @Column(name = "subsidy_price")
    @ApiModelProperty(value = "补贴金额",example = "6.66")
    private BigDecimal subsidyPrice;

    /**
     * 早餐补贴状态（老师 ）（0未启用 1启用 ）
     */
    @Column(name = "bfsb_status")
    @ApiModelProperty(value = "早餐补贴状态（老师 ）（0未启用 1启用 ）",example = "true")
    private Boolean bfsbStatus;

    /**
     * 午餐补贴状态（老师 ）（0未启用 1启用 ）
     */
    @Column(name = "lsb_status")
    @ApiModelProperty(value = "午餐补贴状态（老师 ）（0未启用 1启用 ）",example = "true")
    private Boolean lsbStatus;

    /**
     * 晚餐补贴状态（老师 ）（0未启用 1启用 ）
     */
    @Column(name = "dsb_status")
    @ApiModelProperty(value = "晚餐补贴状态（老师 ）（0未启用 1启用 ）",example = "true")
    private Boolean dsbStatus;

    @Column(name = "open_id")
    @ApiModelProperty(value = "微信open_id",example = "123456asd")
    private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getIsFace() {
        return isFace;
    }

    public void setIsFace(Integer isFace) {
        this.isFace = isFace;
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    /**
     * 获取用户id
     *
     * @return uid - 用户id
     */
    public String getUid() {
        return uid;
    }

    /**
     * 设置用户id
     *
     * @param uid 用户id
     */
    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    /**
     * 获取用户名/姓名
     *
     * @return username - 用户名/姓名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名/姓名
     *
     * @param username 用户名/姓名
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
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
     * 获取token令牌
     *
     * @return token - token令牌
     */
    public String getToken() {
        return token;
    }

    /**
     * 设置token令牌
     *
     * @param token token令牌
     */
    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    /**
     * 获取支付密码
     *
     * @return pay_password - 支付密码
     */
    public String getPayPassword() {
        return payPassword;
    }

    /**
     * 设置支付密码
     *
     * @param payPassword 支付密码
     */
    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword == null ? null : payPassword.trim();
    }

    /**
     * 获取年龄
     *
     * @return age - 年龄
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 设置年龄
     *
     * @param age 年龄
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 获取性别(1男 2女)
     *
     * @return sex - 性别(1男 2女)
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 设置性别(1男 2女)
     *
     * @param sex 性别(1男 2女)
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * 获取用户类型（1学生 2班主任 3其他（食堂人员/供应商））
     *
     * @return type - 用户类型（1学生 2班主任 3其他（食堂人员/供应商））
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置用户类型（1学生 2班主任 3其他（食堂人员/供应商））
     *
     * @param type 用户类型（1学生 2班主任 3其他（食堂人员/供应商））
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取状态（0未通过 1通过 2待审核）
     *
     * @return status - 状态（0未通过 1通过 2待审核）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态（0未通过 1通过 2待审核）
     *
     * @param status 状态（0未通过 1通过 2待审核）
     */
    public void setStatus(Integer status) {
        this.status = status;
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
     * 获取年级id
     *
     * @return cid - 年级id
     */
    public Integer getCid() {
        return cid;
    }

    /**
     * 设置年级id
     *
     * @param cid 年级id
     */
    public void setCid(Integer cid) {
        this.cid = cid;
    }

    /**
     * 获取钱包金额
     *
     * @return balance - 钱包金额
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * 设置钱包金额
     *
     * @param balance 钱包金额
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * 获取学号
     *
     * @return stu_num - 学号
     */
    public String getStuNum() {
        return stuNum;
    }

    /**
     * 设置学号
     *
     * @param stuNum 学号
     */
    public void setStuNum(String stuNum) {
        this.stuNum = stuNum == null ? null : stuNum.trim();
    }

    /**
     * 获取头像
     *
     * @return icon - 头像
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置头像
     *
     * @param icon 头像
     */
    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    /**
     * 获取创建时间/注册时间
     *
     * @return crtime - 创建时间/注册时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Date getCrtime() {
        return crtime;
    }

    /**
     * 设置创建时间/注册时间
     *
     * @param crtime 创建时间/注册时间
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

    /**
     * 获取补贴金额
     *
     * @return subsidy_price - 补贴金额
     */
    public BigDecimal getSubsidyPrice() {
        return subsidyPrice;
    }

    /**
     * 设置补贴金额
     *
     * @param subsidyPrice 补贴金额
     */
    public void setSubsidyPrice(BigDecimal subsidyPrice) {
        this.subsidyPrice = subsidyPrice;
    }

    /**
     * 获取早餐补贴状态（老师 ）（0未启用 1启用 ）
     *
     * @return bfsb_status - 早餐补贴状态（老师 ）（0未启用 1启用 ）
     */
    public Boolean getBfsbStatus() {
        return bfsbStatus;
    }

    /**
     * 设置早餐补贴状态（老师 ）（0未启用 1启用 ）
     *
     * @param bfsbStatus 早餐补贴状态（老师 ）（0未启用 1启用 ）
     */
    public void setBfsbStatus(Boolean bfsbStatus) {
        this.bfsbStatus = bfsbStatus;
    }

    /**
     * 获取午餐补贴状态（老师 ）（0未启用 1启用 ）
     *
     * @return lsb_status - 午餐补贴状态（老师 ）（0未启用 1启用 ）
     */
    public Boolean getLsbStatus() {
        return lsbStatus;
    }

    /**
     * 设置午餐补贴状态（老师 ）（0未启用 1启用 ）
     *
     * @param lsbStatus 午餐补贴状态（老师 ）（0未启用 1启用 ）
     */
    public void setLsbStatus(Boolean lsbStatus) {
        this.lsbStatus = lsbStatus;
    }

    /**
     * 获取晚餐补贴状态（老师 ）（0未启用 1启用 ）
     *
     * @return dsb_status - 晚餐补贴状态（老师 ）（0未启用 1启用 ）
     */
    public Boolean getDsbStatus() {
        return dsbStatus;
    }

    /**
     * 设置晚餐补贴状态（老师 ）（0未启用 1启用 ）
     *
     * @param dsbStatus 晚餐补贴状态（老师 ）（0未启用 1启用 ）
     */
    public void setDsbStatus(Boolean dsbStatus) {
        this.dsbStatus = dsbStatus;
    }
}