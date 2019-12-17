package com.tlongx.sorder.manager.dao;

import com.tlongx.sorder.dto.WindowDto;
import com.tlongx.sorder.manager.pojo.Window;
import com.tlongx.sorder.utils.MyMapper;
import com.tlongx.sorder.vo.WindowVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WindowMapper extends MyMapper<Window> {


    List<Window> selectWindowList(WindowVo windowVo);

    WindowDto selectWindowMessage(@Param("wid")Integer wid,@Param("type")Integer type);
}