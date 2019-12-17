package com.tlongx.sorder.food.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tl_canteen_food")
@ApiModel
public class CanteenFood {
    @Id
    private Integer fid;

    @Column(name = "bar_code")
    @ApiModelProperty(value = "条形码")
    private String barCode;

    /**
     * 食品名称
     */
    @Column(name = "food_name")
    @ApiModelProperty(value = "食品名称")
    private String foodName;

    /**
     * 食品描述
     */
    @Column(name = "food_rmk")
    @ApiModelProperty(value = "食品说明")
    private String foodRmk;

    /**
     * 食堂id
     */
    @ApiModelProperty(value = "食堂id",example = "1")
    private Integer cid;

    /**
     * 供应时段类型 1早餐 2午餐 3晚餐 4水果超市 5便利小超 6特色早餐 7特色午餐 8特色晚餐
     */
    @ApiModelProperty(value = "供应时段类型 1早餐..",example = "1")
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

    /**
     * 食品图片
     */
    @ApiModelProperty(value = "食品图片")
    private String photo;

    /**
     * 0下架 1上架
     */
    @ApiModelProperty(value = "状态 0下架 1上架",example = "1")
    private Integer status;

    @ApiModelProperty(value = "评分",example = "1.5")
    private BigDecimal score;

    @ApiModelProperty(value = "单日供应量",example = "1")
    @Column(name = "daily_supply")
    private Integer dailySupply;


    private Integer sid;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date crtime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date uptime;

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
    @ApiModelProperty(value = "总销量",example = "1")
    private Integer totalSales;

    /**
     * 食品价格
     */
    @ApiModelProperty(value = "食品价格",example = "1.55")
    private BigDecimal price;

    /**
     * 操作人
     */
    @ApiModelProperty(value = "操作人")
    private String uid;

    /**
     * 供应量
     */
    @Column(name = "supply_count")
    @ApiModelProperty(value = "供应量",example = "2.55")
    private Integer supplyCount;

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getDailySupply() {
        return dailySupply;
    }

    public void setDailySupply(Integer dailySupply) {
        this.dailySupply = dailySupply;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return fid
     */
    public Integer getFid() {
        return fid;
    }

    /**
     * @param fid
     */
    public void setFid(Integer fid) {
        this.fid = fid;
    }

    /**
     * 获取食品名称
     *
     * @return food_name - 食品名称
     */
    public String getFoodName() {
        return foodName;
    }

    /**
     * 设置食品名称
     *
     * @param foodName 食品名称
     */
    public void setFoodName(String foodName) {
        this.foodName = foodName == null ? null : foodName.trim();
    }

    /**
     * 获取食品描述
     *
     * @return food_rmk - 食品描述
     */
    public String getFoodRmk() {
        return foodRmk;
    }

    /**
     * 设置食品描述
     *
     * @param foodRmk 食品描述
     */
    public void setFoodRmk(String foodRmk) {
        this.foodRmk = foodRmk == null ? null : foodRmk.trim();
    }

    /**
     * 获取食堂id
     *
     * @return cid - 食堂id
     */
    public Integer getCid() {
        return cid;
    }

    /**
     * 设置食堂id
     *
     * @param cid 食堂id
     */
    public void setCid(Integer cid) {
        this.cid = cid;
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
     * 获取食品图片
     *
     * @return photo - 食品图片
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * 设置食品图片
     *
     * @param photo 食品图片
     */
    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
    }

    /**
     * 获取0下架 1上架
     *
     * @return status - 0下架 1上架
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置0下架 1上架
     *
     * @param status 0下架 1上架
     */
    public void setStatus(Integer status) {
        this.status = status;
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
     * 获取修改时间
     *
     * @return uptime - 修改时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Date getUptime() {
        return uptime;
    }

    /**
     * 设置修改时间
     *
     * @param uptime 修改时间
     */
    public void setUptime(Date uptime) {
        this.uptime = uptime;
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
     * 获取食品价格
     *
     * @return price - 食品价格
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置食品价格
     *
     * @param price 食品价格
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取操作人
     *
     * @return uid - 操作人
     */
    public String getUid() {
        return uid;
    }

    /**
     * 设置操作人
     *
     * @param uid 操作人
     */
    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    /**
     * 供应量
     *
     * @return supply_count - 供应量
     */
    public Integer getSupplyCount() {
        return supplyCount;
    }

    /**
     * 供应量
     *
     * @param supplyCount 供应量
     */
    public void setSupplyCount(Integer supplyCount) {
        this.supplyCount = supplyCount;
    }

}