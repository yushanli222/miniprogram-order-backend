package com.iwe3.hzl.controller;

import com.github.pagehelper.PageInfo;
import com.iwe3.hzl.pojo.Comment;
import com.iwe3.hzl.service.CommentService;
import com.iwe3.hzl.util.Result;
import com.iwe3.hzl.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wx")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @RequestMapping("findMyComments")
    public Result findMyComments(String openid, Integer currentPage, Integer pageSize) {
        PageInfo<Comment> pageInfo = commentService.findMyComments(openid, currentPage, pageSize);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
    @RequestMapping("addComment")
    public Result addComment(Comment comment) {
        return commentService.addComment(comment);
    }
}
