package com.tlongx.sorder.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CartDto {

    @ApiModelProperty(value = "购物车id")
    private String id;
    @ApiModelProperty(value = "预定时间")
    private String subDate;
    @ApiModelProperty(value = "食品图片")
    private String photo;
    @ApiModelProperty(value = "食品名称")
    private String foodName;
    @ApiModelProperty(value = "食品说明")
    private String foodRmk;
    @ApiModelProperty(value = "食堂名称")
    private String cname;
    @ApiModelProperty(value = "窗口名称")
    private String wname;
    @ApiModelProperty(value = "购买数量",example = "1")
    private Integer num;
    @ApiModelProperty(value = "食品价格")
    private String price;
    @ApiModelProperty(value = "供应时段",example = "1")
    private Integer type;
    @ApiModelProperty(value = "窗口食品id",example = "1")
    private Integer wfid;
    @ApiModelProperty(value = "是否使用补贴",example = "1")
    private Integer isSubsidy;

}
