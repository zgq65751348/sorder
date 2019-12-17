package com.tlongx.common;

import com.tlongx.sorder.utils.MyUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class TLResult<T> {
    @ApiModelProperty(value = "响应信息")
    private String msg;
    @ApiModelProperty(value = "响应码",example = "200")
    private Integer code;
    @ApiModelProperty(value = "数据")
    private T data;

    public TLResult() {
        this.msg = Constants.SUCCESS_MSG;
        this.code = Constants.SUCCESS_CODE;
    }

    public TLResult(T data) {
        this.msg = Constants.SUCCESS_MSG;
        this.code = Constants.SUCCESS_CODE;
        this.data = data;
    }

    public TLResult(String msg, Integer code, T data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }
    public static TLResult ok(){
        TLResult result=new TLResult();
        result.setCode (Constants.SUCCESS_CODE);
        result.setMsg(Constants.SUCCESS_MSG);
        return result;
    }
    public static TLResult ok(Object data){
        TLResult result=new TLResult();
        result.setCode (Constants.SUCCESS_CODE);
        result.setMsg(Constants.SUCCESS_MSG);
        result.setData(data);
        return result;
    }

    public static TLResult okPage(List<?> data){
        TLResult result=new TLResult();
        result.setCode (Constants.SUCCESS_CODE);
        result.setMsg(Constants.SUCCESS_MSG);
        result.setData(MyUtil.returnPageResultMap(data));
        return result;
    }

    public static TLResult error(ErrorEnum errorEnum){
        TLResult result=new TLResult();
        result.setCode(errorEnum.getKey());
        result.setMsg(errorEnum.getMsg());
        return result;
    }
}
