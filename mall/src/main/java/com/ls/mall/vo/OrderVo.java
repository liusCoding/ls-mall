package com.ls.mall.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @className: OrderVo
 * @description: 订单VO
 * @author: liusCoding
 * @create: 2020-02-19 15:27
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderVo {

    private Long orderNo;


    private BigDecimal payment;

    private Integer paymentType;

    private Integer postage;

    private Integer status;

    private LocalDateTime paymentTime;

    private LocalDateTime sendTime;

    private LocalDateTime endTime;

    private LocalDateTime closeTime;

    private LocalDateTime createTime;

    private List<OrderItemVo> orderItemVos;

    private Integer shippingId;

    private ShippingVo shippingVo;
}
