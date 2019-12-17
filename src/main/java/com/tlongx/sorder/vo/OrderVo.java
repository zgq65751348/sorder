package com.tlongx.sorder.vo;

import lombok.Data;

import java.util.List;

@Data
public class OrderVo {

    private String uid;

    private String subTime;

    private List<Integer> types;
}
