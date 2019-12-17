package com.tlongx.sorder.order.dao;

import com.tlongx.sorder.dto.DataStatisticDto;
import com.tlongx.sorder.dto.OrderDto;
import com.tlongx.sorder.dto.OrderItemDto;
import com.tlongx.sorder.order.pojo.OrderItem;
import com.tlongx.sorder.utils.MyMapper;
import com.tlongx.sorder.vo.DataStatisticsVo;
import com.tlongx.sorder.vo.OrderVo;
import org.apache.ibatis.annotations.Param;
import org.aspectj.weaver.ast.Or;

import java.util.List;
import java.util.Map;

public interface OrderItemMapper extends MyMapper<OrderItem> {

    List<OrderItemDto> selectList(OrderItem orderItem);

    List<OrderDto> selectListByIds(List<String> list);

    OrderItemDto selectOIForComment(@Param("id") Integer id);

    //食堂就餐日销量
    List<Map<String,Object>> selectDataStatisticList(DataStatisticsVo dataStatisticsVo);

    /**
     * 查询已补贴订单数量
     * @param orderVo
     * @return
     */
    int selectHasSubsidyOrderCount(OrderVo orderVo);

    List<OrderItemDto> selectCanteenOrderList(OrderItemDto orderItemDto);

    List<OrderItemDto> selectSupplierOrderList(OrderItemDto orderItemDto);

    List<OrderItem> selectOrderItemList();

    List<OrderItemDto> selectCanteenOrderUser(OrderItemDto orderItemDto);

    List<OrderItemDto> selectSupplierOrderUser(OrderItemDto orderItemDto);

    //超市日销量
    List<Map<String,Object>> selectMarketSaleList(DataStatisticsVo dataStatisticsVo);
}