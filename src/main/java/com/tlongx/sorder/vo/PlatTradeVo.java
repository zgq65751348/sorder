package com.tlongx.sorder.vo;

import lombok.Data;

@Data
public class PlatTradeVo {

    private Integer pageNum;

    private Integer pageSize;

    private String startTime;

    private String endTime;

    private Integer sid;
}
