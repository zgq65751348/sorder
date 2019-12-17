package com.tlongx.sorder.controller;

import com.tlongx.common.ErrorEnum;
import com.tlongx.common.TLResult;
import com.tlongx.common.exception.CodeException;
import com.tlongx.sorder.dto.MsgDto;
import com.tlongx.sorder.dto.SchoolDto;
import com.tlongx.sorder.manager.pojo.School;
import com.tlongx.sorder.msg.pojo.MsgLog;
import com.tlongx.sorder.manager.pojo.Window;
import com.tlongx.sorder.manager.service.ManagerService;
import com.tlongx.sorder.user.pojo.*;
import com.tlongx.sorder.user.service.UserService;
import com.tlongx.sorder.utils.MyUtil;
import com.tlongx.sorder.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author xin.rf
 * @date 2019/2/13 17:52
 * @Description TODO
 **/
@RestController
@RequestMapping("user")
@Api("用户相关API")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ManagerService managerService;

    @RequestMapping(value = "sendPhoneCode",method = RequestMethod.POST)
    @ApiOperation(value = "【APP端】发送手机验证码", notes = "发送手机验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "phone", value = "手机号", required = true),
            @ApiImplicitParam(paramType = "query", name = "type", value = "发送类型 1：注册时, 2：找回密码时/更换手机号时/重置钱包密码 3: ", required = true),
            @ApiImplicitParam(paramType = "query", name = "userType", value = "用户类型 1学生端 2食堂 3供应商", required = true),
    })
    public TLResult sendPhoneCode(String phone,String type,Integer userType) {
        PhoneCode phoneCode=new PhoneCode();
        phoneCode.setPhone(phone);
        String code= userService.sendPhoneCode(phoneCode,type,userType);
        return TLResult.ok(code);
    }

    @RequestMapping(value = "register",method = RequestMethod.POST)
    @ApiOperation(value = "【APP端】完成注册", notes = "完成注册")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "phone", value = "手机号", required = true),
            @ApiImplicitParam(paramType = "query", name = "password", value = "密码 ", required = true),
            @ApiImplicitParam(paramType = "query", name = "type", value = "注册类型 3供应商端 ", required = true),
    })
    public TLResult register(String phone,String password,String type) {
        TLResult result=userService.register(phone,password,type);
        return result;
    }

    @RequestMapping(value = "resetPassword",method = RequestMethod.POST)
    @ApiOperation(value = "【APP端】重置密码", notes = "重置密码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "uid", value = "用户id", required = true),
            @ApiImplicitParam(paramType = "query", name = "oldPassword", value = "旧密码 ", required = true),
            @ApiImplicitParam(paramType = "query", name = "newPassword", value = "新密码 ", required = true),
            @ApiImplicitParam(paramType = "query", name = "type", value = "用户类型 1学生端 2食堂端 3供应商端 ", required = true),
    })
    public TLResult resetPassword(String uid,String oldPassword,String newPassword,String type) {
        userService.resetPassword(uid,oldPassword,newPassword,type);
        return TLResult.ok();
    }

    @RequestMapping(value ="login",method = RequestMethod.POST)
    @ApiOperation(value = "【APP端】登录", notes = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "token", value = "token令牌", required = false),
            @ApiImplicitParam(paramType = "query", name = "phone", value = "手机号 ", required = false),
            @ApiImplicitParam(paramType = "query", name = "openId", value = "微信openid", required = false),
            @ApiImplicitParam(paramType = "query", name = "password", value = "密码 ", required = false),
            @ApiImplicitParam(paramType = "query", name = "type", value = "用户类型 1学生端 2食堂端 3供应商端 ", required = true),
    })
    public TLResult<User> login(String token, String password, String phone, String type,String openId) {
        Map<String,Object> result=null;

        if (token!=null){
            result=userService.tokenValid(token,type);
        }else {
            result=userService.getUserInfo(phone,password,type,openId);
        }
        return TLResult.ok(result);
    }

    @RequestMapping(value ="forgetPassword",method = RequestMethod.POST)
    @ApiOperation(value = "【APP端】忘记密码", notes = "忘记密码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "phone", value = "手机号", required = true),
            @ApiImplicitParam(paramType = "query", name = "password", value = "密码 ", required = true),
            @ApiImplicitParam(paramType = "query", name = "type", value = "用户类型 1学生端 2食堂端 3供应商端 ", required = true),
    })
    public TLResult forgetPassword(String phone,String password,String type) {
        TLResult result=userService.forgetPassword(phone,password,type);
        return result;
    }



    @RequestMapping(value ="getSchoolComboBoxList",method = RequestMethod.POST)
    @ApiOperation(value = "【APP端】获取所有学校下拉列表", notes = "获取学校下拉列表")
    public TLResult getSchoolComboBoxList() {
        List<School> schoolComboBoxList = managerService.getSchoolComboBoxList();
        return TLResult.ok(schoolComboBoxList);
    }

    @RequestMapping(value ="getSchoolSupplierListByUid",method = RequestMethod.POST)
    @ApiOperation(value = "【供应商端】获取绑定学校下拉列表", notes = "获取绑定学校下拉列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "uid", value = "uid", required = true),
    })
    public TLResult getSchoolSupplierListByUid(String uid) {
        List<School> schoolList = userService.getSchoolSupplierListByUid(uid);
        return TLResult.ok(schoolList);
    }

