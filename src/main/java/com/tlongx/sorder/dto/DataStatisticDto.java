package com.tlongx.sorder.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel
public class DataStatisticDto extends BaseRowModel {

    @ApiModelProperty(value = "食堂名称")
    private String cname;
    @ApiModelProperty(value = "日期")
    @ExcelProperty(value = {"日期","日期"} ,index = 0)
    private String uptime;
    @ApiModelProperty(value = "早餐人数",example = "1")
    @ExcelProperty(value = {"早餐","人次"} ,index = 1)
    private Integer zcCount;
    @ApiModelProperty(value = "早餐总金额",example = "1.2")
    @ExcelProperty(value = {"早餐","金额"} ,index = 2)
    private BigDecimal zcSum;
    @ApiModelProperty(value = "午餐人数",example = "1")
    @ExcelProperty(value = {"午餐","人次"} ,index =3)
    private Integer wcCount;
    @ApiModelProperty(value = "午餐总金额",example = "1.2")
    @ExcelProperty(value = {"午餐","金额"} ,index = 4)
    private BigDecimal wcSum;
    @ApiModelProperty(value = "晚餐人数",example = "1")
    @ExcelProperty(value = {"晚餐","人次"} ,index = 5)
    private Integer dcCount;
    @ApiModelProperty(value = "晚餐总金额",example = "1.2")
    @ExcelProperty(value = {"晚餐","金额"} ,index = 6)
    private BigDecimal dcSum;
    @ApiModelProperty(value = "水果人数",example = "1")
    @ExcelProperty(value = {"水果超市","人次"} ,index = 7)
    private Integer sgCount;
    @ApiModelProperty(value = "水果总金额",example = "1.2")
    @ExcelProperty(value = {"水果超市","金额"} ,index = 8)
    private BigDecimal sgSum;
    @ApiModelProperty(value = "超市人数",example = "1")
    @ExcelProperty(value = {"便利小超","人次"} ,index = 9)
    private Integer csCount;
    @ApiModelProperty(value = "超市总金额",example = "1.2")
    @ExcelProperty(value = {"便利小超","金额"} ,index = 10)
    private BigDecimal csSum;
    @ApiModelProperty(value = "特色早餐人数",example = "1")
    @ExcelProperty(value = {"特色早餐","人次"} ,index = 11)
    private Integer tzcCount;
    @ApiModelProperty(value = "特色早餐总金额",example = "1.2")
    @ExcelProperty(value = {"特色早餐","金额"} ,index = 12)
    private BigDecimal tzcSum;
    @ApiModelProperty(value = "特色午餐人数",example = "1")
    @ExcelProperty(value = {"特色午餐","人次"} ,index = 13)
    private Integer twcCount;
    @ApiModelProperty(value = "特色午餐总金额",example = "1.2")
    @ExcelProperty(value = {"特色午餐","金额"} ,index = 14)
    private BigDecimal twcSum;
    @ApiModelProperty(value = "特色晚餐人数",example = "1")
    @ExcelProperty(value = {"特色晚餐","人次"} ,index = 15)
    private Integer tdcCount;
    @ApiModelProperty(value = "特色晚餐总金额",example = "1.2")
    @ExcelProperty(value = {"特色晚餐","金额"} ,index = 16)
    private BigDecimal tdcSum;
    @ApiModelProperty(value = "总人数",example = "1")
    @ExcelProperty(value = {"合计","人数"} ,index = 17)
    private Integer totalCount;
    @ApiModelProperty(value = "总金额",example = "1.2")
    @ExcelProperty(value = {"合计","金额"} ,index = 18)
    private BigDecimal totalPriceSum;
}
