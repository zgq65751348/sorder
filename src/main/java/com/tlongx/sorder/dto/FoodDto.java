package com.tlongx.sorder.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xin.rf
 * @date 2019/2/20 15:42
 * @Description TODO
 **/
@Data
@ApiModel
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class FoodDto {

    private String id;
    @ApiModelProperty(value = "窗口id")
    private String wid;
    @ApiModelProperty(value = "食品id")
    private String fid;
    @ApiModelProperty(value = "状态 0下架 1上架")
    private String status;
    @ApiModelProperty(value = "供应时段类型 1早餐..OR 食品平台 1食堂自营 2供应商")
    private String type;
    @ApiModelProperty(value = "食品名称")
    private String foodName;
    @ApiModelProperty(value = "食品说明")
    private String foodRmk;
    @ApiModelProperty(value = "食品图片")
    private String photo;
    @ApiModelProperty(value = "食品价格")
    private String price;
    @ApiModelProperty(value = "实际食堂出售食品价格")
    private String sellPrice;
    @ApiModelProperty(value = "月销量")
    private String monthlySales;
    @ApiModelProperty(value = "供应结束时间 格式2019-12-25")
    private String supplyEnd;
    @ApiModelProperty(value = "供应量")
    private String supplyCount;
    @ApiModelProperty(value = "供应开始时间 格式2019-12-25")
    private String supplyStart;
    @ApiModelProperty(value = "创建时间，根据接口灵活运用")
    private String crtime;
    @ApiModelProperty(value = "购物车id")
    private Integer cardId;
    @ApiModelProperty(value = "条目商品数量")
    private Integer cartNum;
    @ApiModelProperty(value = "单日供应量")
    private Integer dailySupply;
    @ApiModelProperty(value = "剩余单日供应量")
    private Integer residueSupply;
    @ApiModelProperty(value = "条形码")
    private String barCode;



}
