package com.tlongx.sorder.food.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tlongx.common.ErrorEnum;
import com.tlongx.common.TLResult;
import com.tlongx.common.exception.CodeException;
import com.tlongx.sorder.dto.FoodDto;
import com.tlongx.sorder.food.dao.CanteenFoodMapper;
import com.tlongx.sorder.food.dao.SchoolSupfoodMapper;
import com.tlongx.sorder.food.dao.SupplierFoodMapper;
import com.tlongx.sorder.food.dao.WindowFoodMapper;
import com.tlongx.sorder.food.pojo.CanteenFood;
import com.tlongx.sorder.food.pojo.SchoolSupfood;
import com.tlongx.sorder.food.pojo.SupplierFood;
import com.tlongx.sorder.food.pojo.WindowFood;
import com.tlongx.sorder.food.service.FoodService;
import com.tlongx.sorder.manager.dao.CanteenMapper;
import com.tlongx.sorder.manager.dao.WindowMapper;
import com.tlongx.sorder.manager.pojo.Canteen;
import com.tlongx.sorder.manager.pojo.School;
import com.tlongx.sorder.manager.pojo.Window;
import com.tlongx.sorder.order.dao.CartMapper;
import com.tlongx.sorder.order.pojo.Cart;
import com.tlongx.sorder.user.dao.SchoolSupplierMapper;
import com.tlongx.sorder.user.pojo.SchoolSupplier;
import com.tlongx.sorder.utils.MyUtil;
import com.tlongx.sorder.vo.FoodVo;
import com.tlongx.sorder.vo.SchoolSupFoodVo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 * @author xin.rf
 * @date 2019/2/20 9:29
 * @Description TODO
 **/
@Service
@Slf4j
public class FoodServiceImpl implements FoodService {

    @Autowired
    private CanteenFoodMapper canteenFoodMapper;
    @Autowired
    private WindowMapper windowMapper;
    @Autowired
    private WindowFoodMapper windowFoodMapper;
    @Autowired
    private SupplierFoodMapper supplierFoodMapper;
    @Autowired
    private SchoolSupplierMapper schoolSupplierMapper;
    @Autowired
    private SchoolSupfoodMapper schoolSupfoodMapper;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private CanteenMapper canteenMapper;

    @Override
    @Transactional(rollbackFor = CodeException.class)
    public void addCanteenFood(String data) {
        Date now=new Date();
        List<CanteenFood> list =(List<CanteenFood>) MyUtil.verifyStrToList(data,CanteenFood.class);
        if (list.size() > 0) {
            //查找该食堂所有窗口
            Integer cid = list.get(0).getCid();
            Canteen canteen=canteenMapper.selectByPrimaryKey(cid);
            Integer sid=canteen.getSid();
            Window window = new Window();
            window.setType(1);
            window.setCid(cid);
            List<Window> windowList = windowMapper.select(window);
            for (CanteenFood canteenFood : list) {
                canteenFood.setMonthlySales(0);
                canteenFood.setTotalSales(0);
                canteenFood.setCrtime(now);
                canteenFood.setSid(sid);
                canteenFood.setStatus(1);
                canteenFoodMapper.insertUseGeneratedKeys(canteenFood);
                log.info(canteenFood.getFid() + "");
                for (Window window1 : windowList) {
                    WindowFood windowFood = new WindowFood();
                    windowFood.setStatus(0);
                    windowFood.setType(1);
                    windowFood.setCrtime(now);
                    windowFood.setUptime(now);
                    windowFood.setFid(canteenFood.getFid());
                    windowFood.setWid(window1.getWid());
                    windowFood.setDailySupply(canteenFood.getDailySupply());
                    windowFood.setResidueSupply(canteenFood.getDailySupply());
                    windowFood.setPrice(canteenFood.getPrice());
                    windowFoodMapper.insertSelective(windowFood);
                }
            }
        } else {
            throw new CodeException(ErrorEnum.LACK_REQ_PARAM);
        }
    }

    @Override
    public List<Window> getWindowByCid(Integer cid) {
        Window window = new Window();
        window.setCid(cid);
        window.setType(1);
        List<Window> list = windowMapper.select(window);
        return list;
    }

    @Override
    public List<FoodDto> getCanteenFoodList(FoodVo foodVo) {
        List<FoodDto> foodDtoList = windowFoodMapper.selectCanteenFoodList(foodVo);
        return foodDtoList;
    }

