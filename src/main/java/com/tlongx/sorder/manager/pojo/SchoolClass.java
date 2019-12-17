package com.tlongx.sorder.manager.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@Table(name = "tl_school_class")
@ApiModel
public class SchoolClass {
    @Id
    @ApiModelProperty(value = "班级id",example = "1")
    private Integer scid;

    /**
     * 类型 1高中 2大学 3初中 4小学
     */
    @ApiModelProperty(value = "学校类型",example = "1")
    private Integer type;

    /**
     * 年级
     */
    @ApiModelProperty(value = "年级",example = "1")
    private Integer grade;

    /**
     * 班级
     */
    @Column(name = "class_num")
    @ApiModelProperty(value = "班级",example = "1")
    private Integer classNum;

    /**
     * 学校id
     */
    @ApiModelProperty(value = "学校id",example = "1")
    private Integer sid;

    public Integer getScid() {
        return scid;
    }

    public void setScid(Integer scid) {
        this.scid = scid;
    }

    /**
     * 获取类型 1高中 2大学
     *
     * @return type - 类型 1高中 2大学
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型 1高中 2大学
     *
     * @param type 类型 1高中 2大学
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取年级
     *
     * @return grade - 年级
     */
    public Integer getGrade() {
        return grade;
    }

    /**
     * 设置年级
     *
     * @param grade 年级
     */
    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    /**
     * 获取班级
     *
     * @return class_num - 班级
     */
    public Integer getClassNum() {
        return classNum;
    }

    /**
     * 设置班级
     *
     * @param classNum 班级
     */
    public void setClassNum(Integer classNum) {
        this.classNum = classNum;
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
}