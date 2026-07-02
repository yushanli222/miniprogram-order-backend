package com.iwe3.hzl.dao;

import com.iwe3.hzl.pojo.Food;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
@Mapper
public interface FoodDao {
    @Select("select * from food where is_recommend = 1")
    List<Food> selectRecommendFoods();
    @Select("select * from food order by rand() limit 6")
    List<Food> NewRecommendFoods();
    @Select("select * from food")
    List<Food> selectAllFoods();
    @Select("select * from food where  fname like #{keyWord}")
    List<Food>  selectFoodsByKey(String keyword);
    @Update("update food set storage=storage -#{foodQuantity} where fname=#{foodName}")
    void updateFoodStorage(@Param("foodName") String foodName,
                           @Param("foodQuantity")Integer foodQuantity);
    // 新增菜品详情查询：根据fid查单条菜品
    @Select("select * from food where fid = #{fid}")
    Food selectFoodById(@Param("fid") Integer fid);

}