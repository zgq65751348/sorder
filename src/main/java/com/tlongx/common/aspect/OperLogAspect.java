package com.tlongx.common.aspect;

import com.alibaba.fastjson.JSON;
import com.tlongx.common.annotation.OpLog;
import com.tlongx.sorder.manager.pojo.Manager;
import com.tlongx.sorder.manager.service.ManagerService;
import com.tlongx.sorder.operation.pojo.OperationLog;
import com.tlongx.sorder.operation.service.OperationLogService;
import com.tlongx.sorder.utils.MyUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Enumeration;

/**
 * 系统日志：切面处理类
 */
@Aspect
@Component
public class OperLogAspect {

    @Autowired
    private OperationLogService operLogService;
    @Autowired
    private ManagerService managerService;

    HttpSession session = null;
    int count = 0;

    /**
     * 在注解的位置切入代码
     */
    @Pointcut("@annotation( com.tlongx.common.annotation.OpLog)")
    public void logPoinCut() {
    }

    /**
     * 配置通知
     * @param joinPoint
     */
    @AfterReturning("logPoinCut()")
    public void addOperLog(JoinPoint joinPoint) {
        //TODO session 判断
        //保存日志
        OperationLog operLog=new OperationLog();
        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();

        //获取操作
        OpLog opLog = method.getAnnotation(OpLog.class);
        if (opLog != null) {
            String value = opLog.value();
            //保存获取的操作
            operLog.setOper(value);
        }

        //获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        //获取请求的方法名
        String methodName = method.getName();
        operLog.setMethod(className + "." + methodName);

        //请求的参数
        Object[] args = joinPoint.getArgs();
        //将参数所在的数组转换成json
        String params = JSON.toJSONString(args);
        operLog.setParam(params);


        //获取用户ip地址
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        operLog.setIp(MyUtil.getIp2(request));
        operLog.setCrtime(new Date());
        String sid = request.getHeader("Sid");
        String mid = request.getHeader("Mid");
        Manager manager = managerService.getManageInfoByMid(mid);
        operLog.setMname(manager.getUsername());
        operLog.setSid(Integer.valueOf(sid));
        operLogService.addOperLog(operLog);
    }

}
