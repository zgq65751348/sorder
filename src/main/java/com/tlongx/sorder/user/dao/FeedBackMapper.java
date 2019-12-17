package com.tlongx.sorder.user.dao;

import com.tlongx.sorder.dto.FeedBackDto;
import com.tlongx.sorder.user.pojo.FeedBack;
import com.tlongx.sorder.utils.MyMapper;
import com.tlongx.sorder.vo.FeedBackVo;

import java.util.List;

public interface FeedBackMapper extends MyMapper<FeedBack> {

    List<FeedBackDto> selectFeedBackList(FeedBackVo feedBackVo);
}