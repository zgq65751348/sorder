package com.tlongx.sorder.food.service;

import com.tlongx.common.TLResult;
import com.tlongx.common.TLResult;
import com.tlongx.sorder.dto.FoodDto;
import com.tlongx.sorder.food.pojo.CanteenFood;
import com.tlongx.sorder.food.pojo.WindowFood;
import com.tlongx.sorder.manager.pojo.School;
import com.tlongx.sorder.manager.pojo.Window;
import com.tlongx.sorder.vo.FoodVo;
import com.tlongx.sorder.vo.SchoolSupFoodVo;
import io.swagger.models.auth.In;

import java.util.List;
import java.util.Map;

/**
 * @author xin.rf
 * @date 2019/2/20 9:28
 * @Description TODO
 **/
public interface FoodService {

    /**
     * 食堂批量添加自营食品
     * @param data
     * @return
     */
    void addCanteenFood(String data);

    /**
     * 获取食堂窗口下拉框
     * @param cid
     * @return
     */
    List<Window> getWindowByCid(Integer cid);

    /**
     * 获取食堂食品信息列表
     * @param foodVo
     * @return
     */
    List<FoodDto> getCanteenFoodList(FoodVo foodVo);

    /**
     * 批量编辑食堂食品信息
     * @param data
     * @return
     */
    void editCanteenFoodBatch(String data);

    /**
     * 批量上架食堂食品
     * @param ids
     * @return
     */
    void putawayCanteenFoodBatch(String ids);

    /**
     * 获取多个食堂食品
     * @param fids
     * @return
     */
    List<CanteenFood> getCanteenFoodBatch(String fids);

    /**
     * 批量添加供应商食品
     * @param data
     * @return
     */
    void addSupplierFoodBatch(String data);

    /**
     * 获取供应商食品列表
     * @param foodVo
     * @return
     */
    List<FoodDto> getSupplierFoodList(FoodVo foodVo);

    /**
     * 获取多个供应商食品
     * @param fids
     * @return
     */
    List<FoodDto> getSupplierFoodBatch(String fids);

    /**
     * 批量编辑供应商食品信息
     * @param data
     * @return
     */
    void editSupplierFoodBatch(String data);

    /**
     * 批量删除（假删除 置为不可用）
     * @param fids
     * @return
     */
    void deleteSupplierFoodBatch(String fids);

    /**
     * 获取供应商所供应的学校
     * @param uid
     * @return
     */
    List<School> getSupplierSchoolByUid(String uid);

    /**
     * 添加学校供应食品
     * @param data
     * @return
     */
    void addSchoolSupfood(String data);


    /**
     * 获取学校供应食品列表
     * @param foodVo
     * @return
     */
    List<FoodDto> getSchoolSupFoodList(FoodVo foodVo);

    /**
     * 批量上下架学校供应食品
     * @param ids
     * @param status
     * @return
     */
    void upOrDownSchoolSupFood(String ids,Integer status);

    /**
     * 获取学校供应食品信息
     * @param id
     * @return
     */
    FoodDto getSchoolSupFoodInfo(Integer id);

    /**
     * 编辑学校供应食品信息
     * @param schoolSupFoodVo
     * @return
     */
    void editSchoolSupFood(SchoolSupFoodVo schoolSupFoodVo);

    /**
     * 获取窗口中供应食品列表
     * @param foodVo
     * @return
     */
    List<FoodDto> getWindowSupFoodList(FoodVo foodVo);

    /**
     * 获取窗口中供应食品信息
     * @param id
     * @return
     */
    FoodDto getWindowSupFoodInfo(Integer id);

    /**
     * 食堂上架商家提供的食品（修改供应价格）
     * @param windowFood
     * @return
     */
    void editWindowSupFood(WindowFood windowFood);

    /**
     * 获取食堂窗口下拉框
     * @param cid
     * @return
     */
    List<Window> getWindowList(Integer cid);

    /**
     * 获取窗口食品列表(食堂端)
     * @param foodVo
     * @return
     */
    List<FoodDto> getWindowFoodList(FoodVo foodVo);

    /**
     * 下架窗口食品
     * @param windowFood
     * @return
     */
    void downWindowFood(WindowFood windowFood);

    /**
     * 获取窗口食品信息(食堂端)
     * @param id
     * @param type
     * @return
     */
    FoodDto getWindowFoodInfo(Integer id,Integer type);

    /**
     * 获取窗口食品列表（学生端）
     * @param foodVo
     * @return
     */
    List<FoodDto> getWindowFoodListByStu(FoodVo foodVo);

    /**
     * 恢复当日供应量
     */
    void recoverDailySupply();

    /**
     * 添加超市商品
     * @param sid
     * @param data
     */
    void addMarketProduct(Integer sid,String data);

    /**
     * 获取超市商品列表
     * @param foodVo
     * @return
     */
    List<CanteenFood> getMarketProduct(FoodVo foodVo);

    /**
     * 获取食堂商品信息
     * @param sid
     * @param barCode
     * @return
     */
    FoodDto getMarketProduct(Integer sid,String barCode);

    /**
     * 删除食堂商品
     * @param fid
     */
    void deleteCanteenFood(Integer fid);

    /**
     * 批量修改超市商品
     * @param data
     */
    void editMarketProduct(String data);

    /**
     * 获取可选日期列表
     * @return
     */
    Map<String,Object> getFoodTimeWeek();

    /**
     * 批量获取窗口供应信息
     * @param ids
     * @return
     */
    List<FoodDto> getWindowSubFoodBatch(String ids);

    /**
     * 批量编辑窗口供应食品信息
     * @param data
     */
    void editWindowSupFoodBatch(String data);

    /**
     * 食品月销量归零
     */
    void cleanFoodMonthlySales();
}
