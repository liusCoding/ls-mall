package com.ls.pay.service;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayResponse;
import com.ls.pay.pojo.PayInfo;

import java.math.BigDecimal;

/**
 * @author liuscoding
 */
public interface IPayService {

    /**
     * 创建/发起支付
     * @author: liusCoding
     * @date: 2019/11/24
     * @param orderId
     * @param amount
     * @param bestPayTypeEnum 支付方式
     * @return: void
     **/
    PayResponse create(String orderId, BigDecimal amount, BestPayTypeEnum bestPayTypeEnum);

    /**
     * 异步通知处理
     * @date: 2020/2/13
     * @param notifyData  异步通知数据
     * @return: void
     **/

    String asyncNotify(String notifyData);

    /**
     * 通过订单号查询支付信息
     * @date: 2020/2/14
     * @param orderId 订单号
     * @return: com.ls.pay.pojo.PayInfo
     **/
    PayInfo queryByOrderId(String orderId);
}
