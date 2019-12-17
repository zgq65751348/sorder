package com.tlongx.sorder.dto;

import lombok.Data;

@Data
public class FeedBackDto {

    private Integer id;

    private String username;

    private String phone;

    private String content;

    private String crtime;
}
