package com.tlongx.sorder.manager.dao;

import com.tlongx.sorder.manager.pojo.Canteen;
import com.tlongx.sorder.user.pojo.UserCanteen;
import com.tlongx.sorder.utils.MyMapper;

import java.util.List;

public interface CanteenMapper extends MyMapper<Canteen> {

    int insertCanteenReturnId(Canteen canteen);

    List<UserCanteen> selectCanteenList(String sid);


}