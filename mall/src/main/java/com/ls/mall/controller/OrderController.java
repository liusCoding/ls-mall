package com.ls.mall.controller;

import com.github.pagehelper.PageInfo;
import com.ls.mall.constants.MallConst;
import com.ls.mall.form.OrderCreateForm;
import com.ls.mall.pojo.User;
import com.ls.mall.service.IOrderService;
import com.ls.mall.vo.OrderVo;
import com.ls.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @className: OrderController
 * @description: 订单Controller
 * @author: liusCoding
 * @create: 2020-02-24 10:31
 */

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private IOrderService orderService;

    @PostMapping
    public ResponseVo<OrderVo> create(@Valid @RequestBody OrderCreateForm form, HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return orderService.create(user.getId(), form.getShippingId());
    }

    @GetMapping
    public ResponseVo<PageInfo> list(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize,
                                     HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return orderService.list(user.getId(), pageNum, pageSize);
    }

    @GetMapping("/{orderNo}")
    public ResponseVo<OrderVo> detail(@PathVariable("orderNo") Long orderNo, HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return orderService.detail(user.getId(), orderNo);
    }

    @PutMapping("/{orderNo}")
    public ResponseVo cancel(@PathVariable("orderNo") Long orderNo, HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return orderService.cancel(user.getId(), orderNo);
    }

}
