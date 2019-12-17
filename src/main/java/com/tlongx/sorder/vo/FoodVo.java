package com.tlongx.sorder.vo;


import lombok.Data;

/**
 * @author xin.rf
 * @date 2019/2/20 15:46
 * @Description TODO
 **/
@Data
public class FoodVo {

    private Integer pageNum;

    private Integer pageSize;

    private String wid;

    private String type;

    private String[] ids;

    private String foodName;

    private String uid;

    private String[] fids;

    private Integer status;

    private String sid;

    private String startTime;

    private String endTime;

    private Integer id;

    private Integer cid;

    private String sortScore;

    private String sortPrice;

    private String uname;

    private String supplier;
}
