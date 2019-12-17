package com.tlongx.sorder.operation.service.impl;

import com.tlongx.sorder.operation.dao.OperationLogMapper;
import com.tlongx.sorder.operation.pojo.OperationLog;
import com.tlongx.sorder.operation.service.OperationLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OperationLogServiceImpl implements OperationLogService {

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Override
    public void addOperLog(OperationLog operationLog) {
        operationLogMapper.insertSelective(operationLog);
    }
}
