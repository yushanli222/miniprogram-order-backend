package com.iwe3.hzl.dao;

import com.iwe3.hzl.pojo.Food;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FoodDao {
    @Select("select * from food where is_recommend=1")
    List<Food> selectRecommendFoods();

    @Select("select * from food where is_recommend=0")
    List<Food> selectgetLatestFoods();

    @Select("select * from food")
    List<Food> selectAllFoods();

    @Select("select * from food where fname like #{keyword}")
    List<Food> selectLikeFoods(@Param("keyword") String keyword);

    // 给业务调用的 selectFoodsByKey 补充SQL注解，和上方模糊查询逻辑一致
    @Select("select * from food where fname like #{keyWord}")
    List<Food> selectFoodsByKey(@Param("keyWord") String keyWord);
}