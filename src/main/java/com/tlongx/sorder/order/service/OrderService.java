package com.tlongx.sorder.order.service;

import com.tlongx.common.TLResult;
import com.tlongx.sorder.dto.*;
import com.tlongx.sorder.order.pojo.Cart;
import com.tlongx.sorder.order.pojo.Comment;
import com.tlongx.sorder.order.pojo.OrderItem;
import com.tlongx.sorder.order.pojo.Trade;
import com.tlongx.sorder.user.pojo.User;
import com.tlongx.sorder.vo.CommentVo;
import com.tlongx.sorder.vo.DataStatisticsVo;
import com.tlongx.sorder.vo.TradeVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface OrderService {

    /**
     * 添加购物车
     * @param cart
     * @return
     */
    void addCart(Cart cart,Integer type);

    /**
     * 获取购物车信息
     * @param uid
     * @return
     */
    List<Map<String,Object>> getCart(String uid);

    /**
     * 添加/减少购物车数量
     * @param id
     * @param num
     * @return
     */
    void editCart(Integer id,Integer num);

    /**
     * 批量删除购物车信息
     * @param ids
     * @return
     */
    void deleteCart(String ids);

    /**
     * 结算购物车信息
     * @param uid
     * @param cardIds
     * @param useSubsidy
     * @return
     */
    Map<String,Object> settleCart(String uid, String cardIds,Boolean useSubsidy);

    /**
     * 钱包充值
     * @param uid
     * @param price
     * @param type
     * @return
     */
    Map<String,Object> recharge(String uid,String price,String type);

    /**
     * 微信支付回调
     * @return
     */
    TLResult wxNotify(HttpServletRequest request, HttpServletResponse response);

    /**
     * 支付宝支付回调
     * @return
     */
    TLResult aliPayNotify(HttpServletRequest request, HttpServletResponse response);

    /**
     * 支付订单
     * @param trade
     * @param cardIds
     * @return
     */
    TLResult placeAnOrder(Trade trade, String cardIds);

    /**
     * 获取个人订单列表
     * @param uid
     * @param type
     * @param subDate
     * @return
     */
    List<OrderItemDto> getMyOrderList(String uid,Integer type,String subDate);

    /**
     * 取消订单
     * @param ids
     * @return
     */
    void cancelOrder(String ids);

    /**
     * 获取二维码订单数据
     * @param ids
     * @return
     */
    List<OrderDto> getQrOrder(String ids);

    /**
     * 食堂端取餐
     * @param ids
     * @return
     */
    List<OrderDto> takeFood(String ids,Integer wid,Integer type);

    /**
     * 获取订单条目信息
     * @param id
     * @return
     */
    OrderItem getOrderItemInfo(Integer id);

    /**
     *添加评论
     * @param comment
     * @return
     */
    void addComment(Comment comment);

    /**
     * 食堂端/用户端获取评论列表
     * @param commentVo
     * @return
     */
    List<CommentDto> getCommentListByCidOrUid(CommentVo commentVo);

    /**
     * 回复评论
     * @param comment
     * @return
     */
    void replyComment(Comment comment);

    /**
     * 后台获取评论列表
     * @param commentVo
     * @return
     */
    List<CommentDto> getCommentListBySid(CommentVo commentVo);

    /**
     * 删除评论
     * @param id
     * @return
     */
    void deleteComment(Integer id);

    /**
     * 获取交易记录信息
     * @param tradeVo
     * @return
     */
    List<TradeVo> getTradeListByUid(TradeVo tradeVo);

    /**
     * 获取食堂日销量统计列表
     * @param dataStatisticsVo
     * @return
     */
    List<Map<String,Object>> getDataStatisticList(DataStatisticsVo dataStatisticsVo);

    /**
     * 清空购物车
     * @return
     */
    int cleanCart();

    /**
     * 获取今天所有评论
     * @return
     */
    List<Comment> getCommentListNow();

    /**
     * 计算食品评分
     */
    void calculateFoodScore();

    /**
     * 导出/备份用餐统计数据
     * @param response
     * @param dataStatisticsVo
     */
    //void backupData(HttpServletResponse response,DataStatisticsVo dataStatisticsVo);

    /**
     * 扫支付码支付（窗口点餐）
     * @param code
     * @param data
     */
    WindowOrderDto scanPayQRcode(String code, String data, String cartIds);

    /**
     * 人脸支付 需手机号
     * @param phone
     * @param file
     * @param data
     */
    WindowOrderDto facePay(String phone,MultipartFile file,String data, String cartIds);

    /**
     * 人脸支付
     * @param sid
     * @param file
     * @param data
     */
    WindowOrderDto facePay1(Integer sid,MultipartFile file,String data, String cartIds);


    /**
     * 获取窗口信息
     * @param wid
     * @param type
     * @return
     */
    WindowDto getWindowMessage(Integer wid,Integer type);

    /**
     * 获取购物车id数据和总金额
     * @param uid
     * @return
     */
    Map<String,Object> getCartIds(String uid);

    /**
     *h获取食堂订单列表
     * @param sid
     * @param cid
     * @param wid
     * @param subTime
     * @param type
     * @param uid
     * @return
     */
    List<OrderItemDto> getCanteenOrderList(Integer sid,Integer cid,Integer wid,String subTime,Integer type,String uid);

    /**
     * 获取供应商订单列表
     * @param sid
     * @param uid
     * @param type
     * @param subTime
     * @return
     */
    List<OrderItemDto> getSupplierOrderList(Integer sid,String uid,Integer type,String subTime);

    /**
     * 食堂端查看供应商的评论列表
     * @param commentVo
     * @return
     */
    List<CommentDto> getSupplierCommentList(CommentVo commentVo);

    /**
     * 清除当天未取餐或未结算的订单列表
     * @return
     */
    void cleanOrderList();

    /**
     * 查看我的订单列表
     * @param commentVo
     * @return
     */
    List<CommentDto> getMyCommentList(CommentVo commentVo);

    /**
     * 获取食堂/供应商订单 全部/未取餐的用户信息
     * @param orderItemDto
     * @return
     */
    List<String> getCanteenOrSupOrderUser(OrderItemDto orderItemDto,Integer csType);

    /**
     * 农行支付回调
     * @return
     */
    TLResult abcPayNotify(HttpServletRequest request, HttpServletResponse response);

    /**
     * 超市日销量统计
     * @return
     */
    List<Map<String,Object>> getMarketSaleList(DataStatisticsVo dataStatisticsVo);

    /**
     * 收款码充值
     * @param uid
     */
    void codeRecharge(String uid,Integer sid);

    /**
     * 审核充值
     * @param tid
     */
    void checkCodeRecharge(String tid,Integer status);

    /**
     * 获取收款码充值审核列表
     * @param tradeVo
     * @return
     */
    List<Map<String,Object>> getQrTradeList(TradeVo tradeVo);
}
