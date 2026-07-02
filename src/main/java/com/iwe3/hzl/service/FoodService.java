package com.iwe3.hzl.service;

import com.iwe3.hzl.util.Result;

public interface FoodService {
    Result getRecommendFoods();

    Result getLatestFoods();

    Result getFoodById(Integer fid);
}
