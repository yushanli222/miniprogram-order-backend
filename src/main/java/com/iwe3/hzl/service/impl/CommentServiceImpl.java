package com.iwe3.hzl.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iwe3.hzl.dao.CommentDao;
import com.iwe3.hzl.dao.OrderDao;
import com.iwe3.hzl.pojo.Comment;
import com.iwe3.hzl.service.CommentService;
import com.iwe3.hzl.util.Result;
import com.iwe3.hzl.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;
    @Autowired
    private OrderDao orderDao;

    @Override
    public PageInfo<Comment> findMyComments(String openid, Integer currentPage, Integer pageSize) {
        // 分页参数和前端js pageSize:5 匹配
        PageHelper.startPage(currentPage, pageSize);
        // 根据用户openid筛选自己的评论
        List<Comment> commentList = commentDao.selectByOpenid(openid);
        // 封装PageInfo，包含list、total，前端js读取res.data.data.list / res.data.data.total
        return new PageInfo<>(commentList);
    }

    @Override
    @Transactional
    public Result addComment(Comment comment) {
        if (comment == null) {
            return ResultGenerator.genFailResult("评价信息不能为空");
        }
        if (comment.getOrderId() == null || comment.getOrderId().trim().isEmpty()) {
            return ResultGenerator.genFailResult("订单编号不能为空");
        }
        if (comment.getOpenid() == null || comment.getOpenid().trim().isEmpty()) {
            return ResultGenerator.genFailResult("请先登录后再评价");
        }
        if (comment.getScore() == null || comment.getScore() < 1 || comment.getScore() > 5) {
            return ResultGenerator.genFailResult("请选择1-5星评分");
        }
        if (comment.getComment() == null || comment.getComment().trim().length() < 10) {
            return ResultGenerator.genFailResult("评价内容至少10个字");
        }
        if (commentDao.countByOrderId(comment.getOrderId()) > 0) {
            return ResultGenerator.genFailResult("该订单已评价");
        }
        if (orderDao.countCommentableOrder(comment.getOrderId(), comment.getOpenid()) == 0) {
            return ResultGenerator.genFailResult("只能评价待评价订单");
        }

        comment.setComment(comment.getComment().trim());
        if (comment.getNickname() == null || comment.getNickname().trim().isEmpty()) {
            comment.setNickname("匿名用户");
        }

        commentDao.insertComment(comment);
        int updated = orderDao.finishOrderAfterComment(comment.getOrderId(), comment.getOpenid());
        if (updated == 0) {
            throw new IllegalStateException("订单状态更新失败");
        }
        return ResultGenerator.genSuccessResult("评价成功");
    }
}
