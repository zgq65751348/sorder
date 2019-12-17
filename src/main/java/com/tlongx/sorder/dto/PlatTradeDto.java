package com.tlongx.sorder.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel
public class PlatTradeDto {

    private Integer sid;

    private String sname;
    @ApiModelProperty(value = "收入",example = "1.2")
    private BigDecimal insum;
    @ApiModelProperty(value = "支出",example = "1.2")
    private BigDecimal outsum;
}
