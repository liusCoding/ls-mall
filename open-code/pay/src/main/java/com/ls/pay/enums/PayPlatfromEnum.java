package com.ls.pay.enums;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PayPlatfromEnum {

    //1-支付宝，2-微信
    APLIPAY(1),
    WX(2)
    ;
    private Integer code;


    public  static  PayPlatfromEnum  getByBestPayTypeEnum(BestPayTypeEnum bestPayTypeEnum){
        for (PayPlatfromEnum payPlatfromEnum : PayPlatfromEnum.values()) {
            if(bestPayTypeEnum.getPlatform().name().equals(payPlatfromEnum.name())){
                return payPlatfromEnum;
            }
        }
        throw new RuntimeException("错误的支付平台：" + bestPayTypeEnum.name());
    }
}
