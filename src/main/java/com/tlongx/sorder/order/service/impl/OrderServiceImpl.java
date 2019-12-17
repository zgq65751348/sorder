package com.tlongx.sorder.order.service.impl;

import com.abc.pay.client.TrxException;
import com.abc.pay.client.ebus.PaymentResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.util.AlipaySignature;
import com.tlongx.common.ErrorEnum;
import com.tlongx.common.TLResult;
import com.tlongx.common.enums.*;
import com.tlongx.common.excel.ExcelUtil;
import com.tlongx.common.exception.CodeException;
import com.tlongx.sorder.config.AliPayConfig;
import com.tlongx.sorder.config.WeChatConfig;
import com.tlongx.sorder.dto.*;
import com.tlongx.sorder.food.dao.CanteenFoodMapper;
import com.tlongx.sorder.food.dao.SchoolSupfoodMapper;
import com.tlongx.sorder.food.dao.SupplierFoodMapper;
import com.tlongx.sorder.food.dao.WindowFoodMapper;
import com.tlongx.sorder.food.pojo.CanteenFood;
import com.tlongx.sorder.food.pojo.SchoolSupfood;
import com.tlongx.sorder.food.pojo.SupplierFood;
import com.tlongx.sorder.food.pojo.WindowFood;
import com.tlongx.sorder.manager.dao.*;
import com.tlongx.sorder.manager.pojo.*;
import com.tlongx.sorder.order.dao.*;
import com.tlongx.sorder.order.pojo.*;
import com.tlongx.sorder.order.service.OrderService;
import com.tlongx.sorder.user.dao.UserCanteenMapper;
import com.tlongx.sorder.user.dao.UserMapper;
import com.tlongx.sorder.user.pojo.User;
import com.tlongx.sorder.user.pojo.UserCanteen;
import com.tlongx.sorder.utils.*;
import com.tlongx.sorder.vo.CommentVo;
import com.tlongx.sorder.vo.DataStatisticsVo;
import com.tlongx.sorder.vo.OrderVo;
import com.tlongx.sorder.vo.TradeVo;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private TradeMapper tradeMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DiscountPriceMapper discountPriceMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private SupplierTimeMapper supplierTimeMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CanteenFoodMapper canteenFoodMapper;
    @Autowired
    private SchoolSupfoodMapper schoolSupfoodMapper;
    @Autowired
    private SupplierFoodMapper supplierFoodMapper;
    @Autowired
    private CanteenMapper canteenMapper;
    @Autowired
    private WindowMapper windowMapper;
    @Autowired
    private WindowFoodMapper windowFoodMapper;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private SchoolMapper schoolMapper;
    @Autowired
    private UserCanteenMapper userCanteenMapper;
    @Autowired
    private TemporaryMapper temporaryMapper;
    @Autowired
    private RechargeMapper rechargeMapper;
    @Autowired
    private SysRechargeMapper sysRechargeMapper;

    @Override
    public void addCart(Cart cart,Integer type) {

        Date now=new Date();
        Integer num=cart.getNum();
        cart.setNum(null);
        List<Cart> list=cartMapper.select(cart);
        if (list.size()>0){
            Cart cart1=list.get(0);
            if (num.equals(0)){
                cartMapper.deleteByPrimaryKey(cart1.getId());
            }else{
                cart1.setNum(num);
                cart1.setUptime(now);
                cartMapper.updateByPrimaryKeySelective(cart1);
            }
        }else{
            //判断是否是特色餐品
            if (type.equals(SupplyTime.特色午餐.getType())||type.equals(SupplyTime.特色早餐.getType())||type.equals(SupplyTime.特色晚餐.getType())){
                User user=userMapper.selectByPrimaryKey(cart.getUid());
                SupplierTime supplierTime=new SupplierTime();
                supplierTime.setSid(user.getSid());
                supplierTime.setType(type);
                SupplierTime supplierTime1=supplierTimeMapper.selectOne(supplierTime);
                String startTimeStr=MyUtil.formStrFormat(now,"yyyy-MM-dd")+" "+supplierTime1.getSupplyStart()+":00";
                String endTimeStr=MyUtil.formStrFormat(now,"yyyy-MM-dd")+" "+supplierTime1.getSupplyEnd()+":00";
                Date startTime=null;
                Date endTime=null;
                try {
                    startTime=MyUtil.formDateFormat(startTimeStr,"yyyy-MM-dd HH:mm:ss");
                    endTime=MyUtil.formDateFormat(endTimeStr,"yyyy-MM-dd HH:mm:ss");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (now.getTime()<startTime.getTime() || now.getTime() > endTime.getTime()){
                    throw new CodeException(ErrorEnum.NOT_IN_SUPPLY_TIME);
                }
            }
            //添加购物车
            cart.setNum(num);
            cart.setCrtime(now);
            cart.setStatus(1);
            cartMapper.insertSelective(cart);
        }

    }

    @Override
    public List<Map<String,Object>> getCart(String uid) {
        List<Map<String,Object>> list=cartMapper.selectCartList(uid,null);
        int i=0;
        if (list.size()>0){
            for (Map<String,Object> map:list){
                if (Integer.valueOf(map.get("residueSupply").toString())>0){
                    if(Integer.valueOf(map.get("num").toString())>Integer.valueOf(map.get("residueSupply").toString())){
                        Cart cart=cartMapper.selectByPrimaryKey(map.get("id"));
                        cart.setNum(Integer.valueOf(map.get("residueSupply").toString()));
                        cartMapper.updateByPrimaryKeySelective(cart);
                        i++;
                    }
                }else{
                    cartMapper.deleteByPrimaryKey(map.get("id"));
                }
            }
        }
        if (i>0){
            list=cartMapper.selectCartList(uid,null);
        }
        list=MyUtil.parseListBySort(list,"subDate");
        return list;
    }

    @Override
    public void editCart(Integer id, Integer num) {
        Cart cart=new Cart();
        cart.setId(Long.valueOf(id.toString()));
        cart.setNum(num);
        int result=cartMapper.updateByPrimaryKeySelective(cart);
        if (result==0){
            throw new CodeException(ErrorEnum.UPDATE_ERROR);
        }
    }

    @Override
    public void deleteCart(String ids) {
        String[] str=MyUtil.verifyArrayFormat(ids);
        int result=0;
        for (int i=0;i<str.length;i++){
            result=cartMapper.deleteByPrimaryKey(str[i]);
            if (result==0){
                throw new CodeException(ErrorEnum.DELETE_FAIL);
            }
        }
    }

    @Override
    public Map<String,Object> settleCart(String uid,String cardIds,Boolean useSubsidy) {
        Map<String,Object> resultMap=new HashMap<>();
        Date now=new Date();
        //查询结算商品列表
        List<Map<String,Object>> list=cartMapper.selectCartList(uid,cardIds.split(","));
        int i=0;
        if (list.size()>0){
            for (Map<String,Object> map:list){
                Temporary temporary = new Temporary();
                try {
                    temporary.setSubTime(MyUtil.formDateFormat(map.get("subDate").toString(),"yyyy-MM-dd"));
                    temporary.setWfid(Integer.valueOf(map.get("wfid").toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                temporary = temporaryMapper.selectOne(temporary);
                if(temporary == null){
                    if(Integer.valueOf(map.get("dailySupply").toString())>0){
                        if(Integer.valueOf(map.get("num").toString())>Integer.valueOf(map.get("residueSupply").toString())){
                            Cart cart=cartMapper.selectByPrimaryKey(map.get("id"));
                            cart.setNum(Integer.valueOf(map.get("residueSupply").toString()));
                            cartMapper.updateByPrimaryKeySelective(cart);
                            i++;
                        }
                    }else{
                        cartMapper.deleteByPrimaryKey(map.get("id"));
                    }
                }else{
                    if(temporary.getDailySupply()>0){
                        Integer diff = temporary.getDailySupply()-temporary.getSaleNum();
                        if(Integer.valueOf(map.get("num").toString())>diff){
                            Cart cart=cartMapper.selectByPrimaryKey(map.get("id"));
                            cart.setNum(diff);
                            cartMapper.updateByPrimaryKeySelective(cart);
                            i++;
                        }
                    }else{
                        cartMapper.deleteByPrimaryKey(map.get("id"));
                    }
                    }
                }
//                if (Integer.valueOf(map.get("residueSupply").toString())>0){
//                    if(Integer.valueOf(map.get("num").toString())>Integer.valueOf(map.get("residueSupply").toString())){
//                        Cart cart=cartMapper.selectByPrimaryKey(map.get("id"));
//                        cart.setNum(Integer.valueOf(map.get("residueSupply").toString()));
//                        cartMapper.updateByPrimaryKeySelective(cart);
//                        i++;
//                    }
//                }else{
//                    cartMapper.deleteByPrimaryKey(map.get("id"));
//                }
        }
        if (i>0){
            list=cartMapper.selectCartList(uid,cardIds.split(","));
            resultMap.put("advice","受供应量影响，部分商品已发生变化，请确认");
        }
        resultMap.put("list",list);
        //计算总额
        BigDecimal totalPrice=new BigDecimal(0);
        Integer num=0;
        String priceStr=null;
        Set<Integer> typeSet=new HashSet<>();
        String subDate=null;

        BigDecimal mFoodPrice = new BigDecimal(0);
        BigDecimal aFoodPrice = new BigDecimal(0);
        BigDecimal nFoodPrice = new BigDecimal(0);
        BigDecimal tmFoodPrice = new BigDecimal(0);
        BigDecimal taFoodPrice = new BigDecimal(0);
        BigDecimal tnFoodPrice = new BigDecimal(0);

        for (Map<String,Object> map:list){
            num=Integer.valueOf(map.get("num").toString());
            BigDecimal price=new BigDecimal(0);
            priceStr=map.get("price").toString();
            price=price.add(new BigDecimal(priceStr));
            totalPrice=totalPrice.add(price.multiply(new BigDecimal(num)));//要支付的总价
            //条目中供应类型
            if (!map.get("type").toString().equals("4")&&!map.get("type").toString().equals("5")){
                typeSet.add(Integer.valueOf(map.get("type").toString()));
            }
            resultMap.put("subDate",map.get("subDate").toString());
            subDate=map.get("subDate").toString();
            int foodType =Integer.valueOf(map.get("type").toString());

            if(foodType == 1){//早餐
                mFoodPrice = mFoodPrice.add(price.multiply(new BigDecimal(num)));
            }else if(foodType == 2){//午餐
                aFoodPrice = aFoodPrice.add(price.multiply(new BigDecimal(num)));
            }else if(foodType == 3){//晚餐
                nFoodPrice = nFoodPrice.add(price.multiply(new BigDecimal(num)));
            }else if(foodType == 6){//特色早餐
                tmFoodPrice = tmFoodPrice.add(price.multiply(new BigDecimal(num)));
            }else if(foodType == 7){//特色午餐
                taFoodPrice = taFoodPrice.add(price.multiply(new BigDecimal(num)));
            }else if(foodType == 8){//特色晚餐
                tnFoodPrice = tnFoodPrice.add(price.multiply(new BigDecimal(num)));
            }

        }
        resultMap.put("totalPrice",totalPrice);
        User user=userMapper.selectByPrimaryKey(uid);//获取该uid的用户信息
        if(user==null){//平板端
            resultMap.put("totalPrice",totalPrice);
        }else{
        //获取该学校优惠制度
        DiscountPrice discountPrice=new DiscountPrice();
        discountPrice.setSid(user.getSid());
        List<DiscountPrice> discountPriceList=null;
        //判断用户类型
        Integer type=user.getType();
        Integer disType=0;
        BigDecimal price=new BigDecimal(0);
        BigDecimal disPrice=new BigDecimal(0);//满减金额
        if (type.equals(1)){
            discountPrice.setRole(1);
        }else if(type.equals(2)){
            discountPrice.setRole(2);
        }else {
            discountPrice.setRole(3);
        }
        resultMap.put("disType",0);
        resultMap.put("enableSubsidy",0);
        BigDecimal subSidyPrice=new BigDecimal(0);//补贴金额
        resultMap.put("subSidyPrice",0);
        //是否有教师补贴可用
        if (type.equals(2)||type.equals(3)){ // 教师/班主任
            OrderVo orderVo=new OrderVo();
            orderVo.setSubTime(subDate);
            SupplierTime supplierTime=new SupplierTime();
            supplierTime.setSid(user.getSid());
            List<SupplierTime> supplierTimeList=supplierTimeMapper.select(supplierTime);

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Long nowTime = null;
            try {
                nowTime = MyUtil.formDateFormat(sdf.format(now),"HH:mm").getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //判断今天是否用过
            if (typeSet.contains(1)||typeSet.contains(6)){
                //早餐、特色早餐
                //判断是否开启
                if (supplierTimeList.get(0).getSubsidyStatus().equals(1)||supplierTimeList.get(5).getSubsidyStatus().equals(1)){
                    List<Integer> types = new ArrayList<>();
                    types.add(1);
                    types.add(6);
                    orderVo.setTypes(types);
                    int zcCount=orderItemMapper.selectHasSubsidyOrderCount(orderVo);
                    if (zcCount==0){
                        if (typeSet.contains(1) && supplierTimeList.get(0).getSubsidyStatus().equals(1)){
                            useSubsidy = true;
                            String msStr = supplierTimeList.get(0).getSupplyStart();
                            String meStr = supplierTimeList.get(0).getSupplyEnd();
                            try {
                                Long msTime = MyUtil.formDateFormat(msStr,"HH:mm").getTime();
                                Long meTime = MyUtil.formDateFormat(meStr,"HH:mm").getTime();
                                if(nowTime >= msTime && nowTime <=meTime){
                                    subSidyPrice = subSidyPrice.add(isUserSub(supplierTimeList.get(0).getSubsidyPrice(),mFoodPrice,subSidyPrice));
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }else if(typeSet.contains(6) && supplierTimeList.get(5).getSubsidyStatus().equals(1)){
                            useSubsidy = true;
                            String tmsStr = supplierTimeList.get(5).getSupplyStart();
                            String tmeStr = supplierTimeList.get(5).getSupplyEnd();
                            try {
                                Long tmsTime = MyUtil.formDateFormat(tmsStr,"HH:mm").getTime();
                                Long tmeTime = MyUtil.formDateFormat(tmeStr,"HH:mm").getTime();
                                if(nowTime >= tmsTime && nowTime <=tmeTime){
                                    subSidyPrice = subSidyPrice.add(isUserSub(supplierTimeList.get(5).getSubsidyPrice(),tmFoodPrice,subSidyPrice));
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            if (typeSet.contains(2)||typeSet.contains(7)){
                //午餐、特色午餐
                //判断是否开启
                if (supplierTimeList.get(1).getSubsidyStatus().equals(1)||supplierTimeList.get(6).getSubsidyStatus().equals(1)){
                    List<Integer> types = new ArrayList<>();
                    types.add(2);
                    types.add(7);
                    orderVo.setTypes(types);
                    int wcCount=orderItemMapper.selectHasSubsidyOrderCount(orderVo);
                    if (wcCount==0){
                        if (typeSet.contains(2) && supplierTimeList.get(1).getSubsidyStatus().equals(1)){
                            useSubsidy = true;
                            String asStr = supplierTimeList.get(1).getSupplyStart();
                            String aeStr = supplierTimeList.get(1).getSupplyEnd();
                            try {
                                Long asTime = MyUtil.formDateFormat(asStr,"HH:mm").getTime();
                                Long aeTime = MyUtil.formDateFormat(aeStr,"HH:mm").getTime();
                                if(nowTime >= asTime && nowTime <=aeTime){
                                    subSidyPrice = subSidyPrice.add(isUserSub(supplierTimeList.get(1).getSubsidyPrice(),aFoodPrice,subSidyPrice));
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }else if(typeSet.contains(7) && supplierTimeList.get(6).getSubsidyStatus().equals(1)){
                            useSubsidy = true;
                            String tasStr = supplierTimeList.get(6).getSupplyStart();
                            String taeStr = supplierTimeList.get(6).getSupplyEnd();
                            try {
                                Long tasTime = MyUtil.formDateFormat(tasStr,"HH:mm").getTime();
                                Long taeTime = MyUtil.formDateFormat(taeStr,"HH:mm").getTime();
                                if(nowTime >= tasTime && nowTime <=taeTime){
                                    subSidyPrice = subSidyPrice.add(isUserSub(supplierTimeList.get(6).getSubsidyPrice(),taFoodPrice,subSidyPrice));
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            if (typeSet.contains(3)||typeSet.contains(8)){
                //晚餐、特色晚餐
                //判断是否开启
                if (supplierTimeList.get(2).getSubsidyStatus().equals(1)||supplierTimeList.get(7).getSubsidyStatus().equals(1)){
                    List<Integer> types = new ArrayList<>();
                    types.add(3);
                    types.add(8);
                    orderVo.setTypes(types);
                    int dcCount=orderItemMapper.selectHasSubsidyOrderCount(orderVo);
                    if (dcCount==0){
                        if (typeSet.contains(3) && supplierTimeList.get(2).getSubsidyStatus().equals(1)){
                            useSubsidy = true;
                            String nsStr = supplierTimeList.get(2).getSupplyStart();
                            String neStr = supplierTimeList.get(2).getSupplyEnd();
                            try {
                                Long nsTime = MyUtil.formDateFormat(nsStr,"HH:mm").getTime();
                                Long neTime = MyUtil.formDateFormat(neStr,"HH:mm").getTime();
                                if(nowTime >= nsTime && nowTime <=neTime){
                                    subSidyPrice = subSidyPrice.add(isUserSub(supplierTimeList.get(2).getSubsidyPrice(),nFoodPrice,subSidyPrice));
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }else if(typeSet.contains(8)&&supplierTimeList.get(7).getSubsidyStatus().equals(1)){
                            useSubsidy = true;
                            String tnsStr = supplierTimeList.get(7).getSupplyStart();
                            String tneStr = supplierTimeList.get(7).getSupplyEnd();
                            try {
                                Long tnsTime = MyUtil.formDateFormat(tnsStr,"HH:mm").getTime();
                                Long tneTime = MyUtil.formDateFormat(tneStr,"HH:mm").getTime();
                                if(nowTime >= tnsTime && nowTime <=tneTime){
                                    subSidyPrice = subSidyPrice.add(isUserSub(supplierTimeList.get(7).getSubsidyPrice(),tnFoodPrice,subSidyPrice));
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            if (subSidyPrice.compareTo(new BigDecimal(0))>0){
                resultMap.put("enableSubsidy",1);
                resultMap.put("subSidyPrice",subSidyPrice);
            }
        }
        if (!useSubsidy){
            //判断是否达到满减要求
            discountPriceList=discountPriceMapper.select(discountPrice);
            DiscountPrice discountPrice1=discountPriceList.get(0);
            //判断是否在优惠时段内
            if (now.getTime()>=discountPrice1.getStartTime().getTime()&&now.getTime()<=discountPrice1.getEndTime().getTime()){
                disType=discountPrice1.getType();
                price=price.add(discountPrice1.getPrice());
                disPrice=disPrice.add(discountPrice1.getDiscountPrice());
                //优惠类型
                if (disType.equals(1)){
                    if (totalPrice.compareTo(disPrice)>=0){
                        totalPrice.subtract(price);
                        resultMap.put("disPrice",price);
                        resultMap.put("disType",1);
                        resultMap.put("totalPrice",totalPrice);
                    }
                }else{
                    BigDecimal tmp=new BigDecimal(0);
                    tmp=totalPrice;
                    if(disPrice.compareTo(new BigDecimal(0))>0){
                        totalPrice=totalPrice.multiply(disPrice.divide(new BigDecimal(10)));
                    }
                    tmp=tmp.subtract(totalPrice);
                    resultMap.put("disPrice",tmp);
                    resultMap.put("disType",2);
                    resultMap.put("totalPrice",totalPrice);
                }
            }

        }else{
            //使用教师补贴
            totalPrice=totalPrice.subtract(subSidyPrice);
            resultMap.put("disType",3);
            resultMap.put("totalPrice",totalPrice);
            resultMap.put("subSidyPrice",subSidyPrice);
        }

        }
        return resultMap;
    }

    @Override
    public Map<String,Object> recharge(String uid, String price, String type) {
        Map<String,Object> resultMap=new HashMap<>();
        User user=userMapper.selectByPrimaryKey(uid);
        String outTradeNo=MyUtil.createId("O");
        Date now=new Date();
        //添加流水
        Trade trade=new Trade();
        trade.setTid(MyUtil.createId("T"));
        trade.setUid(uid);
        trade.setOutTradeNo(outTradeNo);
        trade.setTradeType(1);

        trade.setIe(1);
        trade.setPrice(new BigDecimal(price));
        trade.setStatus(2);
        trade.setCrtime(now);
        //trade.setBalance(user.getBalance());

        School school = schoolMapper.selectByPrimaryKey(user.getSid());

        //微信支付
        if (type.equals("1")){
            if(school.getDredgeWxpay()==0){ //学校未开通微信支付
                throw new CodeException(ErrorEnum.School_No_Dredge_Wxpay);
            }
            trade.setPayType(PayType.微信.getPayType());
            //调起微信支付
            resultMap=WeChatPayUtil.unifiedorder(trade);
        }else if (type.equals("2")){
            if(school.getDredgeAlipay()==0){// 学校未开通支付宝支付
                throw new CodeException(ErrorEnum.School_No_Dredge_Alipay);
            }
            trade.setPayType(PayType.支付宝.getPayType());
            try {
                resultMap= AliPayUtil.alipay(trade);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
//        else if (type.equals("3")){
//            trade.setPayType(PayType.农行.getPayType());
//            resultMap = AbcUtil.abcPay(trade);
//        }
        tradeMapper.insertSelective(trade);
        return resultMap;
    }

    @Override
    public TLResult wxNotify(HttpServletRequest request, HttpServletResponse response) {
        log.info("=========================进入微信异步回调======================");
        Boolean resultFLag = true;
        Map resMap = new HashMap();
        resMap.put("return_code", "SUCCESS");
        resMap.put("return_msg", "OK");
        SortedMap<String, String> map = new TreeMap<String, String>();
        InputStream inputStream = null;
        Date now=new Date();
        try {
            inputStream = request.getInputStream();
            SAXReader reader = new SAXReader();

            Document document = reader.read(inputStream);
            Element root = document.getRootElement();
            List<Element> elementList = root.elements();

            for (Element e : elementList) {
                map.put(e.getName(), e.getText());
                log.info(e.getName() + "    " + e.getText());
            }

            String responseSign = map.get("sign").toString();
            if ((responseSign.equals("")) || (responseSign.equals(null))) {
                resMap.put("return_code", "FAIL");
                resMap.put("return_msg", "FAIL");
            }
            String tradeType0=map.get("trade_type").toString();
            map.put("sign", " ");
            log.info("微信签名参数:{}",map);
            String tagSign = WeChatPayUtil.createSign(map, "UTF-8");
            log.info("微信签名是否通过：{}",tagSign);
            if(tradeType0.equals(WeChatConfig.tradeType)) {
                tagSign = WeChatPayUtil.createSign(map, "UTF-8");
            }
            if (!tagSign.equals(responseSign)) {
                // 签名验不过，表示这个API返回的数据有可能已经被篡改了
                log.info("签名验不过，表示这个API返回的数据有可能已经被篡改了");
                resMap.put("return_code", "FAIL");
                resMap.put("return_msg", "FAIL");
            }

            if (map.get("return_code").toString().equals("SUCCESS")) {
                String outTradeNo = MyUtil.strObject(map.get("out_trade_no"));
                String attach = MyUtil.strObject(map.get("attach"));
                log.info(outTradeNo+"=========>"+attach);
                //支付种类 1钱包充值
                String tid=attach;
                Trade trade=tradeMapper.selectByPrimaryKey(tid);
                Integer tradeType=trade.getTradeType();
                String uid=trade.getUid();
                //微信回调传回支付金额
                BigDecimal totalFee=new BigDecimal(MyUtil.strObject(map.get("total_fee")));
                String cartIds=trade.getCartIds();
                totalFee=totalFee.divide(new BigDecimal(100));
                Boolean useSubsidy=false;
                if (trade.getUseSubsidy().equals(1)){
                    useSubsidy=true;
                }
                Integer discountType = null;
                BigDecimal subSidyPrice = new BigDecimal(0);
                if(cartIds!=null && cartIds!=""){
                    Map<String,Object> cartMap=settleCart(uid,cartIds,useSubsidy);
                    discountType=Integer.valueOf(cartMap.get("disType").toString());
                    subSidyPrice=new BigDecimal(cartMap.get("subSidyPrice").toString());
                }
                handlerBusiness(now, tid, trade, uid, tradeType, totalFee,cartIds,useSubsidy,discountType,subSidyPrice);
            }
        }catch (Exception e) {
            resultFLag = Boolean.valueOf(false);
            e.printStackTrace();
            resMap.put("return_code", "FAIL");
            resMap.put("return_msg", "FAIL");
            new CodeException(ErrorEnum.ILLEGAL_REQUEST);
        }

        String xmlStr = MyUtil.parseXML(resMap);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/xml;charset=utf-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (resultFLag.booleanValue()) {
            resMap.put("return_code", "SUCCESS");
            resMap.put("return_msg", "OK");
        } else {
            resMap.put("return_code", "FAIL");
            resMap.put("return_msg", "FAIL");
        }
        out.write(xmlStr);
        out.flush();
        out.close();
        return null;
    }


    @Override
    public TLResult aliPayNotify(HttpServletRequest request, HttpServletResponse response) {
        log.info("----------------支付宝回调------------------");
        Date now=new Date();
        Boolean resultFLag = Boolean.valueOf(true);
        Map map = request.getParameterMap();

        Set keSet = map.entrySet();
        for (Iterator itr = keSet.iterator(); itr.hasNext();) {
            Map.Entry me = (Map.Entry) itr.next();
            Object ok = me.getKey();
            Object ov = me.getValue();
            String[] value = new String[1];
            if (ov instanceof String[]) {
                value = (String[]) ov;
            } else {
                value[0] = ov.toString();
            }

            for (int k = 0; k < value.length; k++) {
                log.info(ok + "=" + value[k]);
            }
        }

        log.info("----------------{trade_status}------------------", request.getParameter("trade_status"));
        if ("TRADE_SUCCESS".equals(request.getParameter("trade_status"))) {
            Enumeration pNames = request.getParameterNames();
            Map param = new HashMap();
            try {
                while (pNames.hasMoreElements()) {
                    String pName = (String) pNames.nextElement();
                    param.put(pName, request.getParameter(pName));
                }
                log.info("参数param:{}",param,"UTF-8");
                boolean signVerified = AlipaySignature.rsaCheckV1(param, AliPayConfig.publicKey, "UTF-8",
                        "RSA2");
                log.info("签名是否正确：{}",signVerified);
                if (signVerified) {
                    log.info("校验签名是否正确");
                    log.info("passback_params:{}", request.getParameter("passback_params"), "UTF-8");
                    String tid = URLDecoder.decode(request.getParameter("passback_params"), "UTF-8");
                    Trade trade=tradeMapper.selectByPrimaryKey(tid);
                    String uid=trade.getUid();
                    Integer tradeType=trade.getTradeType();
                    log.info("tradeType:{}",tradeType);
                    BigDecimal totalFee=new BigDecimal(MyUtil.strObject(param.get("total_amount")));
                    log.info("total_amount:{}",totalFee);
                    String cartIds=trade.getCartIds();
                    log.info("cartIds:{}",cartIds);
                    Boolean useSubsidy=false;
                    if (trade.getUseSubsidy().equals(1)){
                        useSubsidy=true;
                    }
                    log.info("useSubsidy:{}",useSubsidy);
                    //数据库查询出支付金额
                    Integer discountType = null;
                    BigDecimal subSidyPrice = new BigDecimal(0);
                    if(cartIds!=null && cartIds!=""){
                        Map<String,Object> cartMap=settleCart(uid,cartIds,useSubsidy);
                        discountType=Integer.valueOf(cartMap.get("disType").toString());
                        subSidyPrice=new BigDecimal(cartMap.get("subSidyPrice").toString());
                    }
                    handlerBusiness(now, tid, trade, uid, tradeType, totalFee,cartIds,useSubsidy,discountType,subSidyPrice);
                } else {
                    resultFLag = Boolean.valueOf(false);
                }
            } catch (Exception e) {
                resultFLag = Boolean.valueOf(false);
            }
        } else {
            resultFLag = Boolean.valueOf(false);
        }

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/xml;charset=utf-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String result="";
        if (resultFLag.booleanValue()){
            log.info("success");
            result = "success";
        }else {
            log.info("failure");
            result = "fail";
        }
        out.write(result);
        out.flush();
        out.close();
        return null;
    }

    private void handlerBusiness(Date now, String tid, Trade trade, String uid, Integer tradeType,BigDecimal totalFee
    ,String cartIds,Boolean useSubsidy,Integer discountType,BigDecimal subsidyPrice) {
        log.info("进入handlerBusiness");
        BigDecimal price=trade.getPrice();
        if (totalFee.compareTo(price)!=0){
            throw new CodeException(ErrorEnum.PAY_PRICE_ERROR);
        }
        if (tradeType.equals(1)){
            User user1=userMapper.selectByPrimaryKey(uid);
            log.info("钱包充值");
            //更新流水支付状态
            trade.setStatus(1);
            trade.setUptime(now);
            trade.setBalance(user1.getBalance().add(totalFee));
            tradeMapper.updateByPrimaryKeySelective(trade);
            //钱包充值
            User user=new User();
            user.setUid(uid);
            log.info("钱包充值的用户余额：{}",user1.getBalance());
            log.info("钱包充值的充值余额：{}",trade.getPrice());
            log.info("totalFee：{}",totalFee);
            user.setBalance(user1.getBalance().add(totalFee));
            userMapper.updateByPrimaryKeySelective(user);
        }else if(tradeType.equals(2)){
            //查询购物车信息
            Map<String,Object> cartMap=settleCart(uid,cartIds,useSubsidy);
            //添加预订单信息
            Order order=new Order();
            String oid= MyUtil.createId("O");
            order.setOid(oid);
            order.setOutTradeNo(trade.getOutTradeNo());
            order.setUid(uid);
            //选择是否使用补贴
            order.setPayPrice(new BigDecimal(cartMap.get("totalPrice").toString()));
            order.setStatus(3);
            order.setPayType(trade.getPayType());
            if(order.getPayType().equals(PayType.钱包支付.getPayType())){
                order.setPayTime(now);
               // order.setUptime(now);
            }
            order.setDiscountsType(discountType);
            order.setSubsidyPrice(subsidyPrice);

            order.setCrtime(now);
            orderMapper.insertSelective(order);

            String json = JSONObject.toJSONString(cartMap.get("list"));
            List<CartDto> cartList= JSON.parseArray(json,CartDto.class);
            if (cartList.size()>0){
                for (CartDto cartDto:cartList){
                    //添加订单明细信息
                    OrderItem orderItem=new OrderItem();
                    orderItem.setCrtime(now);
                    orderItem.setOid(order.getOid());
                    orderItem.setStatus(3);
                    orderItem.setSubTime(cartDto.getSubDate());
                    orderItem.setNum(cartDto.getNum());
                    orderItem.setPhoto(cartDto.getPhoto());
                    orderItem.setPrice(new BigDecimal(cartDto.getPrice()));
                    orderItem.setTotalPrice(orderItem.getPrice().multiply(new BigDecimal(orderItem.getNum())));
                    orderItem.setType(cartDto.getType());
                    orderItem.setWfid(cartDto.getWfid());
                    orderItem.setFoodName(cartDto.getFoodName());
                    orderItem.setCname(cartDto.getCname());
                    orderItem.setWname(cartDto.getWname());
                    orderItem.setUid(uid);
                    orderItemMapper.insertSelective(orderItem);
                }
            }
            User user = userMapper.selectByPrimaryKey(uid);
            //更新流水信息
            Trade trade1=new Trade();
            trade1.setTid(tid);
            trade1.setOid(oid);
            trade1.setStatus(1);

            if(trade.getPayType().equals(PayType.支付宝.getPayType()) || trade.getPayType().equals(PayType.微信.getPayType()) || trade.getPayType().equals(PayType.农行.getPayType())){
                trade1.setBalance(user.getBalance());
            }else{
                if(user.getBalance().compareTo(totalFee)<0){
                    throw new CodeException(ErrorEnum.Not_Sufficient_Funds);
                }
                trade1.setBalance(user.getBalance().subtract(totalFee));
            }
            trade1.setUptime(now);
            if(useSubsidy){
                trade1.setSubsidyPrice(subsidyPrice);
            }
            tradeMapper.updateByPrimaryKeySelective(trade1);

            //减对应日期供应量
            List<Map<String, Object>> list = cartMapper.selectCartList(uid, cartIds.split(","));
            if (list.size()>0){
                Temporary temporary = new Temporary();
                Temporary temporary1 = new Temporary();
                for (Map<String,Object> map:list){
                    WindowFood windowFood=windowFoodMapper.selectByPrimaryKey(map.get("wfid"));
                    try {
                        temporary.setSubTime(MyUtil.formDateFormat(map.get("subDate").toString(),"yyyy-MM-dd"));
                        temporary.setWfid(Integer.valueOf(map.get("wfid").toString()));
                        temporary1 = temporaryMapper.selectOne(temporary);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if(ObjectUtils.isEmpty(temporary1)){
                        Integer num = Integer.valueOf(map.get("num").toString());
                        temporary.setSaleNum(num);
                        temporary.setDailySupply(windowFood.getDailySupply());
                        temporaryMapper.insertSelective(temporary);
                    }else{
                        temporary1.setSaleNum(temporary1.getSaleNum()+Integer.valueOf(map.get("num").toString()));
                        temporaryMapper.updateByPrimaryKeySelective(temporary1);
                    }

//                    windowFood.setResidueSupply(windowFood.getResidueSupply()-Integer.valueOf(map.get("num").toString()));
//                    windowFoodMapper.updateByPrimaryKeySelective(windowFood);
//                    //增加销量
//                    if(windowFood.getType()==1){//食堂销售量增加
//                        CanteenFood canteenFood = canteenFoodMapper.selectByPrimaryKey(windowFood.getFid());
//                        int monthlySales = canteenFood.getMonthlySales()+Integer.valueOf(map.get("num").toString());
//                        int totalSales = canteenFood.getTotalSales()+Integer.valueOf(map.get("num").toString());
//                        canteenFood.setMonthlySales(monthlySales);
//                        canteenFood.setTotalSales(totalSales);
//                        canteenFoodMapper.updateByPrimaryKeySelective(canteenFood);
//                    }
//                    if(windowFood.getType()==2){//供应商销售量增加
//                        SchoolSupfood schoolSupfood = schoolSupfoodMapper.selectByPrimaryKey(windowFood.getFid());
//                        int monthlySales = schoolSupfood.getMonthlySales()+Integer.valueOf(map.get("num").toString());
//                        int totalSales = schoolSupfood.getTotalSales()+Integer.valueOf(map.get("num").toString());
//                        schoolSupfood.setMonthlySales(monthlySales);
//                        schoolSupfood.setTotalSales(totalSales);
//                        schoolSupfoodMapper.updateByPrimaryKeySelective(schoolSupfood);
//                    }
                }
            }
            //删除购物车
            deleteCart(cartIds);
        }
    }

    @Override
    @Transactional(rollbackFor = CodeException.class)
    public TLResult placeAnOrder(Trade tr, String cardIds) {
        Map<String,Object> resultMap=new HashMap<>();
        Date now=new Date();
        //查询购物车信息
        Boolean useSubsidy=false;
        if (tr.getUseSubsidy().equals(1)){
            useSubsidy=true;
        }
        Map<String,Object> cartMap=settleCart(tr.getUid(),cardIds,useSubsidy);
        //添加预流水
        Trade trade=new Trade();
        trade.setTid(MyUtil.createId("T"));
        String otn=MyUtil.createId("OTN");
        trade.setUid(tr.getUid());
        trade.setOutTradeNo(otn);
        trade.setTradeType(2);
        trade.setPayType(tr.getPayType());
        trade.setPrice(new BigDecimal(cartMap.get("totalPrice").toString()));

        trade.setCartIds(cardIds);
        if (useSubsidy){
            trade.setUseSubsidy(1);
            trade.setSubsidyPrice(new BigDecimal(cartMap.get("subSidyPrice").toString()));//使用补贴
        }else{
            trade.setUseSubsidy(0);
        }
        //待支付
        trade.setStatus(2);
        trade.setIe(2);
        trade.setCrtime(now);
        tradeMapper.insertSelective(trade);

        User user1=userMapper.selectByPrimaryKey(tr.getUid());
        School school = schoolMapper.selectByPrimaryKey(user1.getSid());

        //钱包支付
        if (tr.getPayType().equals(PayType.钱包支付.getPayType())){
            Integer discountType=Integer.valueOf(cartMap.get("disType").toString());
            BigDecimal subSidyPrice=new BigDecimal(cartMap.get("subSidyPrice").toString());
            handlerBusiness(now,trade.getTid(),trade,tr.getUid(),2,trade.getPrice(),cardIds,useSubsidy,discountType,subSidyPrice);

            //判断账户余额是否充足
            if(user1.getBalance().compareTo(trade.getPrice())<0){
                throw new CodeException(ErrorEnum.Not_Sufficient_Funds);
            }
            //从钱包扣钱
            User user=new User();
            user.setUid(tr.getUid());
            user.setBalance(user1.getBalance().subtract(trade.getPrice()));
            user.setUptime(now);
            userMapper.updateByPrimaryKeySelective(user);

            //学校加钱
            school.setBalance(school.getBalance().add(trade.getPrice()));
            school.setUptime(now);
            schoolMapper.updateByPrimaryKeySelective(school);

        }else if (tr.getPayType().equals(PayType.微信.getPayType())){
            if(school.getDredgeWxpay()==0){ // 未开通微信支付
                throw new CodeException(ErrorEnum.School_No_Dredge_Wxpay);
            }
            //微信支付
            resultMap=WeChatPayUtil.unifiedorder(trade);
        }else if (tr.getPayType().equals(PayType.支付宝.getPayType())){
            if(school.getDredgeAlipay()==0){ // 未开通支付宝支付
                throw new CodeException(ErrorEnum.School_No_Dredge_Alipay);
            }
            //支付宝支付
            try {
                resultMap=AliPayUtil.alipay(trade);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else if (tr.getPayType().equals(PayType.农行.getPayType())){
            //农行支付
            resultMap = AbcUtil.abcPay(trade);
        }
        return new TLResult(resultMap);
    }

    @Override
    public List<OrderItemDto> getMyOrderList(String uid, Integer type, String subDate) {
        Date now=new Date();
        OrderItem orderItem=new OrderItem();
        orderItem.setUid(uid);
        orderItem.setSubTime(subDate);
        List<OrderItemDto> list=orderItemMapper.selectList(orderItem);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Long nowTime = null;
        try {
            nowTime = sdf.parse(sdf.format(now)).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //TODO
        if (list.size()>0){
            String subTimeStr=null;
            String nowStr=null;
            Date subTime=null;
            for (OrderItemDto orderItemDto:list){
                subTimeStr=orderItem.getSubTime()+" 17:00:00";
                nowStr=MyUtil.formStrFormat(now,"yyyy-MM-dd");

                if(nowStr.equals(orderItemDto.getSubTime())){//当前日期==预定日期  不能取消订单

                    orderItemDto.setRefundEnable(0);

                    if(orderItemDto.getType() == 1 || orderItemDto.getType() == 6){//早餐和特色早餐  7：00-9：00
                        try {
                            if(nowTime >=sdf.parse("6:00:00").getTime() && nowTime<=sdf.parse("9:30:00").getTime()){//在早餐时间段内
                                orderItemDto.setTakeEnable(1);//可以取餐
                            }else if(nowTime>sdf.parse("9:30:00").getTime()){//超过早餐时间段
                                orderItemDto.setTakeEnable(0);
                                orderItemDto.setIsExpire(1);//订单过期
                            }else if(nowTime<sdf.parse("6:00:00").getTime()){//还没到早餐时间段
                                orderItemDto.setTakeEnable(2);//等待取餐
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    if(orderItemDto.getType() == 2 || orderItemDto.getType() == 7){//午餐和特色午餐 11：00-13：00
                        try {
                            if(nowTime >=sdf.parse("10:00:00").getTime() && nowTime<=sdf.parse("14:30:00").getTime()){//在午餐时间段内
                                orderItemDto.setTakeEnable(1);//可以取餐
                            }else if (nowTime>sdf.parse("14:30:00").getTime()){
                                orderItemDto.setTakeEnable(0);
                                orderItemDto.setIsExpire(1);//订单过期
                            }else if(nowTime<sdf.parse("10:00:00").getTime()){
                                orderItemDto.setTakeEnable(2);//等待取餐
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    if(orderItemDto.getType() == 3 || orderItemDto.getType() == 8){//晚餐和特色晚餐  17：00-19：00
                        try {
                            if(nowTime >=sdf.parse("16:00:00").getTime() && nowTime<=sdf.parse("20:00:00").getTime()){//在晚餐时间段内
                                orderItemDto.setTakeEnable(1);//可以取餐
                            }else if(nowTime>sdf.parse("20:00:00").getTime()){
                                orderItemDto.setTakeEnable(0);
                                orderItemDto.setIsExpire(1);//订单过期
                            }else if(nowTime<sdf.parse("16:00:00").getTime()){
                                orderItemDto.setTakeEnable(2);//等待取餐
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    if(orderItemDto.getType() == 4 || orderItemDto.getType() == 5){//水果超市
                        orderItemDto.setTakeEnable(1);
                    }

                }else{
                    orderItemDto.setTakeEnable(0);
                }

                try {
                    subTime=MyUtil.formDateFormat(subTimeStr,"yyyy-MM-dd HH:mm:ss");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar c=Calendar.getInstance();
                c.setTime(subTime);
                c.add(5,-1);
                //判断是否可取消订单
                if (now.getTime()>=c.getTime().getTime()){
                    orderItemDto.setRefundEnable(0);
                }else {
                    orderItemDto.setRefundEnable(1);
                }

            }
        }
        return list;
    }

    @Override
    @Transactional(rollbackFor = CodeException.class)
    public void cancelOrder(String ids)  {
        Date now =new Date();
        List<OrderDto> orderItemList=getQrOrder(ids);
        for (OrderDto orderDto:orderItemList){

            if(orderDto.getStatus()==5 || orderDto.getStatus() == 4 || orderDto.getStatus() == 6){
                throw new CodeException(ErrorEnum.No_Cancel);
            }

            Integer payType=orderDto.getPayType();
            //增对应日期的供应量
            OrderItem orderItem1 = new OrderItem();
            orderItem1.setOid(orderDto.getOid());
            try {
                Date subTime = MyUtil.formDateFormat(orderItemMapper.select(orderItem1).get(0).getSubTime(),"yyyy-MM-dd");
                Temporary temporary = new Temporary();
                temporary.setSubTime(subTime);
                temporary.setWfid(orderDto.getWfid());
                temporary = temporaryMapper.selectOne(temporary);
                temporary.setSaleNum(temporary.getSaleNum()-orderDto.getNum());
                temporaryMapper.updateByPrimaryKeySelective(temporary);
            } catch (ParseException e) {
                e.printStackTrace();
            }
//            WindowFood windowFood=windowFoodMapper.selectByPrimaryKey(orderDto.getWfid());
//            windowFood.setResidueSupply(windowFood.getResidueSupply()+orderDto.getNum());
//            windowFoodMapper.updateByPrimaryKeySelective(windowFood);
            //生成流水
            Trade trade=new Trade();
            trade.setTid(MyUtil.createId("T"));
            trade.setUid(orderDto.getUid());
            trade.setOid(orderDto.getOid());
            trade.setOutTradeNo(orderDto.getOutTradeNo());
            trade.setTradeType(3);
            trade.setPrice(orderDto.getPayPrice());
            trade.setStatus(1);
            trade.setIe(1);
            trade.setCrtime(now);
            OrderItem orderItem=new OrderItem();
            orderItem.setId(orderDto.getId());
            orderItem.setStatus(5);
            orderItem.setRefundTime(now);
            Order order = new Order();
            order.setOid(orderDto.getOid());
            order.setStatus(5);
            order.setUptime(now);
            User user1=userMapper.selectByPrimaryKey(orderDto.getUid());
            if (payType.equals(PayType.钱包支付.getPayType())){
                orderItemMapper.updateByPrimaryKeySelective(orderItem);
                orderMapper.updateByPrimaryKeySelective(order);
                trade.setPayType(PayType.钱包支付.getPayType());
                //退回钱包
                User user=new User();
                user.setUid(orderDto.getUid());
                user.setBalance(user1.getBalance().add(orderDto.getPayPrice()));
                userMapper.updateByPrimaryKeySelective(user);
                trade.setBalance(user1.getBalance().add(orderDto.getPayPrice()));
                tradeMapper.insertSelective(trade);
                School school = schoolMapper.selectByPrimaryKey(user1.getSid());
                school.setBalance(school.getBalance().subtract(orderDto.getPayPrice()));
                schoolMapper.updateByPrimaryKeySelective(school);
            }else if(payType.equals(PayType.微信.getPayType())){
                try {
                    Map<String,String> resultMap=WeChatPayUtil.refund(orderDto);
                    if(resultMap.get("result_code").toString().equals("SUCCESS")&&resultMap.get("return_code").toString().equals("SUCCESS")){
                        orderItemMapper.updateByPrimaryKeySelective(orderItem);
                        orderMapper.updateByPrimaryKeySelective(order);
                        trade.setPayType(PayType.微信.getPayType());
                        trade.setBalance(user1.getBalance());
                        tradeMapper.insertSelective(trade);
                    }else {
                        throw new CodeException(ErrorEnum.CANCEL_ORDER_FAIL);
                    }
                } catch (Exception e) {
                    throw new CodeException(ErrorEnum.CANCEL_ORDER_FAIL);
                }
            }else if(payType.equals(PayType.支付宝.getPayType())){
                try {
                    Map<String,String> resultMap=AliPayUtil.refund(orderDto);
                    if (resultMap.get("code").toString().equals("10000")){
                        orderItemMapper.updateByPrimaryKeySelective(orderItem);
                        orderMapper.updateByPrimaryKeySelective(order);
                        trade.setPayType(PayType.支付宝.getPayType());
                        trade.setBalance(user1.getBalance());
                        tradeMapper.insertSelective(trade);
                    }else {
                        throw new CodeException(ErrorEnum.CANCEL_ORDER_FAIL);
                    }
                } catch (UnsupportedEncodingException e) {
                    throw new CodeException(ErrorEnum.CANCEL_ORDER_FAIL);
                }
            }else if(payType.equals(PayType.农行.getPayType())){
                Trade trade1 = tradeMapper.selectOneTradeByOid(orderDto.getOid());
                Map resultMap = AbcUtil.refund(trade1,trade.getTid());
                if(resultMap.get("code").toString().equals("0000")){
                    orderItemMapper.updateByPrimaryKeySelective(orderItem);
                    orderMapper.updateByPrimaryKeySelective(order);
                    trade.setPayType(PayType.农行.getPayType());
                    trade.setBalance(user1.getBalance());
                    tradeMapper.insertSelective(trade);
                }else {
                    throw new CodeException(ErrorEnum.CANCEL_ORDER_FAIL);
                }
            }
        }
    }

    @Override
    public List<OrderDto> getQrOrder(String ids) {
        String[] str=MyUtil.verifyArrayFormat(ids);
        List<String> list=new ArrayList<>();
        for (int i=0;i<str.length;i++){
            list.add(str[i]);
        }
        List<OrderDto> orderItemList=orderItemMapper.selectListByIds(list);
        return orderItemList;
    }

    @Override
    public List<OrderDto> takeFood(String ids,Integer wid,Integer type) {
        Date now=new Date();
        List<OrderDto> list=getQrOrder(ids);
        for(OrderDto orderDto:list){
            if(type == 1){
                if(orderDto.getWid()!=wid){
                    throw new CodeException(ErrorEnum.Choose_Correct_Windows);
                }
            }
            if (orderDto.getStatus().equals(5)){
                throw new CodeException(ErrorEnum.ORDER_HAS_CANCEL);
            }
            if(orderDto.getStatus().equals(6)){
                throw new CodeException(ErrorEnum.ORDER_HAS_FINISH);
            }
            if(orderDto.getStatus().equals(4)){
                throw new CodeException(ErrorEnum.Take_Food_Finish);
            }
            OrderItem orderItem=new OrderItem();
            orderItem.setId(orderDto.getId());
            orderItem.setStatus(4);
            orderItem.setUptime(now);
            orderItemMapper.updateByPrimaryKeySelective(orderItem);
            Order order=new Order();
            order.setOid(orderDto.getOid());
            order.setStatus(4);
            order.setUptime(now);
            orderMapper.updateByPrimaryKeySelective(order);
        }
        return list;
    }

    @Override
    public OrderItem getOrderItemInfo(Integer id) {
        OrderItem orderItem=orderItemMapper.selectByPrimaryKey(id);
        return orderItem;
    }

    @Override
    public void addComment(Comment comment) {
        //添加评论
        Date now=new Date();
        comment.setCrtime(now);
        OrderItemDto orderItemDto=orderItemMapper.selectOIForComment(comment.getOid());
        comment.setCid(orderItemDto.getCid());
        comment.setSid(orderItemDto.getSid());
        comment.setFid(orderItemDto.getFid());
        comment.setFoodName(orderItemDto.getFoodName());
        comment.setFoodType(orderItemDto.getType());
        comment.setUid(orderItemDto.getUid());
        comment.setStatus(1);
        commentMapper.insertSelective(comment);
        //修改订单条目状态
        OrderItem orderItem=new OrderItem();
        orderItem.setId(comment.getOid());
        orderItem.setStatus(6);
        orderItemMapper.updateByPrimaryKeySelective(orderItem);
    }

    @Override
    public List<CommentDto> getCommentListByCidOrUid(CommentVo commentVo) {
        List<CommentDto> list=commentMapper.selectCommentByCidOrUid(commentVo);
        return list;
    }

    @Override
    public void replyComment(Comment comment) {
        int result=commentMapper.updateByPrimaryKeySelective(comment);
        if (result==0){
            throw new CodeException(ErrorEnum.UPDATE_ERROR);
        }
    }

    @Override
    public List<CommentDto> getCommentListBySid(CommentVo commentVo) {
        List<CommentDto> list=commentMapper.selectCommentBySid(commentVo);
        return list;
    }

    @Override
    public void deleteComment(Integer id) {
        int result=commentMapper.deleteByPrimaryKey(id);
        if (result==0){
            throw new CodeException(ErrorEnum.DELETE_FAIL);
        }
    }

    @Override
    public List<TradeVo> getTradeListByUid(TradeVo tradeVo) {
        List<TradeVo> list=tradeMapper.selectTradeListByUid(tradeVo);
        return list;
    }

    @Override
    public List<Map<String,Object>> getDataStatisticList(DataStatisticsVo dataStatisticsVo) {
        List<Map<String,Object>> list = new ArrayList<>();
        if(dataStatisticsVo.getCid()==null){
            return list;
        }
        if(dataStatisticsVo.getStartTime()==null){
            Date now = new Date();
            dataStatisticsVo.setStartTime(MyUtil.formStrFormat(now,"yyyy-MM-dd"));
        }
        list = orderItemMapper.selectDataStatisticList(dataStatisticsVo);
        return list;
    }

    @Override
    public int cleanCart() {
        int result=cartMapper.cleanCart();
        return result;
    }

    @Override
    public List<Comment> getCommentListNow() {
        List<Comment> list=commentMapper.selectCommentListNow();
        return list;
    }

    @Override
    @Transactional(rollbackFor = CodeException.class)
    public void calculateFoodScore() {
        Date now=new Date();
        List<Comment> list=getCommentListNow();
        if (list.size()>0 ){
            for (Comment comment:list){
                if (comment.getFoodType().equals(1)){
                    CanteenFood canteenFood=canteenFoodMapper.selectByPrimaryKey(comment.getFid());
                    BigDecimal score=(canteenFood.getScore().add(comment.getScore())).divide(new BigDecimal(2)).setScale(1);
                    canteenFood.setScore(score);
                    canteenFood.setUptime(now);
                    canteenFoodMapper.updateByPrimaryKeySelective(canteenFood);
                }else{
                    SchoolSupfood schoolSupfood=schoolSupfoodMapper.selectByPrimaryKey(comment.getFid());
                    SupplierFood supplierFood=supplierFoodMapper.selectByPrimaryKey(schoolSupfood.getFid());
                    BigDecimal score=(supplierFood.getScore().add(comment.getScore())).divide(new BigDecimal(2)).setScale(1);
                    supplierFood.setScore(score);
                    supplierFood.setUptime(now);
                    supplierFoodMapper.updateByPrimaryKeySelective(supplierFood);
                }
            }
        }
    }

//    @Override
//    public void backupData(HttpServletResponse response, DataStatisticsVo dataStatisticsVo) {
//        List<DataStatisticDto> list=orderItemMapper.selectDataStatisticList(dataStatisticsVo);
//        Canteen canteen=canteenMapper.selectByPrimaryKey(dataStatisticsVo.getCid());
//        StringBuffer fileName=new StringBuffer(canteen.getCname());
//        if (!StringUtils.isEmpty(dataStatisticsVo.getWid())){
//            Window window=windowMapper.selectByPrimaryKey(dataStatisticsVo.getWid());
//            if (!StringUtils.isEmpty(window)){
//                fileName.append(window.getWname());
//            }
//        }
//        if (!StringUtils.isEmpty(dataStatisticsVo.getStartTime())&&!StringUtils.isEmpty(dataStatisticsVo.getEndTime())){
//            String startTime=null;
//            String endTime=null;
//            try {
//                startTime=MyUtil.formStrFormat(MyUtil.formDateFormat(dataStatisticsVo.getStartTime(),"yyyy-MM-dd HH:mm:ss"),"yyyy.MM.dd");
//                endTime=MyUtil.formStrFormat(MyUtil.formDateFormat(dataStatisticsVo.getEndTime(),"yyyy-MM-dd HH:mm:ss"),"yyyy.MM.dd");
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            fileName.append("("+startTime+"-"+endTime+")");
//        }
//        if (!StringUtils.isEmpty(dataStatisticsVo.getType())){
//            if (dataStatisticsVo.getType().equals(1)){
//                fileName.append("-学生-用餐统计报表");
//            }else if (dataStatisticsVo.getType().equals(2)){
//                fileName.append("-教师-用餐统计报表");
//            }else if (dataStatisticsVo.getType().equals(3)){
//                fileName.append("-其他人员 -用餐统计报表");
//            }
//        }
//        StringBuffer sheetName=new StringBuffer();
//        if (StringUtils.isEmpty(dataStatisticsVo.getGrade())&&StringUtils.isEmpty(dataStatisticsVo.getClassNum())){
//            sheetName.append("sheet1");
//        }
//        if (!StringUtils.isEmpty(dataStatisticsVo.getGrade())) {
//            sheetName.append("高" + dataStatisticsVo.getGrade()+"年级");
//        }
//        if (StringUtils.isEmpty(dataStatisticsVo.getClassNum())){
//            sheetName.append(dataStatisticsVo.getClassNum()+"班");
//        }
//        ExcelUtil.writeExcel(response, list, fileName.toString(), sheetName.toString(), new DataStatisticDto());
//    }

    @Override
    @Transactional(rollbackFor = CodeException.class)
    public WindowOrderDto scanPayQRcode(String code, String data,String cartIds) {
        //校验支付码
        String uid=code;
//        String dnCode=null;
//        dnCode=EncryptUtil.AESDncode(code);
//        Long secondStr=Long.valueOf(dnCode.split(dnCode)[1]);
//        Long nowSecond = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
//        if (nowSecond-secondStr>60*5){
//            throw new CodeException(ErrorEnum.Pay_Out_Time);
//        }
//        uid=dnCode.split(dnCode)[0];
        //处理业务逻辑
        WindowOrderDto windowOrderDto=new WindowOrderDto();
        List<OrderItem> orderItemList=handlePayCodeOrFacePay(uid,PayType.支付码.getPayType(),data,cartIds);
        User user=userMapper.selectByPrimaryKey(uid);
        windowOrderDto.setUsername(user.getUsername());
        windowOrderDto.setUserType(user.getType());
        BigDecimal totalPrice = new BigDecimal(0);
        String oid = null;
        String cname = null;
        Integer num = 0;
        for(OrderItem orderItem: orderItemList){
            totalPrice = totalPrice.add(orderItem.getTotalPrice());
            oid = orderItem.getOid();
            cname = orderItem.getCname();
            num = num + orderItem.getNum();
        }
        windowOrderDto.setCname(cname);
        windowOrderDto.setOid(oid);
        windowOrderDto.setTotalPrice(totalPrice);
        windowOrderDto.setNum(num);
        windowOrderDto.setList(orderItemList);
        windowOrderDto.setBalance(user.getBalance());
        return windowOrderDto;
    }

    @Override
    @Transactional(rollbackFor = CodeException.class)
    public WindowOrderDto facePay(String phone, MultipartFile file, String data,String cartIds) {
        if (StringUtils.isEmpty(file)){
            throw new CodeException(ErrorEnum.LACK_REQ_PARAM);
        }
        User user=new User();
        user.setPhone(phone);
        List<User> userList=userMapper.select(user);
        if (userList.size()==0){
            throw new CodeException(ErrorEnum.Phone_Not_Register);
        }
        User user1=userList.get(0);
        if (user1.getIsFace().equals(0)){
            throw new CodeException(ErrorEnum.Not_Dredge_Face);
        }
        String imageUrl=fileStorageService.storeFile(file, ResourceType.IMAGE, ResourceBusiness.FACE);
        String url=MyUtil.getImageUrl(imageUrl);
        boolean result= FaceUtil.verifyFace(user1.getUid(),url);
        List<OrderItem> list=null;
        if (result){
            list=handlePayCodeOrFacePay(user1.getUid(),PayType.人脸支付.getPayType(),data,cartIds);
        }else{
            throw new CodeException(ErrorEnum.Face_Mapping_Fail);
        }
        WindowOrderDto windowOrderDto=new WindowOrderDto();
        windowOrderDto.setUsername(user1.getUsername());
        windowOrderDto.setUserType(user1.getType());
        BigDecimal totalPrice = new BigDecimal(0);
        String oid = null;
        String cname = null;
        Integer num = 0;
        for(OrderItem orderItem: list){
            totalPrice = totalPrice.add(orderItem.getTotalPrice());
            oid = orderItem.getOid();
            cname = orderItem.getCname();
            num = num + orderItem.getNum();
        }
        windowOrderDto.setCname(cname);
        windowOrderDto.setOid(oid);
        windowOrderDto.setTotalPrice(totalPrice);
        windowOrderDto.setNum(num);
        windowOrderDto.setList(list);
        return windowOrderDto;
    }

    @Override
    @Transactional(rollbackFor = CodeException.class)
    public WindowOrderDto facePay1(Integer sid, MultipartFile file, String data,String cartIds) {

        School school = schoolMapper.selectByPrimaryKey(sid);
        if(school.getDredgeFace()==0){
            throw new CodeException(ErrorEnum.School_No_Dredge_Face);
        }
        String imageUrl=fileStorageService.storeFile(file, ResourceType.IMAGE, ResourceBusiness.FACE);
        String url=MyUtil.getImageUrl(imageUrl);
        String uid= FaceUtil.searchFaces(String.valueOf(sid),url);
        List<OrderItem> list=null;
        if (!StringUtils.isEmpty(uid)){
            list=handlePayCodeOrFacePay(uid,PayType.人脸支付.getPayType(),data,cartIds);
        }else{
            throw new CodeException(ErrorEnum.Face_Mapping_Fail);
        }
        User user=userMapper.selectByPrimaryKey(uid);
        WindowOrderDto windowOrderDto=new WindowOrderDto();
        windowOrderDto.setUsername(user.getUsername());
        windowOrderDto.setUserType(user.getType());
        BigDecimal totalPrice = new BigDecimal(0);
        String oid = null;
        String cname = null;
        Integer num = 0;
        for(OrderItem orderItem: list){
            totalPrice = totalPrice.add(orderItem.getTotalPrice());
            oid = orderItem.getOid();
            cname = orderItem.getCname();
            num = num +orderItem.getNum();
        }
        windowOrderDto.setCname(cname);
        windowOrderDto.setOid(oid);
        windowOrderDto.setTotalPrice(totalPrice);
        windowOrderDto.setNum(num);
        windowOrderDto.setList(list);
        windowOrderDto.setBalance(user.getBalance());
        return windowOrderDto;
    }

    @Override
    public WindowDto getWindowMessage(Integer wid, Integer type) {
        WindowDto windowDto=windowMapper.selectWindowMessage(wid,type);
        return windowDto;
    }

    @Override
    public Map<String, Object> getCartIds(String uid) {
        Cart cart=new Cart();
        cart.setUid(uid);
        cart.setStatus(1);
        List<Cart> list=cartMapper.select(cart);
        List<Long> cartIds=new ArrayList<>();
        BigDecimal totalPrice=new BigDecimal(0);
        if (list.size()>0){
            for (Cart cart1:list){
                cartIds.add(cart1.getId());
                WindowFood windowFood=windowFoodMapper.selectByPrimaryKey(cart1.getWfid());
                totalPrice=totalPrice.add(windowFood.getPrice().multiply(new BigDecimal(cart1.getNum())));
            }
        }
        Map<String,Object> result=new HashMap<>();
        result.put("cartIds",cartIds);
        result.put("totalPrice",totalPrice);
        return result;
    }

    /**
     * 处理支付码支付和人脸支付业务逻辑--学生端/食堂端
     * @param uid
     * @param payType
     * @param data
     */
    private List<OrderItem> handlePayCodeOrFacePay(String uid,Integer payType,String data,String cartIds){
        Date now=new Date();
        List<OrderItem> list = (List<OrderItem>) MyUtil.verifyStrToList(data,OrderItem.class);
        String oid=MyUtil.createId("O");
        List<OrderItem> orderItemList=new ArrayList<>();
        BigDecimal totalPrice=new BigDecimal(0);
//        List<OrderItemDto> orderItemDtoList=new ArrayList<>();
        for (OrderItem orderItem:list){
            orderItem.setUid(uid);
            orderItem.setOid(oid);

                WindowFood windowFood = windowFoodMapper.selectByPrimaryKey(orderItem.getWfid());

                if (!StringUtils.isEmpty(cartIds)) {
                    //减少食堂供应量
                    if (windowFood.getResidueSupply() - orderItem.getNum() > 0) {
                        windowFood.setResidueSupply(windowFood.getResidueSupply() - orderItem.getNum());
                    } else {
                        windowFood.setResidueSupply(0);
                    }
                    windowFoodMapper.updateByPrimaryKeySelective(windowFood);
                }

                Window window = windowMapper.selectByPrimaryKey(windowFood.getWid());
                Canteen canteen = canteenMapper.selectByPrimaryKey(window.getCid());
                if (windowFood.getType().equals(1)) {
                    //食堂销量增加
                    CanteenFood canteenFood = canteenFoodMapper.selectByPrimaryKey(windowFood.getFid());
                    int monthlySales = 0;
                    if (canteenFood.getMonthlySales() == null) {
                        monthlySales = 0 + orderItem.getNum();
                    } else {
                        monthlySales = canteenFood.getMonthlySales() + orderItem.getNum();
                    }

                    int totalSales = 0;
                    if (canteenFood.getTotalSales() == null) {
                        totalSales = 0 + orderItem.getNum();
                    } else {
                        totalSales = canteenFood.getTotalSales() + orderItem.getNum();
                    }
                    canteenFood.setMonthlySales(monthlySales);
                    canteenFood.setTotalSales(totalSales);
                    canteenFoodMapper.updateByPrimaryKeySelective(canteenFood);

                    orderItem.setType(canteenFood.getType());
                    orderItem.setFoodName(canteenFood.getFoodName());
                    orderItem.setPhoto(canteenFood.getPhoto());
                } else if (windowFood.getType().equals(2)) {
                    //供应商销量增加
                    SchoolSupfood schoolSupfood = schoolSupfoodMapper.selectByPrimaryKey(windowFood.getFid());
                    SupplierFood supplierFood = supplierFoodMapper.selectByPrimaryKey(schoolSupfood.getFid());
                    int monthlySales = 0;
                    if (schoolSupfood.getMonthlySales() == null) {
                        monthlySales = 0 + orderItem.getNum();
                    } else {
                        monthlySales = schoolSupfood.getMonthlySales() + orderItem.getNum();
                    }

                    int totalSales = 0;
                    if (schoolSupfood.getTotalSales() == null) {
                        totalSales = 0 + orderItem.getNum();
                    } else {
                        totalSales = schoolSupfood.getTotalSales() + orderItem.getNum();
                    }
                    schoolSupfood.setMonthlySales(monthlySales);
                    schoolSupfood.setTotalSales(totalSales);
                    schoolSupfoodMapper.updateByPrimaryKeySelective(schoolSupfood);

                    orderItem.setType(schoolSupfood.getType());
                    orderItem.setFoodName(supplierFood.getFoodName());
                    orderItem.setPhoto(supplierFood.getPhoto());
                }
                if(canteen!=null){
                    orderItem.setCname(canteen.getCname());
                }
                orderItem.setWname(window.getWname());
                orderItem.setPrice(windowFood.getPrice());
                orderItem.setTotalPrice(orderItem.getPrice().multiply(new BigDecimal(String.valueOf(orderItem.getNum()))));
                orderItem.setSubTime(MyUtil.formStrFormat(now, "yyyy-MM-dd"));
                orderItem.setStatus(4);
                orderItem.setCrtime(now);
                orderItem.setUptime(now);
                orderItemList.add(orderItem);
                totalPrice = totalPrice.add(orderItem.getTotalPrice());
//            OrderItemDto orderItemDto=new OrderItemDto();
//            orderItemDto.setFoodName(orderItem.getFoodName());
//            orderItemDto.setNum(orderItem.getNum());
//            orderItemDto.setTotalPrice(orderItem.getTotalPrice());
//            orderItemDto.setOid(orderItem.getOid());
//            orderItemDtoList.add(orderItemDto);
        }
        //判断余额是否充足
        User user=userMapper.selectByPrimaryKey(uid);
        if (user.getBalance().compareTo(totalPrice)<0){
            throw new CodeException(ErrorEnum.Not_Sufficient_Funds);
        }
        //添加订单信息
        Order order=new Order();
        order.setOid(oid);
        order.setUid(uid);
        order.setStatus(4);
        order.setPayType(payType);
        order.setPayPrice(totalPrice);
        order.setPayTime(now);
        order.setCrtime(now);
        order.setUptime(now);
        orderMapper.insertSelective(order);
        //添加订单条目信息
        orderItemMapper.insertList(orderItemList);
        //扣减余额
        user.setBalance(user.getBalance().subtract(totalPrice));
        user.setUptime(now);
        userMapper.updateByPrimaryKeySelective(user);
        //学校账户增加余额
        School school = schoolMapper.selectByPrimaryKey(user.getSid());
        school.setBalance(school.getBalance().add(totalPrice));
        school.setUptime(new Date());
        schoolMapper.updateByPrimaryKeySelective(school);
        //添加流水
        Trade trade=new Trade();
        trade.setTid(MyUtil.createId("T"));
        trade.setUid(uid);
        trade.setOid(oid);
        trade.setTradeType(TradeType.订单.getTradeType());
        trade.setPayType(payType);
        trade.setPrice(totalPrice);
        trade.setStatus(1);
        trade.setIe(2);
        trade.setBalance(user.getBalance());
        trade.setCrtime(now);
        tradeMapper.insertSelective(trade);
        if(!StringUtils.isEmpty(cartIds)){
            deleteCart(cartIds);
        }
        return orderItemList;
    }

    /**
     * 格式化我的订单列表
     * @param list
     * @return
     */
    private static List<Map<String,Object>> orderSortByGroup(List<OrderItem> list){
        Date now=new Date();
        Map<String, List<OrderItem>> map = new LinkedHashMap<>();
        for(OrderItem orderItem : list) {
            List<OrderItem> orList = map.get(orderItem.getCname()+orderItem.getWname());
            if(orList==null){
                orList = new ArrayList<OrderItem>();
            }
            orList.add(orderItem);
            Collections.sort(orList, new Comparator<OrderItem>() {
                @Override
                public int compare(OrderItem o1, OrderItem o2) {
                    return o2.getCrtime().compareTo(o1.getCrtime());
                }
            });
            map.put(orderItem.getCname()+orderItem.getWname(), orList);
        }
        List<Map<String,Object>> resultMap=new ArrayList<>();
        for(Map.Entry<String,List<OrderItem>> entry:map.entrySet()){
            Map<String,Object> map1=new HashMap<>();
            BigDecimal sum=new BigDecimal(0);
            String subTimeStr=null;
            Date subTime=null;
            String nowStr=null;
            for (OrderItem orderItem:entry.getValue()){
                sum=sum.add(orderItem.getTotalPrice());
                subTimeStr=orderItem.getSubTime()+" 17:00:00";
                nowStr=MyUtil.formStrFormat(now,"yyyy-MM-dd");
                if(nowStr.equals(orderItem.getSubTime())){
                    //判断是否可以取餐
                    map1.put("takeEnable",1);
                }else{
                    map1.put("takeEnable",0);
                }
            }
            try {
                subTime=MyUtil.formDateFormat(subTimeStr,"yyyy-MM-dd HH:mm:ss");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar c=Calendar.getInstance();
            c.setTime(subTime);
            c.add(5,-1);
            //判断是否可取消订单
            if (now.getTime()>=c.getTime().getTime()){
                map1.put("refundEnable",0);
            }else {
                map1.put("refundEnable",1);
            }
            map1.put("sum",sum);
            map1.put("list",entry.getValue());
            map1.put("cname",entry.getKey());
            resultMap.add(map1);
        }
        return resultMap;
    }

    @Override
    public List<OrderItemDto> getCanteenOrderList(Integer sid,Integer cid,Integer wid,String subTime,Integer type,String uid){

        UserCanteen userCanteen = userCanteenMapper.selectByPrimaryKey(uid);

        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setSid(sid);
        orderItemDto.setSubTime(subTime);
        orderItemDto.setCid(cid);
        orderItemDto.setType(type);
        //orderItemDto.setUid(uid);

        if(userCanteen.getType() == 1){ //员工
            orderItemDto.setWid(userCanteen.getWid());
            orderItemDto.setCid(userCanteen.getCid());
        }else{ // 负责人
            orderItemDto.setCid(cid);
            orderItemDto.setWid(wid);
        }

        List<OrderItemDto> orderItemDtoList = orderItemMapper.selectCanteenOrderList(orderItemDto);
        return orderItemDtoList;
    }

    @Override
    public List<OrderItemDto> getSupplierOrderList(Integer sid,String uid,Integer type,String subTime){

        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setUid(uid);
        orderItemDto.setType(type);
        orderItemDto.setSid(sid);
        orderItemDto.setSubTime(subTime);

        List<OrderItemDto> orderItemDtoList = orderItemMapper.selectSupplierOrderList(orderItemDto);
        return orderItemDtoList;
    }

    @Override
    public List<CommentDto> getSupplierCommentList(CommentVo commentVo){
        List<CommentDto> list=commentMapper.selectSupplierCommentList(commentVo);
        return list;
    }

    public BigDecimal isUserSub(BigDecimal supplierTimeSub,BigDecimal price,BigDecimal subSidyPrice){
        if(supplierTimeSub.compareTo(price)>0){//该时段的补贴 大于 该时段的消费金额
            subSidyPrice = subSidyPrice.add(price);
        }else {
            subSidyPrice = supplierTimeSub;
        }
        return subSidyPrice;
    }

    @Override
    public void cleanOrderList() {
        List<OrderItem> orderItems = orderItemMapper.selectOrderItemList();
        if(orderItems.size()>0){
            Date now = new Date();
            for(OrderItem orderItem : orderItems){
                Order order = orderMapper.selectByPrimaryKey(orderItem.getOid());
                orderItem.setStatus(0);//订单关闭
                orderItem.setUptime(now);
                orderItemMapper.updateByPrimaryKeySelective(orderItem);
                order.setStatus(0);
                order.setUptime(now);
                orderMapper.updateByPrimaryKeySelective(order);
            }
        }
    }

    @Override
    public List<CommentDto> getMyCommentList(CommentVo commentVo) {
        List<CommentDto> list = commentMapper.selectMyCommentList(commentVo);
        return list;
    }

    @Override
    public List<String> getCanteenOrSupOrderUser(OrderItemDto orderItemDto,Integer scType) {
        List<OrderItemDto> list = new ArrayList<>();
        if(scType==1){//食堂
            list = orderItemMapper.selectCanteenOrderUser(orderItemDto);
        }else{//供应商
            list = orderItemMapper.selectSupplierOrderUser(orderItemDto);
        }
        List<String> userList = new ArrayList<>();
        if(list.size()>0){
            for(OrderItemDto order : list){
                String uid = order.getUid();
                User user = userMapper.selectByPrimaryKey(uid);
                userList.add(user.getUsername());
            }
        }
        return userList;
    }

    @Override
    public TLResult abcPayNotify(HttpServletRequest request, HttpServletResponse response) {

        log.info("-------------进入农行支付回调---------------");

        log.info("请求：======"+request.toString());
        log.info("回调请求request====》"+request.getParameterMap().toString());

        Boolean resultFLag = Boolean.valueOf(true);
        String MSG = request.getParameter("MSG");
        log.info("农行支付通知MSG:{}"+MSG);
        PaymentResult  result = null;
        try {
            result = new PaymentResult(MSG);
            log.info("农行支付结果为：{}"+result);
        } catch (TrxException e) {
            e.printStackTrace();
        }

        Date now = new Date();
        log.info("农行支付回调是否成功：{}"+result.isSuccess());
        if(result.isSuccess()){
            String tid = null;
                tid = result.getValue("OrderNo");
                log.info("订单tid:{}"+tid);
                Trade trade=tradeMapper.selectByPrimaryKey(tid);
                String uid=trade.getUid();
                Integer tradeType=trade.getTradeType();
                BigDecimal totalFee=new BigDecimal(MyUtil.strObject(result.getValue("Amount")));
                String cartIds=trade.getCartIds();
                Boolean useSubsidy=false;
                if (trade.getUseSubsidy().equals(1)){
                    useSubsidy=true;
                }
                log.info("useSubsidy:{}",useSubsidy);
                //数据库查询出支付金额
                Integer discountType = null;
                BigDecimal subSidyPrice = new BigDecimal(0);
                if(cartIds!=null && cartIds!=""){
                    Map<String,Object> cartMap=settleCart(uid,cartIds,useSubsidy);
                    discountType=Integer.valueOf(cartMap.get("disType").toString());
                    subSidyPrice=new BigDecimal(cartMap.get("subSidyPrice").toString());
                }
                handlerBusiness(now, tid, trade, uid, tradeType, totalFee,cartIds,useSubsidy,discountType,subSidyPrice);
        }else{
            log.info("交易失败：{}"+result.getErrorMessage());
            resultFLag = false;
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/xml;charset=utf-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String s="";
        if (resultFLag.booleanValue()){
            log.info("成功");
            s = "成功";
        }else {
            log.info("失败");
            s = "失败";
        }
        out.write(s);
        out.flush();
        out.close();

        return null;
    }

    @Override
    public List<Map<String,Object>> getMarketSaleList(DataStatisticsVo dataStatisticsVo) {
        if(dataStatisticsVo.getStartTime()==null){
            Date now = new Date();
            dataStatisticsVo.setStartTime(MyUtil.formStrFormat(now,"yyyy-MM-dd"));
        }
        List<Map<String,Object>> list=orderItemMapper.selectMarketSaleList(dataStatisticsVo);
        return list;
    }

    @Override
    public void codeRecharge(String uid,Integer sid) {
        Recharge recharge = new Recharge();
        recharge.setSid(sid);
        recharge = rechargeMapper.selectOne(recharge);
        Trade trade = new Trade();
        Date now = new Date();
        trade.setTid(MyUtil.createId("T"));
        trade.setUid(uid);
        trade.setTradeType(1);
        trade.setIe(1);
        trade.setPrice(recharge.getPrice());
        trade.setStatus(2);
        trade.setCrtime(now);
        trade.setPayType(recharge.getType());
        User user1=userMapper.selectByPrimaryKey(uid);
        //更新流水支付状态
        trade.setStatus(0);
        trade.setQrCode(1);
        //trade.setBalance(user1.getBalance().add(recharge.getPrice()));
        trade.setBalance(user1.getBalance());

        tradeMapper.insertSelective(trade);

    }

    @Override
    public void checkCodeRecharge(String tid, Integer status) {
        if(tid==null|| tid.equals("")){
            Trade trade = new Trade();
            trade.setQrCode(1);
            trade.setStatus(0);
            List<Trade> trades = tradeMapper.select(trade);
            if(trades.size()>0){
                for(Trade trade1 : trades){
                    trade1.setStatus(status);
                    trade1.setUptime(new Date());
                    if(status==1){
                        trade1.setBalance(trade1.getBalance().add(trade1.getPrice()));
                    }
                    tradeMapper.updateByPrimaryKeySelective(trade1);
                    if(status==1){
                        //钱包充值
                        User user=new User();
                        user.setUid(trade1.getUid());
                        user.setBalance(trade1.getBalance());
                        userMapper.updateByPrimaryKeySelective(user);

                        User user1 = userMapper.selectByPrimaryKey(trade1.getUid());

                        SysRecharge sysRecharge = new SysRecharge();
                        sysRecharge.setUid(trade1.getUid());
                        sysRecharge.setCrtime(new Date());
                        sysRecharge.setSid(user1.getSid());
                        sysRecharge.setPrice(trade1.getPrice());
                        sysRecharge.setIe(1);
                        sysRechargeMapper.insertSelective(sysRecharge);
                    }
                }
            }
        }else{
            Trade trade = tradeMapper.selectByPrimaryKey(tid);
            trade.setStatus(status);
            trade.setUptime(new Date());
            trade.setBalance(trade.getBalance().add(trade.getPrice()));

            if(status==1){
                //钱包充值
                User user=new User();
                user.setUid(trade.getUid());
                user.setBalance(trade.getBalance());
                userMapper.updateByPrimaryKeySelective(user);

                User user1 = userMapper.selectByPrimaryKey(trade.getUid());

                SysRecharge sysRecharge = new SysRecharge();
                sysRecharge.setUid(trade.getUid());
                sysRecharge.setCrtime(new Date());
                sysRecharge.setSid(user1.getSid());
                sysRecharge.setPrice(trade.getPrice());
                sysRecharge.setIe(1);
                sysRechargeMapper.insertSelective(sysRecharge);
            }
            tradeMapper.updateByPrimaryKeySelective(trade);
        }
    }

    @Override
    public List<Map<String, Object>> getQrTradeList(TradeVo tradeVo) {
        if(tradeVo.getMtime()==null){
            tradeVo.setMtime(MyUtil.formStrFormat(new Date(),"yyyy-MM-dd"));
        }
        List<Map<String,Object>> list = tradeMapper.selectTradeList(tradeVo);
        return list;
    }
}
