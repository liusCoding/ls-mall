package com.ls.mall.controller;

import com.ls.mall.constants.MallConst;
import com.ls.mall.form.ShippingForm;
import com.ls.mall.pojo.User;
import com.ls.mall.service.IShippingService;
import com.ls.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @className: ShippingController
 * @description: 地址Controller
 * @author: liusCoding
 * @create: 2020-02-19 11:54
 */
@RestController
@RequestMapping("/shippings")
public class ShippingController {

    @Autowired
    private IShippingService shippingService;

    @PostMapping
    public ResponseVo add(@Valid @RequestBody ShippingForm form, HttpSession session) {

        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.add(user.getId(), form);
    }

    @DeleteMapping("/{shippingId}")
    public ResponseVo delete(@PathVariable("shippingId") Integer shoppingId, HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.delete(user.getId(), shoppingId);
    }


    @PutMapping("/{shippingId}")
    public ResponseVo update(@PathVariable("shippingId") Integer shoppingId, @Valid @RequestBody ShippingForm form, HttpSession session) {

        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.update(user.getId(), shoppingId, form);
    }

    @GetMapping
    public ResponseVo list(HttpSession session, @RequestParam(required = false, value = "pageNum", defaultValue = "1") Integer pageNum,
                           @RequestParam(required = false, value = "pageSize", defaultValue = "10") Integer pageSize) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return shippingService.list(user.getId(), pageNum, pageSize);
    }

}
