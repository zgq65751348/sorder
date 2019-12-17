package com.tlongx.sorder.controller;

import com.tlongx.common.TLResult;
import com.tlongx.sorder.dto.*;
import com.tlongx.sorder.order.pojo.Cart;
import com.tlongx.sorder.order.pojo.Comment;
import com.tlongx.sorder.order.pojo.OrderItem;
import com.tlongx.sorder.order.pojo.Trade;
import com.tlongx.sorder.order.service.OrderService;
import com.tlongx.sorder.user.pojo.User;
import com.tlongx.sorder.user.service.UserService;
import com.tlongx.sorder.utils.MyUtil;
import com.tlongx.sorder.vo.CommentVo;
import com.tlongx.sorder.vo.TradeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.Request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author xrf
 * @Date 2019年3月1日15:11:30
 */
@RestController
@RequestMapping("order")
@Api("订单相关API")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "addCart", method = RequestMethod.POST)
    @ApiOperation(value = "【学生端】添加购物车", notes = "添加购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "uid", value = "学生uid", required = true),
            @ApiImplicitParam(paramType = "query", name = "wfid", value = "食品id", required = true),
            @ApiImplicitParam(paramType = "query", name = "subDate", value = "预定日期", required = true),
            @ApiImplicitParam(paramType = "query", name = "type", value = "供应时段类型 1早餐 2午餐 3晚餐 4水果超市 5便利小超 6特色早餐 7特色午餐 8特色晚餐", required = true),
            @ApiImplicitParam(paramType = "query", name = "num", value = "数量", required = true),
    })
    public TLResult addCart(Cart cart,Integer type) {
        orderService.addCart(cart,type);
        return TLResult.ok();
    }

    @RequestMapping(value = "getCart", method = RequestMethod.POST)
    @ApiOperation(value = "【学生端】获取购物车信息", notes = "获取购物车信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "uid", value = "学生uid", required = true),
    })
    public TLResult<CartDto> getCart(String uid) {
        List<Map<String,Object>> list=orderService.getCart(uid);
        return TLResult.ok(list);
    }

    @RequestMapping(value = "editCart", method = RequestMethod.POST)
    @ApiOperation(value = "【学生端】编辑购物车信息", notes = "编辑购物车信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "购物车id", required = true),
            @ApiImplicitParam(paramType = "query", name = "num", value = "数量 传加减后数量", required = true),
    })
    public TLResult editCart(Integer id,Integer num) {
        orderService.editCart(id,num);
        return TLResult.ok();
    }

    @RequestMapping(value = "deleteCart", method = RequestMethod.POST)
    @ApiOperation(value = "【学生端】批量删除购物车信息", notes = "批量删除购物车信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", value = "id 以逗号分隔", required = true),
    })
    public TLResult<CartDto> deleteCart(String ids) {
        orderService.deleteCart(ids);
        return TLResult.ok();
    }

    @RequestMapping(value = "settleCart", method = RequestMethod.POST)
    @ApiOperation(value = "【学生端】结算购物车", notes = "结算购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "uid", value = "uid", required = true),
            @ApiImplicitParam(paramType = "query", name = "cardIds", value = "购物车id 以逗号分隔", required = true),
            @ApiImplicitParam(paramType = "query", name = "useSubsidy", value = "是否使用补贴 默认false", required = true),
    })
    public TLResult<CartDto> settleCart(String uid,String cardIds,Boolean useSubsidy) {
        Map<String,Object> result=orderService.settleCart(uid,cardIds,useSubsidy);
        return  TLResult.ok(result);
    }

    @RequestMapping(value = "recharge", method = RequestMethod.POST)
    @ApiOperation(value = "【学生端】钱包充值", notes = "钱包充值")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "uid", value = "uid", required = true),
            @ApiImplicitParam(paramType = "query", name = "price", value = "金额", required = true),
            @ApiImplicitParam(paramType = "query", name = "type", value = "类型 1微信 2支付宝 3农行", required = true),
    })
    public TLResult recharge(String uid,String price,String type) {
        Map<String,Object> map=orderService.recharge(uid,price,type);
        return TLResult.ok(map);
    }

    @RequestMapping(value = "wxNotify", method = RequestMethod.POST)
    @ApiOperation(value = "【学生端】微信支付回调", notes = "微信支付回调")
    @ApiImplicitParams({
    })
    public TLResult wxNotify() {
        TLResult result=orderService.wxNotify(request,response);
        return result;
    }

    @RequestMapping(value = "aliPayNotify", method = RequestMethod.POST)
    @ApiOperation(value = "【学生端】支付宝支付回调", notes = "支付宝支付回调")
    @ApiImplicitParams({
    })
    public TLResult aliPayNotify() {
        TLResult result=orderService.aliPayNotify(request,response);
        return result;
    }

    @RequestMapping(value = "placeAnOrder", method = RequestMethod.POST)
    @ApiOperation(value = "【学生端】订单结算", notes = "订单结算")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "uid", value = "uid", required = true),
            @ApiImplicitParam(paramType = "query", name = "payType", value = "类型 1钱包 2微信 3支付宝 4农行", required = true),
            @ApiImplicitParam(paramType = "query", name = "cartIds", value = "购物车id 以逗号分隔", required = true),
            @ApiImplicitParam(paramType = "query", name = "useSubsidy", value = "是否使用补贴 1是 0否（默认）", required = true),
    })
    public TLResult placeAnOrder(Trade trade,String cartIds) {
        TLResult result=orderService.placeAnOrder(trade,cartIds);
        return result;
    }

    @RequestMapping(value = "getMyOrderList", method = RequestMethod.POST)
    @ApiOperation(value = "【学生端】个人订单列表", notes = "个人订单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "uid", value = "uid", required = true),
//            @ApiImplicitParam(paramType = "query", name = "type", value = "供应时段类型 1早餐 2午餐 3晚餐 4水果超市 5便利小超 6特色早餐 7特色午餐 8特色晚餐", required = true),
            @ApiImplicitParam(paramType = "query", name = "subDate", value = "预定时间 2019-03-20", required = true),
    })
    public TLResult<OrderItemDto> getMyOrderList(String uid, Integer type, String subDate) {
        List<OrderItemDto> list=orderService.getMyOrderList(uid,type,subDate);
        return TLResult.ok(list);
    }

    @RequestMapping(value = "cancelOrder", method = RequestMethod.POST)
    @ApiOperation(value = "【学生端】取消订单", notes = "取消订单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", value = "订单条目id 格式以逗号分隔", required = true),
    })
    public TLResult<OrderItem> cancelOrder(String ids) {
        orderService.cancelOrder(ids);
        return TLResult.ok();
    }

    @RequestMapping(value = "getQrOrder", method = RequestMethod.POST)
    @ApiOperation(value = "【学生端】扫码获取订单信息", notes = "扫码获取订单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", value = "订单条目id 格式以逗号分隔", required = true),
    })
    public TLResult<OrderDto> getQrOrder(String ids) {
        List<OrderDto> list=orderService.getQrOrder(ids);
        return TLResult.ok(list);
    }

    @RequestMapping(value = "takeFood", method = RequestMethod.POST)
    @ApiOperation(value = "【食堂端】取餐", notes = "取餐")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", value = "订单条目id 格式以逗号分隔", required = true),
            @ApiImplicitParam(paramType = "query", name = "wid", value = "窗口id", required = true),
            @ApiImplicitParam(paramType = "query", name = "type", value = "当前登录人员type", required = true),
    })
    public TLResult takeFood(String ids,Integer wid,Integer type) {
        List<OrderDto> list= orderService.takeFood(ids,wid,type);
        return TLResult.ok(list);
    }

    @RequestMapping(value = "getOrderItemInfo", method = RequestMethod.POST)
    @ApiOperation(value = "【学生端】获取订单条目信息", notes = "获取订单条目信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "订单条目id", required = true),
    })
    public TLResult<OrderItem> getOrderItemInfo(Integer id) {
        OrderItem orderItem=orderService.getOrderItemInfo(id);
        return TLResult.ok(orderItem);
    }

    @RequestMapping(value = "addComment", method = RequestMethod.POST)
    @ApiOperation(value = "【学生端】添加评论", notes = "添加评论")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "oid", value = "订单条目id", required = true),
            @ApiImplicitParam(paramType = "query", name = "remark", value = "评论内容", required = true),
            @ApiImplicitParam(paramType = "query", name = "photo", value = "图片路径", required = true),
            @ApiImplicitParam(paramType = "query", name = "score", value = "评分 0-5 ", required = true),
            @ApiImplicitParam(paramType = "query", name = "anomy", value = "是否匿名 0否 1是", required = true),
    })
    public TLResult addComment(Comment comment) {
        orderService.addComment(comment);
        return TLResult.ok();
    }

    @RequestMapping(value = "getCommentListByCidOrUid", method = RequestMethod.POST)
    @ApiOperation(value = "【用户端/食堂端】查看评论列表", notes = "查看评论列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "cid", value = "食堂id", required = false),
            @ApiImplicitParam(paramType = "query", name = "uid", value = "用户id", required = false),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页行数", required = true),
            @ApiImplicitParam(paramType = "query", name = "type", value = "1食堂 2供应商 3学生",required = true),
            @ApiImplicitParam(paramType = "query", name = "suid", value = "供应商uid",required = false)
    })
    public TLResult<CommentDto> getCommentListByCidOrUid(CommentVo commentVo) {
        List<CommentDto> list = null;
        if(commentVo.getType()==1){
             list=orderService.getCommentListByCidOrUid(commentVo);
        }else if(commentVo.getType()==2){
             list = orderService.getSupplierCommentList(commentVo);
        }else if(commentVo.getType()==3){
            list = orderService.getMyCommentList(commentVo);
        }
        return TLResult.okPage(list);
    }

    @RequestMapping(value = "replyComment", method = RequestMethod.POST)
    @ApiOperation(value = "【食堂端】回复评论", notes = "回复评论")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "评论id", required = true),
            @ApiImplicitParam(paramType = "query", name = "reply", value = "回复内容", required = true),
    })
    public TLResult replyComment(Comment comment) {
        orderService.replyComment(comment);
        return TLResult.ok();
    }

    @RequestMapping(value = "getCommentListBySid", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】查看评论列表", notes = "查看评论列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "sid", value = "学校id", required = true),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页行数", required = true),
            @ApiImplicitParam(paramType = "query", name = "foodName", value = "食品名称", required = false),
            @ApiImplicitParam(paramType = "query", name = "supplier", value = "供应商", required = false),
    })
    public TLResult<CommentDto> getCommentListBySid(CommentVo commentVo) {
        List<CommentDto> list=orderService.getCommentListBySid(commentVo);
        return TLResult.okPage(list);
    }

    @RequestMapping(value = "deleteComment", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】删除评论", notes = "删除评论")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "id", required = true),
    })
    public TLResult deleteComment(Integer id) {
        orderService.deleteComment(id);
        return TLResult.ok();
    }

    @RequestMapping(value = "getTradeListByUid", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校/学生端】交易记录", notes = "交易记录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "uid", value = "uid", required = true),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页行数", required = true),
    })
    public TLResult<TradeVo> getTradeListByUid(TradeVo tradeVo) {
        List<TradeVo> list=orderService.getTradeListByUid(tradeVo);
        return TLResult.okPage(list);
    }

    @RequestMapping(value = "scanPayQRcode", method = RequestMethod.POST)
    @ApiOperation(value = "【窗口端】扫支付码（窗口点餐）", notes = "扫支付码（窗口点餐）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "code", value = "二维码信息", required = true),
            @ApiImplicitParam(paramType = "query", name = "data", value = "[{\"wfid\":\"21\",\"num\":\"1\"},{\"wfid\":\"22\",\"num\":\"3\"}]", required = true),
            @ApiImplicitParam(paramType = "query", name = "cartIds", value = "购物车ids", required = false),
    })
    public TLResult<WindowOrderDto> scanPayQRcode(String code, String data, String cartIds) {
        WindowOrderDto windowOrderDto=orderService.scanPayQRcode(code,data,cartIds);
        return TLResult.ok(windowOrderDto);
    }

