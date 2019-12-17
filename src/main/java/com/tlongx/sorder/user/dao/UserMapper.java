package com.tlongx.sorder.user.dao;

import com.tlongx.sorder.dto.UserDto;
import com.tlongx.sorder.user.pojo.User;
import com.tlongx.sorder.utils.MyMapper;
import com.tlongx.sorder.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper extends MyMapper<User> {

    Map<String,Object> selectUserInfo(User user);

    List<UserDto> selectUserList(UserVo userVo);

    List<UserDto> selectTeacherList(UserVo userVo);

    List<String> selectAllUid(@Param("sid") Integer sid);

    int insertUserBatch(List<User> list);

    Map<String,Object> selectBalanceSum(UserVo userVo);

    List<Map<String,Object>> selectTeachAndTradeList(UserVo userVo);
}