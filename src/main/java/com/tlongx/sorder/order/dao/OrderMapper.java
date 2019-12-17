package com.tlongx.sorder.order.dao;

import com.tlongx.sorder.order.pojo.Order;
import com.tlongx.sorder.utils.MyMapper;
import com.tlongx.sorder.vo.EatingInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderMapper extends MyMapper<Order> {

    List<Map<String,Object>> selectStuEatingInfo(EatingInfoVo eatingInfoVo);

    int cleanOrder();


}