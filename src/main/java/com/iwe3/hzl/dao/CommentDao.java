package com.iwe3.hzl.dao;
import org.apache.ibatis.annotations.*;
import com.iwe3.hzl.pojo.Comment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface CommentDao {
    @Select("SELECT comment_id,order_id,openid,nickname,comment,table_id,score,createtime " +
            "FROM comment WHERE openid = #{openid} ORDER BY createtime DESC")
    List<Comment> selectByOpenid(@Param("openid") String openid);

    @Select("SELECT COUNT(1) FROM comment WHERE order_id = #{orderId}")
    int countByOrderId(@Param("orderId") String orderId);

    @Insert("INSERT INTO comment(order_id,openid,nickname,comment,table_id,score,createtime) " +
            "VALUES(#{orderId},#{openid},#{nickname},#{comment},#{tableId},#{score},NOW())")
    @Options(useGeneratedKeys = true, keyColumn = "comment_id", keyProperty = "commentId")
    int insertComment(Comment comment);
}