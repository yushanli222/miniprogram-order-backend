package com.iwe3.hzl.dao;

import com.iwe3.hzl.pojo.Food;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface FoodDao {
    @Select("select * from food where is_recommend=1")
    List<Food> selectRecommendFoods();

    @Select("select * from food where is_recommend=0")
    List<Food> selectgetLatestFoods();

    @Select("select * from food")
    List<Food> selectAllFoods();

    @Select("select * from food where fname like #{keyWord}")
    List<Food> selectFoodsByKey(String keyWord);

    @Update("update food set storage = storage - #{foodQuantity} where fname = #{foodName}")
    void updateFoodStorage(@Param("foodName") String foodName,
                           @Param("foodQuantity") Integer foodQuantity);
}