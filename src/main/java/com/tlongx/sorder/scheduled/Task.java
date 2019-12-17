package com.tlongx.sorder.scheduled;

import com.tlongx.common.ErrorEnum;
import com.tlongx.common.exception.CodeException;
import com.tlongx.sorder.dto.SchoolDto;
import com.tlongx.sorder.food.dao.CanteenFoodMapper;
import com.tlongx.sorder.food.dao.SchoolSupfoodMapper;
import com.tlongx.sorder.food.dao.WindowFoodMapper;
import com.tlongx.sorder.food.pojo.CanteenFood;
import com.tlongx.sorder.food.pojo.SchoolSupfood;
import com.tlongx.sorder.food.pojo.WindowFood;
import com.tlongx.sorder.food.service.FoodService;
import com.tlongx.sorder.manager.dao.FundsStatisticsMapper;
import com.tlongx.sorder.manager.pojo.FundsStatistics;
import com.tlongx.sorder.manager.service.ManagerService;
import com.tlongx.sorder.order.dao.CartMapper;
import com.tlongx.sorder.order.dao.TemporaryMapper;
import com.tlongx.sorder.order.dao.TradeMapper;
import com.tlongx.sorder.order.pojo.Temporary;
import com.tlongx.sorder.order.service.OrderService;
import com.tlongx.sorder.user.dao.UserMapper;
import com.tlongx.sorder.utils.MyUtil;
import com.tlongx.sorder.vo.SchoolVo;
import com.tlongx.sorder.vo.TradeVo;
import com.tlongx.sorder.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class Task {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ManagerService managerService;
    @Autowired
    private FoodService foodService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TradeMapper tradeMapper;
    @Autowired
    private FundsStatisticsMapper fundsStatisticsMapper;
    @Autowired
    private TemporaryMapper temporaryMapper;
    @Autowired
    private WindowFoodMapper windowFoodMapper;
    @Autowired
    private CanteenFoodMapper canteenFoodMapper;
    @Autowired
    private SchoolSupfoodMapper schoolSupfoodMapper;
    @Autowired
    private CartMapper cartMapper;

    /**
     * 清空当日购物车
     * 每天零点
     */
    @Scheduled(cron = "0 55 23 * * ?")
    public void cleanCart(){
        log.info("开始执行清空购物车");
        //orderService.cleanCart();
        cartMapper.deleteCart();
        log.info("结束执行清空购物车");
    }

    @Scheduled(cron = "0 50 23 * * ?")
    public void calculateFoodScore(){
        log.info("开始执行计算食品评分");
        orderService.calculateFoodScore();
        log.info("结束执行计算食品评分");
    }

    /**
     * 发放教师补贴
     */
    //@Scheduled(cron = "0 0 0 * * ?")
    public void grantTeacherSubsidy(){
        log.info("开始发放教师补贴");
        managerService.grantTeacherSubsidy();
        log.info("结束发放教师补贴");
    }

    /**
     * 恢复当日供应量
     */
    @Scheduled(cron = "0 10 0 * * ?")
    public void recoverDailySupply(){
        log.info("开始恢复当日供应量");
        foodService.recoverDailySupply();
        log.info("结束恢复当日供应量");
    }

    /**
     * 食品月销量清零
     */
    @Scheduled(cron = "0 0 0 1 * ?")
    public void cleanMonthlySales(){
        log.info("开始月销量归零");
        foodService.cleanFoodMonthlySales();
        log.info("结束月销量归零");
    }

    /**
     * 每天23：59：59清理没有结算或者取餐的订餐
     */
    @Scheduled(cron="59 59 23 * * ?")
    public void cleanOrder(){
        log.info("开始清理没有结算或没有取餐的订单信息");
        orderService.cleanOrderList();
        log.info("结束清理没有结算或没有取餐的订单信息");
    }

    @Scheduled(cron="59 59 23 * * ?")
    public void cleanDepositPrice(){
        log.info("开始清零学校当日已提现金额");
        managerService.cleanSchoolDepositPrice();
        log.info("结束清零学校当日已提现金额");
    }

    @Scheduled(cron = "0 57 23 * * ?")
    public void statisticsFundData(){
        log.info("开始统计当天各学校的账目数据");
        SchoolVo schoolVo = new SchoolVo();
        schoolVo.setRole(1);
        List<SchoolDto> schoolList = managerService.getSchoolList(schoolVo);
        TradeVo tradeVo = new TradeVo();
        Date now = new Date();
        if(schoolList.size()>0){
            Integer sid = null;
            UserVo userVo = new UserVo();
            for(SchoolDto school:schoolList){
                sid = Integer.valueOf(school.getSid());
                Map<String,Object> balanceOnPlatMap = tradeMapper.selectSchoolBalanceOnPlat(sid); //查询当前学校在平台上的余额
                userVo.setSid(school.getSid());
                //userVo.setType("1");//学生
                Map<String,Object> studentWalletMap = userMapper.selectBalanceSum(userVo); //查询当前所有人员的钱包余额
                tradeVo.setSid(sid);
                tradeVo.setCrtime(now);
                tradeVo.setIsPlat(1); //排除收款码充值记录
                Map<String,Object> tradeMap= tradeMapper.selectTradeBySidAndTime(tradeVo); //统计学校当天的资金往来
                BigDecimal refundPrice = tradeMapper.selectRefundPrice(tradeVo); //统计学校当天退款金额
                Map<String,Object> studentWalletTradeMap = tradeMapper.selectStudentWalletTrade(tradeVo);//统计当天学生充值消费
                if(tradeMap!=null){  //资金往来账
                    FundsStatistics fundsStatistics = new FundsStatistics();
                    if(tradeMap.get("income")!=null){
                        fundsStatistics.setIncome(new BigDecimal(tradeMap.get("income").toString()).subtract(refundPrice));
                    }
                    if(tradeMap.get("expand")!=null){
                        fundsStatistics.setExpand(new BigDecimal(tradeMap.get("expand").toString()));
                    }
                    if(balanceOnPlatMap!=null) {
                        fundsStatistics.setTotalPrice(new BigDecimal(balanceOnPlatMap.get("balance").toString()).subtract(refundPrice));
                        fundsStatistics.setCrtime(now);
                        fundsStatistics.setType(2);
                        fundsStatistics.setSid(sid);
                        fundsStatisticsMapper.insertSelective(fundsStatistics);
                    }
                }
                if(studentWalletTradeMap!=null){ //钱包管理
                    FundsStatistics fundsStatistics = new FundsStatistics();
                    if(studentWalletTradeMap.get("incomeTotal")!=null){  //充值
                        fundsStatistics.setIncome(new BigDecimal(studentWalletTradeMap.get("incomeTotal").toString()));
                    }
                    if(studentWalletTradeMap.get("expandTotal")!=null){ //购买
                        fundsStatistics.setExpand(new BigDecimal(studentWalletTradeMap.get("expandTotal").toString()));
                    }
                    if(studentWalletTradeMap.get("sysTotal")!=null){ //系统充值
                        fundsStatistics.setSysRecharge(new BigDecimal(studentWalletTradeMap.get("sysTotal").toString()));
                    }
                    if(studentWalletMap!=null){
                        fundsStatistics.setTotalPrice(new BigDecimal(studentWalletMap.get("totalBalance").toString()));
                        fundsStatistics.setCrtime(now);
                        fundsStatistics.setType(3);
                        fundsStatistics.setSid(sid);
                        fundsStatisticsMapper.insertSelective(fundsStatistics);
                    }
                }
            }
        }
        log.info("结束统计当天各学校的账目数据");
    }

    @Scheduled(cron = "0 57 23 * * ?")
    public void platCapitalTransaction(){
        log.info("开始统计平台资金往来");
        TradeVo tradeVo = new TradeVo();
        Date now = new Date();
        tradeVo.setCrtime(now);
        //统计平台资金往来 需排除收款码充值记录
        tradeVo.setIsPlat(1);
        Map<String,Object> tradeMap= tradeMapper.selectTradeBySidAndTime(tradeVo); //统计当天平台资金往来
        BigDecimal refundPrice = tradeMapper.selectRefundPrice(tradeVo); // 统计当天退款金额
        Map<String,Object> platBalanceMap = tradeMapper.selectPlatBalance();
        if(tradeMap!=null){
            FundsStatistics fundsStatistics = new FundsStatistics();
            if(tradeMap.get("income")!=null){
                fundsStatistics.setIncome(new BigDecimal(tradeMap.get("income").toString()).subtract(refundPrice));
            }
            if(tradeMap.get("expand")!=null){
                fundsStatistics.setExpand(new BigDecimal(tradeMap.get("expand").toString()));
            }
            if(platBalanceMap!=null){
                fundsStatistics.setTotalPrice(new BigDecimal(platBalanceMap.get("sum").toString()).subtract(refundPrice));
                fundsStatistics.setCrtime(now);
                fundsStatistics.setType(1);
                fundsStatisticsMapper.insertSelective(fundsStatistics);
            }

        }
        log.info("结束统计平台资金往来");
    }

    /**
     * 每天凌晨1点 统计销量
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void caculateSale(){
        Temporary temporary = new Temporary();
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            temporary.setSubTime(MyUtil.formDateFormat(sdf.format(now),"yyyy-MM-dd"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<Temporary> temporaries = temporaryMapper.select(temporary);
        if(temporaries.size()>0){
            for(Temporary temp : temporaries){
                WindowFood windowFood=windowFoodMapper.selectByPrimaryKey(temp.getWfid());
                windowFood.setResidueSupply(windowFood.getResidueSupply()-Integer.valueOf(temp.getSaleNum()));
                windowFoodMapper.updateByPrimaryKeySelective(windowFood);
                //增加销量
                if(windowFood.getType()==1){//食堂销售量增加
                    CanteenFood canteenFood = canteenFoodMapper.selectByPrimaryKey(windowFood.getFid());
                    int monthlySales = canteenFood.getMonthlySales()+Integer.valueOf(temp.getSaleNum());
                    int totalSales = canteenFood.getTotalSales()+Integer.valueOf(temp.getSaleNum());
                    canteenFood.setMonthlySales(monthlySales);
                    canteenFood.setTotalSales(totalSales);
                    canteenFoodMapper.updateByPrimaryKeySelective(canteenFood);
                }
                if(windowFood.getType()==2){//供应商销售量增加
                    SchoolSupfood schoolSupfood = schoolSupfoodMapper.selectByPrimaryKey(windowFood.getFid());
                    int monthlySales = schoolSupfood.getMonthlySales()+Integer.valueOf(temp.getSaleNum());
                    int totalSales = schoolSupfood.getTotalSales()+Integer.valueOf(temp.getSaleNum());
                    schoolSupfood.setMonthlySales(monthlySales);
                    schoolSupfood.setTotalSales(totalSales);
                    schoolSupfoodMapper.updateByPrimaryKeySelective(schoolSupfood);
                }
                temporaryMapper.delete(temp);
            }
        }
    }
}
