package com.tlongx.sorder.dto;

import lombok.Data;

@Data
public class SupplierDto {

    private Integer id;

    private String uid;

    private String shopName;

    private String leaderName;

    private String leaderPhone;

    private String crtime;

    private String approveTime;

    private Integer status;

    private String mname;
}
