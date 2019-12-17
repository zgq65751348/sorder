package com.tlongx.sorder.food.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tl_school_supfood")
@ApiModel
public class SchoolSupfood {
    @Id
    private Integer id;

    /**
     * 食品id
     */
    @ApiModelProperty(value = "食品id",example = "1")
    private Integer fid;

    /**
     * 学校id
     */
    @ApiModelProperty(value = "学校id",example = "1")
    private Integer sid;

    /**
     * 供应时段类型
     */
    @ApiModelProperty(value = "供应时段类型 1早餐...",example = "1")
    private Integer type;

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

    @ApiModelProperty(value = "食品价格",example = "5.55")
    private BigDecimal price;

    /**
     * 供应商餐品状态 1-上架  ,0-下架     食堂，供应商可看，食堂不可看  未上架餐品
     */
    @ApiModelProperty(value = "供应商餐品状态 1-上架  ,0-下架     食堂，供应商可看，食堂不可看  未上架餐品",example = "0")
    private Integer status;

    /**
     * 月销量
     */
    @Column(name = "monthly_sales")
    @ApiModelProperty(value = "月销量",example = "1")
    private Integer monthlySales;

    /**
     * 总销量
     */
    @Column(name = "total_sales")
    @ApiModelProperty(value = "总销量 ",example = "1")
    private Integer totalSales;

    /**
     * 食堂购买数量
     */
    @Column(name = "buy_count")
    @ApiModelProperty(value = "食堂购买数量",example = "1")
    private Integer buyCount;

    /**
     * 食堂供应数量
     */
    @Column(name = "supplier_count")
    @ApiModelProperty(value = "食堂供应数量",example = "1")
    private Integer supplierCount;

    @Column(name = "daily_supply")
    @ApiModelProperty(value = "当日供应数量",example = "1")
    private Integer dailySupply;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date crtime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date uptime;


    public Integer getDailySupply() {
        return dailySupply;
    }

    public void setDailySupply(Integer dailySupply) {
        this.dailySupply = dailySupply;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取食品id
     *
     * @return fid - 食品id
     */
    public Integer getFid() {
        return fid;
    }

    /**
     * 设置食品id
     *
     * @param fid 食品id
     */
    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
     * 获取供应商餐品状态 1-上架  ,0-下架     食堂，供应商可看，食堂不可看  未上架餐品
     *
     * @return status - 供应商餐品状态 1-上架  ,0-下架     食堂，供应商可看，食堂不可看  未上架餐品
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置供应商餐品状态 1-上架  ,0-下架     食堂，供应商可看，食堂不可看  未上架餐品
     *
     * @param status 供应商餐品状态 1-上架  ,0-下架     食堂，供应商可看，食堂不可看  未上架餐品
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取月销量
     *
     * @return monthly_sales - 月销量
     */
    public Integer getMonthlySales() {
        return monthlySales;
    }

    /**
     * 设置月销量
     *
     * @param monthlySales 月销量
     */
    public void setMonthlySales(Integer monthlySales) {
        this.monthlySales = monthlySales;
    }

    /**
     * 获取总销量
     *
     * @return total_sales - 总销量
     */
    public Integer getTotalSales() {
        return totalSales;
    }

    /**
     * 设置总销量
     *
     * @param totalSales 总销量
     */
    public void setTotalSales(Integer totalSales) {
        this.totalSales = totalSales;
    }

    /**
     * 获取食堂购买数量
     *
     * @return buy_count - 食堂购买数量
     */
    public Integer getBuyCount() {
        return buyCount;
    }

    /**
     * 设置食堂购买数量
     *
     * @param buyCount 食堂购买数量
     */
    public void setBuyCount(Integer buyCount) {
        this.buyCount = buyCount;
    }

    /**
     * 获取食堂供应数量
     *
     * @return supplier_count - 食堂供应数量
     */
    public Integer getSupplierCount() {
        return supplierCount;
    }

    /**
     * 设置食堂供应数量
     *
     * @param supplierCount 食堂供应数量
     */
    public void setSupplierCount(Integer supplierCount) {
        this.supplierCount = supplierCount;
    }

    /**
     * 获取创建时间
     *
     * @return crtime - 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Date getCrtime() {
        return crtime;
    }

    /**
     * 设置创建时间
     *
     * @param crtime 创建时间
     */
    public void setCrtime(Date crtime) {
        this.crtime = crtime;
    }

    /**
     * 获取更新时间
     *
     * @return uptime - 更新时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Date getUptime() {
        return uptime;
    }

    /**
     * 设置更新时间
     *
     * @param uptime 更新时间
     */
    public void setUptime(Date uptime) {
        this.uptime = uptime;
    }
}