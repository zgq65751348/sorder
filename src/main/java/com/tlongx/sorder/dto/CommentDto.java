package com.tlongx.sorder.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel
public class CommentDto {

    private Integer id;
    @ApiModelProperty(value = "用户头像")
    private String icon;
    @ApiModelProperty(value = "姓名")
    private String username;
    @ApiModelProperty(value = "评论时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date crtime;
    @ApiModelProperty(value = "评分",example = "1")
    private BigDecimal score;
    @ApiModelProperty(value = "评论内容")
    private String remark;
    @ApiModelProperty(value = "回复内容")
    private String reply;
    @ApiModelProperty(value = "回复人")
    private String cname;
    @ApiModelProperty(value = "评论图片")
    private String photo;
    @ApiModelProperty(value = "食物名")
    private String foodName;
    @ApiModelProperty(value="供应商负责人姓名")
    private String leaderName;
    @ApiModelProperty(value="供应商负责人手机号")
    private String leaderPhone;
    @ApiModelProperty(value = "供应商")
    private String supplier;

}
