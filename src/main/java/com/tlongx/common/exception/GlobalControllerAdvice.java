package com.tlongx.common.exception;

import com.tlongx.common.ErrorEnum;
import com.tlongx.common.TLResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xin.rf
 * @date 2019/2/13 19:10
 * @Description 全局异常捕捉处理
 **/
@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(value = Exception.class)
    public TLResult errorHandler(Exception ex) {
        return TLResult.error(ErrorEnum.EXCEPTION_REQ);
    }

    /**
     * 拦截捕捉自定义异常 MyException.class
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = CodeException.class)
    public TLResult myErrorHandler(CodeException ex) {
        TLResult result=new TLResult();
        result.setMsg(ex.getMsg());
        result.setCode(ex.getCode());
        return result;
    }
}
