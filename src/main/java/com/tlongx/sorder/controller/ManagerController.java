package com.tlongx.sorder.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.tlongx.common.TLResult;
import com.tlongx.common.annotation.OpLog;
import com.tlongx.sorder.dto.*;
import com.tlongx.sorder.manager.pojo.*;
import com.tlongx.sorder.manager.service.ManagerService;
import com.tlongx.sorder.operation.pojo.OperationLog;
import com.tlongx.sorder.order.pojo.Recharge;
import com.tlongx.sorder.order.pojo.Trade;
import com.tlongx.sorder.order.service.OrderService;
import com.tlongx.sorder.setting.service.SettingService;
import com.tlongx.sorder.user.pojo.User;
import com.tlongx.sorder.user.pojo.UserCanteen;
import com.tlongx.sorder.user.pojo.UserSupplier;
import com.tlongx.sorder.utils.MyUtil;
import com.tlongx.sorder.vo.*;
import io.swagger.annotations.*;
import io.swagger.models.auth.In;
import javafx.scene.input.DataFormat;
import org.apache.poi.ss.formula.functions.T;
import org.bouncycastle.ocsp.Req;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;


/**
 * @author xin.rf
 * @date 2019/2/12 16:58
 * @Description TODO
 **/
@RestController
@RequestMapping("manager")
@Api("后台管理员相关API")
public class ManagerController {

