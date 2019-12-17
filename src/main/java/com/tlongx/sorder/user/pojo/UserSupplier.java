package com.tlongx.sorder.user.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tl_user_supplier")
@ApiModel
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class UserSupplier {
    @Id
    private String uid;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 密码
     */
    private String password;

    /**
     * token
     */
    private String token;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String portrait;

    /**
     * 主营产品
     */
    @Column(name = "main_product")
    @ApiModelProperty(value = "主营产品")
    private String mainProduct;

    /**
     * 钱包余额
     */
    @ApiModelProperty(value = "钱包余额",example = "14.55")
    private Double balance;

    /**
     * 店铺审核状态 0未通过 1通过 2待审核 3注册后未提交入驻审核
     */
    @ApiModelProperty(value = "店铺审核状态 0未通过 1通过 2待审核 3注册后未提交入驻审核",example = "1")
    private Integer status;

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
     * 负责人姓名
     */
    @Column(name = "leader_name")
    @ApiModelProperty(value = "负责人姓名")
    private String leaderName;

    /**
     * 负责人联系方式
     */
    @Column(name = "leader_phone")
    @ApiModelProperty(value = "负责人联系方式")
    private String leaderPhone;

    /**
     * 身份证正面照
     */
    @Column(name = "id_card_front")
    @ApiModelProperty(value = "身份证正面照")
    private String idCardFront;

    /**
     * 身份证反面照
     */
    @Column(name = "id_card_contrary")
    @ApiModelProperty(value = "身份证反面照")
    private String idCardContrary;

    /**
     * 营业执照
     */
    @Column(name = "business_license")
    @ApiModelProperty(value = "营业执照")
    private String businessLicense;

    /**
     * 相关许可证
     */
    @Column(name = "relevant_license")
    @ApiModelProperty(value = "相关许可证")
    private String relevantLicense;

    /**
     * 店铺名称
     */
    @Column(name = "shop_name")
    @ApiModelProperty(value = "店铺名称")
    private String shopName;

    /**
     * 店铺招牌
     */
    @Column(name = "shop_photo")
    @ApiModelProperty(value = "店铺照片")
    private String shopPhoto;

    /**
     * 总销量
     */
    @Column(name = "sale_count")
    @ApiModelProperty(value = "总销量",example = "55")
    private Integer saleCount;

    /**
     * 审核通过时间
     */
    @Column(name = "approve_time")
    @ApiModelProperty(value = "审核通过时间")
    private Date approveTime;

    /**
     * 月销量
     */
    @Column(name="m_sale_count")
    @ApiModelProperty(value = "月销量",example = "66")
    private Integer mSaleCount;

    /**
     * 评分
     */
    @Column(name = "score")
    @ApiModelProperty(value = "评分")
    private String score;

    /**
     * 人均消费
     */
    @Column(name = "axf")
    @ApiModelProperty(value = "人均消费",example = "55")
    private Integer axf;

    public Integer getAxf() {
        return axf;
    }

    public void setAxf(Integer axf) {
        this.axf = axf;
    }

    public Integer getmSaleCount() {
        return mSaleCount;
    }

    public void setmSaleCount(Integer mSaleCount) {
        this.mSaleCount = mSaleCount;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    /**
     * @return uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * @param uid
     */
    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    /**
     * 获取手机号
     *
     * @return phone - 手机号
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置手机号
     *
     * @param phone 手机号
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取token
     *
     * @return token - token
     */
    public String getToken() {
        return token;
    }

    /**
     * 设置token
     *
     * @param token token
     */
    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    /**
     * 获取头像
     *
     * @return portrait - 头像
     */
    public String getPortrait() {
        return portrait;
    }

    /**
     * 设置头像
     *
     * @param portrait 头像
     */
    public void setPortrait(String portrait) {
        this.portrait = portrait == null ? null : portrait.trim();
    }

    /**
     * 获取主营产品
     *
     * @return main_product - 主营产品
     */
    public String getMainProduct() {
        return mainProduct;
    }

    /**
     * 设置主营产品
     *
     * @param mainProduct 主营产品
     */
    public void setMainProduct(String mainProduct) {
        this.mainProduct = mainProduct == null ? null : mainProduct.trim();
    }

    /**
     * 获取钱包余额
     *
     * @return balance - 钱包余额
     */
    public Double getBalance() {
        return balance;
    }

    /**
     * 设置钱包余额
     *
     * @param balance 钱包余额
     */
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    /**
     * 获取店铺审核状态 0未通过 1通过 2待审核 3注册后未提交入驻审核
     *
     * @return status - 店铺审核状态 0未通过 1通过 2待审核 3注册后未提交入驻审核
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置店铺审核状态 0未通过 1通过 2待审核 3注册后未提交入驻审核
     *
     * @param status 店铺审核状态 0未通过 1通过 2待审核 3注册后未提交入驻审核
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
     * 获取负责人姓名
     *
     * @return leader_name - 负责人姓名
     */
    public String getLeaderName() {
        return leaderName;
    }

    /**
     * 设置负责人姓名
     *
     * @param leaderName 负责人姓名
     */
    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName == null ? null : leaderName.trim();
    }

    /**
     * 获取负责人联系方式
     *
     * @return leader_phone - 负责人联系方式
     */
    public String getLeaderPhone() {
        return leaderPhone;
    }

    /**
     * 设置负责人联系方式
     *
     * @param leaderPhone 负责人联系方式
     */
    public void setLeaderPhone(String leaderPhone) {
        this.leaderPhone = leaderPhone == null ? null : leaderPhone.trim();
    }

    /**
     * 获取身份证正面照
     *
     * @return id_card_front - 身份证正面照
     */
    public String getIdCardFront() {
        return idCardFront;
    }

    /**
     * 设置身份证正面照
     *
     * @param idCardFront 身份证正面照
     */
    public void setIdCardFront(String idCardFront) {
        this.idCardFront = idCardFront == null ? null : idCardFront.trim();
    }

    /**
     * 获取身份证反面照
     *
     * @return id_card_contrary - 身份证反面照
     */
    public String getIdCardContrary() {
        return idCardContrary;
    }

    /**
     * 设置身份证反面照
     *
     * @param idCardContrary 身份证反面照
     */
    public void setIdCardContrary(String idCardContrary) {
        this.idCardContrary = idCardContrary == null ? null : idCardContrary.trim();
    }

    /**
     * 获取营业执照
     *
     * @return business_license - 营业执照
     */
    public String getBusinessLicense() {
        return businessLicense;
    }

    /**
     * 设置营业执照
     *
     * @param businessLicense 营业执照
     */
    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense == null ? null : businessLicense.trim();
    }

    /**
     * 获取相关许可证
     *
     * @return relevant_license - 相关许可证
     */
    public String getRelevantLicense() {
        return relevantLicense;
    }

    /**
     * 设置相关许可证
     *
     * @param relevantLicense 相关许可证
     */
    public void setRelevantLicense(String relevantLicense) {
        this.relevantLicense = relevantLicense == null ? null : relevantLicense.trim();
    }

    /**
     * 获取店铺名称
     *
     * @return shop_name - 店铺名称
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * 设置店铺名称
     *
     * @param shopName 店铺名称
     */
    public void setShopName(String shopName) {
        this.shopName = shopName == null ? null : shopName.trim();
    }

    /**
     * 获取店铺招牌
     *
     * @return shop_photo - 店铺招牌
     */
    public String getShopPhoto() {
        return shopPhoto;
    }

    /**
     * 设置店铺招牌
     *
     * @param shopPhoto 店铺招牌
     */
    public void setShopPhoto(String shopPhoto) {
        this.shopPhoto = shopPhoto == null ? null : shopPhoto.trim();
    }

    /**
     * 获取总销量
     *
     * @return sale_count - 总销量
     */
    public Integer getSaleCount() {
        return saleCount;
    }

    /**
     * 设置总销量
     *
     * @param saleCount 总销量
     */
    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
    }

    /**
     * 获取审核通过时间
     *
     * @return approve_time - 审核通过时间
     */
    public Date getApproveTime() {
        return approveTime;
    }

    /**
     * 设置审核通过时间
     *
     * @param approveTime 审核通过时间
     */
    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }
}