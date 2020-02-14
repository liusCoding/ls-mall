package com.ls.pay.controller;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayResponse;
import com.ls.pay.service.IPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @className: PayController
 * @description:
 * @author: liusCoding
 * @create: 2019-11-24 20:16
 */

@Controller
@RequestMapping("pay")
@Slf4j
public class PayController {
    @Autowired
    private IPayService payService;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId, @RequestParam("amount") BigDecimal amount,
                               @RequestParam("payType")BestPayTypeEnum payType
                               ){

        Map<String,String> map = new HashMap<String,String>(16);
        PayResponse payResponse = payService.create(orderId, amount,payType);

        //支付方式不同，渲染就不同，WXPAY_NATIVE使用codeUrl,AliPay_PC使用Body
        if(payType.equals(BestPayTypeEnum.WXPAY_NATIVE)){
            map.put("codeUrl",payResponse.getCodeUrl());
            return new ModelAndView("createForWxNative",map);
        }else if(payType.equals(BestPayTypeEnum.ALIPAY_PC)){
            map.put("body",payResponse.getBody());
            return new ModelAndView("createForAliPayPc",map);
        }

        throw new RuntimeException("暂不支持的支付类型");
    }

    @PostMapping("/notify")
    @ResponseBody
    public String asyncNotify(@RequestBody  String notifyData){
        return payService.asyncNotify(notifyData);
    }
}
