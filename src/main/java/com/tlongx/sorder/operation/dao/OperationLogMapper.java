package com.tlongx.sorder.operation.dao;

import com.tlongx.sorder.operation.pojo.OperationLog;
import com.tlongx.sorder.utils.MyMapper;
import com.tlongx.sorder.vo.OpLogVo;

import java.util.List;

public interface OperationLogMapper extends MyMapper<OperationLog> {
    List<OperationLog> selectOperLogList(OpLogVo opLogVo);
}