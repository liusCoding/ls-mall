package com.ls.mall.service;

import com.github.pagehelper.PageInfo;
import com.ls.mall.form.ShippingForm;
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

import java.util.Map;

import static com.ls.mall.enums.ResponseEnum.SUCCESS;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class IShippingServiceTest {

    @Autowired
    private IShippingService shippingService;
    private Integer uid = 12;
    private ShippingForm form;
    private Integer shippingId;

    @Before
    public void before() {
        ShippingForm form = ShippingForm.builder()
                .receiverAddress("市政大院")
                .receiverCity("深圳")
                .receiverDistrict("荔枝西路")
                .receiverMobile("1333455666")
                .receiverProvince("广东省")
                .receiverZip("0999")
                .receiverName("刘帅")
                .receiverPhone("23333333333333333")
                .build();

        this.form = form;

        add();
    }

    public void add() {
        ResponseVo<Map<String, Integer>> result = shippingService.add(uid, form);
        log.info("result={}", result);
        this.shippingId = result.getData().get("shippingId");
        Assert.assertEquals(SUCCESS.getCode(), result.getStatus());
    }

    @After
    public void delete() {
        ResponseVo deleteResult = shippingService.delete(uid, shippingId);
        Assert.assertEquals(SUCCESS.getCode(), deleteResult.getStatus());
    }

    @Test
    public void update() {
        ShippingForm form = ShippingForm.builder()
                .receiverAddress("市政大院")
                .receiverCity("衡阳")
                .receiverDistrict("荔枝西路")
                .receiverMobile("1333455666")
                .receiverProvince("广东省")
                .receiverZip("0999")
                .receiverName("刘帅update")
                .receiverPhone("23333333333333333")
                .build();

        ResponseVo result = shippingService.update(uid, shippingId, form);
        Assert.assertEquals(SUCCESS.getCode(), result.getStatus());
    }

    @Test
    public void list() {

        ResponseVo<PageInfo> result = shippingService.list(uid, 1, 10);
        log.info("result = {}", result);
        Assert.assertEquals(SUCCESS.getCode(), result.getStatus());

    }
}