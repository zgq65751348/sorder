package com.tlongx.sorder.manager.service;

import com.tlongx.common.TLResult;
import com.tlongx.sorder.dto.*;
import com.tlongx.sorder.manager.pojo.*;
import com.tlongx.sorder.operation.pojo.OperationLog;
import com.tlongx.sorder.order.pojo.Recharge;
import com.tlongx.sorder.order.pojo.Trade;
import com.tlongx.sorder.user.pojo.User;
import com.tlongx.sorder.user.pojo.UserCanteen;
import com.tlongx.sorder.user.pojo.UserSupplier;
import com.tlongx.sorder.vo.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.nio.file.OpenOption;
import java.util.List;
import java.util.Map;

/**
 * @author xin.rf
 * @date 2019/2/12 16:37
 * @Description
 **/
public interface ManagerService {

    /**
     * 管理员登录
     * @param username
     * @param password
     * @param type
     * @return
     */
    Manager login(String username,String password,String type);

    /**
     * 添加学校信息
     * @param schoolVo
     * @return
     */
    void addSchool(SchoolVo schoolVo);

    /**
     * 获取学校列表
     * @param schoolVo
     * @return
     */
    List getSchoolList(SchoolVo schoolVo);

    /**
     * 获取学校详情信息
     * @param sid
     * @return
     */
    SchoolDto getSchoolInfo(String sid);

    /**
     * 编辑学校信息
     * @param schoolVo
     * @return
     */
    void editSchool(SchoolVo schoolVo);

    /**
     * 修改管理员密码
     * @param mid
     * @param oldPassword
     * @param newPassword
     * @return
     */
    void upManagerPassword(String mid,String oldPassword,String newPassword,String phoneCode);

    /**
     * 添加班级
     * @param schoolClass
     * @return
     */
    void addClass(SchoolClass schoolClass);

    /**
     * 查询指定学校年级信息
     * @param schoolClassVo
     * @return
     */
    List<SchoolClass> getClassList(SchoolClassVo schoolClassVo);

    /**
     * 编辑班级信息
     * @param schoolClass
     * @return
     */
    void editClass(SchoolClass schoolClass);

    /**
     * 删除班级
     * @param cid
     * @return
     */
    void deleteClass(Integer cid);

    /**
     * 添加食堂信息
     * @param userCanteen
     * @return
     */
    void addCanteen(UserCanteen userCanteen);

    /**
     * 获取食堂列表
     * @param sid
     * @return
     */
    List<UserCanteen> getCanteenList(String sid);

    /**
     * 编辑食堂信息
     * @param userCanteen
     * @return
     */
    void editCanteen(UserCanteen userCanteen);

    /**
     * 获取食堂信息
     * @param uid
     * @return
     */
    UserCanteen getCanteenInfo(String uid);

    /**
     * 删除食堂
     * @param cid
     * @return
     */
    void deleteCanteen(String cid);

    /**
     * 添加轮播广告
     * @return
     */
    void addAdv(Advertisement advertisement);

    /**
     * 获取轮播广告列表
     * @param sid
     * @return
     */
    List<Advertisement> getAdvList(String sid);

    /**
     * 编辑轮播广告信息
     * @param id
     * @param photo
     * @return
     */
    void editAdv(String id,String photo);

    /**
     * 删除轮播广告信息
     * @param id
     * @return
     */
    void deleteAdv(String id);

    /**
     * 获取食品时段列表
     * @param sid
     * @return
     */
    List<SupplierTime> getSupplierTimeList(String sid);

    /**
     * 获取食品时段信息
     * @param cid
     * @return
     */
    SupplierTime getSupplierTimeInfo(Integer cid);

    /**
     * 编辑食品时段信息
     * @param supplierTime
     * @return
     */
    void editSupplierTime(SupplierTime supplierTime);

    /**
     * 获取师生列表
     * @param userVo
     * @return
     */
    List getUserList(UserVo userVo);

    /**
     * 获取师生详细信息
     * @param uid
     * @return
     */
    User getUserInfo(String uid);

    /**
     * 编辑师生信息
     * @param user
     * @return
     */
    void editUser(User user);

    /**
     * 删除师生信息
     * @param uid
     * @return
     */
    void deleteUser(String uid);

    /**
     * 获取教师列表（审核查看）
     * @param userVo
     * @return
     */
    List<UserDto> getAuditTeacherList(UserVo userVo);

    /**
     * 获取供应商列表
     * @param userSupplierVo
     * @return
     */
    List<SupplierDto> getUserSupplierList(UserSupplierVo userSupplierVo);

    /**
     * 获取供应商信息
     * @param uid
     * @return
     */
    UserSupplier getUserSupplierInfo(String uid);

    /**
     * 编辑供应商信息
     * @param uid
     * @param status
     * @return
     */
    void editUserSupplierStatus(String uid,Integer status);

    /**
     * 启用/停用供应商
     * @param id
     * @param status
     * @param mid
     * @return
     */
    void editSchoolSupplierStatus(Integer id,Integer status,String mid);

    /**
     * 添加食堂窗口
     * @param window
     * @return
     */
    void addWindow(Window window);

    /**
     * 编辑食堂窗口
     * @param window
     * @return
     */
    void editWindow(Window window);

    /**
     * 获取食堂窗口列表
     * @param windowVo
     * @return
     */
    List<Window> getWindowList(WindowVo windowVo);

