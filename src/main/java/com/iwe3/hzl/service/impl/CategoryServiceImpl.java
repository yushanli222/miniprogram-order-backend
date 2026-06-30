package com.iwe3.hzl.service.impl;

import com.iwe3.hzl.api.CategoryVo;
import com.iwe3.hzl.api.FoodRes;
import com.iwe3.hzl.dao.CategoryDao;
import com.iwe3.hzl.dao.FoodDao;
import com.iwe3.hzl.pojo.Category;
import com.iwe3.hzl.pojo.Food;
import com.iwe3.hzl.service.CategoryService;
import com.iwe3.hzl.util.Result;
import com.iwe3.hzl.util.ResultGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    // 自动注入DAO，禁止手动赋值null
    @Autowired
    private FoodDao foodDao;

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public Result findAllCategory(String keyWord) {
        List<Food> foods = new ArrayList<>();

        // 兼容三种情况：null、空字符串、"undefined"
        if (keyWord == null || "".equals(keyWord) || "undefined".equals(keyWord)) {
            // 无搜索条件：查询所有菜品
            foods = foodDao.selectAllFoods();
        } else {
            // 模糊搜索
            String likeKey = "%" + keyWord + "%";
            foods = foodDao.selectFoodsByKey(likeKey);
        }

        // 调用分类查询，传入null（不需要条件查询全部分类）
        List<Category> categories = categoryDao.findAllCategory(null);

        // 防止foods为空导致stream报错
        List<String> kindNames = new ArrayList<>();
        if (!foods.isEmpty()) {
            kindNames = foods.stream()
                    .map(Food::getFkind)
                    .collect(Collectors.toList());
        }

        List<CategoryVo> categoryVos = new ArrayList<>();

        for (Category category : categories) {
            if (kindNames.contains(category.getKindName())) {
                CategoryVo categoryVo = new CategoryVo();
                categoryVo.setCid(category.getCid());
                categoryVo.setKindName(category.getKindName());

                List<FoodRes> foodResList = new ArrayList<>();
                for (Food food : foods) {
                    if (category.getKindName().equals(food.getFkind())) {
                        FoodRes foodRes = new FoodRes();
                        BeanUtils.copyProperties(food, foodRes);
                        foodResList.add(foodRes);
                    }
                }
                categoryVo.setFoodResList(foodResList);
                categoryVos.add(categoryVo);
            }
        }

        return ResultGenerator.genSuccessResult(categoryVos);
    }
}
