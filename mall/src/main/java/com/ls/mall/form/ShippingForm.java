package com.ls.mall.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: ShippingForm
 * @description: 地址表单对象
 * @author: liusCoding
 * @create: 2020-02-19 10:08
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShippingForm {
    private String receiverName;

    private String receiverPhone;

    private String receiverMobile;

    private String receiverProvince;

    private String receiverCity;

    private String receiverDistrict;

    private String receiverAddress;

    private String receiverZip;
}