    /**
     * 删除食堂窗口
     * @param wid
     * @return
     */
    void deleteWindow(Integer wid);


    /**
     * 编辑优惠制度
     * @param discountPrice
     * @return
     */
    void editDiscountInfo(DiscountPrice discountPrice);

    /**
     * 获取优惠制度列表
     * @param sid
     * @return
     */
    List<DiscountPrice> getDiscountList(Integer sid);

    /**
     * 后台获取食品列表
     * @param foodVo
     * @return
     */
    List<FoodBackDto> getFoodListBack(FoodVo foodVo);

    /**
     * 审核教师
     * @param uid
     * @param status
     * @return
     */
    void auditTeacher(String uid,Integer status);

    /**
     * 获取年级下拉列表
     * @param sid
     * @return
     */
    List<SchoolClass> getSchoolGradeList(Integer sid,Integer type,Integer grade,Integer classNum);

    /**
     * 获取班级下拉列表
     * @param schoolClass
     * @return
     */
    List<SchoolClass> getClassNumList(SchoolClass schoolClass);


    /**
     * 添加人员信息
     * @param user
     */
    void addUserInfo(User user,Integer schoolType,Integer grade,Integer classNum);

    /**
     * 批量添加人员信息
     * @param excel
     * @param sid
     */
    void addUserInfoBatch(MultipartFile excel,Integer sid);

    /**
     * 获取年级和班级下拉列表
     * @param schoolClass
     * @return
     */
    List<SchoolClass> getGradeAndClassList(SchoolClass schoolClass);

    /**
     * 获取平台流水列表
     * @param platTradeVo
     * @return
     */
    List<PlatTradeDto> getPlatTradeList(PlatTradeVo platTradeVo);

    /**
     * 发放教师补贴
     */
    void grantTeacherSubsidy();

    /**
     * 对单个教师进行补贴
     * @param user
     */
    void subsidyForTeacher(User user);

    /**
     * 获取补贴历史列表
     * @param tradeVo
     * @return
     */
    List<SubsidyHistoryDto> getSubsidyHistoryList(TradeVo tradeVo);

    /**
     * 获取意见反馈列表
     * @param feedBackVo
     * @return
     */
    List<FeedBackDto> getFeedBackList(FeedBackVo feedBackVo);

    /**
     * 添加学校子管理员
     * @param manager
     */
    void addSchoolManager(Manager manager);

    /**
     * 获取学校子管理员列表
     * @param manager
     * @return
     */
    List<Manager> getSchoolManagerList(Manager manager);

    /**
     * 获取学校下拉
     * @return
     */
    List<School> getSchoolComboBoxList();

    /**
     * 获取学校详情
     * @param sid
     * @return
     */
    School getSchoolDetail(String sid);

    /**
     * 提现
     * @param sid
     * @param type
     * @param price
     * @param mid
     * @param data
     * @return
     */
    void withDraw(String sid, Integer type, BigDecimal price, String mid,String data);

    /**
     * 获取学校银行账户信息
     * @param sid
     * @return
     */
    School getSchoolBankAccountInfo(Integer sid);

    /**
     * 增加/编辑学校银行账户信息
     * @param school
     * @return
     */
    void addOrEditSchoolBankAccount(School school);

    /**
     * 新增付款记录-平台
     * @param trade
     * @param sid
     */
    void addPayOrder(TradeDto trade,Integer sid);

    /**
     * 获取学校账户信息--平台
     * @param sid
     * @return
     */
    SchoolDto getSchoolAccountInfo(Integer sid);

    /**
     * 查询支付凭证记录
     * @param tradeDto
     * @return
     */
    List<TradeDto> getPayOrderList(TradeDto tradeDto);

    /**
     * 获取支付凭证
     * @param tid
     * @return
     */
    String getPayOrder(String tid);

    /**
     * 获取操作日志列表
     * @param opLogVo
     * @return
     */
    List<OperationLog> getOperationLogList(OpLogVo opLogVo);

    /**
     * 学校当日已提现金额清零
     */
    void cleanSchoolDepositPrice();

    /**
     * 发送短信验证码
     * @param mid
     * @return
     */
    String sendPhoneCode(String mid);

    /**
     * 获取管理员信息
     * @param mid
     * @return
     */
    Manager getManageInfoByMid(String mid);

    /**
     * 学校收入分类账
     * @param schoolVo
     * @return
     */
    List<Map<String,Object>> getSchoolLedgerList(SchoolVo schoolVo);

    /**
     * 学校资金往来账/学生钱包管理/平台资金到账
     * @param schoolVo
     * @return
     */
    List<FundsStatistics> getSchoolCapitalAccount(SchoolVo schoolVo);

    /**
     * 修改余额
     * @param user
     */
    void editUserBalance(User user);

    /**
     * 各学校销售列表
     * @param schoolVo
     * @return
     */
    List<List<Map<String,Object>>> getSchoolSaleListOnPlat(SchoolVo schoolVo);

    /**
     * 平台收入分类账
     * @param schoolVo
     * @returnjie'shi
     */
    List<Map<String,Object>> getPlatLedger(SchoolVo schoolVo);

    /**
     * 上传收款照片
     * @param recharge
     */
    void upRechargePhoto(Recharge recharge);

    /**
     * 获取收款照片
     * @param sid
     * @return
     */
    Recharge getRecharge(Integer sid);
}
