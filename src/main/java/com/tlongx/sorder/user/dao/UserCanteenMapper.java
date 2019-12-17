package com.tlongx.sorder.user.dao;

import com.tlongx.sorder.user.pojo.UserCanteen;
import com.tlongx.sorder.utils.MyMapper;
import com.tlongx.sorder.vo.UserCanteenVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserCanteenMapper extends MyMapper<UserCanteen> {

    Map<String,Object> selectUserCanteenInfo(UserCanteen userCanteen);

    List<UserCanteen> selectUserCanteenList(UserCanteenVo userCanteenVo);

    List<String> selectAllUid(@Param("sid") Integer sid);

}