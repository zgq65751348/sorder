package com.tlongx.sorder.user.service;

import com.tlongx.common.TLResult;
import com.tlongx.sorder.dto.MsgDto;
import com.tlongx.sorder.manager.pojo.School;
import com.tlongx.sorder.msg.pojo.MsgLog;
import com.tlongx.sorder.user.pojo.*;
import com.tlongx.sorder.vo.EatingInfoVo;
import com.tlongx.sorder.vo.UserCanteenVo;
import com.tlongx.sorder.vo.UserSupplierVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author xin.rf
 * @date 2019/2/13 17:07
 * @Description TODO
 **/
public interface UserService {
    /**
     * 发送手机验证码
     * @param phoneCode
     * @param type
     * @return
     */
    String sendPhoneCode(PhoneCode phoneCode,String type,Integer userType);

    /**
     * 完成注册
     * @param phone
     * @param password
     * @param type
     * @return
     */
    TLResult register(String phone,String password,String type);

    /**
     * 重置密码
     * @param uid
     * @param oldPassword
     * @param newPassword
     * @param type
     * @return
     */
    void resetPassword(String uid,String oldPassword,String newPassword,String type);

    /**
     * token验证登录
     * @param token
     * @param type
     * @return
     */
    Map<String,Object> tokenValid(String token,String type);

    /**
     * 账号密码登录
     * @param phone
     * @param password
     * @param type
     * @return
     */
    Map<String,Object> getUserInfo(String phone,String password,String type,String openId);

    /**
     * 忘记密码
     * @param phone
     * @param password
     * @param type
     * @return
     */
    TLResult forgetPassword(String phone,String password,String type);

    /**
     * 获取学校列表
     * @param sname
     * @param status
     * @return
     */
    TLResult getSchoolList(String sname,String status);


    /**
     * 完善用户资料
     * @param user
     * @return
     */
    TLResult perfectUserInfo(User user);

    /**
     * 入驻申请
     * @param userSupplierVo
     * @return
     */
    void enterApply(UserSupplierVo userSupplierVo);

    /**
     * 编辑供应商信息
     * @param userSupplier
     * @return
     */
    void editUserSupplierInfo(UserSupplier userSupplier);

    /**
     * 提交意见反馈
     * @param feedBack
     * @return
     */
    void submitFeedback(FeedBack feedBack);

    /**
     * 批量添加食堂员工
     * @param data
     * @param cid
     * @param uid
     * @return
     */
    void addUserCanteenBatch(String data,String cid,String uid);

    /**
     * 获取食堂员工列表
     * @param userCanteenVo
     * @return
     */
    List<UserCanteen> getCanteenEmpList(UserCanteenVo userCanteenVo);

    /**
     * 编辑食堂员工信息
     * @param userCanteen
     * @return
     */
    void editCanteenEmp(UserCanteen userCanteen);

    /**
     * 删除员工信息
     * @param uid
     * @return
     */
    void deleteCanteenEmp(String uid);

    /**
     * 编辑学生/教师信息
     * @param user
     * @return
     */
    void editUserInfo(User user);

    /**
     * 重置密码
     * @param phone
     * @param payPwd
     * @return
     */
    void resetPayPwd(String phone,String payPwd);

    /**
     * 钱包密码验证身份
     * @param uid
     * @param payPwd
     * @return
     */
    void verifyPayPwd(String uid,String payPwd);

    /**
     * 获取餐饮商城列表
     * @param userSupplierVo
     * @return
     */
    List<UserSupplier> getUserSupplierBySidList(UserSupplierVo userSupplierVo);

    /**
     * 教师查看当天学生用餐情况
     * @param eatingInfoVo
     * @return
     */
    List<Map<String,Object>> getStuEatingInfo(EatingInfoVo eatingInfoVo);

    /**
     * 录入人脸
     * @param uid
     * @param multipartFile
     */
    void addFace(String uid, MultipartFile multipartFile);

    /**
     * 获取支付码
     * @param uid
     * @return
     */
    String getPayCode(String uid);


    /**
     * 供应商获取绑定的学校
     * @param uid
     * @return
     */
    List<School> getSchoolSupplierListByUid(String uid);

    /**
     * 获取学生端用户信息
     * @param uid
     * @return
     */
    Map<String,Object> getUserInfo(String uid);

}
