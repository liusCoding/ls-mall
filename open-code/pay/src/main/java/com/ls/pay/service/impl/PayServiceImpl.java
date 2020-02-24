package com.ls.pay.service.impl;

import com.alibaba.fastjson.JSON;
import com.lly835.bestpay.enums.BestPayPlatformEnum;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.enums.OrderStatusEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.BestPayService;
import com.ls.pay.dao.PayInfoMapper;
import com.ls.pay.enums.PayPlatfromEnum;
import com.ls.pay.pojo.PayInfo;
import com.ls.pay.service.IPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

import static com.ls.pay.constants.QueueStats.QUEUE_PAY_NOTIFY;

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

    @Autowired
    private PayInfoMapper payInfoMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 创建/发起支付
     *
     * @param orderId
     * @param amount
     * @param bestPayTypeEnum 支付方式
     * @author: liusCoding
     * @date: 2019/11/24
     * @return: void
     **/
    @Override
    public PayResponse create(String orderId, BigDecimal amount,BestPayTypeEnum bestPayTypeEnum) {

        //写入数据库
        PayInfo payInfo = PayInfo.builder().orderNo(Long.parseLong(orderId))
                .payPlatform(PayPlatfromEnum.getByBestPayTypeEnum(bestPayTypeEnum).getCode())
                .platformStatus(OrderStatusEnum.NOTPAY.name())
                .payAmount(amount).build();

        payInfoMapper.insertSelective(payInfo);


        PayRequest payRequest = new PayRequest();
        payRequest.setOrderName("493674-最好的支付sdk");
        payRequest.setOrderId(orderId);
        payRequest.setOrderAmount(amount.doubleValue());
        payRequest.setPayTypeEnum(bestPayTypeEnum);
        PayResponse response = bestPayService.pay(payRequest);

        log.info("【发起支付的response:】:{}",response);

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
        log.info("【异步通知Response】:{}",payResponse);

        //2.金额校验（从数据库查订单）
        PayInfo payInfo = payInfoMapper.selectByOrderNo(Long.parseLong(payResponse.getOrderId()));
        if(Objects.isNull(payInfo)){
            throw new RuntimeException("通过orderNo查询的结果是Null"+payResponse.getOrderId());
        }

        //如果订单支付状态不是已支付
        if(!payInfo.getPlatformStatus().equals(OrderStatusEnum.SUCCESS)){
            if(payInfo.getPayAmount().compareTo(BigDecimal.valueOf(payResponse.getOrderAmount()))!=0){
                //告警
                throw new RuntimeException("异步通知中的金额和数据库里的不一致，orderNo="+payResponse.getOrderId());
            }
        }
        //3.修改订单支付状态

        payInfo.setPlatformStatus(OrderStatusEnum.SUCCESS.name());
        payInfo.setPlatformNumber(payResponse.getOutTradeNo());
        payInfoMapper.updateByPrimaryKeySelective(payInfo);

        //TODO pay发送MQ消息，mall接受MQ消息
        amqpTemplate.convertAndSend(QUEUE_PAY_NOTIFY, JSON.toJSONString(payInfo));

        //4.告诉微信不要在通知了
        if(payResponse.getPayPlatformEnum().equals(BestPayPlatformEnum.WX)){
            return "<xml>\n" +
                    "\n" +
                    "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                    "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                    "</xml>";
        }else if(payResponse.getPayPlatformEnum().equals(BestPayPlatformEnum.ALIPAY)){
            return "success";
        }

        throw new RuntimeException("异步通知中错误的支付平台");

    }

    /**
     * 通过订单号查询支付记录
     *
     * @param orderId 订单号
     * @date: 2020/2/14
     * @return: com.ls.pay.pojo.PayInfo
     **/
    @Override
    public PayInfo queryByOrderId(String orderId) {

        PayInfo payInfo = payInfoMapper.selectByOrderNo(Long.parseLong(orderId));
        return payInfo;
    }
}