//    @RequestMapping(value = "facePay", method = RequestMethod.POST)
//    @ApiOperation(value = "【窗口端】人脸支付（窗口点餐）", notes = "人脸支付（窗口点餐）")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", name = "phone", value = "phone", required = true),
//            @ApiImplicitParam(paramType = "query", name = "data", value = "[{\"wfid\":\"21\",\"num\":\"1\"},{\"wfid\":\"22\",\"num\":\"3\"}]", required = true),
//    })
//    public TLResult<WindowOrderDto> facePay(String phone, String data, MultipartFile file) {
//        WindowOrderDto windowOrderDto=orderService.facePay(phone,file,data);
//        return TLResult.ok(windowOrderDto);
//    }

    @RequestMapping(value = "facePay", method = RequestMethod.POST)
    @ApiOperation(value = "【窗口端】人脸支付（窗口点餐）", notes = "人脸支付（窗口点餐）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "sid", value = "学校id", required = true),
            @ApiImplicitParam(paramType = "query", name = "data", value = "[{\"wfid\":\"21\",\"num\":\"1\"},{\"wfid\":\"22\",\"num\":\"3\"}]", required = true),
            @ApiImplicitParam(paramType = "query", name = "cartIds", value = "购物车ids", required = false),
    })
    public TLResult<WindowOrderDto> facePay(Integer sid, String data, MultipartFile file, String cartIds) {
        WindowOrderDto windowOrderDto=orderService.facePay1(sid,file,data,cartIds);
        return TLResult.ok(windowOrderDto);
    }

    @RequestMapping(value = "getWindowMessage", method = RequestMethod.POST)
    @ApiOperation(value = "【窗口端】去结算", notes = "去结算")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "wid", value = "窗口id", required = true),
            @ApiImplicitParam(paramType = "query", name = "type", value = "供应类型 1早餐 。。。", required = true),
    })
    public TLResult getWindowMessage(Integer wid,Integer type) {
        WindowDto windowDto =orderService.getWindowMessage(wid,type);
        return TLResult.ok(windowDto);
    }

    @RequestMapping(value = "getCartIds", method = RequestMethod.POST)
    @ApiOperation(value = "【学生端】获取购物车ids数据和总金额", notes = "获取购物车ids数据和总金额")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "uid", value = "uid", required = true),
    })
    public TLResult getCartIds(String uid) {
        Map<String,Object> result=orderService.getCartIds(uid);
        return TLResult.ok(result);
    }

    @RequestMapping(value = "getCanteenOrderList",method = RequestMethod.POST)
    @ApiOperation(value = "【食堂端】获取食堂订单", notes = "获取食堂订单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "uid", value = "uid", required = true),
            @ApiImplicitParam(paramType = "query", name = "sid", value = "学校Id", required = true),
            @ApiImplicitParam(paramType = "query", name = "cid", value = "食堂Id", required = true),
            @ApiImplicitParam(paramType = "query", name = "wid", value = "窗口Id", required = true),
            @ApiImplicitParam(paramType = "query", name = "subTime", value = "subTime", required = true),
            @ApiImplicitParam(paramType = "query", name = "type", value = "食物类别", required = false)
    })
    public TLResult getCanteenOrderList(Integer sid,Integer cid,Integer wid,String subTime,Integer type,String uid){
        List<OrderItemDto> list = orderService.getCanteenOrderList(sid,cid,wid,subTime,type,uid);
        return TLResult.ok(list);
    }

    @RequestMapping(value = "getSupplierOrderList", method = RequestMethod.POST)
    @ApiOperation(value = "【供应商端】获取供应商订单", notes = "获取供应商订单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name ="sid", value = "学校id", required = true),
            @ApiImplicitParam(paramType = "query", name = "uid", value = "uid", required = true),
            @ApiImplicitParam(paramType = "query", name = "subTime", value = "subTime", required = true),
            @ApiImplicitParam(paramType = "query", name = "type", value = "食物类别", required = false)
    })
    public TLResult getSupplierOrderList(Integer sid,String subTime,Integer type,String uid){
        List<OrderItemDto> list = orderService.getSupplierOrderList(sid,uid,type,subTime);
        return TLResult.ok(list);
    }

    @RequestMapping(value="getCanteenOrderDetail",method = RequestMethod.POST)
    @ApiOperation(value = "【食堂端/供应商端】获取订单详情",notes = "获取订单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "status",value = "状态 不传或null 全部 4已取餐",required = false),
            @ApiImplicitParam(paramType = "query", name = "uid", value = "uid", required = true),
            @ApiImplicitParam(paramType = "query", name = "sid", value = "学校Id", required = true),
            @ApiImplicitParam(paramType = "query", name = "cid", value = "食堂Id，食堂端传", required = false),
            @ApiImplicitParam(paramType = "query", name = "wid", value = "窗口Id ,食堂端传", required = false),
            @ApiImplicitParam(paramType = "query", name = "subTime", value = "subTime", required = true),
            @ApiImplicitParam(paramType = "query", name = "type", value = "食物类别", required = false),
            @ApiImplicitParam(paramType = "query",name="wfid",value="食品id",required = true),
            @ApiImplicitParam(paramType = "query",name="csType",value = "1 食堂  2供应商",required = true)
    })
    public TLResult getCanteenOrderDetail(OrderItemDto orderItemDto,Integer csType){
        List<String> userList = orderService.getCanteenOrSupOrderUser(orderItemDto,csType);
        return TLResult.ok(userList);
    }

    @RequestMapping(value = "abcPayNotify", method = RequestMethod.POST)
    @ApiOperation(value = "【学生端】农行支付回调", notes = "农行支付回调")
    @ApiImplicitParams({
    })
    public TLResult abcPayNotify() {
        TLResult result=orderService.abcPayNotify(request,response);
        return result;
    }

    @RequestMapping(value = "codeRecharge", method = RequestMethod.POST)
    @ApiOperation(value = "【平板端】收款码充值", notes = "收款码充值")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "uid", value = "用户id", required = true),
            @ApiImplicitParam(paramType = "query", name = "sid", value = "学校id", required = true),
    })
    public TLResult codeRecharge(String uid,Integer sid) {
        orderService.codeRecharge(uid,sid);
        return TLResult.ok();
    }

    @RequestMapping(value = "getQrTradeList", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】收款码充值审核列表", notes = "收款码充值审核列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "sid", value = "学校id", required = true),
            @ApiImplicitParam(paramType = "query", name = "isPlat", value = "传1", required = true),
            @ApiImplicitParam(paramType = "query", name = "status", value = "审核状态", required = true),
            @ApiImplicitParam(paramType = "query", name = "crtime", value = "时间", required = true),
    })
    public TLResult getQrTradeList(TradeVo tradeVo){
        List<Map<String,Object>> list = orderService.getQrTradeList(tradeVo);
        return TLResult.okPage(list);
    }

}
