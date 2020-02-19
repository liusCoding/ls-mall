package com.ls.mall.controller;

import com.ls.mall.constants.MallConst;
import com.ls.mall.form.CartAddForm;
import com.ls.mall.form.CartUpdateForm;
import com.ls.mall.pojo.User;
import com.ls.mall.service.ICartService;
import com.ls.mall.vo.CartVo;
import com.ls.mall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @className: CartController
 * @description: 购物车Controller
 * @author: liusCoding
 * @create: 2020-02-17 17:08
 */

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private ICartService cartService;


    @GetMapping
    public ResponseVo<CartVo> list(HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.list(user.getId());
    }

    @PutMapping("/{productId}")
    public ResponseVo<CartVo> update(@PathVariable("productId") Integer productId, @Valid @RequestBody CartUpdateForm form,
                                     HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.update(user.getId(), productId, form);

    }

    @DeleteMapping("/{productId}")
    public ResponseVo<CartVo> delete(@PathVariable("productId") Integer productId, HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.delete(user.getId(), productId);
    }

    @PutMapping("/selectAll")
    public ResponseVo<CartVo> selectAll(HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.selectAll(user.getId());
    }

    @PutMapping("/unSelectAll")
    public ResponseVo<CartVo> unSelectAll(HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.unSelectAll(user.getId());
    }

    @GetMapping("/products/sum")
    public ResponseVo<Integer> sum(HttpSession session) {
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.sum(user.getId());
    }

    @PostMapping
    public ResponseVo<CartVo> add(@Valid @RequestBody CartAddForm cartAddForm, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute(MallConst.CURRENT_USER);

        return cartService.add(user.getId(), cartAddForm);
    }
}
