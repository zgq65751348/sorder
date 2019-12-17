package com.tlongx.sorder.order.dao;

import com.tlongx.sorder.order.pojo.Cart;
import com.tlongx.sorder.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CartMapper extends MyMapper<Cart> {

    List<Map<String,Object>> selectCartList(@Param("uid") String uid, @Param("ids") String[] ids);

    int cleanCart();

    void deleteCart();
}