    @Autowired
    private ManagerService managerService;
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ApiOperation(value = "【后台/平台】管理员登录", notes = "管理员登录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "username", value = "用户名", required = true),
            @ApiImplicitParam(paramType = "query", name = "password", value = "密码", required = true),
            @ApiImplicitParam(paramType = "query", name = "type", value = "1学校 2平台", required = true),
    })
    public TLResult<Manager> login(String username, String password, String type) {
        Manager manager= managerService.login(username, password, type);
        return TLResult.ok(manager);
    }


    @RequestMapping(value = "addSchool", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-平台】添加学校", notes = "添加学校")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "sname", value = "学校名称", required = true),
            @ApiImplicitParam(paramType = "query", name = "snum", value = "学校编号 限四位字母", required = true),
            @ApiImplicitParam(paramType = "query", name = "mname", value = "管理员名称", required = true),
            @ApiImplicitParam(paramType = "query", name = "username", value = "管理员用户名", required = true),
            @ApiImplicitParam(paramType = "query", name = "password", value = "管理员用户密码", required = true),
            @ApiImplicitParam(paramType = "query", name = "phone", value = "管理员手机号", required = true),
            @ApiImplicitParam(paramType = "query", name = "overdueTime", value = "过期时间 格式（yyyy-MM-dd HH:mm:ss）", required = true),
            @ApiImplicitParam(paramType = "query", name = "dredgeAlipay", value = "开通支付宝 0否 1是", required = true),
            @ApiImplicitParam(paramType = "query", name = "dredgeWxpay", value = "开通微信 0否 1是", required = true),
            @ApiImplicitParam(paramType = "query", name = "dredgeFace", value = "开通人脸 0否 1是", required = true),
    })
    public TLResult addSchool(SchoolVo schoolVo) {
        managerService.addSchool(schoolVo);
        return TLResult.ok();
    }

    @RequestMapping(value = "getSchoolList", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-平台】获取学校列表", notes = "获取学校列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "crtime", value = "开通时间", required = false),
            @ApiImplicitParam(paramType = "query", name = "snum", value = "学校编号 限四位字母", required = false),
            @ApiImplicitParam(paramType = "query", name = "mname", value = "管理员名称", required = false),
            @ApiImplicitParam(paramType = "query", name = "username", value = "管理员用户名", required = false),
            @ApiImplicitParam(paramType = "query", name = "sname", value = "学校名称", required = false),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页行数", required = true),
    })
    public TLResult getSchoolList(SchoolVo schoolVo) {
        List list=managerService.getSchoolList(schoolVo);
        return TLResult.okPage(list);
    }

    @RequestMapping(value = "getSchoolInfo", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-平台】获取学校信息", notes = "获取学校信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "sid", value = "学校id", required = true),
    })
    public TLResult<SchoolDto> getSchoolInfo(String sid) {
        SchoolDto schoolDto = managerService.getSchoolInfo(sid);
        return TLResult.ok(schoolDto);
    }

    @RequestMapping(value = "editSchool", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-平台】编辑学校", notes = "编辑学校")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "sid", value = "学校id", required = true),
            @ApiImplicitParam(paramType = "query", name = "mid", value = "管理员id", required = true),
            @ApiImplicitParam(paramType = "query", name = "status", value = "状态（0停用 1启用）", required = false),
            @ApiImplicitParam(paramType = "query", name = "sname", value = "学校名称", required = false),
            @ApiImplicitParam(paramType = "query", name = "snum", value = "学校编号 限四位字母", required = false),
            @ApiImplicitParam(paramType = "query", name = "mname", value = "管理员名称", required = false),
            @ApiImplicitParam(paramType = "query", name = "username", value = "管理员用户名", required = false),
            @ApiImplicitParam(paramType = "query", name = "password", value = "管理员用户密码", required = false),
            @ApiImplicitParam(paramType = "query", name = "phone", value = "管理员手机号", required = false),
            @ApiImplicitParam(paramType = "query", name = "overdueTime", value = "过期时间 格式（yyyy-MM-dd HH:mm:ss）", required = false),
            @ApiImplicitParam(paramType = "query", name = "subsidyStatus", value = "是否启用补贴 编辑时传1", required = false),
            @ApiImplicitParam(paramType = "query", name = "subsidyPrice", value = "补贴金额", required = false),
            @ApiImplicitParam(paramType = "query", name = "subsidyTime", value = "补贴时间", required = false),
            @ApiImplicitParam(paramType = "query", name = "dredgeAlipay", value = "开通支付宝 0否 1是", required = false),
            @ApiImplicitParam(paramType = "query", name = "dredgeWxpay", value = "开通微信 0否 1是", required = false),
            @ApiImplicitParam(paramType = "query", name = "dredgeFace", value = "开通人脸 0否 1是", required = false),
    })
    public TLResult editSchool(SchoolVo schoolVo) {
        managerService.editSchool(schoolVo);
        return TLResult.ok();
    }

    @RequestMapping(value = "upManagerPassword", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校/平台】修改管理员密码", notes = "修改管理员密码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "mid", value = "mid", required = true),
            @ApiImplicitParam(paramType = "query", name = "oldPassword", value = "旧密码", required = true),
            @ApiImplicitParam(paramType = "query", name = "newPassword", value = "新密码", required = true),
            @ApiImplicitParam(paramType = "query", name = "phoneCode", value = "手机验证码",required = true)
    })
    public TLResult upManagerPassword(String mid, String oldPassword, String newPassword, String phoneCode) {
        managerService.upManagerPassword(mid, oldPassword, newPassword,phoneCode);
        return TLResult.ok();
    }

    @OpLog(value = "添加班级")
    @RequestMapping(value = "addSchoolClass", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】添加班级", notes = "添加班级")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "grade", value = "年级 格式：1", required = true),
            @ApiImplicitParam(paramType = "query", name = "classNum", value = "班级 格式：2", required = true),
            @ApiImplicitParam(paramType = "query", name = "sid", value = "学校id", required = true),
            @ApiImplicitParam(paramType = "query", name = "type", value = "学校类型", required = true),
    })
    public TLResult addSchoolClass(SchoolClass schoolClass) {
        managerService.addClass(schoolClass);
        return TLResult.ok();
    }

    @RequestMapping(value = "getSchoolClassList", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】获取班级列表", notes = "获取班级列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "sid", value = "学校id", required = true),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页行数", required = true),
    })
    public TLResult<SchoolClass> getSchoolClassList(SchoolClassVo schoolClassVo) {
        List<SchoolClass> list= managerService.getClassList(schoolClassVo);
        return TLResult.okPage(list);
    }

    @OpLog(value = "编辑班级信息")
    @RequestMapping(value = "editClass", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】编辑班级信息", notes = "编辑班级信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "scid", value = "scid", required = true),
            @ApiImplicitParam(paramType = "query", name = "grade", value = "年级 格式：1", required = true),
            @ApiImplicitParam(paramType = "query", name = "classNum", value = "班级 格式：2", required = true),
    })
    public TLResult editClass(SchoolClass schoolClass) {
        managerService.editClass(schoolClass);
        return TLResult.ok();
    }

    @OpLog(value = "删除班级信息")
    @RequestMapping(value = "deleteClass", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】删除班级信息", notes = "删除班级信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "scid", value = "scid", required = true),
    })
    public TLResult deleteClass(Integer scid) {
        managerService.deleteClass(scid);
        return TLResult.ok();
    }

    @OpLog(value = "添加食堂")
    @RequestMapping(value = "addCanteen", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】添加食堂", notes = "添加食堂")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "cname", value = "食堂名称", required = true),
            @ApiImplicitParam(paramType = "query", name = "sid", value = "学校id", required = true),
            @ApiImplicitParam(paramType = "query", name = "uname", value = "食堂管理员姓名", required = true),
            @ApiImplicitParam(paramType = "query", name = "phone", value = "食堂管理员手机号", required = true),
            @ApiImplicitParam(paramType = "query", name = "sex", value = "食堂管理员性别 1男 2女", required = true),

    })
    public TLResult addCanteen(UserCanteen userCanteen) {
        managerService.addCanteen(userCanteen);
        return TLResult.ok();
    }

    @RequestMapping(value = "getCanteenList", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校/APP】获取食堂列表", notes = "获取食堂列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "sid", value = "学校id", required = true),
    })
    public TLResult<UserCanteen> getCanteenList(String sid) {
        List<UserCanteen> list=managerService.getCanteenList(sid);
        return TLResult.ok(list);
    }

    @RequestMapping(value = "getCanteenInfo", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】获取食堂信息", notes = "获取食堂信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "uid", value = "食堂管理员id", required = true),
    })
    public TLResult getCanteenInfo(String uid) {
        UserCanteen userCanteen=managerService.getCanteenInfo(uid);
        return TLResult.ok(userCanteen);
    }

    @OpLog(value = "编辑食堂信息")
    @RequestMapping(value = "editCanteen", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】编辑食堂信息", notes = "编辑食堂信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "cid", value = "食堂id", required = true),
            @ApiImplicitParam(paramType = "query", name = "cname", value = "食堂名称", required = false),
            @ApiImplicitParam(paramType = "query", name = "uid", value = "食堂管理员id", required = true),
            @ApiImplicitParam(paramType = "query", name = "uname", value = "食堂管理员姓名", required = false),
            @ApiImplicitParam(paramType = "query", name = "phone", value = "食堂管理员手机号", required = false),
            @ApiImplicitParam(paramType = "query", name = "password", value = "食堂管理员密码", required = false),
            @ApiImplicitParam(paramType = "query", name = "sex", value = "食堂管理员性别 1男 2女", required = false),
    })
    public TLResult editCanteen(UserCanteen userCanteen) {
        managerService.editCanteen(userCanteen);
        return TLResult.ok();
    }

    @OpLog(value = "删除食堂信息")
    @RequestMapping(value = "deleteCanteen", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】删除食堂信息", notes = "删除食堂信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "cid", value = "食堂id", required = true),
    })
    public TLResult deleteCanteen(String cid) {
        managerService.deleteCanteen(cid);
        return TLResult.ok();
    }

    @OpLog(value = "添加轮播广告信息")
    @RequestMapping(value = "addAdv", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】添加轮播广告信息", notes = "添加轮播广告信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "sid", value = "学校id", required = true),
            @ApiImplicitParam(paramType = "query", name = "photo", value = "图片途径", required = true),
    })
    public TLResult addAdv(Advertisement advertisement) {
        managerService.addAdv(advertisement);
        return TLResult.ok();
    }

    @RequestMapping(value = "getAdvList", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】获取轮播广告列表", notes = "获取轮播广告列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "sid", value = "学校id", required = true),
    })
    public TLResult<Advertisement> getAdvList(String sid) {
        List<Advertisement> list=managerService.getAdvList(sid);
        return TLResult.ok(list);
    }

    @OpLog(value = "编辑轮播广告信息")
    @RequestMapping(value = "editAdv", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】编辑轮播广告信息", notes = "编辑轮播广告")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "id", required = true),
            @ApiImplicitParam(paramType = "query", name = "photo", value = "图片途径", required = true),
    })
    public TLResult editAdv(String id,String photo) {
        managerService.editAdv(id,photo);
        return TLResult.ok();
    }

    @OpLog(value = "删除轮播广告信息")
    @RequestMapping(value = "deleteAdv", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】删除轮播广告信息", notes = "删除轮播广告")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "id", required = true),
    })
    public TLResult deleteAdv(String id) {
        managerService.deleteAdv(id);
        return TLResult.ok();
    }

    @RequestMapping(value = "getFoodCategoryList", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】获取食品时段列表", notes = "获取食品时段列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "sid", value = "sid", required = true),
    })
    public TLResult<SupplierTime> getFoodCategoryList(String sid) {
        List<SupplierTime> list=managerService.getSupplierTimeList(sid);
        return TLResult.ok(list);
    }

    @RequestMapping(value = "getFoodCategoryInfo", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】获取食品时段信息", notes = "获取食品时段信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "id", required = true),
    })
    public TLResult<SupplierTime> getFoodCategoryInfo(Integer id) {
        SupplierTime supplierTime=managerService.getSupplierTimeInfo(id);
        return TLResult.ok(supplierTime);
    }

    @OpLog(value = "编辑食品时段信息")
    @RequestMapping(value = "editSupplierTime", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】编辑食品时段信息", notes = "编辑食品时段信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "id", required = true),
            @ApiImplicitParam(paramType = "query", name = "supplyStart", value = "供应开始时间", required = true),
            @ApiImplicitParam(paramType = "query", name = "supplyEnd", value = "供应结束时间", required = true),
            @ApiImplicitParam(paramType = "query", name = "subsidyPrice", value = "补贴金额", required = true),
            @ApiImplicitParam(paramType = "query", name = "subsidyStatus", value = "补贴状态 0停用 1启用", required = true),
    })
    public TLResult editFoodCategory(SupplierTime supplierTime) {
        managerService.editSupplierTime(supplierTime);
        return TLResult.ok();
    }

    @RequestMapping(value ="getUserList",method = RequestMethod.POST)
    @ApiOperation(value = "【后台/平台-学校】师生列表", notes = "师生列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "username", value = "姓名", required = false),
            @ApiImplicitParam(paramType = "query",name = "stuNum", value = "学号", required = false),
            @ApiImplicitParam(paramType = "query",name = "phone", value = "手机号", required = false),
            @ApiImplicitParam(paramType = "query",name = "type", value = "用户类型 1学生 2教师 3班主任 4其他人员(平台传1) 5老师和班主任", required = false),
            @ApiImplicitParam(paramType = "query",name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(paramType = "query",name = "pageSize", value = "每页行数", required = true),
            @ApiImplicitParam(paramType = "query",name = "sid", value = "学校id", required = false),
            @ApiImplicitParam(paramType = "query",name = "grade", value = "年级", required = false),
            @ApiImplicitParam(paramType = "query",name = "classNum", value = "班级", required = false),
            @ApiImplicitParam(paramType = "query",name = "schoolNum", value = "学校类型", required = false),
})
    public TLResult getUserList(UserVo userVo) {
        List result=managerService.getUserList(userVo);
        return TLResult.okPage(result);
    }

    @RequestMapping(value ="getUserInfo",method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】获取师生详细信息", notes = "获取师生详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "uid", value = "uid", required = true),
    })
    public TLResult<User> getUserInfo(String uid) {
        User user=managerService.getUserInfo(uid);
        return TLResult.ok(user);
    }


    @OpLog(value = "编辑师生信息")
    @RequestMapping(value ="editUser",method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】编辑师生信息", notes = "编辑师生信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "uid", value = "uid", required = true),
            @ApiImplicitParam(paramType = "query",name = "username", value = "姓名", required = false),
            @ApiImplicitParam(paramType = "query",name = "stuNum", value = "学号", required = false),
            @ApiImplicitParam(paramType = "query",name = "type", value = "用户类型 1学生 2教师 3班主任 4其他人员", required = false),
            @ApiImplicitParam(paramType = "query",name = "phone", value = "手机号", required = false),
            @ApiImplicitParam(paramType = "query",name = "sex", value = "性别", required = false),
            @ApiImplicitParam(paramType = "query",name = "status", value = "状态（0未通过 1通过 2待审核）", required = false),
            @ApiImplicitParam(paramType = "query",name = "balance", value = "余额", required = false),
    })
    public TLResult editUser(User user) {
        managerService.editUser(user);
        return TLResult.ok();
    }

    @OpLog(value = "删除师生信息")
    @RequestMapping(value ="deleteUser",method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】删除师生信息", notes = "删除师生信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "uid", value = "uid", required = true),
    })
    public TLResult deleteUser(String uid) {
        managerService.deleteUser(uid);
        return TLResult.ok();
    }

    @RequestMapping(value ="getAuditTeacherList",method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】获取审核教师列表", notes = "获取审核教师列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(paramType = "query",name = "pageSize", value = "每页行数", required = true),
            @ApiImplicitParam(paramType = "query",name = "sid", value = "学校id", required = true),
            @ApiImplicitParam(paramType = "query",name = "sid", value = "学校id", required = false),
            @ApiImplicitParam(paramType = "query",name = "grade", value = "年级", required = false),
            @ApiImplicitParam(paramType = "query",name = "classNum", value = "班级", required = false),
            @ApiImplicitParam(paramType = "query",name = "schoolNum", value = "学校类型", required = false),
    })
    public TLResult<UserDto> getAuditTeacherList(UserVo userVo) {
        List<UserDto> list=managerService.getAuditTeacherList(userVo);
        return TLResult.okPage(list);
    }

    @RequestMapping(value ="getUserSupplierList",method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】获取供应商列表", notes = "获取供应商列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "sid", value = "学校id", required = true),
            @ApiImplicitParam(paramType = "query",name = "shopName", value = "店名", required = false),
            @ApiImplicitParam(paramType = "query",name = "leaderName", value = "负责人姓名", required = false),
            @ApiImplicitParam(paramType = "query",name = "leaderPhone", value = "负责人电话", required = false),
            @ApiImplicitParam(paramType = "query",name = "approveTime", value = "入驻时间（审核通过时间）", required = false),
            @ApiImplicitParam(paramType = "query",name = "status", value = "店铺审核状态 0未通过 1通过 2待审核 3注册后未提交入驻审核", required = false),
            @ApiImplicitParam(paramType = "query",name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(paramType = "query",name = "pageSize", value = "每页行数", required = true),
    })
    public TLResult<SupplierDto> getUserSupplierList(UserSupplierVo userSupplierVo) {
        List<SupplierDto> list=managerService.getUserSupplierList(userSupplierVo);
        return TLResult.okPage(list);
    }

    @RequestMapping(value ="getUserSupplierInfo",method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】获取供应商信息", notes = "获取供应商信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "uid", value = "uid", required = true),
    })
    public TLResult<UserSupplier> getUserSupplierInfo(String uid) {
        UserSupplier userSupplier=managerService.getUserSupplierInfo(uid);
        return TLResult.ok(userSupplier);
    }

