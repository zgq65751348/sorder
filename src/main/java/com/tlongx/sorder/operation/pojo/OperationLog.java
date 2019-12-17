package com.tlongx.sorder.operation.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tl_operation_log")
public class OperationLog {
    @Id
    private Integer id;

    /**
     * 操作方法名
     */
    @ApiModelProperty(name = "操作方法名")
    private String method;

    /**
     * 操作
     */
    @ApiModelProperty(name = "操作")
    private String oper;

    @ApiModelProperty(name="ip地址")
    private String ip;

    /**
     * 操作时间
     */
    @ApiModelProperty(name = "操作时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date crtime;

    /**
     * 学校id
     */
    @ApiModelProperty(name = "学校id")
    private Integer sid;

    /**
     * 参数
     */
    @ApiModelProperty(name = "参数")
    private String param;

    @ApiModelProperty(name = "操作人")
    private String mname;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取操作方法名
     *
     * @return method - 操作菜单
     */
    public String getMethod() {
        return method;
    }

    /**
     * 设置操作方法名
     *
     * @param method 操作菜单
     */
    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    /**
     * 获取操作
     *
     * @return oper - 操作
     */
    public String getOper() {
        return oper;
    }

    /**
     * 设置操作
     *
     * @param oper 操作
     */
    public void setOper(String oper) {
        this.oper = oper == null ? null : oper.trim();
    }

    /**
     * @return ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * 获取操作时间
     *
     * @return crtime - 操作时间
     */
    public Date getCrtime() {
        return crtime;
    }

    /**
     * 设置操作时间
     *
     * @param crtime 操作时间
     */
    public void setCrtime(Date crtime) {
        this.crtime = crtime;
    }

    /**
     * 获取学校id
     *
     * @return sid - 学校id
     */
    public Integer getSid() {
        return sid;
    }

    /**
     * 设置学校id
     *
     * @param sid 学校id
     */
    public void setSid(Integer sid) {
        this.sid = sid;
    }

    /**
     * 获取参数
     *
     * @return param - 参数
     */
    public String getParam() {
        return param;
    }

    /**
     * 设置参数
     *
     * @param param 参数
     */
    public void setParam(String param) {
        this.param = param == null ? null : param.trim();
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }
}