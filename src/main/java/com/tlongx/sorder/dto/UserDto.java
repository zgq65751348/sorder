package com.tlongx.sorder.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xin.rf
 * @date 2019/2/14 10:25
 * @Description TODO
 **/
@Data
public class UserDto {

    private String uid;
    private String username;
    private String sex;
    private String phone;
    @ApiModelProperty(value = "年级 格式：1")
    private String grade;
    @ApiModelProperty(value = "班级 格式：2")
    private String classNum;
    @ApiModelProperty(value = "钱包金额")
    private String balance;
    @ApiModelProperty(value = "用户类型（1学生 2教师 3班主任 4其他人员）")
    private String type;
    @ApiModelProperty(value = "状态（0未通过 1通过 2待审核）")
    private String status;
    @ApiModelProperty(value = "学号")
    private String stuNum;
    private Integer sid;
    private String sname;
    private String icon;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private String crtime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private  String uptime;
    @ApiModelProperty(value = "补贴金额")
    private String subsidyPrice;
    @ApiModelProperty(value = "学校类型")
    private Integer schoolType;

}
