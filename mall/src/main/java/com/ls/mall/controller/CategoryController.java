package com.ls.mall.controller;

import com.ls.mall.service.ICategoryService;
import com.ls.mall.vo.CategoryVo;
import com.ls.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @className: CategoryController
 * @description:
 * @author: liusCoding
 * @create: 2020-02-16 17:58
 */
@RestController
@RequestMapping("/categorys")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    @GetMapping
    public ResponseVo<List<CategoryVo>> selectAll() {
        return categoryService.selectAll();
    }
}
