package com.tlongx.sorder.dto;

import com.tlongx.sorder.order.pojo.OrderItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 窗口端结算订单dto
 */
@Data
public class WindowOrderDto {

    private String username;

    private Integer userType;

    private List<OrderItem> list;

    private BigDecimal totalPrice;

    private String oid;

    private String cname;

    private Integer num;

    private BigDecimal balance;
}
