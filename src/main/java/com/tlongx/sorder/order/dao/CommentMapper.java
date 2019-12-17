package com.tlongx.sorder.order.dao;

import com.tlongx.sorder.dto.CommentDto;
import com.tlongx.sorder.order.pojo.Comment;
import com.tlongx.sorder.utils.MyMapper;
import com.tlongx.sorder.vo.CommentVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper extends MyMapper<Comment> {

    List<CommentDto> selectCommentByCidOrUid(CommentVo commentVo);

    List<CommentDto> selectCommentBySid(CommentVo commentVo);

    List<Comment> selectCommentListNow();

    List<CommentDto> selectSupplierCommentList(CommentVo commentVo);

    List<CommentDto> selectMyCommentList(CommentVo commentVo);
}