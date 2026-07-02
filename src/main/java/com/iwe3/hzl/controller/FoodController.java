package com.iwe3.hzl.controller;

import com.iwe3.hzl.service.FoodService;
import com.iwe3.hzl.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wx")
public class FoodController {
    @Autowired
    private FoodService foodService;
    @RequestMapping("getRecommendFoods")
    public Result getRecommendFoods() {
        return foodService.getRecommendFoods();
    }
    @RequestMapping("getLatestFoods")
    public Result getLatestFoods() {
        return foodService.getLatestFoods();
    }
    //菜品详情
    // 前端请求地址：baseUrl + "/wx/getFoodById"
    @GetMapping("/getFoodById")
    public Result getFoodDetail(Integer fid){
        return foodService.getFoodById(fid);
    }

}
