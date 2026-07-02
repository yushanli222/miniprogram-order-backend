package com.iwe3.hzl.dao;

import com.iwe3.hzl.api.OrderMap;
import com.iwe3.hzl.pojo.Detail;
import com.iwe3.hzl.pojo.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderDao {
    @Insert("insert into orders values(null,#{tableId},now(),#{totalPrice}," +
            "#{openid} , #{eatNumber} ,#{remark} , #{status} , #{payway})")
//订单id是数据插入后，才生成的，需要在数据插入后，拿到订单id，并且绑定到订单对象上
    @Options(useGeneratedKeys = true,keyColumn = "oid",keyProperty = "oid")
    void insertOrder(Order order);

    @Insert("insert into detail values(null,#{orderId}," +
            "#{foodName},#{foodQuantity},#{price},#{foodIcon})")
    void addOrderDetail(Detail detail);

    @Select("select * from orders where openid = #{openid} and status = #{status}")
    List<OrderMap> selectOrderByStatus(String openid, Integer status);

    @Select("select * from detail where order_id = #{oid}")
    List<Detail> selectDetailByOid(String oid);


    @Update("UPDATE orders SET status = #{status} WHERE oid = #{oid}")
    void updateOrderStatus(Order order);

    @Select("SELECT * FROM orders WHERE oid = #{oid}")
    Order selectOrderById(Integer oid);



    // 取消订单：把订单状态修改为 2（已取消）
    @Update("UPDATE orders SET status = 2 WHERE oid = #{oid}")
    int cancelOrder(@Param("oid") Integer oid);

    int countCommentableOrder(String orderId, String openid);

    int finishOrderAfterComment(String orderId, String openid);
}