//    @RequestMapping(value ="editUserSupplierStatus",method = RequestMethod.POST)
//    @ApiOperation(value = "【后台-学校】修改供应商状态", notes = "修改供应商状态")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query",name = "uid", value = "uid", required = true),
//            @ApiImplicitParam(paramType = "query",name = "status", value = "status", required = true),
//    })
//    public TLResult editUserSupplierStatus(String uid,Integer status) {
//        managerService.editUserSupplierStatus(uid,status);
//        return TLResult.ok();
//    }

    @OpLog(value = "启用/停用供应商")
    @RequestMapping(value ="editSchoolSupplierStatus",method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】启用/停用供应商", notes = "启用/停用供应商")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "id", value = "id", required = true),
            @ApiImplicitParam(paramType = "query",name = "status", value = "status 0停用 1启用 2拒绝", required = true),
            @ApiImplicitParam(paramType = "query",name = "mid", value = "管理员id", required = true),
    })
    public TLResult editSchoolSupplierStatus(Integer id,Integer status,String mid) {
        managerService.editSchoolSupplierStatus(id,status,mid);
        return TLResult.ok();
    }

    @OpLog(value = "添加食堂窗口")
    @RequestMapping(value ="addWindow",method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】添加食堂窗口", notes = "添加食堂窗口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "wname", value = "窗口名称", required = true),
            @ApiImplicitParam(paramType = "query",name = "status", value = "status 首页是否显示 0不显示 1显示", required = true),
            @ApiImplicitParam(paramType = "query",name = "sid", value = "学校id", required = true),
            @ApiImplicitParam(paramType = "query",name = "cid", value = "食堂id", required = true),
    })
    public TLResult addWindow(Window window) {
        managerService.addWindow(window);
        return TLResult.ok();
    }

    @OpLog(value = "编辑食堂窗口")
    @RequestMapping(value ="editWindow",method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】编辑食堂窗口", notes = "编辑食堂窗口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "wid", value = "窗口wid", required = true),
            @ApiImplicitParam(paramType = "query",name = "wname", value = "窗口名称", required = true),
            @ApiImplicitParam(paramType = "query",name = "status", value = "status 首页是否显示 0不显示 1显示", required = true),
    })
    public TLResult editWindow(Window window) {
        managerService.editWindow(window);
        return TLResult.ok();
    }

    @OpLog(value = "删除食堂窗口")
    @RequestMapping(value ="deleteWindow",method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】删除食堂窗口", notes = "删除食堂窗口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "wid", value = "窗口wid", required = true),
    })
    public TLResult deleteWindow(Integer wid) {
        managerService.deleteWindow(wid);
        return TLResult.ok();
    }

    @RequestMapping(value ="getWindowList",method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】获取食堂窗口列表", notes = "获取食堂窗口列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "cid", value = "食堂id", required = true),
            @ApiImplicitParam(paramType = "query",name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(paramType = "query",name = "pageSize", value = "每页行数", required = true),
    })
    public TLResult<Window> getWindowList(WindowVo windowVo) {
        List<Window> list=managerService.getWindowList(windowVo);
        return TLResult.okPage(list);
    }

    @RequestMapping(value ="getDiscountList",method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】获取优惠制度列表", notes = "获取优惠制度列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "sid", value = "sid", required = true),
    })
    public TLResult<DiscountPrice> getDiscountList(Integer sid) {
        List<DiscountPrice> list=managerService.getDiscountList(sid);
        return TLResult.ok(list);
    }

    @OpLog(value = "编辑优惠制度信息")
    @RequestMapping(value ="editDiscountInfo",method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】编辑优惠制度信息", notes = "编辑优惠制度信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "id", value = "id", required = true),
            @ApiImplicitParam(paramType = "query",name = "type", value = "类型 1:满减 2:金额折扣", required = true),
            @ApiImplicitParam(paramType = "query",name = "discountPrice", value = "优惠价格", required = true),
            @ApiImplicitParam(paramType = "query",name = "price", value = "type=1 时  设置  满减金额", required = true),
            @ApiImplicitParam(paramType = "query",name = "startTime", value = "开始时间", required = true),
            @ApiImplicitParam(paramType = "query",name = "endTime", value = "结束时间", required = true),
    })
    public TLResult editDiscountInfo(Integer id,Integer type,BigDecimal discountPrice,BigDecimal price,String startTime,String endTime) {
        DiscountPrice discountPrice1=new DiscountPrice();
        discountPrice1.setId(id);
        discountPrice1.setType(type);
        discountPrice1.setDiscountPrice(discountPrice);
        discountPrice1.setPrice(price);
        discountPrice1.setStartTime(DateUtil.parse(startTime,"yyyy-MM-dd HH:mm:ss"));
        discountPrice1.setEndTime(DateUtil.parse(endTime,"yyyy-MM-dd HH:mm:ss"));
        managerService.editDiscountInfo(discountPrice1);
        return TLResult.ok();
    }

    @RequestMapping(value ="getFoodListBack",method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】获取食品列表", notes = "获取食品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "pageSize", value = "每页行数", required = true),
            @ApiImplicitParam(paramType = "query",name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(paramType = "query",name = "sid", value = "学校id", required = true),
            @ApiImplicitParam(paramType = "query",name = "foodName", value = "食品名称", required = false),
            @ApiImplicitParam(paramType = "query",name = "supplier", value = "供应商", required = false),
    })
    public TLResult<FoodBackDto> getFoodListBack(FoodVo foodVo) {
        List<FoodBackDto> listBack=managerService.getFoodListBack(foodVo);
        return TLResult.okPage(listBack);
    }

    @OpLog(value = "审核教师")
    @RequestMapping(value ="auditTeacher",method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】审核教师", notes = "审核教师")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "uid", value = "教师uid", required = true),
            @ApiImplicitParam(paramType = "query",name = "status", value = "0不通过 1通过", required = true),
    })
    public TLResult auditTeacher(String uid,Integer status) {
        managerService.auditTeacher(uid,status);
        return TLResult.ok();
    }

    @RequestMapping(value ="getDataStatisticList",method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】获取食堂就餐统计列表", notes = "获取食堂就餐统计列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "sid", value = "学校id", required = true),
            @ApiImplicitParam(paramType = "query",name = "startTime", value = "日期 格式yyyy-MM-dd", required = false),
            @ApiImplicitParam(paramType = "query",name = "cid", value = "食堂id", required = true),
            @ApiImplicitParam(paramType = "query",name = "wid", value = "窗口id", required = false),
            @ApiImplicitParam(paramType = "query",name = "type", value = "用户类型 1学生 2教师 3其他人员", required = false),
            @ApiImplicitParam(paramType = "query",name = "grade", value = "年级id", required = false),
            @ApiImplicitParam(paramType = "query",name = "classNum", value = "班级id", required = false),
            @ApiImplicitParam(paramType = "query",name = "schoolNum", value = "学校类型", required = false),
    })
    public TLResult getDataStatisticList(DataStatisticsVo dataStatisticsVo) {
        List<Map<String,Object>> list=orderService.getDataStatisticList(dataStatisticsVo);
        return TLResult.okPage(list);
    }