    @Override
    public void editCanteenFoodBatch(String data) {
        List<CanteenFood> list = (List<CanteenFood>) MyUtil.verifyStrToList(data,CanteenFood.class);
        for (CanteenFood canteenFood : list) {
            canteenFood.setUptime(new Date());
            canteenFoodMapper.updateByPrimaryKeySelective(canteenFood);
            //同步更新窗口食品信息
            WindowFood windowFood = new WindowFood();
            windowFood.setFid(canteenFood.getFid());
            windowFood.setPrice(canteenFood.getPrice());
            windowFood.setType(1);
            windowFood.setDailySupply(canteenFood.getDailySupply());
            windowFood.setUptime(new Date());
            windowFoodMapper.updateWindowFoodInfo(windowFood);
        }
    }

    @Override
    public void putawayCanteenFoodBatch(String ids) {
        String[] str=MyUtil.verifyArrayFormat(ids);
        FoodVo foodVo = new FoodVo();
        foodVo.setIds(str);
        List<FoodDto> foodDtoList = windowFoodMapper.selectCanteenFoodList(foodVo);
        Date now = new Date();
        String dateStr = null;
        try {
            for (FoodDto foodDto : foodDtoList) {
                dateStr = foodDto.getSupplyEnd() + " 23:59:59";
                if (now.getTime() > MyUtil.formDateFormat(dateStr, "yyyy-MM-dd HH:mm:ss").getTime()) {
                    throw new CodeException(ErrorEnum.SUPPLIER_TIME_OVERDUE);
                }
            }
        } catch (ParseException e) {
            throw new CodeException(ErrorEnum.FORMAT_CONVERT_FAIL);
        }
        WindowFood windowFood = new WindowFood();
        windowFood.setStatus(1);
        windowFood.setUptime(new Date());
        for (int i = 0; i < str.length; i++) {
            windowFood.setId(Integer.valueOf(str[i]));
            windowFoodMapper.updateByPrimaryKeySelective(windowFood);
        }
    }

    @Override
    public List<CanteenFood> getCanteenFoodBatch(String fids) {
        String[] str = MyUtil.verifyArrayFormat(fids);
        List<CanteenFood> list = canteenFoodMapper.selectCanteenFoodByFids(str);
        return list;
    }

    @Override
    public void addSupplierFoodBatch(String data) {
        List<SupplierFood> list = (List<SupplierFood>) MyUtil.verifyStrToList(data,SupplierFood.class);
        for (SupplierFood supplierFood:list){
            supplierFood.setCrtime(new Date());
        }
        supplierFoodMapper.insertSupplierFoodBatch(list);
    }

    @Override
    public List<FoodDto> getSupplierFoodList(FoodVo foodVo) {
        foodVo.setStatus(1);
        List<FoodDto> foodDtoList=supplierFoodMapper.selectSupplierFoodList(foodVo);
        return foodDtoList;
    }



    @Override
    public List<FoodDto> getSupplierFoodBatch(String fids) {
        String[] str=MyUtil.verifyArrayFormat(fids);
        FoodVo foodVo=new FoodVo();
        foodVo.setFids(str);
        foodVo.setStatus(1);
        List<FoodDto> foodDtoList=supplierFoodMapper.selectSupplierFoodList(foodVo);
        return foodDtoList;
    }

    @Override
    public void editSupplierFoodBatch(String data) {
        List<SupplierFood> list = null;
        try {
            list = JSONObject.parseArray(data, SupplierFood.class);
        } catch (Exception ex) {
            throw new CodeException(ErrorEnum.FORMAT_CONVERT_FAIL);
        }
        if (list.size() == 0) {
            throw new CodeException(ErrorEnum.FORMAT_CONVERT_FAIL);
        }
        for (SupplierFood supplierFood : list) {
            supplierFood.setUptime(new Date());
            supplierFoodMapper.updateByPrimaryKeySelective(supplierFood);
            //同步更新窗口食品信息
            WindowFood windowFood = new WindowFood();
            windowFood.setFid(supplierFood.getFid());
            windowFood.setPrice(supplierFood.getPrice());
            windowFood.setType(2);
            windowFood.setUptime(new Date());
            windowFoodMapper.updateWindowFoodInfo(windowFood);
        }
    }