//    @RequestMapping(value ="getSchoolList",method = RequestMethod.POST)
//    @ApiOperation(value = "【APP端】获取学校下拉列表", notes = "获取学校下拉列表")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query",name = "sname", value = "学校名称", required = false),
//            @ApiImplicitParam(paramType = "query",name = "status", value = "状态（传1）0停用 1启用", required = true),
//    })
//    public TLResult<SchoolDto> getSchoolList(String sname, String status) {
//        TLResult result=userService.getSchoolList(sname,status);
//        return result;
//    }

    @RequestMapping(value ="perfectUserInfo",method = RequestMethod.POST)
    @ApiOperation(value = "【APP学生端】完善资料", notes = "完善资料")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "uid", value = "uid", required = true),
            @ApiImplicitParam(paramType = "query",name = "username", value = "姓名", required = true),
            @ApiImplicitParam(paramType = "query",name = "stuNum", value = "学号", required = false),
            @ApiImplicitParam(paramType = "query",name = "cid", value = "年级id", required = true),
            @ApiImplicitParam(paramType = "query",name = "sex", value = "性别", required = true),
            @ApiImplicitParam(paramType = "query",name = "sid", value = "学校id", required = true),
            @ApiImplicitParam(paramType = "query",name = "type", value = "用户类型（1学生 2教师 3班主任）", required = true),
    })
    public TLResult perfectUserInfo(User user) {
        TLResult result=userService.perfectUserInfo(user);
        return result;
    }

    @RequestMapping(value ="enterApply",method = RequestMethod.POST)
    @ApiOperation(value = "【APP食堂/供应商端】入驻申请", notes = "入驻申请")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "uid", value = "uid", required = true),
            @ApiImplicitParam(paramType = "query",name = "sids", value = "学校id，以逗号分隔", required = true),
            @ApiImplicitParam(paramType = "query",name = "shopName", value = "店铺名称", required = true),
            @ApiImplicitParam(paramType = "query",name = "leaderName", value = "负责人姓名", required = true),
            @ApiImplicitParam(paramType = "query",name = "leaderPhone", value = "负责人电话", required = true),
            @ApiImplicitParam(paramType = "query",name = "mainProduct", value = "主营餐品", required = true),
            @ApiImplicitParam(paramType = "query",name = "idCardFront", value = "身份证正面照", required = true),
            @ApiImplicitParam(paramType = "query",name = "idCardContrary", value = "身份证反面照", required = true),
            @ApiImplicitParam(paramType = "query",name = "businessLicense", value = "营业执照", required = true),
            @ApiImplicitParam(paramType = "query",name = "relevantLicense", value = "相关许可证", required = true),
    })
    public TLResult enterApply(UserSupplierVo userSupplierVo) {
        userService.enterApply(userSupplierVo);
        return TLResult.ok();
    }

    @RequestMapping(value ="editUserSupplierInfo",method = RequestMethod.POST)
    @ApiOperation(value = "【供应商端】修改资料", notes = "修改资料")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "uid", value = "uid", required = true),
            @ApiImplicitParam(paramType = "query",name = "shopName", value = "店铺名称", required = false),
            @ApiImplicitParam(paramType = "query",name = "mainProduct", value = "主营餐品", required = false),
            @ApiImplicitParam(paramType = "query",name = "portrait", value = "头像", required = false),
    })
    public TLResult editUserSupplierInfo(UserSupplier userSupplier) {
        userService.editUserSupplierInfo(userSupplier);
        return TLResult.ok();
    }

    @RequestMapping(value ="submitFeedback",method = RequestMethod.POST)
    @ApiOperation(value = "【APP端】提交意见反馈", notes = "提交意见反馈")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "uid", value = "uid", required = true),
            @ApiImplicitParam(paramType = "query",name = "content", value = "内容", required = true),
    })
    public TLResult submitFeedback(FeedBack feedBack) {
        userService.submitFeedback(feedBack);
        return TLResult.ok();
    }

    @RequestMapping(value ="addUserCanteenBatch",method = RequestMethod.POST)
    @ApiOperation(value = "【食堂端】添加食堂员工", notes = "添加食堂员工")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "uid", value = "食堂管理员uid", required = true),
            @ApiImplicitParam(paramType = "query",name = "cid", value = "食堂id", required = true),
            @ApiImplicitParam(paramType = "query", name = "data",
                    value = "员工信息[{\"uname\":\"员工姓名\",\"phone\":\"手机号\",\"photo\":\"真人图片\"," +
                            "\"sex\":\"性别 1男2女\",\"role\":\"职务\",\"wid\":\"窗口id\"," +
                            "\"wname\":\"窗口名称\"}]", required = true),
    })
    public TLResult addUserCanteenBatch(String uid,String cid,String data) {
       userService.addUserCanteenBatch(data,cid,uid);
        return TLResult.ok();
    }

    @RequestMapping(value ="getWindowList",method = RequestMethod.POST)
    @ApiOperation(value = "【APP端】获取食堂窗口列表", notes = "获取食堂窗口列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "cid", value = "食堂id", required = true),
    })
    public TLResult<Window> getWindowList(WindowVo windowVo) {
        List<Window> list=managerService.getWindowList(windowVo);
        return TLResult.okPage(list);
    }

    @RequestMapping(value ="getCanteenEmpList",method = RequestMethod.POST)
    @ApiOperation(value = "【食堂端】获取食堂员工列表", notes = "获取食堂员工列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "cid", value = "食堂id", required = true),
            @ApiImplicitParam(paramType = "query",name = "keyword", value = "关键字（姓名，手机号模糊匹配）", required = false),
            @ApiImplicitParam(paramType = "query",name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(paramType = "query",name = "pageSize", value = "每页行数", required = true),
    })
    public TLResult<UserCanteen> getCanteenEmpList(UserCanteenVo userCanteenVo) {
        List<UserCanteen> list=userService.getCanteenEmpList(userCanteenVo);
        return TLResult.okPage(list);
    }

    @RequestMapping(value ="editCanteenEmp",method = RequestMethod.POST)
    @ApiOperation(value = "【食堂端】编辑食堂员工信息", notes = "编辑食堂员工信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "uid", value = "员工uid", required = true),
            @ApiImplicitParam(paramType = "query",name = "uname", value = "姓名", required = false),
            @ApiImplicitParam(paramType = "query",name = "phone", value = "手机号", required = false),
            @ApiImplicitParam(paramType = "query",name = "photo", value = "真人图片", required = false),
            @ApiImplicitParam(paramType = "query",name = "portrait", value = "头像", required = false),
            @ApiImplicitParam(paramType = "query",name = "sex", value = "性别", required = false),
            @ApiImplicitParam(paramType = "query",name = "role", value = "职务", required = false),
            @ApiImplicitParam(paramType = "query",name = "wid", value = "窗口id", required = false),
            @ApiImplicitParam(paramType = "query",name = "wname", value = "窗口名称", required = false),
    })
    public TLResult editCanteenEmp(UserCanteen userCanteen) {
        userService.editCanteenEmp(userCanteen);
        return TLResult.ok();
    }

    @RequestMapping(value ="deleteCanteenEmp",method = RequestMethod.POST)
    @ApiOperation(value = "【食堂端】删除食堂员工信息", notes = "删除食堂员工信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "uid", value = "员工uid", required = true),
    })
    public TLResult deleteCanteenEmp(String uid) {
        userService.deleteCanteenEmp(uid);
        return TLResult.ok();
    }

    @RequestMapping(value ="editUserInfo",method = RequestMethod.POST)
    @ApiOperation(value = "【学生端】编辑学生/教师信息", notes = "编辑学生/教师信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "uid", value = "uid", required = true),
            @ApiImplicitParam(paramType = "query",name = "username", value = "姓名", required = false),
            @ApiImplicitParam(paramType = "query",name = "sex", value = "性别 1男 2女", required = false),
            @ApiImplicitParam(paramType = "query",name = "icon", value = "头像", required = false),
            @ApiImplicitParam(paramType = "query",name = "cid", value = "班级id", required = false),
            @ApiImplicitParam(paramType = "query",name = "taste", value = "口味偏好", required = false),
    })
    public TLResult editUserInfo(User user) {
        userService.editUserInfo(user);
        return TLResult.ok();
    }

    @RequestMapping(value ="resetPayPwd",method = RequestMethod.POST)
    @ApiOperation(value = "【学生端】重置钱包密码", notes = "重置钱包密码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "phone", value = "手机号", required = true),
            @ApiImplicitParam(paramType = "query",name = "payPwd", value = "钱包密码", required = true),
    })
    public TLResult resetPayPwd(String phone,String payPwd) {
        userService.resetPayPwd(phone,payPwd);
        return TLResult.ok();
    }

    @RequestMapping(value ="verifyPayPwd",method = RequestMethod.POST)
    @ApiOperation(value = "【学生端】钱包密码验证身份", notes = "钱包密码验证身份")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "uid", value = "uid", required = true),
            @ApiImplicitParam(paramType = "query",name = "payPwd", value = "钱包密码", required = true),
    })
    public TLResult verifyPayPwd(String uid,String payPwd) {
        userService.verifyPayPwd(uid,payPwd);
        return TLResult.ok();
    }

    @RequestMapping(value ="getUserSupplierByUidList",method = RequestMethod.POST)
    @ApiOperation(value = "【食堂端】获取餐饮商城列表", notes = "获取餐饮商城列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "sid", value = "学校id", required = true),
            @ApiImplicitParam(paramType = "query",name = "foodName", value = "食品名称（搜索时传）", required = false),
            @ApiImplicitParam(paramType = "query",name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(paramType = "query",name = "pageSize", value = "每页行数", required = true),
    })
    public TLResult<UserSupplier> getUserSupplierByUidList(UserSupplierVo userSupplierVo) {
        List<UserSupplier> list=userService.getUserSupplierBySidList(userSupplierVo);
        return TLResult.okPage(list);
    }



    @RequestMapping(value ="getUserInfo",method = RequestMethod.POST)
    @ApiOperation(value = "【学生端】获取师生详细信息", notes = "获取师生详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "uid", value = "uid", required = true),
    })
    public TLResult getUserInfo(String uid) {
        Map<String, Object> userInfo = userService.getUserInfo(uid);
        return TLResult.ok(userInfo);
    }

    @RequestMapping(value ="getStuEatingInfo",method = RequestMethod.POST)
    @ApiOperation(value = "【学生端】获取学生用餐情况", notes = "获取学生用餐情况")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "cid", value = "cid", required = true),
    })
    public TLResult getStuEatingInfo(EatingInfoVo eatingInfoVo) {
        List<Map<String,Object>> list=userService.getStuEatingInfo(eatingInfoVo);
        return TLResult.ok(MyUtil.returnPageResultMap(list));
    }

    @RequestMapping(value ="addFace",method = RequestMethod.POST)
    @ApiOperation(value = "【学生端】录入人脸", notes = "录入人脸")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "uid", value = "uid", required = true),
    })
    public TLResult addFace(String uid, MultipartFile file) {
        userService.addFace(uid,file);
        return TLResult.ok();
    }


    @RequestMapping(value ="getPayCode",method = RequestMethod.POST)
    @ApiOperation(value = "【学生端】获取支付码", notes = "获取支付码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "uid", value = "uid", required = true),
    })
    public TLResult getPayCode(String uid) {
        String payCode=userService.getPayCode(uid);
        return TLResult.ok(payCode);
    }

    @RequestMapping(value ="getUserSupplierInfo",method = RequestMethod.POST)
    @ApiOperation(value = "【供应商】获取供应商信息", notes = "获取供应商信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "uid", value = "uid", required = true),
    })
    public TLResult<UserSupplier> getUserSupplierInfo(String uid) {
        UserSupplier userSupplier=managerService.getUserSupplierInfo(uid);
        return TLResult.ok(userSupplier);
    }

}
