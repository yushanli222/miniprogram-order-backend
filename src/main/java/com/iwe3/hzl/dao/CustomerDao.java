package com.iwe3.hzl.dao;

import com.iwe3.hzl.pojo.Customer;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CustomerDao {
    @Select("select * from customer where openid =#{openid}")
    Customer selectCustomerById(String openId);
    @Insert("insert  into  customer values (#{openid},#{nickname},#{phoneNumber},#{avatarUrl},0.0)")
    int insertCustomer(Customer customer);

    @Update("update customer set money = money - #{totalPrice} where openid = #{openid}")
    void subCustomerMoney(@Param("openid")String openid,
                          @Param("totalPrice")Integer totalPrice);

    int updateCustomerMoney(Double money, String openid);
}