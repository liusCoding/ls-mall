package com.ls.mall.controller;

import com.github.pagehelper.PageInfo;
import com.ls.mall.service.IProductService;
import com.ls.mall.vo.ProductDetailVo;
import com.ls.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @className: ProductController
 * @description:
 * @author: liusCoding
 * @create: 2020-02-16 20:47
 */

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private IProductService productService;

    @GetMapping
    public ResponseVo<PageInfo> list(@RequestParam(required = false) Integer categoryId,
                                     @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                     @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return productService.list(categoryId, pageNum, pageSize);

    }

    @GetMapping("/{productId}")
    public ResponseVo<ProductDetailVo> detail(@PathVariable("productId") Integer productId) {
        return productService.detail(productId);
    }
}