    @Override
    public void deleteSupplierFoodBatch(String fids) {
        String[] str=MyUtil.verifyArrayFormat(fids);
        SupplierFood supplierFood=new SupplierFood();
        supplierFood.setStatus(0);
        for (int i=0;i<str.length;i++){
            supplierFood.setFid(Integer.valueOf(str[i]));
            supplierFoodMapper.updateByPrimaryKeySelective(supplierFood);
        }
    }

    @Override
    public List<School> getSupplierSchoolByUid(String uid) {
        SchoolSupplier schoolSupplier=new SchoolSupplier();
        schoolSupplier.setUid(uid);
        schoolSupplier.setStatus(1);
        List<School> schoolList=schoolSupplierMapper.selectSchoolBySup(schoolSupplier);
        return schoolList;
    }

    @Override
    @Transactional(rollbackFor = CodeException.class)
    public void addSchoolSupfood(String data) {
        if (StringUtils.isEmpty(data)){
            throw new CodeException(ErrorEnum.LACK_REQ_PARAM);
        }
        Map<String,Object> map=null;
        try {
            map=MyUtil.jsonToMap(data);
        }catch (Exception ex){
            throw new CodeException(ErrorEnum.FORMAT_CONVERT_FAIL);
        }
        if (StringUtils.isEmpty(map)){
            throw new CodeException(ErrorEnum.LACK_REQ_PARAM);
        }
        List foodList;
        try {
            foodList =MyUtil.toList(JSONArray.parseArray(MyUtil.strObject(JSONArray.toJSON(map.get("foodList")))));
        }catch (Exception ex){
            throw new CodeException(ErrorEnum.FORMAT_CONVERT_FAIL);
        }
        if (foodList.size()==0){
            throw new CodeException(ErrorEnum.LACK_REQ_PARAM);
        }
        Date now=new Date();
        for (Object object:foodList){
            Map map1 = (Map) object;
            Integer sid = Integer.valueOf(MyUtil.strObject(map1.get("sid")));
            Integer type = Integer.valueOf(MyUtil.strObject(map1.get("type")));
            String supplyStart=MyUtil.strObject(map1.get("supplyStart"));
            String supplyEnd=MyUtil.strObject(map1.get("supplyEnd"));
            List fidNumList = MyUtil.toList(JSONArray.parseArray(MyUtil.strObject(JSONArray.toJSON(map1.get("fidNumList")))));
            List<Map<String,Object>> schoolSupfoodList=  fidNumList;
            SchoolSupfood schoolSupfood=null;
            for (Map<String,Object> map2:schoolSupfoodList){
                schoolSupfood=new SchoolSupfood();
                schoolSupfood.setSid(sid);
                schoolSupfood.setType(type);
                schoolSupfood.setSupplyStart(supplyStart);
                schoolSupfood.setSupplyEnd(supplyEnd);
                schoolSupfood.setCrtime(now);
                schoolSupfood.setFid(Integer.valueOf(map2.get("fid").toString()));
                schoolSupfood.setSupplierCount(Integer.valueOf(map2.get("supplyCount").toString()));
                schoolSupfood.setPrice(new BigDecimal(map2.get("price").toString()));
                schoolSupfood.setDailySupply(Integer.valueOf(map2.get("dailySupply").toString()));
                schoolSupfoodMapper.insertSelective(schoolSupfood);
            }
        }
    }

    @Override
    public List<FoodDto> getSchoolSupFoodList(FoodVo foodVo) {
        List<FoodDto> foodDtoList=schoolSupfoodMapper.selectSchoolSupFood(foodVo);
        return foodDtoList;
    }

