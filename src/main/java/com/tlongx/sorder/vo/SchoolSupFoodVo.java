package com.tlongx.sorder.vo;

import lombok.Data;

/**
 * @author xin.rf
 * @date 2019/2/22 19:03
 * @Description TODO
 **/
@Data
public class SchoolSupFoodVo {

    private Integer id;

    private Integer fid;

    private String photo;

    private String foodName;

    private String foodRmk;

    private String price;

    private Integer supplierCount;

    private String supplyStart;

    private String supplyEnd;
}
