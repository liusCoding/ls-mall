package com.ls.mall.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @className: ShippingVo
 * @description: 地址Vo
 * @author: liusCoding
 * @create: 2020-02-19 10:07
 */
@Data
public class ShippingVo {
    private Integer id;

    private Integer userId;

    private String receiverName;

    private String receiverPhone;

    private String receiverMobile;

    private String receiverProvince;

    private String receiverCity;

    private String receiverDistrict;

    private String receiverAddress;

    private String receiverZip;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
