package com.tlongx.sorder.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderItemDto {

    private Integer id;

    private String uid;

    private Integer fid;

    private String foodName;

    private Integer type;

    private Integer cid;

    private Integer sid;

    private Integer num;

    private String oid;

    private String cname;

    private String wname;

    private BigDecimal price;

    private BigDecimal totalPrice;

    private String photo;

    private String subTime;

    private Integer status;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date crtime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date uptime;

    private Integer wfid;

    private Integer takeEnable;

    private Integer refundEnable;

    private Integer wid;

    private Integer isExpire;

    //总数
    private Integer foodNum;

    //未取餐
    private Integer noTake;

    //已取餐
    private Integer YesTake;

    //退订数
    private Integer refundNum;

}