package com.ls.mall.service.impl;

import com.alibaba.fastjson.JSON;
import com.ls.mall.enums.ResponseEnum;
import com.ls.mall.form.CartAddForm;
import com.ls.mall.form.CartUpdateForm;
import com.ls.mall.service.ICartService;
import com.ls.mall.vo.CartVo;
import com.ls.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CartServiceImplTest {

    @Autowired
    private ICartService cartService;

    private final static String CART_REDIS_KEY = "cart_%d";

    private Integer uid = 1;
    private Integer productId = 26;

    @Before
    public void add() {
        log.info("新增购物车");
        CartAddForm form = CartAddForm.builder()
                .productId(productId)
                .selected(true)
                .build();

        ResponseVo<CartVo> result = cartService.add(uid, form);
        log.info("result={}", result);

        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), result.getStatus());

    }

    @Test
    public void list() {
        ResponseVo<CartVo> result = cartService.list(uid);

        log.info("result={}", JSON.toJSONString(result));

        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), result.getStatus());
    }

    @Test
    public void update() {
        CartUpdateForm updateForm = CartUpdateForm.builder()
                .quantity(28)
                .selected(false)
                .build();
        ResponseVo<CartVo> updateResult = cartService.update(uid, productId, updateForm);

        log.info("updateResult={}", JSON.toJSONString(updateResult));

        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), updateResult.getStatus());
    }

    @After
    public void delete() {
        log.info("删除购物车");
        ResponseVo<CartVo> deleteResult = cartService.delete(uid, productId);
        log.info("deleteResult={}", JSON.toJSONString(deleteResult));

        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), deleteResult.getStatus());
    }

    @Test
    public void unSelected() {
        ResponseVo<CartVo> result = cartService.unSelectAll(uid);
        log.info("result={}", result);

        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), result.getStatus());
    }

    @Test
    public void selectedAll() {
        ResponseVo<CartVo> result = cartService.selectAll(uid);

        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), result.getStatus());
        log.info("result={}", result);
    }

    @Test
    public void sum() {
        ResponseVo<Integer> result = cartService.sum(uid);
        log.info("result={}", result);

        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(), result.getStatus());
    }
}