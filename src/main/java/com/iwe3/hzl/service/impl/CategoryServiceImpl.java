package com.iwe3.hzl.service.impl;
import java.util.List;

import com.iwe3.hzl.api.CategoryVo;
import com.iwe3.hzl.api.FoodRes;
import com.iwe3.hzl.pojo.Category;
import com.iwe3.hzl.pojo.Food;
import com.iwe3.hzl.service.CategoryService;
import com.iwe3.hzl.util.Result;
import com.iwe3.hzl.util.ResultGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.iwe3.hzl.dao.FoodDao;
import com.iwe3.hzl.dao.CategoryDao;

@Service
public  class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private FoodDao foodDao;
    @Override
    public Result findAllCategory(String keyWord) {
        //获取所有菜单分类的逻辑
        //将来获取的keyWord可能存在3中情况，1，带着搜索的内容 2，空值 3，undefined
        //如果是第一种情况，那么就根据实际内容，完成模糊查询
        //如果是第二、三中情况 ，就是不带条件，查询所有

        List<Food> foods = new ArrayList<Food>();

        if (keyWord == "" || keyWord.equals("undefined")) {
            //没有输入内容搜索，返回所有
            foods = foodDao.selectAllFoods();
        } else {
            keyWord = "%" + keyWord + "%"; //拼接模糊查询的通配符
            foods = foodDao.selectFoodsByKey(keyWord);
        }

        //查询所有的分类信息
        List<Category> categories = categoryDao.findAllCategory();
        //把菜品的信息和分类的信息做关联，关联后一起放到CategoryVo的集合中，返回到前台

        //取出foods中所有的分类名称
        List<String> kindNames = foods.stream().map(s -> s.getFkind())
                .collect(Collectors.toList());

        //创建一个CategoryVO的List集合,这个集合就是将来返回的数据的集合
        List<CategoryVo> categoryVos = new ArrayList<>();

        //遍历分类的集合
        for (Category category : categories) {
            //判断kindNames中是否包含分类信息,如果包含，就存放对应的数据
            if (kindNames.contains(category.getKindName())){
                //创建一个CategoryVo对象
                CategoryVo categoryVo = new CategoryVo();
                categoryVo.setCid(category.getCid()); //设置cid
                categoryVo.setKindName(category.getKindName()); //设置kindName
                //还需要设置foodResList，需要把所有的菜品food转为foodRes，放到foodResList
                List<FoodRes> foodResList = new ArrayList<>();
                //遍历food集合
                for (Food food : foods) {
                    if (food.getFkind().equals(category.getKindName())){
                        FoodRes foodRes = new FoodRes();
                        //把food的属性复制到foodRes上
                        BeanUtils.copyProperties(food, foodRes);
                        //把foodRes加到集合
                        foodResList.add(foodRes);
                    }
                }
                categoryVo.setFoodResList(foodResList);
                //把categoryVo加到集合中返回
                categoryVos.add(categoryVo);
            }

        }
        return ResultGenerator.genSuccessResult(categoryVos);
    }
}