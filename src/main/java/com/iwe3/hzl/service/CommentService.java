package com.iwe3.hzl.service;

import com.github.pagehelper.PageInfo;
import com.iwe3.hzl.pojo.Comment;
import com.iwe3.hzl.util.Result;
import org.springframework.transaction.annotation.Transactional;

public interface CommentService {
    PageInfo<Comment> findMyComments(String openid, Integer currentPage, Integer pageSize);

    @Transactional
    Result addComment(Comment comment);
}
