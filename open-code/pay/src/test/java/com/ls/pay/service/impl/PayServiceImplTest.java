package com.ls.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.enums.OrderStatusEnum;
import com.ls.pay.PayApplicationTests;
import com.ls.pay.pojo.PayInfo;
import com.ls.pay.service.IPayService;
import org.junit.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class PayServiceImplTest extends PayApplicationTests {

    @Autowired
    private IPayService  payService;

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Test
    public void create() {

        payService.create("36666688894404040404", BigDecimal.valueOf(0.01), BestPayTypeEnum.ALIPAY_PC);
    }


    @Test
    public void sendMQMsg()
    {
        PayInfo payInfo = PayInfo.builder()
                .orderNo(1580673562618L)
                .platformStatus(OrderStatusEnum.SUCCESS.name())
                .build();
        rabbitTemplate.convertAndSend("payNotify", JSON.toJSONString(payInfo));
    }
}