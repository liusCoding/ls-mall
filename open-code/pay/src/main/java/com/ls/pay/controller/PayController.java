package com.ls.pay.controller;

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
    public ModelAndView create( @RequestParam(required = false) String orderId, @RequestParam(required = false) BigDecimal amount){

        Map<String,String> map = new HashMap<String,String>(16);
        PayResponse payResponse = payService.create(orderId, amount);
        map.put("codeUrl",payResponse.getCodeUrl());

        return new ModelAndView("create",map);
    }

    @PostMapping("/notify")
    public void asyncNotify(@RequestBody  String notifyData){
        payService.asyncNotify(notifyData);
        log.error("【异步通知结果】:{}",notifyData);
    }
}
