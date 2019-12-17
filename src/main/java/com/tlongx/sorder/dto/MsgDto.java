package com.tlongx.sorder.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class MsgDto {

    private Integer id;

    private String title;

    private String context;
    @ApiModelProperty(value = "0未读 1已读",example = "0")
    private Integer status;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private String crtime;
}
