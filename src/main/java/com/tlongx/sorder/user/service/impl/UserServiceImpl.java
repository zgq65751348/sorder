package com.tlongx.sorder.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tlongx.common.ErrorEnum;
import com.tlongx.common.TLResult;
import com.tlongx.common.enums.ResourceBusiness;
import com.tlongx.common.enums.ResourceType;
import com.tlongx.common.exception.CodeException;
import com.tlongx.sorder.dto.MsgDto;
import com.tlongx.sorder.dto.SchoolDto;
import com.tlongx.sorder.manager.dao.CanteenMapper;
import com.tlongx.sorder.manager.dao.SchoolMapper;
import com.tlongx.sorder.manager.dao.WindowMapper;
import com.tlongx.sorder.manager.pojo.*;
import com.tlongx.sorder.msg.pojo.MsgLog;
import com.tlongx.sorder.order.dao.OrderMapper;
import com.tlongx.sorder.user.dao.*;
import com.tlongx.sorder.user.pojo.*;
import com.tlongx.sorder.user.service.UserService;
import com.tlongx.sorder.utils.*;
import com.tlongx.sorder.vo.EatingInfoVo;
import com.tlongx.sorder.vo.SchoolVo;
import com.tlongx.sorder.vo.UserCanteenVo;
import com.tlongx.sorder.vo.UserSupplierVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

