package com.tlongx.sorder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;

/**
 * @author xin.rf
 * @date 2019/2/13 13:53
 * @Description TODO
 **/
@Data
@ApiModel
public class SchoolDto {
    @ApiModelProperty(value = "学校id")
    private String sid;

    @ApiModelProperty(value = "学校名称")
    private String sname;
    @ApiModelProperty(value = "创建时间")
    private String crtime;
    @ApiModelProperty(value = "账号过期时间")
    private String overdueTime;
    @ApiModelProperty(value = "管理员id")
    private String mid;
    @ApiModelProperty(value = "管理员名称")
    private String mname;
    @ApiModelProperty(value = "管理员登录名")
    private String username;
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "账号状态 0停用 1正常")
    private String status;
    @ApiModelProperty(value = "补贴状态 0停用 1正常")
    private String subsidyStatus;
    @ApiModelProperty(value = "补贴金额")
    private String subsidyPrice;
    @ApiModelProperty(value = "补贴时间")
    private String subsidyTime;
    @ApiModelProperty(value = "学校编号")
    private String snum;
    @ApiModelProperty(value = " 开通支付宝 0否 1是",example = "1")
    private Integer dredgeAlipay;
    @ApiModelProperty(value = " 开通微信 0否 1是",example = "1")
    private Integer dredgeWxpay;
    @ApiModelProperty(value = " 开通人脸 0否 1是",example = "1")
    private Integer dredgeFace;
    @ApiModelProperty(value = "开户行")
    private String openingBank;
    @ApiModelProperty(value = "户名")
    private String bankUserName;
    @ApiModelProperty(value = "银行账号")
    private String bankAccount;
    @ApiModelProperty(value = "学校余额")
    private BigDecimal balance;
    @ApiModelProperty(value = "已提现金额")
    private BigDecimal depositPrice;
    @ApiModelProperty(value = "历史提现总金额")
    private BigDecimal totalDepositPrice;
}
