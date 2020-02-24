package com.ls.mall.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageInfo;
import com.ls.mall.enums.ResponseEnum;
import com.ls.mall.form.CartAddForm;
import com.ls.mall.vo.CartVo;
import com.ls.mall.vo.OrderVo;
import com.ls.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static com.ls.mall.enums.ResponseEnum.SUCCESS;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@Transactional
public class IOrderServiceTest {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ICartService cartService;

    private Integer uid = 12;

    private Integer shippingId = 8;

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
    public void createTest() {
        ResponseVo<OrderVo> result = create();
        Assert.assertEquals(SUCCESS.getCode(), result.getStatus());
    }

    public ResponseVo<OrderVo> create() {

        ResponseVo<OrderVo> result = orderService.create(uid, shippingId);

        log.info("result={}", JSON.toJSONString(result, SerializerFeature.PrettyFormat));
        //JSON.toJSONString(result, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
        //                SerializerFeature.WriteDateUseDateFormat)
        //————————————————
        //版权声明：本文为CSDN博主「清新静远」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
        //原文链接：https://blog.csdn.net/viplisong/article/details/102481657

        Assert.assertEquals(SUCCESS.getCode(), result.getStatus());
        return result;
    }

    @Test
    public void list() {
        ResponseVo<PageInfo> result = orderService.list(uid, 1, 10);
        log.info("result={}", JSON.toJSONString(result, SerializerFeature.PrettyFormat));
        Assert.assertEquals(SUCCESS.getCode(), result.getStatus());
    }


    @Test
    public void detail() {
        ResponseVo<OrderVo> orderVoResponseVo = create();
        ResponseVo<OrderVo> result = orderService.detail(uid, orderVoResponseVo.getData().getOrderNo());
        log.info("result={}", JSON.toJSONString(result, SerializerFeature.PrettyFormat));
        Assert.assertEquals(SUCCESS.getCode(), result.getStatus());

    }


    @Test
    public void cancel() {
        ResponseVo<OrderVo> orderVoResponseVo = create();
        ResponseVo result = orderService.cancel(uid, orderVoResponseVo.getData().getOrderNo());
        log.info("result={}", JSON.toJSONString(result, SerializerFeature.PrettyFormat));
        Assert.assertEquals(SUCCESS.getCode(), result.getStatus());
    }


}