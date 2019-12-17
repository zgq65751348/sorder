package com.tlongx.sorder.food.dao;

import com.tlongx.sorder.dto.FoodDto;
import com.tlongx.sorder.food.pojo.SchoolSupfood;
import com.tlongx.sorder.manager.pojo.Window;
import com.tlongx.sorder.utils.MyMapper;
import com.tlongx.sorder.vo.FoodVo;

import java.util.List;

public interface SchoolSupfoodMapper extends MyMapper<SchoolSupfood> {

    int insertSchoolSupfood(List<SchoolSupfood> list);

    List<FoodDto> selectSchoolSupFood(FoodVo foodVo);

    /**
     * 通过学校供应食品id获取学校所有窗口信息
     * @param id
     * @return
     */
    List<Window> selectWidBySupId(Integer id);

    //月销量归零
    int updateSupfoodMonthlySales();
}