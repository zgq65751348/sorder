package com.tlongx.sorder.msg.service;

import com.tlongx.sorder.dto.MsgDto;
import com.tlongx.sorder.msg.pojo.Msg;
import com.tlongx.sorder.msg.pojo.MsgLog;
import com.tlongx.sorder.vo.MsgVo;

import java.util.List;

public interface MsgService {

    /**
     * 发送推送消息
     * @param msg
     * @return
     */
    void sendMsg(Msg msg);


    /**
     * 获取消息推送列表
     * @param msgVo
     * @return
     */
    List<Msg> getMsgList(MsgVo msgVo);

    /**
     * 获取消息推送详细
     * @param mid
     * @return
     */
    Msg getMsgInfo(Integer mid);

    /**
     * 删除推送消息
     * @param mid
     * @return
     */
    void deleteMsg(Integer mid);

    /**
     * 获取系统消息列表
     * @param msgLog
     * @return
     */
    List<MsgDto> getMsgLogByUid(MsgLog msgLog);

    /**
     * 获取系统消息信息
     * @param id
     * @return
     */
    MsgDto getMsgLogInfoByUid(Integer id);
}
