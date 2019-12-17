package com.tlongx.sorder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 后台餐品列表dto
 */
@Data
@ApiModel
public class FoodBackDto {

    @ApiModelProperty(value = "食品名称")
    private String foodName;
    @ApiModelProperty(value = "食品价格")
    private String price;
    @ApiModelProperty(value = "总销量")
    private String totalSales;
    @ApiModelProperty(value = "评分")
    private String score;
    @ApiModelProperty(value = "供应时段类型 1早餐 2午餐 3晚餐 4水果超市 5便利小超 6特色早餐 7特色午餐 8特色晚餐")
    private String type;
    @ApiModelProperty(value = "供应商")
    private String supplier;
    @ApiModelProperty(value = "负责人")
    private String uname;
    @ApiModelProperty(value = "联系方式")
    private String phone;
    @ApiModelProperty(value = "创建时间")
    private String crtime;
    private String cname;
    private String wname;
}
