package com.tlongx.sorder.setting.service;

import com.tlongx.sorder.manager.pojo.School;
import com.tlongx.sorder.setting.pojo.SysSetting;
import java.util.*;

public interface SettingService {

    /**
     * 修改系统设定
     * @param sysSetting
     * @return
     */
    void editSetting(SysSetting sysSetting);

    /**
     *获取系统设定
     * @return
     */
    List<SysSetting> getSetting();

    /**
     * 修改学校系统设定
     * @param school
     * @return
     */
    void editSchoolSetting(School school);

    /**
     *获取学校系统设定
     * @param sid
     * @return
     */
    School getSchoolSetting(Integer sid);

}
