package com.tlongx.sorder.controller;

import com.tlongx.common.TLResult;
import com.tlongx.sorder.dto.*;
import com.tlongx.sorder.manager.pojo.*;
import com.tlongx.sorder.manager.service.ManagerService;
import com.tlongx.sorder.msg.pojo.Msg;
import com.tlongx.sorder.msg.pojo.MsgLog;
import com.tlongx.sorder.msg.service.MsgService;
import com.tlongx.sorder.order.service.OrderService;
import com.tlongx.sorder.setting.pojo.SysSetting;
import com.tlongx.sorder.setting.service.SettingService;
import com.tlongx.sorder.user.pojo.User;
import com.tlongx.sorder.user.pojo.UserCanteen;
import com.tlongx.sorder.user.pojo.UserSupplier;
import com.tlongx.sorder.utils.MyUtil;
import com.tlongx.sorder.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;


/**
 * @author xin.rf
 * @date 2019/2/12 16:58
 * @Description TODO
 **/
@RestController
@RequestMapping("msg")
@Api("推送消息相关API")
public class MsgController {

    @Autowired
    private MsgService msgService;

    @RequestMapping(value ="sendMsg",method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校/平台】推送消息", notes = "推送消息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "pushPlatform", value = "推送平台( 0:ALL 1:IOS  2:Android )", required = true),
            @ApiImplicitParam(paramType = "query",name = "title", value = "标题", required = true),
            @ApiImplicitParam(paramType = "query",name = "context", value = "内容", required = true),
            @ApiImplicitParam(paramType = "query",name = "pushObj", value = "消息推送对象 1学生端 2食堂 3 供应商", required = true),
            @ApiImplicitParam(paramType = "query",name = "sid", value = "学校id 0为大平台推送", required = true),
    })


    public TLResult sendMsg(Msg msg) {
        msgService.sendMsg(msg);
        return TLResult.ok();
    }

    @RequestMapping(value ="getMsgList",method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校/平台】获取推送消息列表", notes = "获取推送消息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "sid", value = "学校id 平台传0", required = true),
            @ApiImplicitParam(paramType = "query",name = "startTime", value = "开始时间", required = false),
            @ApiImplicitParam(paramType = "query",name = "endTime", value = "结束时间", required = false),
            @ApiImplicitParam(paramType = "query",name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(paramType = "query",name = "pageSize", value = "每页行数", required = true),
    })
    public TLResult<Msg> getMsgList(MsgVo msgVo) {
        List<Msg> list=msgService.getMsgList(msgVo);
        return TLResult.okPage(list);
    }

    @RequestMapping(value ="getMsgInfo",method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校/平台】获取推送消息信息", notes = "获取推送消息信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "mid", value = "mid", required = true),
    })
    public TLResult<Msg> getMsgInfo(Integer mid) {
        Msg msg=msgService.getMsgInfo(mid);
        return TLResult.ok(msg);
    }

//    @RequestMapping(value ="deleteMsg",method = RequestMethod.POST)
//    @ApiOperation(value = "【后台-学校/平台】删除推送消息信息", notes = "删除推送消息信息")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query",name = "mid", value = "mid", required = true),
//    })
//    public TLResult deleteMsg(Integer mid) {
//        msgService.deleteMsg(mid);
//        return TLResult.ok();
//    }

    @RequestMapping(value ="getMsgLogByUid",method = RequestMethod.POST)
    @ApiOperation(value = "【学生端】获取系统消息列表", notes = "获取系统消息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "uid", value = "用户uid", required = true),
    })
    public TLResult<MsgDto> getMsgLogByUid(MsgLog msgLog) {
        List<MsgDto> list=msgService.getMsgLogByUid(msgLog);
        return TLResult.ok(list);
    }

    @RequestMapping(value ="getMsgLogInfoByUid",method = RequestMethod.POST)
    @ApiOperation(value = "【学生端】获取系统消息详情", notes = "获取系统消息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "id", value = "消息id", required = true),
    })
    public TLResult<MsgDto> getMsgLogInfoByUid(Integer id) {
        MsgDto msgDto=msgService.getMsgLogInfoByUid(id);
        return TLResult.ok(msgDto);
    }

}