/**
 * @author xin.rf
 * @date 2019/2/13 17:07
 * @Description TODO
 **/
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserCanteenMapper userCanteenMapper;
    @Autowired
    private UserSupplierMapper userSupplierMapper;
    @Autowired
    private PhoneCodeMapper phoneCodeMapper;
    @Autowired
    private SchoolSupplierMapper schoolSupplierMapper;
    @Autowired
    private SchoolMapper schoolMapper;
    @Autowired
    private FeedBackMapper feedBackMapper;
    @Autowired
    private CanteenMapper canteenMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private FileStorageService fileStorageService;



    @Override
    @Transactional
    public String sendPhoneCode(PhoneCode phoneCode, String type,Integer userType) {
        int userCount=0;
        if (userType.equals(1)){
            User user=new User();
            user.setPhone(phoneCode.getPhone());
            userCount=userMapper.selectCount(user);
        }else if (userType.equals(2)){
            UserCanteen userCanteen=new UserCanteen();
            userCanteen.setPhone(phoneCode.getPhone());
            userCount=userCanteenMapper.selectCount(userCanteen);
        }else if (userType.equals(3)){
            UserSupplier userSupplier=new UserSupplier();
            userSupplier.setPhone(phoneCode.getPhone());
            userCount=userSupplierMapper.selectCount(userSupplier);
        }else{
            throw new CodeException(ErrorEnum.LACK_REQ_PARAM);
        }

        if (type.equals("1")){
            if (userCount>0){
                throw new CodeException(ErrorEnum.PHONE_HAS_REGISTER);
            }
        }else if(type.equals("2")){
            if (userCount==0){
                throw new CodeException(ErrorEnum.PHONE_NOT_REG);
            }
        }
        phoneCodeMapper.delete(phoneCode);
        String code = MyUtil.getcode();
        phoneCode.setCode(code);
        phoneCode.setCrtime(new Date());
        phoneCodeMapper.insertSelective(phoneCode);
        String content = "您好，您的动态验证码为：" + code + "，若非本人操作,请忽略！";
        MyUtil.sendPhoneCode(phoneCode.getPhone(),content);

        return code;
    }

    @Override
    public TLResult register(String phone,String password,String type) {
        String uid=MyUtil.createUUID();
        Date now=new Date();
        if (type.equals("1")){
            User user=new User();
            user.setUid(uid);
            user.setPhone(phone);
            user.setPassword(MD5Util.MD5EncodeUtf8(password));
            user.setCrtime(now);
            user.setUptime(now);
            user.setBalance(new BigDecimal(0));
            userMapper.insertSelective(user);
        }else if (type.equals("2")){
            UserCanteen userCanteen=new UserCanteen();
            userCanteen.setUid(uid);
            userCanteen.setPhone(phone);
            userCanteen.setPassword(MD5Util.MD5EncodeUtf8(password));
            userCanteen.setCrtime(now);
            userCanteen.setUptime(now);
            userCanteenMapper.insertSelective(userCanteen);
        }else if (type.equals("3")){
            UserSupplier userSupplier=new UserSupplier();
            userSupplier.setPhone(phone);
            int count=userSupplierMapper.selectCount(userSupplier);
            if (count>0){
                throw new CodeException(ErrorEnum.PHONE_HAS_REGISTER);
            }
            userSupplier.setUid(uid);

            userSupplier.setPassword(MD5Util.MD5EncodeUtf8(password));
            userSupplier.setStatus(3);
            userSupplier.setCrtime(now);
            userSupplier.setUptime(now);
            userSupplierMapper.insertSelective(userSupplier);
        }
        return new TLResult(uid);
    }

    @Override
    public void resetPassword(String uid, String oldPassword, String newPassword, String type) {
        if (type.equals("1")){
            User user=new User();
            user.setUid(uid);
            user.setPassword(MD5Util.MD5EncodeUtf8(oldPassword));
            int userCount=userMapper.selectCount(user);
            if (userCount==0){
                throw new CodeException(ErrorEnum.OLD_PASSWORD_ERROR);
            }
            user.setPassword(MD5Util.MD5EncodeUtf8(newPassword));
            userMapper.updateByPrimaryKeySelective(user);
        }else if (type.equals("2")){
            UserCanteen userCanteen=new UserCanteen();
            userCanteen.setUid(uid);
            userCanteen.setPassword(MD5Util.MD5EncodeUtf8(oldPassword));
            int userCanteenCount=userCanteenMapper.selectCount(userCanteen);
            if (userCanteenCount==0){
                throw new CodeException(ErrorEnum.OLD_PASSWORD_ERROR);
            }
            userCanteen.setPassword(MD5Util.MD5EncodeUtf8(newPassword));
            userCanteenMapper.updateByPrimaryKeySelective(userCanteen);
        }else if (type.equals("3")){
            UserSupplier userSupplier=new UserSupplier();
            userSupplier.setUid(uid);
            userSupplier.setPassword(MD5Util.MD5EncodeUtf8(oldPassword));
            int userSupplierCount=userSupplierMapper.selectCount(userSupplier);
            if (userSupplierCount==0){
                throw new CodeException(ErrorEnum.OLD_PASSWORD_ERROR);
            }
            userSupplier.setPassword(MD5Util.MD5EncodeUtf8(newPassword));
            userSupplierMapper.updateByPrimaryKeySelective(userSupplier);
        }
    }

    @Override
    public Map<String,Object> tokenValid(String token, String type) {
        String[] arr = EncryptUtil.AESDncode(token).split(",");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = sdf.parse(MyUtil.longTimeToDate(arr[1]));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, 7);
        if (calendar.after(date)) {
            throw new CodeException(ErrorEnum.LOGIN_STATUS_IS_INVALID);
        }
        Map<String,Object> resultMap=null;
        if (StringUtils.equals("1", type)) {
            User user=new User();
            user.setToken(token);
            resultMap = userMapper.selectUserInfo(user);
        }

        if (StringUtils.equals("2", type)) {
            UserCanteen userCanteen=new UserCanteen();
            userCanteen.setToken(token);
            resultMap = userCanteenMapper.selectUserCanteenInfo(userCanteen);
        }

        if (StringUtils.equals("3", type)) {
            UserSupplier userSupplier=new UserSupplier();
            userSupplier.setToken(token);
            resultMap = userSupplierMapper.selectUserSupplierInfo(userSupplier);
            resultMap.put("type",3);
        }
        if (resultMap == null) {
            throw new CodeException(ErrorEnum.LOGIN_STATUS_IS_INVALID);
        }
        return resultMap;
    }

    @Override
    public Map<String,Object> getUserInfo(String phone,String password,String type,String openId) {
        Map<String,Object> userInfo = null;
        if (StringUtils.equals("1", type)) {
            User user=new User();
            user.setPhone(phone);
            user.setPassword(MD5Util.MD5EncodeUtf8(password));
            userInfo = userMapper.selectUserInfo(user);
            if(Objects.isNull(userInfo)&&StringUtils.isEmpty(openId)){
                user.setOpenId(openId);
                Example example = new Example(User.class,false);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("phone",phone);
                userMapper.updateByExampleSelective(user,example);
            }
        }
        if (StringUtils.equals("2", type)) {
            UserCanteen userCanteen=new UserCanteen();
            userCanteen.setPhone(phone);
            userCanteen.setPassword(MD5Util.MD5EncodeUtf8(password));
            userInfo = userCanteenMapper.selectUserCanteenInfo(userCanteen);
        }
        if (StringUtils.equals("3", type)) {
            UserSupplier userSupplier=new UserSupplier();
            userSupplier.setPhone(phone);
            userSupplier.setPassword(MD5Util.MD5EncodeUtf8(password));
            userInfo = userSupplierMapper.selectUserSupplierInfo(userSupplier);
//            if (userInfo != null) {
//                String uid = userInfo.get("uid").toString();
//                SchoolSupplier schoolSupplier=new SchoolSupplier();
//                schoolSupplier.setUid(uid);
//                int count=schoolSupplierMapper.selectCount(schoolSupplier);
//                if (count==0) {
//                    throw new CodeException(ErrorEnum.SUPPLIER_SCHOOL_NOT_BING);
//                }
//            }
//            userInfo.put("type",3);
        }
        if (userInfo == null) {
            throw new CodeException(ErrorEnum.USERNAME_PASSWORD_ERROR);
        }
        long time = System.currentTimeMillis();
        String token = "";
        try {
                token=EncryptUtil.AESEncode(phone+","+time);
        } catch (Exception e) {
            throw new CodeException(ErrorEnum.USERNAME_PASSWORD_ERROR);
        }
        if (StringUtils.equals("1", type)) {
            if (userInfo.get("status").toString().equals("0")){//当前账号审核失败，请重新提交审核 0
                userInfo.put("status",0);
                userInfo.put("statusMsg",ErrorEnum.ACCOUNT_NO_PASS.getMsg());
                //throw new CodeException(ErrorEnum.ACCOUNT_NO_PASS);
            }
            if (userInfo.get("status").toString().equals("2")){//请完善个人资料 3
                userInfo.put("status",3);
                userInfo.put("statusMsg",ErrorEnum.PLEASE_PERFECTINFO.getMsg());
                //throw new CodeException(ErrorEnum.PLEASE_PERFECTINFO);
            }
            if (userInfo.get("status").toString().equals("3")){//当前账号正在审核中 2
                userInfo.put("status",2);
                userInfo.put("statusMsg",ErrorEnum.ACCOUNT_AUDITING.getMsg());
                //throw new CodeException(ErrorEnum.ACCOUNT_AUDITING);
            }
            User user=new User();
            user.setUid(userInfo.get("uid").toString());
            user.setToken(token);
            userMapper.updateByPrimaryKeySelective(user);
        }
        if (StringUtils.equals("2", type)) {
            UserCanteen userCanteen = new UserCanteen();
            userCanteen.setUid(userInfo.get("uid").toString());
            userCanteen.setToken(token);
            userCanteenMapper.updateByPrimaryKeySelective(userCanteen);
        }
        if (StringUtils.equals("3", type)) {
            UserSupplier u = new UserSupplier();
            u.setUid(userInfo.get("uid").toString());
            u.setToken(token);
            this.userSupplierMapper.updateByPrimaryKeySelective(u);
        }


        userInfo.put("token",token);
        userInfo.remove("payPassword");
        return userInfo;
    }

    @Override
    public TLResult forgetPassword(String phone, String password, String type) {
        if (type.equals("1")){
            User user=new User();
            user.setPhone(phone);
            User userInfo=userMapper.selectOne(user);
            user.setUid(userInfo.getUid());
            user.setPassword(MD5Util.MD5EncodeUtf8(password));
            userMapper.updateByPrimaryKeySelective(user);
        }else if (type.equals("2")){
            UserCanteen userCanteen=new UserCanteen();
            userCanteen.setPhone(phone);
            UserCanteen userCanteenInfo=userCanteenMapper.selectOne(userCanteen);
            userCanteen.setUid(userCanteenInfo.getUid());
            userCanteen.setPassword(MD5Util.MD5EncodeUtf8(password));
            userCanteenMapper.updateByPrimaryKeySelective(userCanteen);
        }else if (type.equals("3")){
            UserSupplier userSupplier=new UserSupplier();
            userSupplier.setPhone(phone);
            UserSupplier userSupplierInfo=userSupplierMapper.selectOne(userSupplier);
            userSupplier.setUid(userSupplierInfo.getUid());
            userSupplier.setPassword(MD5Util.MD5EncodeUtf8(password));
            userSupplierMapper.updateByPrimaryKeySelective(userSupplier);
        }
        return new TLResult();
    }

    @Override
    public TLResult getSchoolList(String sname,String status) {
        SchoolVo schoolVo=new SchoolVo();
        schoolVo.setSname(sname);
        schoolVo.setStatus(status);
        List<SchoolDto> schoolList=schoolMapper.selectSchoolList(schoolVo);
        return new TLResult(schoolList);
    }

    @Override
    public TLResult perfectUserInfo(User user) {
        if (user.getType().equals(1)){
            //验证学号格式
            School school=schoolMapper.selectByPrimaryKey(user.getSid());
            String stuNum=school.getSnum().concat(user.getStuNum());

            if(!school.getSnum().equals(user.getStuNum().substring(0,4))){
                throw new CodeException(ErrorEnum.StuNum_Dif_SchNum);
            }

            //验证学号是否存在
            User user1=new User();
            user1.setStuNum(user.getStuNum());
            int userCount=userMapper.selectCount(user1);
            if (userCount>0){
                throw new CodeException(ErrorEnum.STU_NUM_EXIST_ALREADY);
            }
            user.setStatus(1);
        } else{
            user.setStatus(3);
        }
        user.setUptime(new Date());
        int result=userMapper.updateByPrimaryKeySelective(user);
        if (result==0){
            throw new CodeException(ErrorEnum.NETWORK_ERROR);
        }
        return new TLResult();
    }

    @Override
    @Transactional
    public void enterApply(UserSupplierVo userSupplierVo) {
//        UserSupplier userSupplierTmp=new UserSupplier();
//        userSupplierTmp.setPhone(userSupplierVo.getPhone());
//        List<UserSupplier> userSuppliers=userSupplierMapper.select(userSupplierTmp);
//        if (userSuppliers.size()==0){
//            throw new CodeException(ErrorEnum.PHONE_NOT_REG);
//        }
//        String uid=userSuppliers.get(0).getUid();
        Date now=new Date();
        //供应商信息
        UserSupplier userSupplier=new UserSupplier();
        userSupplier.setUid(userSupplierVo.getUid());
        userSupplier.setMainProduct(userSupplierVo.getMainProduct());
        userSupplier.setShopName(userSupplierVo.getShopName());
        userSupplier.setLeaderName(userSupplierVo.getLeaderName());
        userSupplier.setLeaderPhone(userSupplierVo.getLeaderPhone());
        userSupplier.setIdCardFront(userSupplierVo.getIdCardFront());
        userSupplier.setIdCardContrary(userSupplierVo.getIdCardContrary());
        userSupplier.setBusinessLicense(userSupplierVo.getBusinessLicense());
        userSupplier.setRelevantLicense(userSupplierVo.getRelevantLicense());
        userSupplier.setStatus(2);
        userSupplier.setUptime(now);
        userSupplierMapper.updateByPrimaryKeySelective(userSupplier);
        //学校与供应商管理信息
        SchoolSupplier schoolSupplier=new SchoolSupplier();
        schoolSupplier.setUid(userSupplierVo.getUid());
        schoolSupplier.setStatus(0);
        schoolSupplier.setCrtime(now);
        String[] str=userSupplierVo.getSids().split(",");
        for (int i=0;i<str.length;i++){
            schoolSupplier.setSid(Integer.valueOf(str[i]));
            schoolSupplierMapper.insertSelective(schoolSupplier);
        }
    }

    @Override
    public void editUserSupplierInfo(UserSupplier userSupplier) {
        int result=userSupplierMapper.updateByPrimaryKeySelective(userSupplier);
        if (result==0){
            throw new CodeException(ErrorEnum.UPDATE_ERROR);
        }
    }

    @Override
    public void submitFeedback(FeedBack feedBack) {
        feedBack.setCrtime(new Date());
        feedBackMapper.insertSelective(feedBack);
    }

    @Override
    public void addUserCanteenBatch(String data,String cid,String uid) {
        List<UserCanteen> list=null;
        try {
            list = JSONObject.parseArray(data, UserCanteen.class);
        } catch (Exception ex) {
            throw new CodeException(ErrorEnum.FORMAT_CONVERT_FAIL);
        }
        if (list.size() == 0) {
            throw new CodeException(ErrorEnum.FORMAT_CONVERT_FAIL);
        }
        Canteen canteen=canteenMapper.selectByPrimaryKey(cid);
        if (org.springframework.util.StringUtils.isEmpty(canteen)){
            throw new CodeException(ErrorEnum.LACK_REQ_PARAM);
        }
        Date now=new Date();
        for (UserCanteen userCanteen:list){
            UserCanteen userCanteen1 = new UserCanteen();
            userCanteen1.setUname(userCanteen.getUname());
            if(userCanteenMapper.select(userCanteen1).size()>0){
                throw new CodeException(ErrorEnum.UserName_Has_Exist);
            }
            UserCanteen userCanteen2 = new UserCanteen();
            userCanteen2.setPhone(userCanteen.getPhone());
            if(userCanteenMapper.select(userCanteen2).size()>0){
                throw new CodeException(ErrorEnum.PHONE_HAS_REGISTER);
            }
            userCanteen.setUid(MyUtil.createUUID());
            userCanteen.setParentId(uid);
            userCanteen.setSid(canteen.getSid());
            userCanteen.setSname(canteen.getSname());
            userCanteen.setCid(canteen.getCid());
            userCanteen.setCname(canteen.getCname());
            userCanteen.setCrtime(now);
            userCanteenMapper.insertSelective(userCanteen);
        }
    }

    @Override
    public List<UserCanteen> getCanteenEmpList(UserCanteenVo userCanteenVo) {
        List<UserCanteen> list=userCanteenMapper.selectUserCanteenList(userCanteenVo);
        return list;
    }

    @Override
    public void editCanteenEmp(UserCanteen userCanteen) {
        userCanteen.setUptime(new Date());
        int result=userCanteenMapper.updateByPrimaryKeySelective(userCanteen);
        if (result==0){
            throw new CodeException(ErrorEnum.UPDATE_ERROR);
        }
    }

    @Override
    public void deleteCanteenEmp(String uid) {
        int result=userCanteenMapper.deleteByPrimaryKey(uid);
        if (result==0){
            throw new CodeException(ErrorEnum.DELETE_FAIL);
        }
    }

    @Override
    public void editUserInfo(User user) {
        user.setUptime(new Date());
        int result=userMapper.updateByPrimaryKeySelective(user);
        if (result==0){
            throw new CodeException(ErrorEnum.UPDATE_ERROR);
        }
    }

    @Override
    public void resetPayPwd(String phone, String payPwd) {
        User user=new User();
        user.setPhone(phone);
        User user1=userMapper.selectOne(user);
        if (org.springframework.util.StringUtils.isEmpty(user1)){
            throw new CodeException(ErrorEnum.LACK_REQ_PARAM);
        }
        user.setPhone(null);
        user.setUid(user1.getUid());
        user.setPayPassword(MD5Util.MD5EncodeUtf8(payPwd));
        int result=userMapper.updateByPrimaryKeySelective(user);
        if (result==0){
            throw new CodeException(ErrorEnum.UPDATE_ERROR);
        }
    }

    @Override
    public void verifyPayPwd(String uid, String payPwd) {
        User user=userMapper.selectByPrimaryKey(uid);
        payPwd=MD5Util.MD5EncodeUtf8(payPwd);
        if (!user.getPayPassword().equalsIgnoreCase(payPwd)){
            throw new CodeException(ErrorEnum.PAY_PASSWORD_ERROR);
        }
    }

    @Override
    public List<UserSupplier> getUserSupplierBySidList(UserSupplierVo userSupplierVo) {
        List<UserSupplier> list=schoolSupplierMapper.selectUserSupplierBySidList(userSupplierVo);
        return list;
    }

    @Override
    public List<Map<String, Object>> getStuEatingInfo(EatingInfoVo eatingInfoVo) {
        Date now=new Date();
        String uptime=MyUtil.formStrFormat(now,"yyyy-MM-dd");
        eatingInfoVo.setUptime(uptime);
        List<Map<String, Object>> list=orderMapper.selectStuEatingInfo(eatingInfoVo);
        return list;
    }

    @Override
    @Transactional(rollbackFor = CodeException.class)
    public void addFace(String uid, MultipartFile multipartFile) {
        User user=userMapper.selectByPrimaryKey(uid);
        String imageUrl=fileStorageService.storeFile(multipartFile, ResourceType.IMAGE, ResourceBusiness.FACE);
        String url=MyUtil.getImageUrl(imageUrl);
        log.info(url);
        FaceUtil.deletePerson(uid);
        FaceUtil.addFace(user,url);
        user.setIsFace(1);
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public String getPayCode(String uid) {
        User user=userMapper.selectByPrimaryKey(uid);
        if (org.springframework.util.StringUtils.isEmpty(user)){
            throw new CodeException(ErrorEnum.PHONE_NOT_REG);
        }
        Long nowSecond = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        String content=uid+"-"+nowSecond;
        String payCode=EncryptUtil.AESEncode(content);
        return payCode;
    }

    @Override
    public List<School> getSchoolSupplierListByUid(String uid) {
        List<School> schoolList = schoolSupplierMapper.selectSchoolListBySupId(uid);
        return schoolList;
    }

    @Override
    public Map<String, Object> getUserInfo(String uid) {
        User user=new User();
        user.setUid(uid);
        Map<String, Object> userInfo = userMapper.selectUserInfo(user);
        return userInfo;
    }
}
