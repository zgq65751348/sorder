package com.tlongx.sorder.manager.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tl_manager")
@ApiModel
public class Manager {
    @Id
    @ApiModelProperty(value = "管理员id")
    private String mid;

    /**
     * 管理员姓名
     */
    @ApiModelProperty(value = "管理员姓名")
    private String mname;

    /**
     * 登录名
     */
    @ApiModelProperty(value = "登录名")
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String portrait;

    /**
     * 1学校 2平台
     */
    @ApiModelProperty(value = "管理员类型 1学校 2平台",example = "1")
    private Integer type;

    /**
     * 状态 （ 0停用  1正常 ）
     */
    @ApiModelProperty(value = "状态 （ 0停用  1正常 ）",example = "0")
    private Integer status;

    /**
     * 角色 1管理员
     */
    @ApiModelProperty(value = "角色 1管理员",example = "1")
    private Integer role;

    /**
     * 最后一次登录ip
     */
    @Column(name = "login_ip")
    @ApiModelProperty(value = "最后一次登录ip")
    private String loginIp;

    /**
     * 最后一次登录时间
     */
    @Column(name = "login_time")
    @ApiModelProperty(value = "最后一次登录时间")
    private Date loginTime;

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
     * 所属学校id
     */
    @ApiModelProperty(value = "学校id",example = "1")
    private Integer sid;

    /**
     * 过期时间
     */
    @Column(name = "overdue_time")
    @ApiModelProperty(value = "过期时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date overdueTime;

    /**
     * 上级负责人id
     */
    @Column(name = "parent_id")
    @ApiModelProperty(value = "上级负责人id")
    private String parentId;

    /**
     * @return mid
     */
    public String getMid() {
        return mid;
    }

    /**
     * @param mid
     */
    public void setMid(String mid) {
        this.mid = mid == null ? null : mid.trim();
    }

    /**
     * 获取管理员姓名
     *
     * @return mname - 管理员姓名
     */
    public String getMname() {
        return mname;
    }

    /**
     * 设置管理员姓名
     *
     * @param mname 管理员姓名
     */
    public void setMname(String mname) {
        this.mname = mname == null ? null : mname.trim();
    }

    /**
     * 获取登录名
     *
     * @return username - 登录名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置登录名
     *
     * @param username 登录名
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
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
     * 获取头像
     *
     * @return portrait - 头像
     */
    public String getPortrait() {
        return portrait;
    }

    /**
     * 设置头像
     *
     * @param portrait 头像
     */
    public void setPortrait(String portrait) {
        this.portrait = portrait == null ? null : portrait.trim();
    }

    /**
     * 获取1学校 2平台
     *
     * @return type - 1学校 2平台
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置1学校 2平台
     *
     * @param type 1学校 2平台
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取状态 （ 0停用  1正常 ）
     *
     * @return status - 状态 （ 0停用  1正常 ）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态 （ 0停用  1正常 ）
     *
     * @param status 状态 （ 0停用  1正常 ）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取角色 1管理员
     *
     * @return role - 角色 1管理员
     */
    public Integer getRole() {
        return role;
    }

    /**
     * 设置角色 1管理员
     *
     * @param role 角色 1管理员
     */
    public void setRole(Integer role) {
        this.role = role;
    }

    /**
     * 获取最后一次登录ip
     *
     * @return login_ip - 最后一次登录ip
     */
    public String getLoginIp() {
        return loginIp;
    }

    /**
     * 设置最后一次登录ip
     *
     * @param loginIp 最后一次登录ip
     */
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp == null ? null : loginIp.trim();
    }

    /**
     * 获取最后一次登录时间
     *
     * @return login_time - 最后一次登录时间
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * 设置最后一次登录时间
     *
     * @param loginTime 最后一次登录时间
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
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

    /**
     * 获取所属学校id
     *
     * @return sid - 所属学校id
     */
    public Integer getSid() {
        return sid;
    }

    /**
     * 设置所属学校id
     *
     * @param sid 所属学校id
     */
    public void setSid(Integer sid) {
        this.sid = sid;
    }

    /**
     * 获取过期时间
     *
     * @return overdue_time - 过期时间
     */
    public Date getOverdueTime() {
        return overdueTime;
    }

    /**
     * 设置过期时间
     *
     * @param overdueTime 过期时间
     */
    public void setOverdueTime(Date overdueTime) {
        this.overdueTime = overdueTime;
    }

    /**
     * 获取上级负责人id
     *
     * @return parent_id - 上级负责人id
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 设置上级负责人id
     *
     * @param parentId 上级负责人id
     */
    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }
}