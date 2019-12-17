package com.tlongx.sorder.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import java.io.Serializable;


public class UserExcelVo extends BaseRowModel implements Serializable {
    @ExcelProperty(index = 0)
    private String type;
    @ExcelProperty(index = 1)
    private String stuNum;
    @ExcelProperty(index = 2)
    private String username;
    @ExcelProperty(index = 3)
    private String phone;
    @ExcelProperty(index = 4)
    private Integer sex;
    @ExcelProperty(index = 5)
    private Integer grade;
    @ExcelProperty(index = 6)
    private Integer classNum;
    @ExcelProperty(index = 7)
    private String schoolType;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStuNum() {
        return stuNum;
    }

    public void setStuNum(String stuNum) {
        this.stuNum = stuNum;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getClassNum() {
        return classNum;
    }

    public void setClassNum(Integer classNum) {
        this.classNum = classNum;
    }

    public String getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType;
    }
}
