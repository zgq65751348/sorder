package com.tlongx.sorder.manager.dao;

import com.tlongx.sorder.manager.pojo.FundsStatistics;
import com.tlongx.sorder.utils.MyMapper;
import com.tlongx.sorder.vo.SchoolVo;

import java.util.List;
import java.util.Map;

public interface FundsStatisticsMapper extends MyMapper<FundsStatistics> {
    List<Map<String,Object>> selectList(SchoolVo schoolVo);
}