    @Override
    @Transactional(rollbackFor = CodeException.class)
    public void upOrDownSchoolSupFood(String ids,Integer status) {
        String[] str=MyUtil.verifyArrayFormat(ids);
        SchoolSupfood schoolSupfood=new SchoolSupfood();
        Date now=new Date();
        Integer id=null;
        for (int i=0;i<str.length;i++){
            id=Integer.valueOf(str[i]);
            schoolSupfood.setId(id);
            schoolSupfood.setStatus(status);
            schoolSupfood.setUptime(now);

            schoolSupfoodMapper.updateByPrimaryKeySelective(schoolSupfood);

            List<Window> list=schoolSupfoodMapper.selectWidBySupId(id);
            SchoolSupfood schoolSupfood1=schoolSupfoodMapper.selectByPrimaryKey(id);

            if (status.equals(1)){
                for (Window window:list){
                    WindowFood windowFood=new WindowFood();
                    windowFood.setCrtime(now);
                    windowFood.setFid(id);
                    windowFood.setType(2);
                    windowFood.setStatus(0);
                    windowFood.setPrice(schoolSupfood1.getPrice());
                    windowFood.setDailySupply(schoolSupfood1.getDailySupply());
                    windowFood.setResidueSupply(schoolSupfood1.getDailySupply());
                    //普通窗口
                    if (!schoolSupfood1.getType().equals(4)&&!schoolSupfood1.getType().equals(5)){
                        if (window.getType().equals(1)){
                            windowFood.setWid(window.getWid());
                            windowFoodMapper.insertSelective(windowFood);
                        }
                    }
                    //水果超市
                    if (schoolSupfood1.getType().equals(4)&&window.getType().equals(2)){
                        windowFood.setWid(window.getWid());
                        windowFoodMapper.insertSelective(windowFood);
                    }
                    //便利小超
                    if (schoolSupfood1.getType().equals(5)&&window.getType().equals(3)){
                        windowFood.setWid(window.getWid());
                        windowFoodMapper.insertSelective(windowFood);
                    }
                }
            }else {
                for (Window window:list) {
                    if (window.getType().equals(1)) {
                        WindowFood windowFood=new WindowFood();
                        windowFood.setFid(id);
                        windowFood.setType(2);
                        windowFood.setWid(window.getWid());
                        windowFoodMapper.delete(windowFood);
                    }
                }
            }
        }
    }





    @Override
    public FoodDto getSchoolSupFoodInfo(Integer id) {
        FoodVo foodVo=new FoodVo();
        foodVo.setId(id);
        List<FoodDto> foodDtoList=schoolSupfoodMapper.selectSchoolSupFood(foodVo);
        FoodDto foodDto=foodDtoList.get(0);
        return foodDto;
    }

    @Override
    @Transactional(rollbackFor = CodeException.class)
    public void editSchoolSupFood(SchoolSupFoodVo schoolSupFoodVo) {
        SupplierFood supplierFood=new SupplierFood();
        supplierFood.setFid(schoolSupFoodVo.getFid());
        supplierFood.setPhoto(schoolSupFoodVo.getPhoto());
        supplierFood.setFoodName(schoolSupFoodVo.getFoodName());
        supplierFood.setFoodRmk(schoolSupFoodVo.getFoodRmk());
        supplierFood.setPrice(new BigDecimal(schoolSupFoodVo.getPrice()));
        supplierFood.setUptime(new Date());
        supplierFoodMapper.updateByPrimaryKeySelective(supplierFood);
        SchoolSupfood schoolSupfood=new SchoolSupfood();
        schoolSupfood.setId(schoolSupFoodVo.getId());
        schoolSupfood.setSupplierCount(schoolSupFoodVo.getSupplierCount());
        schoolSupfood.setSupplyStart(schoolSupFoodVo.getSupplyStart());
        schoolSupfood.setSupplyEnd(schoolSupFoodVo.getSupplyEnd());
        schoolSupfood.setPrice(new BigDecimal(schoolSupFoodVo.getPrice()));
        schoolSupfood.setUptime(new Date());
        schoolSupfoodMapper.updateByPrimaryKeySelective(schoolSupfood);
    }

    @Override
    public List<FoodDto> getWindowSupFoodList(FoodVo foodVo) {
        List<FoodDto> list=windowFoodMapper.selectSupFoodList(foodVo);
        return list;
    }

    @Override
    public FoodDto getWindowSupFoodInfo(Integer id) {
        WindowFood windowFood=new WindowFood();
        windowFood.setId(id);
        FoodDto foodDto=windowFoodMapper.selectSupFoodInfo(windowFood);
        return foodDto;
    }

    @Override
    public void editWindowSupFood(WindowFood windowFood) {
        windowFood.setUptime(new Date());
        int result=windowFoodMapper.updateByPrimaryKeySelective(windowFood);
        if (result==0){
            throw new CodeException(ErrorEnum.UPDATE_ERROR);
        }
    }

    @Override
    public List<Window> getWindowList(Integer cid) {
        Window window=new Window();
        window.setCid(cid);
        List<Window> list=windowMapper.select(window);
        return list;
    }

    @Override
    public List<FoodDto> getWindowFoodList(FoodVo foodVo) {
        List<FoodDto> list=windowFoodMapper.selectWindowFoodList(foodVo);
        return list;
    }

