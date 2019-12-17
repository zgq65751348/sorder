package com.tlongx.sorder.food.dao;

import com.tlongx.sorder.food.pojo.CanteenFood;
import com.tlongx.sorder.utils.MyMapper;
import com.tlongx.sorder.vo.FoodVo;

import java.util.List;

public interface CanteenFoodMapper extends MyMapper<CanteenFood> {

    int insertCanteenFood(List<CanteenFood> list);

    List<CanteenFood> selectCanteenFoodByFids(String[] fids);

    List<CanteenFood> selectCanteenFoodList(FoodVo foodVo);

    int updateCanteenFoodMonthlySales();
}