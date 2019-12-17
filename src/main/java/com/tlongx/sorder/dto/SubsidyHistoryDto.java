package com.tlongx.sorder.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SubsidyHistoryDto {

    private String username;

    private String phone;

    private Integer type;

    private Integer tradeType;

    private BigDecimal price;

    private String crtime;

    private BigDecimal subsidyPrice;
}
