package com.tlongx.sorder.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.tlongx.common.ErrorEnum;
import com.tlongx.common.TLResult;
import com.tlongx.common.enums.PayType;
import com.tlongx.common.enums.ResourceBusiness;
import com.tlongx.common.enums.ResourceType;
import com.tlongx.common.enums.TradeType;
import com.tlongx.common.excel.ExcelUtil;
import com.tlongx.common.exception.CodeException;
import com.tlongx.sorder.dto.*;
import com.tlongx.sorder.food.dao.CanteenFoodMapper;
import com.tlongx.sorder.food.dao.WindowFoodMapper;
import com.tlongx.sorder.manager.dao.*;
import com.tlongx.sorder.manager.pojo.*;
import com.tlongx.sorder.manager.service.ManagerService;
import com.tlongx.sorder.operation.dao.OperationLogMapper;
import com.tlongx.sorder.operation.pojo.OperationLog;
import com.tlongx.sorder.order.dao.RechargeMapper;
import com.tlongx.sorder.order.dao.TradeMapper;
import com.tlongx.sorder.order.pojo.Recharge;
import com.tlongx.sorder.order.pojo.Trade;
import com.tlongx.sorder.user.dao.*;
import com.tlongx.sorder.user.pojo.*;
import com.tlongx.sorder.utils.*;
import com.tlongx.sorder.vo.*;
import io.swagger.annotations.ApiImplicitParam;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author xin.rf
 * @date 2019/2/12 16:38
 * @Description TODO
 **/
