package com.tlongx.sorder.vo;

import lombok.Data;

/**
 * @author xin.rf
 * @date 2019/2/13 10:56
 * @Description TODO
 **/
@Data
public class SchoolVo{
    /**
     * 学校id
     */
    private String sid;
    /**
     * 管理员id
     */
    private String mid;
    /**
     * 学校名称
     */
    private String sname;

    /**
     * 学校编号 限四位字母
     */
    private String snum;
    /**
     * 管理员名称
     */
    private String mname;
    /**
     * 管理员用户名
     */
    private String username;
    /**
     * 管理员用户密码
     */
    private String password;
    /**
     * 管理员手机号
     */
    private String phone;
    /**
     * 到期时间
     */
    private String overdueTime;
    /**
     * 开始时间
     */
    private String crtime;
    /**
     * 状态 0停用 1启用
     */
    private String status;

    private Integer pageNum;

    private Integer pageSize;

    private String subsidyStatus;

    private String subsidyPrice;

    private String subsidyTime;
    /**
     * 开通支付宝 0否 1是
     */
    private Integer dredgeAlipay;
    /**
     * 开通微信 0否 1是
     */
    private Integer dredgeWxpay;
    /**
     * 开通人脸 0否 1是
     */
    private Integer dredgeFace;

    /**
     * 角色
     */
    private Integer role;

    /**
     * 1平台 2学校 3学生   流水相关接口用
     */
    private Integer type;

}
