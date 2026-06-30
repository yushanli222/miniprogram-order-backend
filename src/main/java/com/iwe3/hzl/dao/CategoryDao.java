package com.iwe3.hzl.dao;

import com.iwe3.hzl.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryDao {
    // 手动给数据库字段 kind_name 起别名 kindName，解决下划线无法映射驼峰属性
    @Select("select cid, kind_name as kindName from category")
    List<Category> findAllCategory(@Param("keyWord") String keyWord);
}