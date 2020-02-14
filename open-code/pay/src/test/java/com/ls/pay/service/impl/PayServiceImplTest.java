package com.ls.pay.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.ls.pay.PayApplicationTests;
import com.ls.pay.service.IPayService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class PayServiceImplTest extends PayApplicationTests {

    @Autowired
    private IPayService  payService;

    @Test
    public void create() {

        payService.create("36666688894404040404", BigDecimal.valueOf(0.01), BestPayTypeEnum.ALIPAY_PC);
    }
}