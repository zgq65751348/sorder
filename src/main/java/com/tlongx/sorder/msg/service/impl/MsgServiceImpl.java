package com.tlongx.sorder.msg.service.impl;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import com.google.common.collect.Lists;
import com.tlongx.common.ErrorEnum;
import com.tlongx.common.TLResult;
import com.tlongx.common.exception.CodeException;
import com.tlongx.sorder.dto.MsgDto;
import com.tlongx.sorder.msg.dao.MsgLogMapper;
import com.tlongx.sorder.msg.dao.MsgMapper;
import com.tlongx.sorder.msg.pojo.Msg;
import com.tlongx.sorder.msg.pojo.MsgLog;
import com.tlongx.sorder.msg.service.MsgService;
import com.tlongx.sorder.user.dao.SchoolSupplierMapper;
import com.tlongx.sorder.user.dao.UserCanteenMapper;
import com.tlongx.sorder.user.dao.UserMapper;
import com.tlongx.sorder.utils.JGPushUtil;
import com.tlongx.sorder.utils.MyUtil;
import com.tlongx.sorder.utils.RemoveHTML;
import com.tlongx.sorder.vo.MsgVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MsgServiceImpl implements MsgService {

    @Autowired
    private MsgMapper msgMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserCanteenMapper userCanteenMapper;
    @Autowired
    private SchoolSupplierMapper schoolSupplierMapper;
    @Autowired
    private MsgLogMapper msgLogMapper;

    @Override
    public void sendMsg(Msg msg) {
        Date now=new Date();
        msg.setCrtime(now);
        List<String> uidList = Lists.newArrayList();
        if (msg.getPushObj().equals(1)){
            if (msg.getSid().equals(0)){
                uidList = userMapper.selectAllUid(null);
            }else {
                uidList=userMapper.selectAllUid(msg.getSid());
            }
        }
        else if (msg.getPushObj().equals(2)){
            if (msg.getSid().equals(0)) {
                uidList = userCanteenMapper.selectAllUid(null);
            }else{
                uidList = userCanteenMapper.selectAllUid(msg.getSid());
            }
        }
        else if (msg.getPushObj().equals(3)) {
            if (msg.getSid().equals(0)) {
                uidList =schoolSupplierMapper.selectAllSupUid(null);
            }else {
                uidList=schoolSupplierMapper.selectAllSupUid(msg.getSid());
            }
        }
        msgMapper.insertUseGeneratedKeys(msg);
        List<MsgLog> msgLogList=new ArrayList<>();
        for (String uid:uidList){
            MsgLog msgLog=new MsgLog();
            msgLog.setMid(msg.getMid());
            msgLog.setCrtime(now);
            msgLog.setStatus(0);
            msgLog.setUid(uid);
            msgLogList.add(msgLog);
        }
        msgLogMapper.insertBatch(msgLogList);
        msg.setAlias(msg.getPushObj().toString().split(","));
        try {
            JGPushUtil.sendPushMsg(msg);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Msg> getMsgList(MsgVo msgVo) {
        List<Msg> msgList=msgMapper.selectMsgList(msgVo);
        return msgList;
    }

    @Override
    public Msg getMsgInfo(Integer mid) {
        Msg msg=msgMapper.selectByPrimaryKey(mid);
        return msg;
    }

    @Override
    public void deleteMsg(Integer mid) {
        int result=msgMapper.deleteByPrimaryKey(mid);
        if (result==0){
            throw new CodeException(ErrorEnum.DELETE_FAIL);
        }
    }

    @Override
    public List<MsgDto> getMsgLogByUid(MsgLog msgLog) {
        List<MsgDto> msgLogList=msgLogMapper.selectList(msgLog);
        if (msgLogList.size()>0){
            for (MsgDto msgLog1:msgLogList){
                String context = msgLog1.getContext();
                context = RemoveHTML.Html2Text(context);
                msgLog1.setContext(context);
            }
        }
        return msgLogList;
    }

    @Override
    public MsgDto getMsgLogInfoByUid(Integer id) {
        MsgLog msgLog=new MsgLog();
        msgLog.setId(id);
        List<MsgDto> msgLogList=msgLogMapper.selectList(msgLog);
        MsgDto msgDto=msgLogList.get(0);
        msgDto.setContext(RemoveHTML.Html2Text(msgDto.getContext()));
        if (msgDto.getStatus().equals(0)){
            MsgLog msgLog1=new MsgLog();
            msgLog1.setId(id);
            msgLog1.setStatus(1);
            msgLogMapper.updateByPrimaryKeySelective(msgLog1);
        }
        return msgDto;
    }
}