@Slf4j
@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private SchoolMapper schoolMapper;
    @Autowired
    private ManagerMapper managerMapper;
    @Autowired
    private SchoolClassMapper schoolClassMapper;
    @Autowired
    private CanteenMapper canteenMapper;
    @Autowired
    private UserCanteenMapper userCanteenMapper;
    @Autowired
    private AdvertisementMapper advertisementMapper;
    @Autowired
    private DiscountPriceMapper discountPriceMapper;
    @Autowired
    private SupplierTimeMapper supplierTimeMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SchoolSupplierMapper schoolSupplierMapper;
    @Autowired
    private UserSupplierMapper userSupplierMapper;
    @Autowired
    private WindowMapper windowMapper;
    @Autowired
    private WindowFoodMapper windowFoodMapper;
    @Autowired
    private TradeMapper tradeMapper;
    @Autowired
    private FeedBackMapper feedBackMapper;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private OperationLogMapper operationLogMapper;
    @Autowired
    private PhoneCodeMapper phoneCodeMapper;
    @Autowired
    private FundsStatisticsMapper fundsStatisticsMapper;
    @Autowired
    private SysRechargeMapper sysRechargeMapper;
    @Autowired
    private RechargeMapper rechargeMapper;

    @Override
    public Manager login(String username, String password, String type) {
        Manager manager=new Manager();
        manager.setUsername(username);
        manager.setPassword(MD5Util.MD5EncodeUtf8(password));
        manager.setType(Integer.valueOf(type));
        Manager manager1=managerMapper.selectOne(manager);
        if (StringUtils.isEmpty(manager1)){
            throw new CodeException(ErrorEnum.ADMIN_LOGIN_ERROR);
        }
        manager1.setPassword(null);
        return manager1;
    }

    @Override
    @Transactional(rollbackFor = CodeException.class)
    public void addSchool(SchoolVo schoolVo) {
        School school=new School();
        school.setSname(schoolVo.getSname());
        List<School> schoolList=schoolMapper.select(school);
        if (schoolList.size()>0){
            throw new CodeException(ErrorEnum.SchoolName_Has_Exist);
        }
        school.setSnum(schoolVo.getSnum());
        Date today=new Date();
        BigDecimal zero = new BigDecimal(0);
        school.setCrtime(today);
        school.setUptime(today);
        school.setUseHelp("默认内容");
        school.setAboutUs("默认内容");
        school.setDredgeWxpay(schoolVo.getDredgeWxpay());
        school.setDredgeAlipay(schoolVo.getDredgeAlipay());
        school.setDredgeFace(schoolVo.getDredgeFace());
        school.setBalance(zero);
        school.setTotalDepositPrice(zero);
        school.setDepositPrice(zero);
        schoolMapper.insertUseGeneratedKeys(school);
        //添加学校管理员
        Manager manager=new Manager();
        manager.setUsername(schoolVo.getUsername());
        List<Manager> managerList=managerMapper.select(manager);
        if (managerList.size()>0){
            throw new CodeException(ErrorEnum.UserName_Has_Exist);
        }
        manager.setMid(MyUtil.createUUID());
        manager.setMname(schoolVo.getMname());

        manager.setPassword(MD5Util.MD5EncodeUtf8(schoolVo.getPassword()));
        manager.setPhone(schoolVo.getPhone());
        manager.setCrtime(today);
        manager.setUptime(today);
        manager.setSid(school.getSid());
        try {
            manager.setOverdueTime(MyUtil.formDateFormat(schoolVo.getOverdueTime(),"yyyy-MM-dd HH:mm:ss"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        managerMapper.insertSelective(manager);
        //添加满减优惠信息
        DiscountPrice discountPrice=new DiscountPrice();
        discountPrice.setSid(school.getSid());
        discountPrice.setDiscountPrice(new BigDecimal(0));
        discountPrice.setPrice(new BigDecimal(0));
        discountPrice.setType(1);
        discountPrice.setStartTime(today);
        discountPrice.setEndTime(today);
        for (int i=1;i<=3;i++){
            discountPrice.setRole(i);
            discountPriceMapper.insertSelective(discountPrice);
        }
        //添加食品分类信息
        SupplierTime supplierTime=new SupplierTime();
        supplierTime.setSid(school.getSid());
        for (int i=1;i<=8;i++){
            supplierTime.setType(i);
            supplierTimeMapper.insertSelective(supplierTime);
        }

        //添加人脸库
        FaceUtil.createGroup(school.getSid(),school.getSname());
    }


    @Override
    public List getSchoolList(SchoolVo schoolVo) {
        schoolVo.setRole(1);
        List list=schoolMapper.selectSchoolList(schoolVo);
        return list;
    }

    @Override
    public SchoolDto getSchoolInfo(String sid) {
        SchoolVo schoolVo=new SchoolVo();
        schoolVo.setSid(sid);
        schoolVo.setRole(1);
        schoolVo.setPageNum(1);
        schoolVo.setPageSize(1);
        List<SchoolDto> list=schoolMapper.selectSchoolList(schoolVo);
        return list.get(0);
    }

    @Override
    @Transactional(rollbackFor = CodeException.class)
    public void editSchool(SchoolVo schoolVo) {
        School school=new School();
        school.setSid(Integer.valueOf(schoolVo.getSid()));
        school.setSname(schoolVo.getSname());
        if (schoolVo.getSubsidyStatus()!=null){
            school.setSubsidyStatus(Integer.valueOf(schoolVo.getSubsidyStatus()));
        }
        if (schoolVo.getSubsidyPrice()!=null){
            school.setSubsidyPrice(new BigDecimal(schoolVo.getSubsidyPrice()));
        }
        if (schoolVo.getSubsidyTime()!=null){
            try {
                school.setSubsidyTime(MyUtil.formDateFormat(schoolVo.getSubsidyTime(),"yyyy-MM-dd"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        school.setDredgeWxpay(schoolVo.getDredgeWxpay());
        school.setDredgeAlipay(schoolVo.getDredgeAlipay());
        school.setDredgeFace(schoolVo.getDredgeFace());
        Date now=new Date();
        school.setUptime(now);
        schoolMapper.updateByPrimaryKeySelective(school);
        //修改管理员信息
        Manager manager=new Manager();
        manager.setMid(schoolVo.getMid());
        if (schoolVo.getOverdueTime()!=null){
            try {
                manager.setOverdueTime(MyUtil.formDateFormat(schoolVo.getOverdueTime(),"yyyy-MM-dd HH:mm:ss"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        manager.setMname(schoolVo.getMname());
        manager.setUsername(schoolVo.getUsername());
        manager.setPassword(MD5Util.MD5EncodeUtf8(schoolVo.getPassword()));
        manager.setPhone(schoolVo.getPhone());
        if (schoolVo.getCrtime()!=null){
            try {
                manager.setCrtime(MyUtil.formDateFormat(schoolVo.getCrtime(),"yyyy-MM-dd HH:mm:ss"));
                manager.setUptime(now);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(!StringUtils.isEmpty(schoolVo.getStatus())){
            manager.setStatus(Integer.valueOf(schoolVo.getStatus()));
        }
        managerMapper.updateByPrimaryKeySelective(manager);
    }




    @Override
    public void upManagerPassword(String mid, String oldPassword, String newPassword,String phoneCode) {

        String phone = managerMapper.selectByPrimaryKey(mid).getPhone();
        PhoneCode phoneCode1 = new PhoneCode();
        phoneCode1.setCode(phoneCode);
        phoneCode1.setPhone(phone);
        PhoneCode phoneCodeList = phoneCodeMapper.selectOne(phoneCode1);
        if(phoneCodeList==null){
            throw new CodeException(ErrorEnum.EMAIL_CODE_ERROR);
        }

        Manager manager=new Manager();
        manager.setMid(mid);
        manager.setPassword(MD5Util.MD5EncodeUtf8(oldPassword));
        int managerCount=managerMapper.selectCount(manager);
        if (managerCount==0){
            throw new CodeException(ErrorEnum.OLD_PASSWORD_ERROR);
        }
        manager.setPassword(MD5Util.MD5EncodeUtf8(newPassword));
        managerMapper.updateByPrimaryKeySelective(manager);
    }

    @Override
    @Transactional
    public void addClass(SchoolClass schoolClass) {
        int count=schoolClassMapper.selectCount(schoolClass);
        if (count>0){
            throw new CodeException(ErrorEnum.Class_Has_Exsit);
        }
        //判断该学校类型是否是第一次添加
        SchoolClass schoolClass2 = new SchoolClass();
        schoolClass2.setSid(schoolClass.getSid());
        schoolClass2.setType(schoolClass.getType());
        int schoolTypeCount = schoolClassMapper.selectGradeNum(schoolClass2);
        if(schoolTypeCount<=0){//该类型的学校第一次添加，默认设置一个年级班级都为空的数据
            schoolClassMapper.insert(schoolClass2);
        }
        //判断该学校的要添加年级是否是第一次添加
        int gradeCount = schoolClassMapper.selectGradeNum(schoolClass);
        if(gradeCount<=0){// 是则默认添加该年级的班级为空的数据
            SchoolClass schoolClass1 = new SchoolClass();
            schoolClass1.setType(schoolClass.getType());
            schoolClass1.setSid(schoolClass.getSid());
            schoolClass1.setGrade(schoolClass.getGrade());
            schoolClassMapper.insert(schoolClass1);
        }
        schoolClassMapper.insertSelective(schoolClass);
    }

    @Override
    public List<SchoolClass> getClassList(SchoolClassVo schoolClassVo) {
        List<SchoolClass> list=schoolClassMapper.selectSchoolClassList(schoolClassVo);
        return list;
    }

    @Override
    public void editClass(SchoolClass schoolClass) {
        schoolClassMapper.updateByPrimaryKeySelective(schoolClass);
    }

    @Override
    public void deleteClass(Integer cid) {
        int result=schoolClassMapper.deleteByPrimaryKey(cid);
        if (result==0){
            throw new CodeException(ErrorEnum.DELETE_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = CodeException.class)
    public void addCanteen(UserCanteen userCanteen) {

        UserCanteen userCanteen1 = new UserCanteen();
        userCanteen1.setType(2);
        userCanteen1.setPhone(userCanteen.getPhone());
        List<UserCanteen> userCanteenList = userCanteenMapper.select(userCanteen1);
        if(userCanteenList.size()>0){
            throw  new CodeException(ErrorEnum.PHONE_EXSIT);
        }

        //添加食堂
        Date now=new Date();
        Canteen canteen=new Canteen();
        canteen.setCname(userCanteen.getCname());
        canteen.setSid(userCanteen.getSid());
        School school=schoolMapper.selectByPrimaryKey(userCanteen.getSid());
        if (school==null){
            throw new CodeException(ErrorEnum.SCHOOL_NOT_EXIST);
        }
        canteen.setSname(school.getSname());
        canteen.setCrtime(now);
        canteen.setUptime(now);
        canteenMapper.insertUseGeneratedKeys(canteen);
        //添加食堂管理员
        userCanteen.setUid(MyUtil.createUUID());
        userCanteen.setCid(canteen.getCid());
        userCanteen.setPassword(MD5Util.MD5EncodeUtf8(userCanteen.getPhone()));
        userCanteen.setRole("管理员");
        userCanteen.setType(2);
        userCanteen.setSname(school.getSname());
        userCanteen.setCrtime(now);
        userCanteen.setUptime(now);
        userCanteenMapper.insertSelective(userCanteen);
        //添加默认窗口
        Window window=new Window();
        window.setCrtime(now);
        window.setUptime(now);
        window.setCid(canteen.getCid());
        window.setSid(userCanteen.getSid());
        window.setStatus(0);
//        window.setType(2);
//        window.setWname("水果超市");
//        windowMapper.insertSelective(window);
        window.setType(3);
        window.setWname("便利小超");
        windowMapper.insertSelective(window);
        window.setType(1);
        window.setStatus(1);
        window.setWname("1号窗口");
        windowMapper.insertSelective(window);
    }

    @Override
    public List<UserCanteen> getCanteenList(String sid) {
        List<UserCanteen> canteenList=canteenMapper.selectCanteenList(sid);
        return canteenList;
    }

    @Override
    @Transactional
    public void editCanteen(UserCanteen userCanteen) {
        Date now=new Date();
        Canteen canteen=new Canteen();
        canteen.setCid(userCanteen.getCid());
        canteen.setCname(userCanteen.getCname());
        canteen.setUptime(now);
        int result=canteenMapper.updateByPrimaryKeySelective(canteen);
        if (result==0){
            throw new CodeException(ErrorEnum.UPDATE_ERROR);
        }
        userCanteen.setPassword(MD5Util.MD5EncodeUtf8(userCanteen.getPassword()));
        userCanteen.setUptime(now);
        int result1=userCanteenMapper.updateByPrimaryKeySelective(userCanteen);
        if (result1==0){
            throw new CodeException(ErrorEnum.UPDATE_ERROR);
        }
    }

    @Override
    public UserCanteen getCanteenInfo(String uid) {
        UserCanteen userCanteen=userCanteenMapper.selectByPrimaryKey(uid);
        return userCanteen;
    }

    @Override
    public void deleteCanteen(String cid) {
        int result=canteenMapper.deleteByPrimaryKey(cid);

        if (result==0){
            throw new CodeException(ErrorEnum.DELETE_FAIL);
        }
    }

    @Override
    public void addAdv(Advertisement advertisement) {
        advertisement.setCrtime(new Date());
        advertisementMapper.insertSelective(advertisement);
    }

    @Override
    public  List<Advertisement> getAdvList(String sid) {
        Advertisement advertisement=new Advertisement();
        advertisement.setSid(Integer.valueOf(sid));
        List<Advertisement> list=advertisementMapper.select(advertisement);
        return list;
    }

    @Override
    public void editAdv(String id,String photo) {
        Advertisement advertisement=new Advertisement();
        advertisement.setId(Integer.valueOf(id));
        advertisement.setPhoto(photo);
        int result=advertisementMapper.updateByPrimaryKeySelective(advertisement);
        if (result==0){
            throw new CodeException(ErrorEnum.UPDATE_ERROR);
        }
    }

    @Override
    public void deleteAdv(String id) {
        int result=advertisementMapper.deleteByPrimaryKey(id);
        if (result==0){
            throw new CodeException(ErrorEnum.DELETE_FAIL);
        }
    }

    @Override
    public List<SupplierTime> getSupplierTimeList(String sid) {
        SupplierTime supplierTime=new SupplierTime();
        supplierTime.setSid(Integer.valueOf(sid));
        List<SupplierTime> list=supplierTimeMapper.select(supplierTime);
        return list;
    }

    @Override
    public SupplierTime getSupplierTimeInfo(Integer id) {
        SupplierTime foodCategory=supplierTimeMapper.selectByPrimaryKey(id);
        return foodCategory;
    }

    @Override
    public void editSupplierTime(SupplierTime foodCategory) {
        int result=supplierTimeMapper.updateByPrimaryKeySelective(foodCategory);
        if (result==0){
            throw new CodeException(ErrorEnum.UPDATE_ERROR);
        }
    }

    @Override
    public List getUserList(UserVo userVo) {
        if(userVo.getType().equals("5")){
            if(userVo.getCrtime()==null){
                userVo.setCrtime(MyUtil.formStrFormat(new Date(),"yyyy-MM-dd HH:mm:ss"));
            }
            List userList=userMapper.selectTeachAndTradeList(userVo);
            return userList;
        }else{
            List<UserDto> userList=userMapper.selectUserList(userVo);
            return userList;
        }
    }

    @Override
    public User getUserInfo(String uid) {
        User user=userMapper.selectByPrimaryKey(uid);
        user.setPassword(null);
        user.setPayPassword(null);
        return user;
    }

    @Override
    public void editUser(User user) {
        if (user.getPayPassword()!=null){
            user.setPayPassword(MD5Util.MD5EncodeUtf8(user.getPayPassword()));
        }
        user.setUptime(new Date());
        int result=userMapper.updateByPrimaryKeySelective(user);
        if (result<=0){
            throw new CodeException(ErrorEnum.UPDATE_ERROR);
        }
    }

    @Override
    public void deleteUser(String uid) {
        int result=userMapper.deleteByPrimaryKey(uid);
        FaceUtil.deletePerson(uid);
        if (result==0){
            throw new CodeException(ErrorEnum.DELETE_FAIL);
        }
    }

    @Override
    public List<UserDto> getAuditTeacherList(UserVo userVo) {
        List<UserDto> userList=userMapper.selectTeacherList(userVo);
        return userList;
    }

    @Override
    public List<SupplierDto> getUserSupplierList(UserSupplierVo userSupplierVo) {
        List<SupplierDto> userSupplierList=schoolSupplierMapper.selectUserSupplierList(userSupplierVo);
        return userSupplierList;
    }

    @Override
    public UserSupplier getUserSupplierInfo(String uid) {
        UserSupplier userSupplier=userSupplierMapper.selectByPrimaryKey(uid);
        userSupplier.setPassword(null);
        return userSupplier;
    }

    @Override
    public void editUserSupplierStatus(String uid, Integer status) {
        UserSupplier userSupplier=new UserSupplier();
        userSupplier.setUid(uid);
        userSupplier.setStatus(status);
        userSupplier.setUptime(new Date());
        int result=userSupplierMapper.updateByPrimaryKeySelective(userSupplier);
        if (result==0){
            throw new CodeException(ErrorEnum.UPDATE_ERROR);
        }
    }

    @Override
    public void editSchoolSupplierStatus(Integer id,Integer status,String mid) {
        SchoolSupplier schoolSupplier=new SchoolSupplier();
        schoolSupplier.setId(id);
        schoolSupplier.setStatus(status);
        schoolSupplier.setMid(mid);
        int result=schoolSupplierMapper.updateByPrimaryKeySelective(schoolSupplier);
        if (result==0){
            throw new CodeException(ErrorEnum.UPDATE_ERROR);
        }
        SchoolSupplier schoolSupplier1=schoolSupplierMapper.selectByPrimaryKey(id);
        UserSupplier userSupplier=new UserSupplier();
        userSupplier.setUid(schoolSupplier1.getUid());
        userSupplier.setStatus(1);
        int result1=userSupplierMapper.updateByPrimaryKeySelective(userSupplier);
        if (result1==0){
            throw new CodeException(ErrorEnum.UPDATE_ERROR);
        }
    }

    @Override
    public void addWindow(Window window) {
        window.setType(1);
        window.setCrtime(new Date());
        window.setUptime(new Date());
        windowMapper.insertSelective(window);
    }

    @Override
    public void editWindow(Window window) {
        window.setUptime(new Date());
        int result=windowMapper.updateByPrimaryKeySelective(window);
        if (result==0){
            throw new CodeException(ErrorEnum.UPDATE_ERROR);
        }
    }

    @Override
    public List<Window> getWindowList(WindowVo windowVo) {
        List<Window> windowList=windowMapper.selectWindowList(windowVo);
        return windowList;
    }

    @Override
    public void deleteWindow(Integer wid) {
        int result=windowMapper.deleteByPrimaryKey(wid);
        if (result==0){
            throw new CodeException(ErrorEnum.DELETE_FAIL);
        }
    }



    @Override
    public void editDiscountInfo(DiscountPrice discountPrice) {
        int result=discountPriceMapper.updateByPrimaryKeySelective(discountPrice);
        if (result==0){
            throw new CodeException(ErrorEnum.UPDATE_ERROR);
        }
    }

    @Override
    public List<DiscountPrice> getDiscountList(Integer sid) {
        DiscountPrice discountPrice=new DiscountPrice();
        discountPrice.setSid(sid);
        List<DiscountPrice> list=discountPriceMapper.select(discountPrice);
        return list;
    }

    @Override
    public List<FoodBackDto> getFoodListBack(FoodVo foodVo) {
        List<FoodBackDto> foodBackDtoList=windowFoodMapper.selectFoodListBack(foodVo);
        return foodBackDtoList;
    }

    @Override
    public void auditTeacher(String uid, Integer status) {
        User user=new User();
        user.setUid(uid);
        user.setStatus(status);
        int result=userMapper.updateByPrimaryKeySelective(user);
        if (result==0){
            throw new CodeException(ErrorEnum.UPDATE_ERROR);
        }
    }

    @Override
    public List<SchoolClass> getSchoolGradeList(Integer sid,Integer type,Integer grade,Integer classNum) {
        List<SchoolClass> list=schoolClassMapper.selectSchoolGradeList(sid,type,grade,classNum);
        return list;
    }

    @Override
    public List<SchoolClass> getClassNumList(SchoolClass schoolClass) {
        List<SchoolClass> list=schoolClassMapper.selectClassNumList(schoolClass);
        return list;
    }

    @Override
    public void addUserInfo(User user,Integer schoolType,Integer grade,Integer classNum) {
        User user1=new User();
        user1.setPhone(user.getPhone());
        int count=userMapper.selectCount(user1);
        if (count>0){
            throw new CodeException(ErrorEnum.PHONE_EXSIT);
        }
        if (user.getType().equals(1)){
            User user2=new User();
            user2.setStuNum(user.getStuNum());
            int count1=userMapper.selectCount(user2);
            if (count1>0){
                throw new CodeException(ErrorEnum.STU_NUM_EXIST_ALREADY);
            }
        }
        user.setUid(MyUtil.createUUID());
        user.setStatus(1);
        user.setCrtime(new Date());
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPhone()));

        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setType(schoolType);
        schoolClass.setSid(user.getSid());
        if(grade!=null){
            schoolClass.setGrade(grade);
        }else{
            schoolClass.setGrade(-1);
        }
        if(classNum!=null){
            schoolClass.setClassNum(classNum);
        }else{
            schoolClass.setClassNum(-1);
        }

        SchoolClass schoolClass1 = schoolClassMapper.selectOneSchoolClass(schoolClass);

        if(schoolClass1!=null){
            user.setCid(schoolClass1.getScid());
        }else{
            throw new CodeException(ErrorEnum.School_Info_Not_True);
        }
        user.setBalance(new BigDecimal(0));
        userMapper.insertSelective(user);
    }

    @Override
    @Transactional(rollbackFor=CodeException.class)
    public void addUserInfoBatch(MultipartFile excel,Integer sid) {
        List<Object> list= ExcelUtil.readExcel(excel,new UserExcelVo());
        if (list.size()==0){
            throw new CodeException(ErrorEnum.EXCEL_FORMAT_ERROR);
        }
        List<User> userList=new ArrayList<>();
        for (Object obj:list){
            UserExcelVo userExcelVo= (UserExcelVo) obj;
            SchoolClass schoolClass=new SchoolClass();
            if(userExcelVo.getGrade()!=null ){
                schoolClass.setGrade(userExcelVo.getGrade());
            }else{
                schoolClass.setGrade(-1);
            }
            if(userExcelVo.getClassNum()!=null){
                schoolClass.setClassNum(userExcelVo.getClassNum());
            }else{
                schoolClass.setClassNum(-1);
            }
            if(userExcelVo.getSchoolType().equals("小学")){
                schoolClass.setType(4);
            }else if(userExcelVo.getSchoolType().equals("初中")){
                schoolClass.setType(3);
            }else if(userExcelVo.getSchoolType().equals("高中")){
                schoolClass.setType(1);
            }else if(userExcelVo.getSchoolType().equals("大学")){
                schoolClass.setType(2);
            }
            schoolClass.setSid(sid);
            SchoolClass schoolClass1=schoolClassMapper.selectOneSchoolClass(schoolClass);
            if (StringUtils.isEmpty(schoolClass1)){
                throw new CodeException(404,"找不到"+schoolClass.getGrade()+"年级"+"("+schoolClass.getClassNum()+")班，请确认班级是否存在");
            }
            User user=new User();
            user.setPhone(userExcelVo.getPhone());
            int userCount=userMapper.selectCount(user);
            if (userCount>0){
                throw new CodeException(1038,"手机号："+user.getPhone()+"已存在");
            }
            user.setUid(MyUtil.createUUID());
            user.setUsername(userExcelVo.getUsername());
            user.setPassword(MD5Util.MD5EncodeUtf8(user.getPhone()));
            user.setSex(userExcelVo.getSex());
            if (userExcelVo.getType().equals("学生")){
                user.setType(1);
                user.setStuNum(userExcelVo.getStuNum());
                User user1=new User();
                user1.setStuNum(userExcelVo.getStuNum());
                user1.setSid(sid);
                int userCount1=userMapper.selectCount(user1);
                if (userCount1>0){
                    throw new CodeException(1022,"学号"+user1.getStuNum()+"已存在");
                }
            }else if (userExcelVo.getType().equals("老师") || userExcelVo.getType().equals("教师") ){
                user.setType(2);
            }else if (userExcelVo.getType().equals("班主任")){
                user.setType(3);
            }else {
                user.setType(4);
            }
            user.setStatus(1);
            user.setSid(sid);
            user.setCid(schoolClass1.getScid());
            user.setCrtime(new Date());
            userList.add(user);
        }
        userMapper.insertUserBatch(userList);
    }

    @Override
    public List<SchoolClass> getGradeAndClassList(SchoolClass schoolClass) {
        SchoolClassVo schoolClassVo=new SchoolClassVo();
        schoolClassVo.setSid(schoolClass.getSid().toString());
        List<SchoolClass> list=schoolClassMapper.selectSchoolClassList(schoolClassVo);
        return list;
    }

    @Override
    public List<PlatTradeDto> getPlatTradeList(PlatTradeVo platTradeVo) {
        List<PlatTradeDto> list=schoolMapper.selectPlaTradeList(platTradeVo);
        return list;
    }

    @Override
    @Transactional(rollbackFor = CodeException.class)
    public void grantTeacherSubsidy() {
        Date now=new Date();
        School school=new School();
        school.setSubsidyStatus(1);
        school.setSubsidyTime(now);
        List<School> list=schoolMapper.selectSchoolBySubsidy(school);
        List<Integer> types=new ArrayList<>();
        types.add(2);
        types.add(3);
        if (list.size()>0){
            for (School school1:list){
                Integer sid=school1.getSid();
                BigDecimal subsidyPrice=school1.getSubsidyPrice();
                Example example=new Example(User.class);
                Example.Criteria criteria=example.createCriteria();
                Iterable<Integer> integers=types;
                criteria.andIn("type",integers);
                criteria.andEqualTo("sid",sid);
                List<User> userList=userMapper.selectByExample(example);
                if (userList.size()>0){
                    List<Trade> list1=new ArrayList<>();
                    for (User user:userList){
                        user.setUptime(now);
                        user.setSubsidyPrice(subsidyPrice.add(user.getSubsidyPrice()));
                        userMapper.updateByPrimaryKeySelective(user);
                        //添加流水
                        Trade trade=new Trade();
                        trade.setTid(MyUtil.createId("T"));
                        trade.setCrtime(now);
                        trade.setIe(1);
                        trade.setStatus(1);
                        trade.setPrice(subsidyPrice);
                        trade.setPayType(PayType.补贴.getPayType());
                        trade.setTradeType(TradeType.系统发放补贴.getTradeType());
                        trade.setUid(user.getUid());
                        list1.add(trade);
                    }
                    tradeMapper.insertBatch(list1);
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = CodeException.class)
    public void subsidyForTeacher(User user) {
        User user1=userMapper.selectByPrimaryKey(user.getUid());
        if (!user1.getType().equals(2)&&!user1.getType().equals(3)){
            throw new CodeException(ErrorEnum.NOT_A_TEACHER);
        }
        user1.setSubsidyPrice(user1.getSubsidyPrice().add(user.getSubsidyPrice()));
        userMapper.updateByPrimaryKeySelective(user1);
        //添加流水
        Trade trade=new Trade();
        trade.setTid(MyUtil.createId("T"));
        trade.setUid(user.getUid());
        trade.setTradeType(TradeType.单独发放补贴.getTradeType());
        trade.setPayType(PayType.补贴.getPayType());
        trade.setPrice(user.getSubsidyPrice());
        trade.setStatus(1);
        trade.setIe(1);
        trade.setCrtime(new Date());
        tradeMapper.insertSelective(trade);
    }

    @Override
    public List<SubsidyHistoryDto> getSubsidyHistoryList(TradeVo tradeVo) {
        List<SubsidyHistoryDto> list=tradeMapper.selectSubsidyHistoryList(tradeVo);
        return list;
    }

    @Override
    public List<FeedBackDto> getFeedBackList(FeedBackVo feedBackVo) {
        List<FeedBackDto> list=feedBackMapper.selectFeedBackList(feedBackVo);
        return list;
    }

    @Override
    public void addSchoolManager(Manager manager) {
        Manager manager1=managerMapper.selectByPrimaryKey(manager.getParentId());
        Manager manager2 = new Manager();
        manager2.setType(1);
        manager2.setUsername(manager.getUsername());
        List<Manager> managerList = managerMapper.select(manager2);
        if(managerList.size()>0){
            throw new CodeException(ErrorEnum.UserName_Has_Exist);
        }
        manager.setMid(MyUtil.createUUID());
        manager.setPassword(MD5Util.MD5EncodeUtf8(manager.getPassword()));
        manager.setStatus(1);
        manager.setRole(manager.getRole());
        manager.setType(1);
        manager.setCrtime(new Date());
        manager.setSid(manager1.getSid());
        manager.setOverdueTime(manager1.getOverdueTime());
        managerMapper.insertSelective(manager);
    }

    @Override
    public List<Manager> getSchoolManagerList(Manager manager) {
        manager.setRole(2);
        manager.setType(1);
        List<Manager> list=managerMapper.select(manager);
        for (Manager manager1:list){
            manager1.setPassword(null);
        }
        return list;
    }

    @Override
    public List<School> getSchoolComboBoxList() {
        List<School> list=schoolMapper.selectSchoolComboBoxList();
        return list;
    }

    @Override
    public School getSchoolDetail(String sid){
        School school = schoolMapper.selectByPrimaryKey(sid);
        return school;
    }

    @Override
    public void withDraw(String sid, Integer type, BigDecimal price, String mid,String data) {
        Map<String,Object> resultMap = new HashMap<>();
        School school = schoolMapper.selectByPrimaryKey(sid);

        Map map = new HashMap();

        if(school.getBalance().compareTo(price)<0){
            log.info("提现失败，提现金额不能大于账户余额");
            resultMap.put("msg","提现失败，提现金额不能大于账户余额");
        }

        String outTradeNo=MyUtil.createId("O");
        Date now=new Date();
        //添加流水
        Trade trade=new Trade();
        String tid = MyUtil.createId("T");
        trade.setTid(tid);
        trade.setUid(mid);
        trade.setOutTradeNo(outTradeNo);
        trade.setTradeType(4);

        trade.setIe(1);
        trade.setPrice(price);
        trade.setStatus(2);
        trade.setCrtime(now);
        trade.setAccount(data);

        Trade trade1 = new Trade();
        BigDecimal zero = new BigDecimal(0);

        if(type==1){ //支付宝
            trade.setPayType(3);
            resultMap= AliPayUtil.transfer(trade);
            log.info("支付宝提现交易返回参数:{}",resultMap);
            log.info("msg:{}",resultMap.get("msg"));
            log.info("payDate:{}",resultMap.get("payDate"));

            tradeMapper.insertSelective(trade);

            trade1 = tradeMapper.selectByPrimaryKey(tid);

            if(resultMap.get("msg").equals("Success") && resultMap.get("payDate")!=null && resultMap.get("payDate")!=""){
                log.info("提现成功，更新流水，更新学校账户");
                //提现成功
                //更新流水
                trade1.setStatus(1);
                trade1.setUptime(new Date());

                //更新学校账户
                BigDecimal balance = school.getBalance().subtract(price);
                log.info("学校账户余额：{}",balance);

                if(balance.compareTo(zero)>=0){
                    log.info("余额大于0");
                    school.setBalance(balance);
                }else{
                    log.info("余额小于于0");
                    school.setBalance(zero);
                }
                school.setUptime(new Date());
                school.setDepositPrice(price);
                school.setTotalDepositPrice(school.getTotalDepositPrice().add(price));
                schoolMapper.updateByPrimaryKeySelective(school);

            }else{//提现失败
                trade1.setStatus(3);
                trade1.setUptime(new Date());
            }
        }
        if(type==2){ //微信
           trade.setPayType(2);
            String outBizNo = MyUtil.strObject(new Date().getTime() + MyUtil.random(6));
            try {
                resultMap= WeChatPayUtil.transfers(outBizNo,data,price.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            tradeMapper.insertSelective(trade);
            trade1 = tradeMapper.selectByPrimaryKey(tid);

            if(resultMap.get("return_code").equals("SUCCESS")&&resultMap.get("result_code").equals("SUCCESS")){
                //提现成功
                //更新流水
                trade1.setStatus(1);
                trade1.setUptime(new Date());

                //更新学校账户
                BigDecimal balance = school.getBalance().subtract(price);
                if(balance.compareTo(zero)>=0){
                    school.setBalance(balance);
                }else{
                    school.setBalance(zero);
                }
                school.setUptime(new Date());
                school.setDepositPrice(price);
                school.setTotalDepositPrice(school.getTotalDepositPrice().add(price));
                schoolMapper.updateByPrimaryKeySelective(school);

            }else{
                trade1.setStatus(3);
                trade1.setUptime(new Date());

            }
        }

        tradeMapper.updateByPrimaryKeySelective(trade1);
    }

    @Override
    public School getSchoolBankAccountInfo(Integer sid) {
        School schoolBankInfo = schoolMapper.selectSchoolBankAccountInfo(sid);
        return schoolBankInfo;
    }

    @Override
    public void addOrEditSchoolBankAccount(School school) {
        schoolMapper.updateByPrimaryKeySelective(school);
    }

    @Override
    @Transactional(rollbackFor = CodeException.class)
    public void addPayOrder(TradeDto trade,Integer sid) {
        Manager manager = new Manager();
        manager.setSid(sid);
        manager.setRole(1);
        manager.setStatus(1);
        manager.setType(1);
        Manager manager1 = managerMapper.selectOne(manager);
        if(manager1==null){
            throw  new CodeException(ErrorEnum.No_Manager);
        }
        Trade trade1 = new Trade();
        trade1.setTid(MyUtil.createId("T"));
        trade1.setUid(manager1.getMid());
        trade1.setTradeType(4);
        trade1.setStatus(1);
        trade1.setIe(2);

        trade1.setBalance(trade.getBalance());
        trade1.setPayOrder(trade.getPayOrder());
        trade1.setAccount(trade.getAccount());
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            trade1.setCrtime(formatter.parse(trade.getCrtime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        trade1.setPrice(trade.getPrice());
        trade1.setMid(trade.getMid());
        tradeMapper.insert(trade1);

        School school = schoolMapper.selectByPrimaryKey(sid);
        school.setBalance(trade.getBalance());
        school.setUptime(new Date());
        school.setDepositPrice(trade.getPrice());
        school.setTotalDepositPrice(school.getTotalDepositPrice().add(trade.getPrice()));
        schoolMapper.updateByPrimaryKeySelective(school);
    }

    @Override
    public SchoolDto getSchoolAccountInfo(Integer sid) {
        Map<String,Object> balanceOnPlatMap = tradeMapper.selectSchoolBalanceOnPlat(sid); //查询当前学校在平台上的余额
        SchoolDto schoolDto = schoolMapper.selectSchoolAccount(sid);
        schoolDto.setBalance(new BigDecimal(balanceOnPlatMap.get("balance").toString()));
        return schoolDto;
    }

    @Override
    public List<TradeDto> getPayOrderList(TradeDto tradeDto) {
        List<TradeDto> tradeDtos = tradeMapper.selectPayOrderList(tradeDto);
        return tradeDtos;
    }

    @Override
    public String getPayOrder(String tid){
        String payOrder= tradeMapper.selectByPrimaryKey(tid).getPayOrder();
        return payOrder;
    }

    @Override
    public List<OperationLog> getOperationLogList(OpLogVo opLogVo) {
        List<OperationLog> logList = operationLogMapper.selectOperLogList(opLogVo);
        return logList;
    }

    @Override
    public void cleanSchoolDepositPrice() {
        schoolMapper.updateSchoolDepositPrice();
    }

    @Override
    public String sendPhoneCode(String mid) {
        Manager manager = managerMapper.selectByPrimaryKey(mid);
        PhoneCode phoneCode = new PhoneCode();
        phoneCode.setPhone(manager.getPhone());
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
    public Manager getManageInfoByMid(String mid) {
        return managerMapper.selectByPrimaryKey(mid);
    }

    @Override
    public List<Map<String, Object>> getSchoolLedgerList(SchoolVo schoolVo) {
        List<Map<String,Object>> list = tradeMapper.selectSchoolLedger(schoolVo);
        return list;
    }

    @Override
    public List<FundsStatistics> getSchoolCapitalAccount(SchoolVo schoolVo) {
        List list = fundsStatisticsMapper.selectList(schoolVo);
        return list;
    }

    @Override
    @Transactional(rollbackFor = CodeException.class)
    public void editUserBalance(User user) {
        User user1 = userMapper.selectByPrimaryKey(user.getUid()); //原来的
        SysRecharge sysRecharge = new SysRecharge();
        Date now = new Date();
        if(user1.getBalance().compareTo(user.getBalance())>0){
            sysRecharge.setIe(2); //负 -
        }else{
            sysRecharge.setIe(1); //正 +
        }
        sysRecharge.setPrice(user.getBalance().subtract(user1.getBalance()));
        sysRecharge.setUid(user.getUid());
        sysRecharge.setSid(user1.getSid());
        sysRecharge.setCrtime(now);
        sysRechargeMapper.insertSelective(sysRecharge);
        user.setUptime(new Date());
        int result=userMapper.updateByPrimaryKeySelective(user);
        if (result<=0){
            throw new CodeException(ErrorEnum.UPDATE_ERROR);
        }
    }

    @Override
    public List<List<Map<String,Object>>> getSchoolSaleListOnPlat(SchoolVo schoolVo) {
        List<List<Map<String,Object>>> list = new ArrayList<>();
        SchoolVo schoolVo1 = new SchoolVo();
        schoolVo1.setRole(1);
        List<SchoolDto> schoolList = schoolMapper.selectSchoolList(schoolVo1);
        if(schoolList.size()>0){
            Date now = new Date();
            List<Map<String,Object>> schoolSaleList = new ArrayList<>();
            for(SchoolDto schoolDto : schoolList){
                schoolVo.setSid(schoolDto.getSid());
                if(schoolVo.getCrtime()==null){
                    schoolVo.setCrtime(MyUtil.formStrFormat(now,"yyyy-MM-dd HH:mm:ss"));
                }
                schoolSaleList = tradeMapper.selectSchoolSaleList(schoolVo);
                if(schoolSaleList.size()>0){
                    list.add(schoolSaleList);
                }
            }
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> getPlatLedger(SchoolVo schoolVo) {
        List<Map<String,Object>> list = tradeMapper.selectPlatLedger(schoolVo);
        return list;
    }

    @Override
    public void upRechargePhoto(Recharge recharge) {
        if(recharge.getId()!=null){
            recharge.setUptime(new Date());
            rechargeMapper.updateByPrimaryKeySelective(recharge);
        }else {
            recharge.setCrtime(new Date());
            rechargeMapper.insertSelective(recharge);
        }
    }

    @Override
    public Recharge getRecharge(Integer sid) {
        Recharge recharge = new Recharge();
        recharge.setSid(sid);
        return rechargeMapper.selectOne(recharge);
    }
}
