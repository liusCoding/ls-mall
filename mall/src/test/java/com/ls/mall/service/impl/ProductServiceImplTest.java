package com.ls.mall.service.impl;

import com.github.pagehelper.PageInfo;
import com.ls.mall.enums.ResponseEnum;
import com.ls.mall.service.IProductService;
import com.ls.mall.vo.ProductDetailVo;
import com.ls.mall.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    private IProductService productService;

    @Test
    public void list() {

        ResponseVo<PageInfo> list = productService.list(100001, 1, 1);

        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), list.getStatus());
    }

    @Test
    public void detail() {
        ResponseVo<ProductDetailVo> result = productService.detail(26);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), result.getStatus());

    }
}