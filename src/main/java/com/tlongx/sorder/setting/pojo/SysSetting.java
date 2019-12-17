package com.tlongx.sorder.setting.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tl_sys_setting")
@ApiModel
public class SysSetting {
    @Id
    private Integer id;

    /**
     * 设置 供应/食堂 提现 缓冲期 n天
     */
    @Column(name = "withdraw_day")
    @ApiModelProperty(value = "设置 供应/食堂 提现 缓冲期 n天",example = "2")
    private Integer withdrawDay;

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
     * 关于我们
     */
    @Column(name = "about_us")
    @ApiModelProperty(value = "关于我们")
    private String aboutUs;

    /**
     * 使用帮助
     */
    @Column(name = "use_help")
    @ApiModelProperty(value = "使用帮助-食堂")
    private String useHelp;

    @Column(name = "use_help_supplier")
    @ApiModelProperty(value = "使用帮助-供应商")
    private String useHelpSupplier;

    @Column(name = "use_help_student")
    @ApiModelProperty(value = "使用帮助-学生")
    private String useHelpStudent;

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
     * 获取设置 供应/食堂 提现 缓冲期 n天
     *
     * @return withdraw_day - 设置 供应/食堂 提现 缓冲期 n天
     */
    public Integer getWithdrawDay() {
        return withdrawDay;
    }

    /**
     * 设置设置 供应/食堂 提现 缓冲期 n天
     *
     * @param withdrawDay 设置 供应/食堂 提现 缓冲期 n天
     */
    public void setWithdrawDay(Integer withdrawDay) {
        this.withdrawDay = withdrawDay;
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
     * 获取关于我们
     *
     * @return about_us - 关于我们
     */
    public String getAboutUs() {
        return aboutUs;
    }

    /**
     * 设置关于我们
     *
     * @param aboutUs 关于我们
     */
    public void setAboutUs(String aboutUs) {
        this.aboutUs = aboutUs == null ? null : aboutUs.trim();
    }

    /**
     * 获取使用帮助-食堂
     *
     * @return use_help - 使用帮助
     */
    public String getUseHelp() {
        return useHelp;
    }

    /**
     * 设置使用帮助-食堂
     *
     * @param useHelp 使用帮助
     */
    public void setUseHelp(String useHelp) {
        this.useHelp = useHelp == null ? null : useHelp.trim();
    }

    /**
     * 获取使用帮助-供应商
     *
     * @return use_help - 使用帮助
     */
    public String getUseHelpSupplier() {
        return useHelpSupplier;
    }

    /**
     * 设置使用帮助供应商
     *
     * @param useHelpSupplier 使用帮助
     */
    public void setUseHelpSupplier(String useHelpSupplier) {
        this.useHelpSupplier = useHelpSupplier == null ? null : useHelpSupplier.trim();
    }

    /**
     * 获取使用帮助-学生
     *
     * @return use_help_student - 使用帮助
     */
    public String getUseHelpStudent() {
        return useHelpStudent;
    }

    /**
     * 设置使用帮助学生
     *
     * @param useHelpStudent 使用帮助
     */
    public void setUseHelpStudent(String useHelpStudent) {
        this.useHelpStudent = useHelpStudent == null ? null : useHelpStudent.trim();
    }
}