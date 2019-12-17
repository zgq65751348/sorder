package com.tlongx.sorder.order.dao;

import com.tlongx.sorder.dto.SubsidyHistoryDto;
import com.tlongx.sorder.dto.TradeDto;
import com.tlongx.sorder.order.pojo.Trade;
import com.tlongx.sorder.utils.MyMapper;
import com.tlongx.sorder.vo.SchoolVo;
import com.tlongx.sorder.vo.TradeVo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface TradeMapper extends MyMapper<Trade> {

    List<TradeVo> selectTradeListByUid(TradeVo tradeVo);

    int insertBatch(List<Trade> list);

    List<SubsidyHistoryDto> selectSubsidyHistoryList(TradeVo tradeVo);

    List<TradeDto> selectPayOrderList(TradeDto tradeDto);

    Trade selectOneTradeByOid(String oid);

    Map<String,Object> selectTradeBySidAndTime(TradeVo tradeVo);

    List<Map<String,Object>> selectSchoolLedger(SchoolVo schoolVo);

    Map<String,Object> selectSchoolBalanceOnPlat(Integer sid);

    Map<String,Object> selectStudentWalletTrade(TradeVo tradeVo);

    List<Map<String,Object>> selectSchoolSaleList(SchoolVo schoolVo);

    Map<String,Object> selectPlatBalance();

    List<Map<String,Object>> selectPlatLedger(SchoolVo schoolVo);

    BigDecimal selectRefundPrice(TradeVo tradeVo);

    List<Map<String,Object>> selectTradeList(TradeVo tradeVo);
}