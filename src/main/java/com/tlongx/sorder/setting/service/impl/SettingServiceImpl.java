package com.tlongx.sorder.setting.service.impl;

import com.tlongx.sorder.manager.dao.SchoolMapper;
import com.tlongx.sorder.manager.pojo.School;
import com.tlongx.sorder.setting.dao.SysSettingMapper;
import com.tlongx.sorder.setting.pojo.SysSetting;
import com.tlongx.sorder.setting.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SettingServiceImpl implements SettingService {

    @Autowired
    private SysSettingMapper sysSettingMapper;
    @Autowired
    private SchoolMapper schoolMapper;

    @Override
    public void editSetting(SysSetting sysSetting) {
        if(sysSetting.getId()!=null){
            sysSetting.setUptime(new Date());
            sysSettingMapper.updateByPrimaryKeySelective(sysSetting);
        }else{
            sysSetting.setUptime(new Date());
            sysSettingMapper.insertSelective(sysSetting);
        }

    }

    @Override
    public List<SysSetting> getSetting() {
        //SysSetting sysSetting=sysSettingMapper.selectByPrimaryKey(1);
        List<SysSetting> sysSettingList = sysSettingMapper.selectAll();
        return sysSettingList;
    }

    @Override
    public void editSchoolSetting(School school) {
        school.setUptime(new Date());
        schoolMapper.updateByPrimaryKeySelective(school);
    }

    @Override
    public School getSchoolSetting(Integer sid) {
        School school=schoolMapper.selectByPrimaryKey(sid);
        return school;
    }
}
