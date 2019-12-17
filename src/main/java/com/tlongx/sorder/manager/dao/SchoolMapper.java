package com.tlongx.sorder.manager.dao;


import com.tlongx.sorder.dto.PlatTradeDto;
import com.tlongx.sorder.dto.SchoolDto;
import com.tlongx.sorder.manager.pojo.School;
import com.tlongx.sorder.user.pojo.SchoolSupplier;
import com.tlongx.sorder.utils.MyMapper;
import com.tlongx.sorder.vo.PlatTradeVo;
import com.tlongx.sorder.vo.SchoolVo;
import io.swagger.models.auth.In;

import java.util.List;


public interface SchoolMapper extends MyMapper<School> {

    int insertSchool(School school);

    List<SchoolDto> selectSchoolList(SchoolVo schoolVo);

    List<School> selectSchoolBySubsidy(School school);

    List<PlatTradeDto> selectPlaTradeList(PlatTradeVo platTradeVo);

    List<School> selectSchoolComboBoxList();

    School selectSchoolBankAccountInfo(Integer sid);

    SchoolDto selectSchoolAccount(Integer sid);

    int updateSchoolDepositPrice();

}