package com.tlongx.sorder.food.dao;

import com.tlongx.sorder.dto.FoodBackDto;
import com.tlongx.sorder.dto.FoodDto;
import com.tlongx.sorder.food.pojo.WindowFood;
import com.tlongx.sorder.utils.MyMapper;
import com.tlongx.sorder.vo.FoodVo;

import java.util.List;

public interface WindowFoodMapper extends MyMapper<WindowFood> {

    List<FoodDto> selectCanteenFoodList(FoodVo foodVo);

    List<FoodDto> selectSupFoodList(FoodVo foodVo);

    FoodDto selectSupFoodInfo(WindowFood windowFood);

    FoodDto selectCanteenFoodInfo(WindowFood windowFood);

    List<FoodDto> selectWindowFoodList(FoodVo foodVo);

    List<FoodDto> selectWindowFoodListByStu(FoodVo foodVo);

    List<FoodBackDto> selectFoodListBack(FoodVo foodVo);

    int updateWindowFoodInfo(WindowFood windowFood);
}