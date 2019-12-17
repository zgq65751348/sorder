package com.tlongx.sorder.user.dao;

import com.tlongx.sorder.user.pojo.UserSupplier;
import com.tlongx.sorder.utils.MyMapper;

import java.util.List;
import java.util.Map;

public interface UserSupplierMapper extends MyMapper<UserSupplier> {

    Map<String,Object> selectUserSupplierInfo(UserSupplier userSupplier);

    UserSupplier selectUserSupplierBySidInfo(UserSupplier userSupplier);

}