    @Override
    public void downWindowFood(WindowFood windowFood) {
        windowFood.setUptime(new Date());
        int result=windowFoodMapper.updateByPrimaryKeySelective(windowFood);
        if (result==0){
            throw new CodeException(ErrorEnum.UPDATE_ERROR);
        }
    }

    @Override
    public FoodDto getWindowFoodInfo(Integer id, Integer type) {
        FoodDto foodDto=null;
        WindowFood windowFood=new WindowFood();
        windowFood.setId(id);
        if (type.equals(1)){
            foodDto=windowFoodMapper.selectCanteenFoodInfo(windowFood);
        }else {
            foodDto=windowFoodMapper.selectSupFoodInfo(windowFood);
        }
        return foodDto;
    }

    @Override
    public List<FoodDto> getWindowFoodListByStu(FoodVo foodVo) {
        List<FoodDto> list=windowFoodMapper.selectWindowFoodListByStu(foodVo);
        int i=0;
        if (list.size()>0){
            for (FoodDto foodDto:list){
                if (foodDto.getResidueSupply()>0){
                    if (foodDto.getCardId()!=null){
                        if(foodDto.getCartNum()>foodDto.getResidueSupply()){
                            Cart cart=cartMapper.selectByPrimaryKey(foodDto.getCardId());
                            cart.setNum(foodDto.getResidueSupply());
                            cartMapper.updateByPrimaryKeySelective(cart);
                            i++;
                        }
                    }
                }else{
                    cartMapper.deleteByPrimaryKey(foodDto.getCardId());
                    i++;
                }
            }
        }
        if (i>0){
            list=windowFoodMapper.selectWindowFoodListByStu(foodVo);
        }
        return list;
    }

    @Override
    public void recoverDailySupply() {
        WindowFood windowFood=new WindowFood();
        windowFood.setStatus(1);
        List<WindowFood> list=windowFoodMapper.select(windowFood);
        if (list.size()>0){
            for (WindowFood windowFood1:list){
                windowFood1.setResidueSupply(windowFood1.getDailySupply());
                windowFoodMapper.updateByPrimaryKeySelective(windowFood1);
            }
        }
    }

    @Override
    public void addMarketProduct(Integer sid, String data) {
        List<CanteenFood> list= (List<CanteenFood>) MyUtil.verifyStrToList(data,CanteenFood.class);
        Date now=new Date();
        Window window=new Window();
        window.setSid(sid);
        window.setType(3);
        List<Window> windowList=windowMapper.select(window);
        for(CanteenFood canteenFood:list){
            CanteenFood canteenFood1=new CanteenFood();
            canteenFood1.setSid(sid);
            canteenFood1.setBarCode(canteenFood.getBarCode());
            List<CanteenFood> canteenFoods=canteenFoodMapper.select(canteenFood1);
            if (canteenFoods.size()>0){
                throw new CodeException(ErrorEnum.Goods_Is_Exit);
            }
            canteenFood.setSid(sid);
            canteenFood.setCrtime(now);
            canteenFood.setStatus(1);
            canteenFood.setType(5);
            canteenFoodMapper.insertUseGeneratedKeys(canteenFood);
            for (Window window1 : windowList) {
                WindowFood windowFood = new WindowFood();
                windowFood.setStatus(1);
                windowFood.setType(1);
                windowFood.setCrtime(now);
                windowFood.setPrice(canteenFood.getPrice());
                windowFood.setFid(canteenFood.getFid());
                windowFood.setWid(window1.getWid());
                windowFoodMapper.insertSelective(windowFood);
            }
        }
    }

    @Override
    public List<CanteenFood> getMarketProduct(FoodVo foodVo) {
        foodVo.setType(String.valueOf(5));
        List<CanteenFood> list=canteenFoodMapper.selectCanteenFoodList(foodVo);
        return list;
    }

