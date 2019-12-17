package com.tlongx.sorder.manager.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "tl_supplier_time")
@ApiModel
public class SupplierTime {
    @Id
    private Integer id;

    /**
     * 食品分类 1早餐 2午餐 3晚餐 4水果超市 5便利小超 6特色早餐 7特色午餐 8特色晚餐
     */
    @ApiModelProperty(value = "食品分类 1早餐..",example = "1")
    private Integer type;

    /**
     * 学校id
     */
    @ApiModelProperty(value = "学校id",example = "1")
    private Integer sid;

    /**
     * 开始供应时间
     */
    @Column(name = "supply_start")
    @ApiModelProperty(value = "开始供应时间")
    private String supplyStart;

    /**
     * 结束供应时间
     */
    @Column(name = "supply_end")
    @ApiModelProperty(value = "结束供应时间")
    private String supplyEnd;


    /**
     * 补贴金额
     */
    @Column(name = "subsidy_price")
    @ApiModelProperty(value = "补贴金额",example = "1.23")
    private BigDecimal subsidyPrice;

    /**
     * 补贴开启  0:否 1:是  
     */
    @Column(name = "subsidy_status")
    @ApiModelProperty(value = "补贴开启  0:否 1:是  ",example = "1")
    private Integer subsidyStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取食品分类 1早餐 2午餐 3晚餐 4水果超市 5便利小超 6特色早餐 7特色午餐 8特色晚餐
     *
     * @return type - 食品分类 1早餐 2午餐 3晚餐 4水果超市 5便利小超 6特色早餐 7特色午餐 8特色晚餐
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置食品分类 1早餐 2午餐 3晚餐 4水果超市 5便利小超 6特色早餐 7特色午餐 8特色晚餐
     *
     * @param type 食品分类 1早餐 2午餐 3晚餐 4水果超市 5便利小超 6特色早餐 7特色午餐 8特色晚餐
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取学校id
     *
     * @return sid - 学校id
     */
    public Integer getSid() {
        return sid;
    }

    /**
     * 设置学校id
     *
     * @param sid 学校id
     */
    public void setSid(Integer sid) {
        this.sid = sid;
    }

    /**
     * 获取开始供应时间
     *
     * @return supply_start - 开始供应时间
     */
    public String getSupplyStart() {
        return supplyStart;
    }

    /**
     * 设置开始供应时间
     *
     * @param supplyStart 开始供应时间
     */
    public void setSupplyStart(String supplyStart) {
        this.supplyStart = supplyStart == null ? null : supplyStart.trim();
    }

    /**
     * 获取结束供应时间
     *
     * @return supply_end - 结束供应时间
     */
    public String getSupplyEnd() {
        return supplyEnd;
    }

    /**
     * 设置结束供应时间
     *
     * @param supplyEnd 结束供应时间
     */
    public void setSupplyEnd(String supplyEnd) {
        this.supplyEnd = supplyEnd == null ? null : supplyEnd.trim();
    }


    /**
     * 获取补贴金额
     *
     * @return subsidy_price - 补贴金额
     */
    public BigDecimal getSubsidyPrice() {
        return subsidyPrice;
    }

    /**
     * 设置补贴金额
     *
     * @param subsidyPrice 补贴金额
     */
    public void setSubsidyPrice(BigDecimal subsidyPrice) {
        this.subsidyPrice = subsidyPrice;
    }

    /**
     * 获取补贴开启  0:否 1:是  
     *
     * @return subsidy_status - 补贴开启  0:否 1:是  
     */
    public Integer getSubsidyStatus() {
        return subsidyStatus;
    }

    /**
     * 设置补贴开启  0:否 1:是  
     *
     * @param subsidyStatus 补贴开启  0:否 1:是  
     */
    public void setSubsidyStatus(Integer subsidyStatus) {
        this.subsidyStatus = subsidyStatus;
    }
}