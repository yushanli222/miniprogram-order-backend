package com.iwe3.hzl.controller;

import com.iwe3.hzl.service.CategoryService;
import com.iwe3.hzl.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wx")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("findAllCategory")
    public Result findAllCategory(String keyWord){
        System.out.println(keyWord);
        // 调用业务层，禁止递归调用自身方法
        return categoryService.findAllCategory(keyWord);
    }

}