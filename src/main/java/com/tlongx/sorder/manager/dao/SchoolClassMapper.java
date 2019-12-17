package com.tlongx.sorder.manager.dao;

import com.tlongx.sorder.manager.pojo.SchoolClass;
import com.tlongx.sorder.utils.MyMapper;
import com.tlongx.sorder.vo.SchoolClassVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SchoolClassMapper extends MyMapper<SchoolClass> {

    List<SchoolClass> selectSchoolClassList(SchoolClassVo schoolClassVo);

    List<SchoolClass> selectSchoolGradeList(@Param("sid")Integer sid,@Param("type")Integer type,@Param("grade")Integer grade,@Param("classNum")Integer classNum);

    List<SchoolClass> selectClassNumList(SchoolClass schoolClass);

    int selectGradeNum(SchoolClass schoolClass);

    SchoolClass selectOneSchoolClass(SchoolClass schoolClass);
}