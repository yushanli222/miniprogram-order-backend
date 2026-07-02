package com.iwe3.hzl.service.impl;

import com.iwe3.hzl.dao.FoodDao;
import com.iwe3.hzl.pojo.Food;
import com.iwe3.hzl.service.FoodService;
import com.iwe3.hzl.util.Result;
import com.iwe3.hzl.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodDao foodDao;
    @Override
    public Result getRecommendFoods() {
        List<Food> list = foodDao.selectRecommendFoods();
        return ResultGenerator.genSuccessResult(list);
    }

    @Override
    public Result getLatestFoods() {
        List<Food> list = foodDao.NewRecommendFoods();
        return ResultGenerator.genSuccessResult(list);
    }
    @Override
    public Result getFoodById(Integer fid) {
        if(fid == null){
            return ResultGenerator.genFailResult("菜品ID不能为空");
        }
        Food food = foodDao.selectFoodById(fid);
        if(food == null){
            return ResultGenerator.genFailResult("未查询到该菜品");
        }
        return ResultGenerator.genSuccessResult(food);
    }

}