package com.ls.mall.listener;

import com.alibaba.fastjson.JSON;
import com.ls.mall.pojo.PayInfo;
import com.ls.mall.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @className: PayMsgListener
 * @description:
 * @author: liusCoding
 * @create: 2020-02-24 16:09
 */
@Component
@RabbitListener(queues = "payNotify")
@Slf4j
public class PayMsgListener {

    @Autowired
    private IOrderService orderService;

    @RabbitHandler
    public void process(String msg) {
        //关于PayInfo，正确姿势，pay项目提供client.jar，mall项目引入jar包
        PayInfo payInfo = JSON.parseObject(msg, PayInfo.class);

        if (payInfo.getPlatformStatus().equals("SUCCESS")) {
            //修改订单状态
            orderService.paid(payInfo.getOrderNo());
        }
    }
}
