package com.ls.pay.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.BestPayService;
import com.ls.pay.service.IPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @className: PayServiceImpl
 * @description:
 * @author: liusCoding
 * @create: 2019-11-24 19:42
 */

@Slf4j
@Service
public class PayServiceImpl implements IPayService {

    @Autowired
    private BestPayService bestPayService;
    /**
     * 创建/发起支付
     *
     * @param orderId
     * @param amount
     * @author: liusCoding
     * @date: 2019/11/24
     * @return: void
     **/
    @Override
    public PayResponse create(String orderId, BigDecimal amount) {


        //写入数据库

        PayRequest payRequest = new PayRequest();
        payRequest.setOrderName("493674-最好的支付sdk");
        payRequest.setOrderId(orderId);
        payRequest.setOrderAmount(amount.doubleValue());
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_NATIVE);
        PayResponse response = bestPayService.pay(payRequest);

        log.info("【response:】:{}",response);

        return response;
    }

    /**
     * 异步通知处理
     *
     * @param notifyData 异步通知数据
     * @date: 2020/2/13
     * @return: void
     **/
    @Override
    public String asyncNotify(String notifyData) {

        //1.签名校验
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("【payResponse】:{}",payResponse);

        //2.金额校验（从数据库查订单）

        //3.修改订单支付状态

        //4.告诉微信不要在通知了

        return "<xml>\n" +
                "\n" +
                "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                "</xml>";
    }
}
