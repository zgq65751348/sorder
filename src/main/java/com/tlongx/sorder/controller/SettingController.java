package com.tlongx.sorder.controller;

import com.tlongx.common.TLResult;
import com.tlongx.sorder.dto.*;
import com.tlongx.sorder.manager.pojo.*;
import com.tlongx.sorder.manager.service.ManagerService;
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
@RequestMapping("setting")
@Api("系统设定相关API")
public class SettingController {

    @Autowired
    private SettingService settingService;


    @RequestMapping(value = "upSetting", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-平台】编辑系统设定", notes = "编辑系统设定")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "aboutUs", value = "关于我们", required = false),
            @ApiImplicitParam(paramType = "query", name = "useHelp", value = "使用帮助", required = false),
            @ApiImplicitParam(paramType = "query", name = "withdrawDay", value = "供应/食堂 提现 缓冲期", required = false),
    })
    public TLResult upSetting(SysSetting sysSetting) {
        settingService.editSetting(sysSetting);
        return TLResult.ok();
    }

    @RequestMapping(value = "getSetting", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-平台】获取系统设定", notes = "获取系统设定")
    public TLResult<SysSetting> getSetting() {
        List<SysSetting> sysSettingList = settingService.getSetting();
        return TLResult.ok(sysSettingList);
    }


    @RequestMapping(value = "upSchoolSetting", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-学校】编辑学校系统设定", notes = "编辑学校系统设定")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "sid", value = "学校di", required = true),
            @ApiImplicitParam(paramType = "query", name = "aboutUs", value = "关于我们", required = false),
            @ApiImplicitParam(paramType = "query", name = "useHelp", value = "使用帮助", required = false),
    })
    public TLResult upSchoolSetting(School school) {
        settingService.editSchoolSetting(school);
        return TLResult.ok();
    }

    @RequestMapping(value = "getSchoolSetting", method = RequestMethod.POST)
    @ApiOperation(value = "【后台-平台】获取系统设定", notes = "获取系统设定")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "sid", value = "学校di", required = true),
    })
    public TLResult<School> getSchoolSetting(Integer sid) {
        School school=settingService.getSchoolSetting(sid);
        return TLResult.ok(school);
    }


}
