package com.tlongx.sorder.operation.service;

import com.tlongx.sorder.operation.pojo.OperationLog;

import java.util.List;

public interface OperationLogService {
    /**
     * 新增操作日志记录
     * @param operationLog
     */
    void addOperLog(OperationLog operationLog);
}
