package com.tlongx.sorder.controller;

import com.tlongx.common.TLResult;
import com.tlongx.sorder.dto.FoodDto;
import com.tlongx.sorder.food.pojo.CanteenFood;
import com.tlongx.sorder.food.pojo.WindowFood;
import com.tlongx.sorder.food.service.FoodService;
import com.tlongx.sorder.manager.pojo.School;
import com.tlongx.sorder.manager.pojo.SupplierTime;
import com.tlongx.sorder.manager.pojo.Window;
import com.tlongx.sorder.manager.service.ManagerService;
import com.tlongx.sorder.utils.MyUtil;
import com.tlongx.sorder.vo.FoodVo;
import com.tlongx.sorder.vo.SchoolSupFoodVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * @author xin.rf
 * @date 2019/2/20 9:23
 * @Description TODO
 **/
@RestController
@RequestMapping("food")
@Api("食品管理相关API")
public class FoodController {

    @Autowired
    private FoodService foodService;
    @Autowired
    private ManagerService managerService;

    @RequestMapping(value = "addCanteenFood", method = RequestMethod.POST)
    @ApiOperation(value = "【食堂端】添加食堂自营餐品", notes = "添加食堂自营餐品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "data",
                    value = "餐品信息[{\"foodName\":\"餐品名称\",\"foodRmk\":\"餐品说明\",\"cid\":\"食堂id\",\"dailySupply\":\"单日供应量\"" +
                            "\"supplyStart\":\"开始供应时间\",\"supplyEnd\":\"结束供应时间\",\"photo\":\"图片地址\"," +
                            "\"price\":\"餐品价格\",\"uid\":\"操作人\".\"supplyCount\":\"供应量\",\"type\":\"供应时段类型 1早餐...\"}]", required = true),
    })
    public TLResult addCanteenFood(String data) {
        foodService.addCanteenFood(data);
        return TLResult.ok();
    }

    @RequestMapping(value = "getWindowByCid", method = RequestMethod.POST)
    @ApiOperation(value = "【食堂端】获取下拉窗口信息", notes = "获取下拉窗口信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "cid", value = "食堂id", required = true),
    })
    public TLResult<Window> getWindowByCid(Integer cid) {
        List<Window> list=foodService.getWindowByCid(cid);
        return TLResult.ok(list);
    }

    @RequestMapping(value = "getCanteenFoodList", method = RequestMethod.POST)
    @ApiOperation(value = "【食堂端】获取食堂食品列表", notes = "获取食堂食品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页行数", required = true),
            @ApiImplicitParam(paramType = "query", name = "wid", value = "窗口id", required = true),
            @ApiImplicitParam(paramType = "query", name = "type", value = "供应时段 1早餐 2午餐 3晚餐 4水果超市 5便利小超 6特色早餐 7特色午餐 8特色晚餐", required = false),
            @ApiImplicitParam(paramType = "query", name = "foodName", value = "食品名称", required = false),
    })
    public TLResult<CanteenFood> getCanteenFoodList(FoodVo foodVo) {
        List<FoodDto> list=foodService.getCanteenFoodList(foodVo);
        return TLResult.okPage(list);
    }

    @RequestMapping(value = "putawayCanteenFoodBatch", method = RequestMethod.POST)
    @ApiOperation(value = "【食堂端】批量上架食堂食品", notes = "批量上架食堂食品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", value = "id 以逗号分隔", required = true),
    })
    public TLResult putawayCanteenFoodBatch(String ids) {
        foodService.putawayCanteenFoodBatch(ids);
        return TLResult.ok();
    }

    @RequestMapping(value = "editCanteenFoodBatch", method = RequestMethod.POST)
    @ApiOperation(value = "【食堂端】批量编辑食堂自营餐品", notes = "批量编辑食堂自营餐品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "data",
                    value = "餐品信息[{\"fid\":\"餐品id\",\"foodName\":\"餐品名称\",\"foodRmk\":\"餐品说明\",\"cid\":\"食堂id\"," +
                            "\"supplyStart\":\"开始供应时间\",\"supplyEnd\":\"结束供应时间\",\"photo\":\"图片地址\"," +
                            "\"price\":\"餐品价格\",\"uid\":\"操作人\".\"supplyCount\":\"供应量\",\"type\":\"供应时段类型 1早餐...\",\"dailySupply\":\"单日供应量\",}]", required = true),
    })
    public TLResult editCanteenFoodBatch(String data) {
        foodService.editCanteenFoodBatch(data);
        return TLResult.ok();
    }

    @RequestMapping(value = "getCanteenFoodBatch", method = RequestMethod.POST)
    @ApiOperation(value = "【食堂端】获取多个食堂食品", notes = "获取多个食堂食品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "fids", value = "fid 以逗号分隔", required = true),
    })
    public TLResult<CanteenFood> getCanteenFoodBatch(String fids) {
        List<CanteenFood> list=foodService.getCanteenFoodBatch(fids);
        return TLResult.ok(list);
    }

    @RequestMapping(value = "addSupplierFoodBatch", method = RequestMethod.POST)
    @ApiOperation(value = "【供应商端】批量添加供应商餐品", notes = "批量添加供应商餐品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "data",
                    value = "餐品信息[{\"foodName\":\"餐品名称\",\"foodRmk\":\"餐品说明\",\"photo\":\"图片\",\"price\":\"价格\",\"uid\":\"供应商uid\"}]", required = true),
    })
    public TLResult addSupplierFoodBatch(String data) {
        foodService.addSupplierFoodBatch(data);
        return TLResult.ok();
    }

    @RequestMapping(value = "getSupplierFoodList", method = RequestMethod.POST)
    @ApiOperation(value = "【供应商端】获取供应商食品列表", notes = "获取供应商食品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页行数", required = true),
            @ApiImplicitParam(paramType = "query", name = "uid", value = "供应商id", required = true),
    })
    public TLResult<FoodDto> getSupplierFoodList(FoodVo foodVo) {
        List<FoodDto> foodDtoList=foodService.getSupplierFoodList(foodVo);
        return TLResult.okPage(foodDtoList);
    }

    @RequestMapping(value = "getSupplierFoodBatch", method = RequestMethod.POST)
    @ApiOperation(value = "【供应商端】获取多个供应商食品", notes = "获取多个供应商食品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "fids", value = "fid 以逗号分隔", required = true),
    })
    public TLResult<FoodDto> getSupplierFoodBatch(String fids) {
        List<FoodDto> list=foodService.getSupplierFoodBatch(fids);
        return TLResult.ok(list);
    }

    @RequestMapping(value = "editSupplierFoodBatch", method = RequestMethod.POST)
    @ApiOperation(value = "【供应商端】批量编辑供应商餐品", notes = "批量编辑供应商餐品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "data",
                    value = "餐品信息[{\"fid\":\"餐品id\",\"foodName\":\"餐品名称\",\"foodRmk\":\"餐品说明\",\"photo\":\"图片地址\"," +
                            "\"price\":\"餐品价格\"}]", required = true),
    })
    public TLResult editSupplierFoodBatch(String data) {
        foodService.editSupplierFoodBatch(data);
        return TLResult.ok();
    }

    @RequestMapping(value = "deleteSupplierFoodBatch", method = RequestMethod.POST)
    @ApiOperation(value = "【供应商端】删除多个供应商食品", notes = "删除多个供应商食品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "fids", value = "fid 以逗号分隔", required = true),
    })
    public TLResult deleteSupplierFoodBatch(String fids) {
        foodService.deleteSupplierFoodBatch(fids);
        return TLResult.ok();
    }

    @RequestMapping(value = "getSupplierSchoolByUid", method = RequestMethod.POST)
    @ApiOperation(value = "【供应商端】获取供应商所供应的学校", notes = "获取供应商所供应的学校")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "uid", value = "供应商uid", required = true),
    })
    public TLResult<School> getSupplierSchoolByUid(String uid) {
        List<School> list=foodService.getSupplierSchoolByUid(uid);
        return TLResult.ok(list);
    }

    @RequestMapping(value = "getFoodCategoryList", method = RequestMethod.POST)
    @ApiOperation(value = "【供应商端】获取食品时段下拉框", notes = "获取食品时段下拉框")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "sid", value = "sid", required = true),
    })
    public TLResult<SupplierTime> getFoodCategoryList(String sid) {
        List<SupplierTime> supplierTime=managerService.getSupplierTimeList(sid);
        return TLResult.ok(supplierTime);
    }

    @RequestMapping(value = "addSchoolSupfood", method = RequestMethod.POST)
    @ApiOperation(value = "【供应商端】添加学校供应食品", notes = "添加学校供应食品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "data", value = "格式：{\"foodList\":[{\"supplyStart\":\"2019-02-21\",\"supplyEnd\":\"2019-02-21\",\"type\":\"1\",\"sid\":1," +
                    "\"fidNumList\":[{\"price\":10.0,\"fid\":28,\"supplyCount\":10,\"dailySupply\":10},{\"price\":12.0,\"fid\":29,\"supplyCount\":10,\"dailySupply\":12}]}],\"uid\":\"a1b208cfa9f649c4a23484b0007f9c38\"}", required = true),
    })
    public TLResult addSchoolSupfood(String data) {
        foodService.addSchoolSupfood(data);
        return TLResult.ok();
    }

    @RequestMapping(value = "getSchoolSupFoodList", method = RequestMethod.POST)
    @ApiOperation(value = "【供应商/食堂端】获取学校供应食品列表", notes = "获取学校供应食品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页行数", required = true),
            @ApiImplicitParam(paramType = "query", name = "uid", value = "供应商id（供应商端必传）", required = false),
            @ApiImplicitParam(paramType = "query", name = "sid", value = "学校id", required = true),
            @ApiImplicitParam(paramType = "query", name = "wid", value = "窗口id", required = false),
            @ApiImplicitParam(paramType = "query", name = "startTime", value = "开始时间", required = true),
            @ApiImplicitParam(paramType = "query", name = "endTime", value = "结束时间", required = true),
            @ApiImplicitParam(paramType = "query", name = "type", value = "供应时段类型 1早餐 2午餐 3晚餐 4水果超市 5便利小超 6特色早餐 7特色午餐 8特色晚餐", required = false),
    })
    public TLResult<FoodDto> getSchoolSupFoodList(FoodVo foodVo) {
        List<FoodDto> list=foodService.getSchoolSupFoodList(foodVo);
        return TLResult.okPage(list);
    }

    @RequestMapping(value = "upOrDownSchoolSupFood", method = RequestMethod.POST)
    @ApiOperation(value = "【供应商端】批量上下架学校供应食品", notes = "批量上下架学校供应食品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "ids", value = "id，以逗号分隔", required = true),
            @ApiImplicitParam(paramType = "query", name = "status", value = "0下架 1上架", required = true),
    })
    public TLResult upOrDownSchoolSupFood(String ids,Integer status) {
        foodService.upOrDownSchoolSupFood(ids,status);
        return TLResult.ok();
    }

    @RequestMapping(value = "getSchoolSupFoodInfo", method = RequestMethod.POST)
    @ApiOperation(value = "【供应商端】获取学校供应食品列表", notes = "获取学校供应食品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "id", required = true),
    })
    public TLResult<FoodDto> getSchoolSupFoodInfo(Integer id) {
        FoodDto foodDto=foodService.getSchoolSupFoodInfo(id);
        return TLResult.ok(foodDto);
    }

    @RequestMapping(value = "editSchoolSupFood", method = RequestMethod.POST)
    @ApiOperation(value = "【供应商端】编辑学校供应食品", notes = "编辑学校供应食品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "id", required = true),
            @ApiImplicitParam(paramType = "query", name = "fid", value = "食品id", required = true),
            @ApiImplicitParam(paramType = "query", name = "photo", value = "食品图片", required = false),
            @ApiImplicitParam(paramType = "query", name = "foodName", value = "食品名称", required = false),
            @ApiImplicitParam(paramType = "query", name = "foodRmk", value = "食品说明", required = false),
            @ApiImplicitParam(paramType = "query", name = "supplierCount", value = "供应数量", required = false),
            @ApiImplicitParam(paramType = "query", name = "supplyStart", value = "开始供应时间", required = false),
            @ApiImplicitParam(paramType = "query", name = "supplyEnd", value = "结束供应时间", required = false),
            @ApiImplicitParam(paramType = "query", name = "price", value = "价格", required = false),
    })
    public TLResult editSchoolSupFood(SchoolSupFoodVo schoolSupFoodVo) {
        foodService.editSchoolSupFood(schoolSupFoodVo);
        return TLResult.ok();
    }

    @RequestMapping(value = "getWindowSupFoodList", method = RequestMethod.POST)
    @ApiOperation(value = "【食堂端】获取窗口供应食品列表", notes = "获取窗口供应食品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "wid", value = "窗口id", required = true),
            @ApiImplicitParam(paramType = "query", name = "sortPrice", value = "价格排序 asc正序 desc倒序", required = false),
            @ApiImplicitParam(paramType = "query", name = "sortScore", value = "评价排序 asc正序 desc倒序", required = false),
            @ApiImplicitParam(paramType = "query", name = "type", value = "供应时段（ 1早餐 2午餐 3晚餐 4水果超市 5便利小超 6特色早餐 7特色午餐 8特色晚餐）", required = false),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页行数", required = true),
            @ApiImplicitParam(paramType = "query", name = "uid", value = "供应商id（查看商家详情传）", required = false),
            @ApiImplicitParam(paramType = "query", name = "foodName", value = "食品名称", required = false),
    })
    public TLResult<FoodDto> getWindowSupFoodList(FoodVo foodVo) {
        List<FoodDto> list=foodService.getWindowSupFoodList(foodVo);
        return TLResult.okPage(list);
    }

    @RequestMapping(value = "getWindowSupFoodInfo", method = RequestMethod.POST)
    @ApiOperation(value = "【食堂端】获取窗口供应食品信息", notes = "获取窗口供应食品信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "窗口食品id", required = true),
    })
    public TLResult<FoodDto> getWindowSupFoodInfo(Integer id) {
        FoodDto foodDto=foodService.getWindowSupFoodInfo(id);
        return TLResult.ok(foodDto);
    }


    @RequestMapping(value ="getWindowList",method = RequestMethod.POST)
    @ApiOperation(value = "【APP端】获取食堂窗口列表", notes = "获取食堂窗口列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "cid", value = "食堂id", required = true),
    })
    public TLResult<Window> getWindowList(Integer cid) {
        List<Window> list=foodService.getWindowList(cid);
        return TLResult.ok(list);
    }


    @RequestMapping(value = "editWindowSupFood", method = RequestMethod.POST)
    @ApiOperation(value = "【食堂端】编辑窗口供应食品信息", notes = "编辑窗口供应食品信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "id", required = true),
            @ApiImplicitParam(paramType = "query", name = "price", value = "供应价格", required = true),
    })
    public TLResult editWindowSupFood(WindowFood windowFood) {
        foodService.editWindowSupFood(windowFood);
        return TLResult.ok();
    }

    @RequestMapping(value ="getWindowFoodList",method = RequestMethod.POST)
    @ApiOperation(value = "【食堂/窗口端】获取窗口食品列表", notes = "获取窗口食品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "wid", value = "窗口id", required = false),
            @ApiImplicitParam(paramType = "query",name = "startTime", value = "开始供应时间", required = false),
            @ApiImplicitParam(paramType = "query",name = "endTime", value = "结束供应时间", required = false),
            @ApiImplicitParam(paramType = "query",name = "type", value = "供应时段类型 1早餐..", required = false),
            @ApiImplicitParam(paramType = "query",name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(paramType = "query",name = "pageSize", value = "每页行数", required = true),
    })
    public TLResult<FoodDto> getWindowFoodList(FoodVo foodVo) {
        List<FoodDto> list=foodService.getWindowFoodList(foodVo);
        return TLResult.okPage(list);
    }

    @RequestMapping(value ="downWindowFood",method = RequestMethod.POST)
    @ApiOperation(value = "【食堂端】下架/编辑窗口食品", notes = "下架/编辑窗下架窗口食品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "id", value = "id", required = true),
            @ApiImplicitParam(paramType = "query",name = "status", value = "0下架", required = false),
            @ApiImplicitParam(paramType = "query",name = "price", value = "供应价格", required = false),
    })
    public TLResult downWindowFood(WindowFood windowFood) {
        foodService.downWindowFood(windowFood);
        return TLResult.ok();
    }

    @RequestMapping(value ="getWindowFoodInfo",method = RequestMethod.POST)
    @ApiOperation(value = "【食堂端】获取窗口食品信息", notes = "获取窗口食品信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "id", value = "id", required = true),
            @ApiImplicitParam(paramType = "query",name = "type", value = "1食堂自营 2供应商", required = true),
    })
    public TLResult<FoodDto> getWindowFoodInfo(Integer id,Integer type) {
        FoodDto foodDto=foodService.getWindowFoodInfo(id,type);
        return TLResult.ok(foodDto);
    }


    @RequestMapping(value ="getWindowFoodListByStu",method = RequestMethod.POST)
    @ApiOperation(value = "【学生端】获取窗口食品列表", notes = "获取窗口食品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "wid", value = "窗口id", required = false),
            @ApiImplicitParam(paramType = "query",name = "startTime", value = "开始供应时间", required = true),
            @ApiImplicitParam(paramType = "query",name = "endTime", value = "结束供应时间", required = true),
            @ApiImplicitParam(paramType = "query",name = "type", value = "供应时段类型 1早餐..", required = true),
            @ApiImplicitParam(paramType = "query",name = "uid", value = "uid", required = true),
            @ApiImplicitParam(paramType = "query",name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(paramType = "query",name = "pageSize", value = "每页行数", required = true),
    })
    public TLResult<FoodDto> getWindowFoodListByStu(FoodVo foodVo) {
        List<FoodDto> list=foodService.getWindowFoodListByStu(foodVo);
        return TLResult.okPage(list);
    }


    @RequestMapping(value ="addMarketProduct",method = RequestMethod.POST)
    @ApiOperation(value = "【窗口端】批量添加超市商品", notes = "批量添加超市商品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "sid", value = "学校id", required = true),
            @ApiImplicitParam(paramType = "query",name = "data", value = "商品信息[{\"foodName\":\"商品名称\",\"barCode\":\"条形码\",\"price\":\"价格\"}]", required = true),
            @ApiImplicitParam(paramType = "query", name = "uid", value ="管理员uid", required = false)
    })
    public TLResult addMarketProduct(Integer sid,String data){
        foodService.addMarketProduct(sid, data);
        return TLResult.ok();
    }

    @RequestMapping(value ="getMarketProductList",method = RequestMethod.POST)
    @ApiOperation(value = "【后台/窗口端】获取超市商品列表", notes = "获取超市商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "sid", value = "学校id", required = true),
            @ApiImplicitParam(paramType = "query",name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(paramType = "query",name = "pageSize", value = "每页行数", required = true),
    })
    public TLResult<CanteenFood> getMarketProductList(FoodVo foodVo){
        List<CanteenFood> list=foodService.getMarketProduct(foodVo);
        return TLResult.okPage(list);
    }

    @RequestMapping(value ="getMarketProduct",method = RequestMethod.POST)
    @ApiOperation(value = "【窗口端】获取超市商品信息", notes = "获取超市商品信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "sid", value = "学校id", required = true),
            @ApiImplicitParam(paramType = "query",name = "barCode", value = "条形码", required = true),
    })
    public TLResult<FoodDto> getMarketProduct(Integer sid,String barCode){
        FoodDto canteenFood=foodService.getMarketProduct(sid,barCode);
        return TLResult.ok(canteenFood);
    }

    @RequestMapping(value ="deleteCanteenFood",method = RequestMethod.POST)
    @ApiOperation(value = "【窗口端】删除食堂商品", notes = "删除食堂商品")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "fid", value = "商品id", required = true),
    })
    public TLResult<CanteenFood> deleteCanteenFood(Integer fid){
        foodService.deleteCanteenFood(fid);
        return TLResult.ok();
    }

    @RequestMapping(value ="editMarketProduct",method = RequestMethod.POST)
    @ApiOperation(value = "【窗口端】编辑超市商品信息", notes = "编辑超市商品信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "data", value = "商品信息[{\"fid\":\"15\",\"foodName\":\"商品名称\",\"price\":\"价格\"}]", required = true),
    })
    public TLResult<FoodDto> editMarketProduct(String data){
        foodService.editMarketProduct(data);
        return TLResult.ok();
    }

    @RequestMapping(value ="getFoodTimeWeek",method = RequestMethod.POST)
    @ApiOperation(value = "【APP端】获取可选日期列表", notes = "获取可选日期列表")
    public TLResult getFoodTimeWeek(){
        Map<String, Object> foodTimeWeek = foodService.getFoodTimeWeek();
        return TLResult.ok(foodTimeWeek);
    }

    @RequestMapping(value ="getWindowSubFoodBatch",method = RequestMethod.POST)
    @ApiOperation(value = "【食堂端】批量获取窗口供应信息", notes = "批量获取窗口供应信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "ids", value = "id 以逗号分隔", required = true),
    })
    public TLResult<FoodDto> getWindowSubFoodBatch(String ids){
        List<FoodDto> windowSubFoodBatch = foodService.getWindowSubFoodBatch(ids);
        return TLResult.ok(windowSubFoodBatch);
    }

    @RequestMapping(value ="editWindowSupFoodBatch",method = RequestMethod.POST)
    @ApiOperation(value = "【食堂端】批量编辑窗口供应食品信息", notes = "批量编辑窗口供应食品信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "data", value = "商品信息[{\"id\":\"15\",\"price\":\"价格\"}]", required = true),
    })
    public TLResult<FoodDto> editWindowSupFoodBatch(String data){
        foodService.editWindowSupFoodBatch(data);
        return TLResult.ok();
    }






}
