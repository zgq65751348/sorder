package com.tlongx.sorder.vo;

import lombok.Data;

/**
 * @author xin.rf
 * @date 2019/2/19 19:07
 * @Description TODO
 **/
@Data
public class MsgVo {

    private Integer pageNum;

    private Integer pageSize;

    private Integer sid;

    private String startTime;

    private String endTime;
}
