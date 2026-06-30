package com.iwe3.hzl.dao;
import com.iwe3.hzl.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryDao {
    @Select("select * from category")
    List<Category> findAllCategory();
}