//    @RequestMapping(value ="backupData",method = RequestMethod.GET)
//    @ApiOperation(value = "【后台-学校】备份用餐统计", notes = "备份用餐统计")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query",name = "sid", value = "学校id", required = true),
//            @ApiImplicitParam(paramType = "query",name = "startTime", value = "日期 格式yyyy-MM-dd HH:mm:ss", required = false),
//            @ApiImplicitParam(paramType = "query",name = "endTime", value = "日期 格式yyyy-MM-dd HH:mm:ss", required = false),
//            @ApiImplicitParam(paramType = "query",name = "cid", value = "食堂id", required = true),
//            @ApiImplicitParam(paramType = "query",name = "wid", value = "窗口id", required = false),
//            @ApiImplicitParam(paramType = "query",name = "type", value = "用户类型 1学生 2教师 3其他人员", required = false),
//            @ApiImplicitParam(paramType = "query",name = "grade", value = "年级id", required = false),
//            @ApiImplicitParam(paramType = "query",name = "classNum", value = "班级id", required = false),
//    })
//    public TLResult backupData(HttpServletResponse response,DataStatisticsVo dataStatisticsVo) {
//        orderService.backupData(response,dataStatisticsVo);
//        return TLResult.ok();
//    }

    @RequestMapping(value = "getSchoolGradeList", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】获取年级下拉列表", notes = "获取年级下拉列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "sid", value = "学校id", required = true),
            @ApiImplicitParam(paramType = "query", name = "type", value = "类型", required = false),
            @ApiImplicitParam(paramType = "query", name = "grade",value="年级",required = false),
            @ApiImplicitParam(paramType = "query", name = "classNum",value="班级",required = false)
    })
    public TLResult<SchoolClass> getSchoolGradeList(Integer sid,Integer type,Integer grade,Integer classNum) {
        List<SchoolClass> list = managerService.getSchoolGradeList(sid,type,grade,classNum);
        return TLResult.ok(list);
    }

    @RequestMapping(value = "getClassNumList", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】获取班级下拉列表", notes = "获取班级下拉列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "sid", value = "学校id", required = true),
            @ApiImplicitParam(paramType = "query", name = "grade", value = "年级id", required = true),
    })
    public TLResult<SchoolClass> getClassNumList(SchoolClass schoolClass) {
        List<SchoolClass> list = managerService.getClassNumList(schoolClass);
        return TLResult.ok(list);
    }

