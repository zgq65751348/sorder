package com.tlongx.sorder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel
public class OrderDto {

    private Integer id;
    @ApiModelProperty(value ="订单id")
    private String oid;

    private String uid;
    @ApiModelProperty(value ="状态",example = "0")
    private Integer status;
    @ApiModelProperty(value ="交易类型 0补贴 1钱包 2微信 3支付宝 4农行",example = "1")
    private Integer payType;
    @ApiModelProperty(value ="支付金额",example = "1.2")
    private BigDecimal payPrice;
    @ApiModelProperty(value ="流水号")
    private String outTradeNo;
    @ApiModelProperty(value ="食品名称")
    private String foodName;
    @ApiModelProperty(value ="数量",example = "1")
    private Integer num;
    @ApiModelProperty(value ="窗口食品id",example = "1")
    private Integer wfid;
    @ApiModelProperty(value ="口味偏好")
    private String taste;
    @ApiModelProperty(value="取餐人")
    private String username;
    @ApiModelProperty(value="学校")
    private String sname;
    @ApiModelProperty(value="食堂")
    private String cname;
    @ApiModelProperty(value="窗口")
    private String wname;
    @ApiModelProperty(value = "窗口id")
    private Integer wid;
    @ApiModelProperty(value = "取餐人账户余额")
    private BigDecimal balance;

}
