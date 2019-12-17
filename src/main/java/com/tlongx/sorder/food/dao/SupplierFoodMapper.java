package com.tlongx.sorder.food.dao;

import com.tlongx.sorder.dto.FoodDto;
import com.tlongx.sorder.food.pojo.SupplierFood;
import com.tlongx.sorder.utils.MyMapper;
import com.tlongx.sorder.vo.FoodVo;

import java.util.List;

public interface SupplierFoodMapper extends MyMapper<SupplierFood> {

    int insertSupplierFoodBatch(List<SupplierFood> list);

    List<FoodDto> selectSupplierFoodList(FoodVo foodVo);

}