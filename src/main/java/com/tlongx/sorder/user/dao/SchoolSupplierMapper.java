package com.tlongx.sorder.user.dao;

import com.tlongx.sorder.dto.SupplierDto;
import com.tlongx.sorder.manager.pojo.School;
import com.tlongx.sorder.user.pojo.SchoolSupplier;
import com.tlongx.sorder.user.pojo.UserSupplier;
import com.tlongx.sorder.utils.MyMapper;
import com.tlongx.sorder.vo.UserSupplierVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SchoolSupplierMapper extends MyMapper<SchoolSupplier> {

    List<SupplierDto> selectUserSupplierList(UserSupplierVo userSupplierVo);

    List<School> selectSchoolBySup(SchoolSupplier schoolSupplier);

    List<UserSupplier> selectUserSupplierBySidList(UserSupplierVo userSupplierVo);

    List<String> selectAllSupUid(@Param("sid") Integer sid);

    List<School> selectSchoolListBySupId(String uid);


}