package com.iwe3.hzl.service;

import com.iwe3.hzl.dao.FoodDao;
import com.iwe3.hzl.util.Result;

public interface CategoryService {

    Result findAllCategory(String keyWord);
}