//    @RequestMapping(value = "getGradeAndClassList", method = RequestMethod.POST)
//    @ApiOperation(value = "【后台-学校】获取年级和班级下拉列表", notes = "获取年级和班级下拉列表")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "sid", value = "学校id", required = true),
//    })
//    public TLResult<SchoolClass> getGradeAndClassList(SchoolClass schoolClass) {
//        List<SchoolClass> list = managerService.getGradeAndClassList(schoolClass);
//        return TLResult.ok(list);
//    }

    @OpLog(value = "添加人员信息")
    @RequestMapping(value = "addUserInfo", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】添加人员信息", notes = "添加人员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "username", value = "姓名", required = true),
            @ApiImplicitParam(paramType = "query", name = "phone", value = "手机号", required = true),
            @ApiImplicitParam(paramType = "query", name = "sex", value = "性别", required = true),
            @ApiImplicitParam(paramType = "query", name = "type", value = "用户类型（1学生 2教师 3班主任 4其他人员）", required = true),
            @ApiImplicitParam(paramType = "query", name = "sid", value = "学校id", required = true),
            @ApiImplicitParam(paramType = "query", name = "schoolType", value = "学校类型", required = true),
            @ApiImplicitParam(paramType = "query", name = "stuNum", value = "学号", required = true),
            @ApiImplicitParam(paramType = "query", name = "grade", value = "年级", required = false),
            @ApiImplicitParam(paramType = "query", name = "classNum", value = "班级", required = false),
    })
    public TLResult addUserInfo(User user,Integer schoolType,Integer grade,Integer classNum) {
        managerService.addUserInfo(user,schoolType,grade,classNum);
        return TLResult.ok();
    }

    @OpLog(value = "批量添加人员信息")
    @RequestMapping(value = "addUserInfoBatch", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】批量添加人员信息", notes = "批量添加人员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "excel", value = "excel文件", required = true),
            @ApiImplicitParam(paramType = "query", name = "sid", value = "学校id", required = true),
    })
    public TLResult addUserInfoBatch(MultipartFile excel,Integer sid) {
        managerService.addUserInfoBatch(excel,sid);
        return TLResult.ok();
    }

    @RequestMapping(value = "getPlaTradeList", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-平台】获取平台流水列表", notes = "获取平台流水列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页行数", required = true),
            @ApiImplicitParam(paramType = "query", name = "startTime", value = "开始时间 yyyy-MM-dd HH:mm:ss", required = false),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "结束时间", required = false),
            @ApiImplicitParam(paramType = "query", name = "sid", value = "学校id", required = false),
    })
    public TLResult getPlaTradeList(PlatTradeVo platTradeVo) {
        List<PlatTradeDto> list=managerService.getPlatTradeList(platTradeVo);
        return TLResult.ok(list);
    }

    @RequestMapping(value = "subsidyForTeacher", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】对单个教师进行补贴", notes = "对单个教师进行补贴")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "uid", value = "uid", required = true),
            @ApiImplicitParam(paramType = "query", name = "subsidyPrice", value = "补贴金额", required = true),
    })
    public TLResult subsidyForTeacher(String uid, BigDecimal subsidyPrice) {
        User user=new User();
        user.setUid(uid);
        user.setSubsidyPrice(subsidyPrice);
        managerService.subsidyForTeacher(user);
        return TLResult.ok();
    }

    @RequestMapping(value = "getSubsidyHistoryList", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】教师补贴历史列表", notes = "教师补贴历史列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页行数", required = true),
            @ApiImplicitParam(paramType = "query", name = "sid", value = "学校id", required = true),
            @ApiImplicitParam(paramType = "query", name = "username", value = "姓名", required = false),
            @ApiImplicitParam(paramType = "query", name = "phone", value = "手机号码", required = false),
            @ApiImplicitParam(paramType = "query", name = "startTime", value = "开始时间", required = false),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "结束时间", required = false),
    })
    public TLResult<SubsidyHistoryDto> getSubsidyHistoryList(TradeVo tradeVo) {
        List<SubsidyHistoryDto> list=managerService.getSubsidyHistoryList(tradeVo);
        return TLResult.okPage(list);
    }

    @RequestMapping(value = "getFeedBackList", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】获取意见反馈列表", notes = "获取意见反馈列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页行数", required = true),
            @ApiImplicitParam(paramType = "query", name = "sid", value = "学校id", required = true),
    })
    public TLResult<FeedBackDto> getFeedBackList(FeedBackVo feedBackVo) {
        List<FeedBackDto> list=managerService.getFeedBackList(feedBackVo);
        return TLResult.okPage(list);
    }

    @OpLog(value = "添加子管理员")
    @RequestMapping(value = "addSchoolManager", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】添加子管理员", notes = "添加子管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "mname", value = "姓名", required = true),
            @ApiImplicitParam(paramType = "query", name = "username", value = "登录名/用户名", required = true),
            @ApiImplicitParam(paramType = "query", name = "password", value = "密码", required = true),
            @ApiImplicitParam(paramType = "query", name = "phone", value = "手机号", required = true),
            @ApiImplicitParam(paramType = "query", name = "parentId", value = "操作人mid", required = true),
            @ApiImplicitParam(paramType = "query", name = "role", value = "角色 1管理员 2子管理员 3收银员", required = true),
    })
    public TLResult addSchoolManager(Manager manager) {
        managerService.addSchoolManager(manager);
        return TLResult.ok();
    }

    @RequestMapping(value = "getSchoolManagerList", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】获取学校子管理员列表", notes = "获取学校子管理员列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "sid", value = "sid", required = true),
    })
    public TLResult<Manager> getSchoolManagerList(Manager manager) {
        List<Manager> list=managerService.getSchoolManagerList(manager);
        return TLResult.ok(list);
    }

    @RequestMapping(value = "getSchoolDetail", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】获取学校信息详情", notes = "获取学校信息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "sid", value = "学校id", required = true)
    })
    public TLResult<School> getSchoolDetail(String sid){
        School school = managerService.getSchoolDetail(sid);
        return TLResult.ok(school);
    }

    @RequestMapping(value = "getWithDrawSchoolBalance", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】提现学校余额", notes = "提现学校余额")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "sid", value = "学校id", required = true),
            @ApiImplicitParam(paramType = "query", name = "type", value = "提现方式 1支付宝 2微信", required = true),
            @ApiImplicitParam(paramType = "query", name = "price", value = "提现金额", required = true),
            @ApiImplicitParam(paramType = "query", name = "mid", value = "mid", required = true),
            @ApiImplicitParam(paramType = "query", name = "data", value = "提现账号  支付宝账号--微信openId", required = true)
    })
    public TLResult getWithDrawSchoolBalance(String sid,Integer type,BigDecimal price,String mid,String data){
         managerService.withDraw(sid,type,price,mid,data);
        return TLResult.ok();
    }

    @RequestMapping(value = "downExcel", method = RequestMethod.GET)
    @ApiOperation(value = "【后台-学校】下载学生/教师信息导入模板", notes = "下载学生/教师信息导入模板")
    public TLResult downExcel(HttpServletResponse response, HttpServletRequest request) throws IOException {

        String filePath = getClass().getResource("/templates/学生-教师信息导入模板.xlsx").getPath();

        int length = filePath.lastIndexOf("/");
        String name = filePath.substring(0,length+1);
        String path = name+"学生-教师信息导入模板.xlsx";
        InputStream bis = new BufferedInputStream(new FileInputStream(new File(path)));

        //设置下载文件名
        String fileName = "学生-教师信息导入模板.xlsx";

        String filename = URLEncoder.encode(fileName,"UTF-8");

        //设置文件下载头
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");

        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        int len = 0;
        while((len = bis.read()) != -1){
            out.write(len);
            out.flush();
        }
        out.close();
        return TLResult.ok();
    }

    @RequestMapping(value = "getSchoolBankAccountInfo",method = RequestMethod.POST)
    @ApiOperation(value="【后台-学校】获取学校银行账户信息",notes = "获取学校银行账户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name="sid",value = "学校id",required = true)
    })
    public TLResult getSchoolBankAccountInfo(Integer sid){
        School schoolBankAccountInfo = managerService.getSchoolBankAccountInfo(sid);
        return TLResult.ok(schoolBankAccountInfo);
    }

    @OpLog(value = "新增/修改学校账户信息")
    @RequestMapping(value = "addOrEditSchoolBankAccount",method = RequestMethod.POST)
    @ApiOperation(value="【后台-学校】新增/修改学校账户信息",notes = "新增/修改学校账户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "sid",value="学校id",required = true),
            @ApiImplicitParam(paramType = "query",name = "openingBank",value = "开户行",required = true),
            @ApiImplicitParam(paramType = "query",name = "bankUserName",value = "户名",required = true),
            @ApiImplicitParam(paramType = "query",name = "bankAccount", value="银行账号",required = true)
    })
    public TLResult addOrEditSchoolBankAccount(School school){
        managerService.addOrEditSchoolBankAccount(school);
        return TLResult.ok();
    }

    @RequestMapping(value = "addPayRecord",method = RequestMethod.POST)
    @ApiOperation(value="【后台-平台】新增付款记录",notes = "新增付款记录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "sid",value = "学校id",required = true),
            @ApiImplicitParam(paramType = "query", name = "crtime",value = "支付日期",required = true),
            @ApiImplicitParam(paramType = "query",name = "balance",value = "结算后账户余额",required = true),
            @ApiImplicitParam(paramType = "query", name ="price",value = "本次支付余额",required = true),
            @ApiImplicitParam(paramType = "query", name = "account",value = "交易账号",required = true),
            @ApiImplicitParam(paramType = "query",name="payOrder",value = "支付凭证",required = true),
    })
    public TLResult addPayRecord(TradeDto trade,Integer sid,String mid){
        managerService.addPayOrder(trade,sid);
        return TLResult.ok();
    }

    @RequestMapping(value = "getSchoolAccountInfo",method = RequestMethod.POST)
    @ApiOperation(value="【后台-平台】获取学校账户相关信息",notes = "获取学校账户相关信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "sid",value = "学校id",required = true)
    })
    public TLResult getSchoolAccountInfo(Integer sid){
        SchoolDto schoolDto = managerService.getSchoolAccountInfo(sid);
        return TLResult.ok(schoolDto);
    }

    @RequestMapping(value = "getPayOrderList", method = RequestMethod.POST)
    @ApiOperation(value ="【后台-平台】查询付款记录",notes = "查询付款记录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name="sname",value="院校名称",required = false),
            @ApiImplicitParam(paramType = "query",name="username",value="负责人",required = false),
            @ApiImplicitParam(paramType = "query", name = "phone",value = "联系方式",required = false),
            @ApiImplicitParam(paramType = "query",name = "startTime",value="开始时间",required = false),
            @ApiImplicitParam(paramType = "query",name = "endTime",value = "结束时间",required = false),
            @ApiImplicitParam(paramType = "query",name="mid",value="操作人id",required = true),
            @ApiImplicitParam(paramType="query",name="pageSize",value="pageSize",required = true),
            @ApiImplicitParam(paramType="query",name="pageNum",value="pageNum",required = true),
    })
    public TLResult getPayOrderList(TradeDto tradeDto){
        List<TradeDto> tradeDtoList = managerService.getPayOrderList(tradeDto);
        return TLResult.okPage(tradeDtoList);
    }

    @RequestMapping(value = "getPayOrder",method = RequestMethod.POST)
    @ApiOperation(value = "【后台-平台】获取支付凭证",notes = "获取支付凭证")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "tid",value="流水id")
    })
    public TLResult getPayOrder(String tid){
        String payOrder = managerService.getPayOrder(tid);
        return TLResult.ok(payOrder);
    }

    @RequestMapping(value="getOperationLogList",method = RequestMethod.POST)
    @ApiOperation(value="【后台-学校】查看操作日志列表",notes="查看操作日志列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name="sid",value="学校id"),
            @ApiImplicitParam(paramType="query",name="pageSize",value="pageSize",required = true),
            @ApiImplicitParam(paramType="query",name="pageNum",value="pageNum",required = true),
    })
    public TLResult getOperationLogList(OpLogVo opLogVo){
        List<OperationLog> logList = managerService.getOperationLogList(opLogVo);
        return TLResult.okPage(logList);
    }

    @RequestMapping(value="sendPhoneCode", method = RequestMethod.POST)
    @ApiOperation(value="【后台-学校/平台】发送短信验证码", notes = "发送短信验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "mid", value = "管理员id"),
    })
    public TLResult sendPhoneCode(String mid){
        String code = managerService.sendPhoneCode(mid);
        return TLResult.ok(code);
    }

    @RequestMapping(value="getMarketSaleList", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】超市日销量统计",notes = "超市日销量统计")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "sid", value = "学校id", required = true),
            @ApiImplicitParam(paramType = "query",name = "startTime", value = "日期 格式yyyy-MM-dd", required = false),
            @ApiImplicitParam(paramType = "query",name = "type", value = "用户类型 1学生 2教师 3其他人员", required = false),
            @ApiImplicitParam(paramType = "query",name = "grade", value = "年级id", required = false),
            @ApiImplicitParam(paramType = "query",name = "classNum", value = "班级id", required = false),
            @ApiImplicitParam(paramType = "query",name = "schoolNum", value = "学校类型", required = false),
    })
    public TLResult getMarketSaleList(DataStatisticsVo dataStatisticsVo){
        List<Map<String,Object>> list = orderService.getMarketSaleList(dataStatisticsVo);
        return TLResult.ok(list);
    }

    @RequestMapping(value="getSchoolLedgerList",method = RequestMethod.POST)
    @ApiOperation(value="【后台-学校】学校收入分类账",notes = "学校收入分类账")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "sid", value = "学校id", required = true),
            @ApiImplicitParam(paramType="query",name="pageSize",value="pageSize",required = true),
            @ApiImplicitParam(paramType="query",name="pageNum",value="pageNum",required = true)
    })
    public TLResult getSchoolLedgerList(SchoolVo schoolVo){
        List<Map<String,Object>> list = managerService.getSchoolLedgerList(schoolVo);
        return TLResult.okPage(list);
    }

    @RequestMapping(value="getSchoolCapitalAccount",method = RequestMethod.POST)
    @ApiOperation(value="【后台-学校/平台】学校资金往来账/学生钱包管理/平台资金到账",notes = "学校资金往来账/学生钱包管理/平台资金到账")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "sid", value = "学校id", required = true),
            @ApiImplicitParam(paramType="query",name="pageSize",value="pageSize",required = true),
            @ApiImplicitParam(paramType="query",name="pageNum",value="pageNum",required = true),
            @ApiImplicitParam(paramType="query",name="type",value="1平台 2学校 3学生钱包",required = true),
    })
    public TLResult getSchoolCapitalAccount(SchoolVo schoolVo){
        List list = managerService.getSchoolCapitalAccount(schoolVo);
        return TLResult.okPage(list);
    }

    @RequestMapping(value ="editUserBalance",method = RequestMethod.POST)
    @ApiOperation(value = "【后台-平台】修改余额", notes = "修改余额")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "uid", value = "uid", required = true),
            @ApiImplicitParam(paramType = "query",name = "balance", value = "余额", required = false),
    })
    public TLResult editUserBalance(User user) {
        managerService.editUserBalance(user);
        return TLResult.ok();
    }

    @RequestMapping(value = "getSchoolSaleListOnPlat",method = RequestMethod.POST)
    @ApiOperation(value = "【后台-平台】各学校销售列表",notes = "各学校销售列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name="crtime",value="crtime",required = false),
            @ApiImplicitParam(paramType="query",name="pageSize",value="pageSize",required = true),
            @ApiImplicitParam(paramType="query",name="pageNum",value="pageNum",required = true),
    })
    public TLResult getSchoolSaleListOnPlat(SchoolVo schoolVo){
        List<List<Map<String,Object>>> list = managerService.getSchoolSaleListOnPlat(schoolVo);
        return TLResult.okPage(list);
    }

    @RequestMapping(value="getPlatLedger",method = RequestMethod.POST)
    @ApiOperation(value="【后台-平台】平台收入分类账",notes = "平台收入分类账")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="pageSize",value="pageSize",required = true),
            @ApiImplicitParam(paramType="query",name="pageNum",value="pageNum",required = true),
            @ApiImplicitParam(paramType="query",name = "crtime",value = "crtime",required = false)
    })
    public TLResult getPlatLedger(SchoolVo schoolVo){
        List<Map<String,Object>> list = managerService.getPlatLedger(schoolVo);
        return TLResult.okPage(list);
    }

    @RequestMapping(value="upRechargePhoto",method = RequestMethod.POST)
    @ApiOperation(value="【后台-学校】上传收款照片",notes = "上传收款照片")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="price",value="充值金额",required = true),
            @ApiImplicitParam(paramType="query",name="photo",value="收款照片",required = true),
            @ApiImplicitParam(paramType="query",name="sid",value="学校sid",required = true),
            @ApiImplicitParam(paramType="query",name="type",value="2微信 3支付宝 4农行",required = true),
            @ApiImplicitParam(paramType="query",name="id",value="编辑传",required = true),
    })
    public TLResult upRechargePhoto(Recharge recharge){
        managerService.upRechargePhoto(recharge);
        return TLResult.ok();
    }

    @RequestMapping(value="getRecharge",method = RequestMethod.POST)
    @ApiOperation(value="【后台-平台】获取收款照片",notes = "获取收款照片")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query",name="sid",value="学校id",required = true),
    })
    public TLResult getRecharge(Integer sid){
        Recharge recharge = managerService.getRecharge(sid);
        return TLResult.ok(recharge);
    }

    @RequestMapping(value = "checkCodeRecharge", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】收款码充值审核", notes = "收款码充值审核")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "tid", value = "交易id", required = true),
            @ApiImplicitParam(paramType = "query", name = "status", value = "0 待审核 1审核通过", required = true),
    })
    public TLResult checkCodeRecharge(String tid,Integer status){
        orderService.checkCodeRecharge(tid,status);
        return TLResult.ok();
    }
}
