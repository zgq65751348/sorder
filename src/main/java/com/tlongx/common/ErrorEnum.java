package com.tlongx.common;

/**
 * @author xin.rf
 * @date 2018/11/12 15:07
 * @Description 异常错误枚举类
 **/
public enum ErrorEnum {
    PHONE_NOT_REG(1000,"手机号尚未注册"),
    EXCEPTION_REQ(1001,"请求异常"),
    LACK_REQ_PARAM(1002,"缺少必填参数"),
    USERNAME_PASSWORD_ERROR(1003,"手机号或密码错误"),
    PHONE_HAS_REGISTER(1004,"手机号已被注册！"),
    EMAIL_CODE_HAS_OVERDUE(1005,"验证码无效或已过期！"),
    EMAIL_CODE_ERROR(1006,"验证码错误！"),
    UPDATE_ERROR(1007,"修改失败！"),
    EMAIL_CODE_HAS_SEND(1008,"验证码已发送，请勿重新提交！"),
    USER_HAS_NOT_ACTIVE(1009,"用户未激活！"),
    DATA_IS_NULL(1011,"数据为空！"),
    DELETE_FAIL(1012,"删除失败"),
    FILE_IS_NULL(1013,"上传文件不得为空"),
    FILE_FAIL(1014,"上传失败,请重新上传"),
    OLD_PASSWORD_ERROR(1015,"旧密码错误"),
    LOGIN_STATUS_IS_INVALID(1016,"登录状态已失效，请重新登录"),
    FORMAT_CONVERT_FAIL(1017,"格式转换失败"),
    SUPPLIER_SCHOOL_NOT_BING(1018,"当前供应商未绑定任何学校"),
    SCHOOL_NOT_EXIST(1019,"不存在该学校"),
    FILE_IS_TOO_LARGE(1020,"上传文件过大，请压缩后上传"),
    STU_NUM_ERROR(1021,"学号格式错误"),
    STU_NUM_EXIST_ALREADY(1022,"学号已存在，请重新填写"),
    NETWORK_ERROR(1023,"网络错误，请检查网络"),
    CANTEEN_FOOD_NOT_SELECT(1024,"请至少选择一件餐品"),
    SUPPLIER_TIME_OVERDUE(1025,"部分商品当前时间超过供应时间，请重新编辑上架"),
    PAY_PASSWORD_ERROR(1026,"支付密码错误"),
    ADMIN_LOGIN_ERROR(1027,"用户名或密码错误"),
    ILLEGAL_REQUEST(1028,"非法请求"),
    PAY_PRICE_ERROR(1029,"支付金额不符"),
    ENCRYPT_ERROR(1030,"加密异常"),
    NOT_IN_SUPPLY_TIME(1031,"当前餐品不在供应时段内"),
    CANCEL_ORDER_FAIL(1032,"取消订单失败，请检查网络"),
    ORDER_HAS_CANCEL(1033,"订单已取消"),
    ORDER_HAS_FINISH(1034,"订单已完成"),
    ACCOUNT_NO_PASS(1035,"当前账号审核失败，请重新提交审核"),
    ACCOUNT_AUDITING(1036,"当前账号正在审核中"),
    PLEASE_PERFECTINFO(1037,"请完善个人资料"),
    PHONE_EXSIT(1038,"手机号已存在，请重新填写"),
    EXCEL_FORMAT_ERROR(1040,"excel表格式错误"),
    NOT_A_TEACHER(1041,"您不是教师！"),
    Not_Sufficient_Funds(1042,"余额不足"),
    Not_Found_Face(1043,"人脸库检测不到您的信息，请确保有上传人脸照片及质量"),
    Phone_Not_Register(1044,"该手机号尚未注册APP，不能点餐"),
    Not_Dredge_Face(1045,"该账户尚未录入人脸信息，不能点餐"),
    Face_Mapping_Fail(1046,"人脸匹配失败，请凝视镜头确保照片质量"),
    Pay_Out_Time(1047,"支付超时，请刷新支付码"),
    Product_Has_Not_Input(1050,"该商品暂未入库"),
    SchoolName_Has_Exist(1051,"学校名已存在，请重新填写"),
    UserName_Has_Exist(1052,"登录名已被注册，请重新填写"),
    Class_Has_Exsit(1053,"班级已存在，请检查"),
    SellPrice_Less_Than_SupPrice(1054,"售价必须大于供应商提供价格"),
    WithDraw_Fail(1055,"提现失败"),
    StuNum_Dif_SchNum(1056,"选择的学校编号和学号不符"),
    Take_Food_Finish(1057,"该订单已经取餐，请勿重复取餐"),
    No_Cancel(1058,"订单状态改变，无法取消订单"),
    No_Manager(1059,"该学校没有有效的管理员"),
    School_Info_Not_True(1060,"添加人员的学校相关信息不正确"),
    Subsidy_Price_Not_Enough(1061,"补贴余额不足"),
    Goods_Is_Exit(1062,"该商品已存在，请勿重复录入"),
    School_No_Dredge_Alipay(1063,"该学校未开通支付宝支付，请选择其他支付方式"),
    School_No_Dredge_Wxpay(1064,"该学校未开通微信支付，请选择其他支付方式"),
    School_No_Dredge_Face(1065,"该学校未开通人脸支付，请选择其他支付方式"),
    Choose_Correct_Windows(1066,"请选择相对应的窗口进行取餐"),
    Manager_No_Permission(1067,"该账号尚不能登录后台管理，请跟管理员确认"),
    ;

    private Integer key;

    private String msg;

    ErrorEnum(Integer key, String msg) {
        this.key = key;
        this.msg = msg;
    }

    public Integer getKey() {
        return key;
    }

    public String getMsg() {
        return msg;
    }
}
