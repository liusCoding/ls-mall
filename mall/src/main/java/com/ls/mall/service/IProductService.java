package com.ls.mall.service;

import com.github.pagehelper.PageInfo;
import com.ls.mall.vo.ProductDetailVo;
import com.ls.mall.vo.ResponseVo;

public interface IProductService {

    /**
     * 查询所有商品（可按分类查询）
     *
     * @param categoryId 分类id
     * @param pageNum
     * @param pageSize
     * @date: 2020/2/16
     * @return: javax.xml.ws.Response<java.util.List < com.ls.mall.vo.ProductVo>>
     **/
    ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize);

    /**
     * 根据商品id 查询商品详情
     *
     * @param productId 商品id
     * @date: 2020/2/17
     * @return: com.ls.mall.vo.ResponseVo<com.ls.mall.vo.ProductDetailVo>
     **/
    ResponseVo<ProductDetailVo> detail(Integer productId);
}
