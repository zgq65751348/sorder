package com.tlongx.sorder.msg.dao;

import com.tlongx.sorder.dto.MsgDto;
import com.tlongx.sorder.msg.pojo.MsgLog;
import com.tlongx.sorder.utils.MyMapper;

import java.util.List;

public interface MsgLogMapper extends MyMapper<MsgLog> {

    int insertBatch(List<MsgLog> list);

    List<MsgDto> selectList(MsgLog msgLog);
}