    @Override
    public FoodDto getMarketProduct(Integer sid,String barCode) {
        CanteenFood canteenFood=new CanteenFood();
        canteenFood.setSid(sid);
        canteenFood.setBarCode(barCode);
        List<CanteenFood> list=canteenFoodMapper.select(canteenFood);
        if (list.size()==0){
            throw new CodeException(ErrorEnum.Product_Has_Not_Input);
        }
        CanteenFood canteenFood1=list.get(0);
        WindowFood windowFood=new WindowFood();
        windowFood.setFid(canteenFood1.getFid());
        windowFood.setType(1);
        List<WindowFood> windowFoodList=windowFoodMapper.select(windowFood);
        if (windowFoodList.size()>0){
            FoodDto foodDto=new FoodDto();
            foodDto.setId(String.valueOf(windowFoodList.get(0).getId()));
            foodDto.setFoodName(canteenFood1.getFoodName());
            foodDto.setPrice(canteenFood1.getPrice().toString());
            foodDto.setBarCode(canteenFood1.getBarCode());
            return foodDto;
        }else {
            return null;
        }
    }

    @Override
    public void deleteCanteenFood(Integer fid) {
        int result=canteenFoodMapper.deleteByPrimaryKey(fid);
        if (result==0){
            throw new CodeException(ErrorEnum.DELETE_FAIL);
        }
        WindowFood windowFood=new WindowFood();
        windowFood.setFid(fid);
        int result1=windowFoodMapper.delete(windowFood);
        if (result1==0){
            throw new CodeException(ErrorEnum.DELETE_FAIL);
        }
    }

    @Override
    public void editMarketProduct(String data) {
        List<CanteenFood> list= (List<CanteenFood>) MyUtil.verifyStrToList(data,CanteenFood.class);
        Date now=new Date();
        for (CanteenFood canteenFood:list){
            canteenFood.setUptime(now);
            canteenFoodMapper.updateByPrimaryKeySelective(canteenFood);
            //同步更新窗口食品信息
            WindowFood windowFood = new WindowFood();
            windowFood.setFid(canteenFood.getFid());
            windowFood.setPrice(canteenFood.getPrice());
            windowFood.setType(1);
            windowFood.setDailySupply(canteenFood.getDailySupply());
            windowFood.setUptime(new Date());
            windowFoodMapper.updateWindowFoodInfo(windowFood);
        }
    }

    @Override
    public Map<String, Object> getFoodTimeWeek() {
        Map map = Maps.newHashMap();
        MyUtil.formStrFormat(new Date(), "yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int week = cal.get(7) - 1;
        List list = Lists.newArrayList();
        List nextList = Lists.newArrayList();

        int i = week;
        for (i = week; i < 8; ++i) {
            if (i != week) {
                Map mapData1 = Maps.newHashMap();
                if (i == week + 1) {
                    String dataString = MyUtil.formStrFormat(cal.getTime(), "yyyy/MM/dd");
                    mapData1.put("week", "明天");
                    mapData1.put("date", dataString);
                    list.add(mapData1);
                } else {
                    list.add(MyUtil.getMap(i, cal.getTime()));
                }
            }
            cal.add(5, 1);
            cal.setTime(cal.getTime());
        }
        map.put("本周", list);

        cal.add(5, -1);
        cal.setTime(cal.getTime());
        for (int j = 1; j < 8; ++j) {
            cal.add(5, 1);
            cal.setTime(cal.getTime());
            nextList.add(MyUtil.getMap(j, cal.getTime()));
        }
        map.put("下周", nextList);
        return map;
    }

    @Override
    public List<FoodDto> getWindowSubFoodBatch(String ids) {
        FoodVo foodVo=new FoodVo();
        foodVo.setIds(ids.split(","));
        List<FoodDto> foodDtoList = windowFoodMapper.selectSupFoodList(foodVo);
        return foodDtoList;
    }

    @Override
    public void editWindowSupFoodBatch(String data) {
        List<WindowFood> list= (List<WindowFood>) MyUtil.verifyStrToList(data,WindowFood.class);
        Date now=new Date();
        for (WindowFood windowFood:list){
            WindowFood windowFood1=windowFoodMapper.selectByPrimaryKey(windowFood.getId());
            SchoolSupfood schoolSupfood=schoolSupfoodMapper.selectByPrimaryKey(windowFood1.getFid());
            if (windowFood.getPrice().compareTo(schoolSupfood.getPrice())<=0){
                throw new CodeException(ErrorEnum.SellPrice_Less_Than_SupPrice);
            }
            windowFood.setUptime(now);
            windowFoodMapper.updateByPrimaryKeySelective(windowFood);
        }
    }

    @Override
    public void cleanFoodMonthlySales(){
        canteenFoodMapper.updateCanteenFoodMonthlySales();
        schoolSupfoodMapper.updateSupfoodMonthlySales();
    }

}
