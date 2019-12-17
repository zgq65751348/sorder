package com.tlongx.sorder.msg.dao;

import com.tlongx.sorder.msg.pojo.Msg;
import com.tlongx.sorder.utils.MyMapper;
import com.tlongx.sorder.vo.MsgVo;

import java.util.List;

public interface MsgMapper extends MyMapper<Msg> {

    List<Msg> selectMsgList(MsgVo msgVo);
}