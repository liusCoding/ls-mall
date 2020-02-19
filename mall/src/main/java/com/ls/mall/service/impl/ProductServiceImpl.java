package com.ls.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ls.mall.dao.ProductMapper;
import com.ls.mall.pojo.Product;
import com.ls.mall.service.ICategoryService;
import com.ls.mall.service.IProductService;
import com.ls.mall.vo.ProductDetailVo;
import com.ls.mall.vo.ProductVo;
import com.ls.mall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ls.mall.enums.ProductStatusEnum.DELETE;
import static com.ls.mall.enums.ProductStatusEnum.OFF_SALE;
import static com.ls.mall.enums.ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE;

/**
 * @className: ProductServiceImpl
 * @description:
 * @author: liusCoding
 * @create: 2020-02-16 19:37
 */
@Service
@Slf4j
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ProductMapper productMapper;

    /**
     * @param categoryId
     * @param pageNum
     * @param pageSize
     * @date: 2020/2/16
     * @return: javax.xml.ws.Response<java.util.List < com.ls.mall.vo.ProductVo>>
     **/
    @Override
    public ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Set<Integer> categoryIdSet = new HashSet<>();
        if (Objects.nonNull(categoryId)) {
            categoryService.findSubCategoryId(categoryId, categoryIdSet);
            categoryIdSet.add(categoryId);
        }
        List<Product> products = productMapper.selectByCategoryIdSet(categoryIdSet);

        List<ProductVo> productVoList = products.stream().map(product -> {
            ProductVo productVo = new ProductVo();
            BeanUtils.copyProperties(product, productVo);
            return productVo;
        }).collect(Collectors.toList());

        log.info("products={}", products);
        PageInfo pageInfo = new PageInfo(products);
        pageInfo.setList(productVoList);


        return ResponseVo.success(pageInfo);
    }

    /**
     * 根据商品id 查询商品详情
     *
     * @param productId 商品id
     * @date: 2020/2/17
     * @return: com.ls.mall.vo.ResponseVo<com.ls.mall.vo.ProductDetailVo>
     **/
    @Override
    public ResponseVo<ProductDetailVo> detail(Integer productId) {

        Product product = productMapper.selectByPrimaryKey(productId);

        //只对确定性的条件判断
        if (product.getStatus().equals(OFF_SALE.getCode())
                || product.getStatus().equals(DELETE.getCode())) {
            return ResponseVo.error(PRODUCT_OFF_SALE_OR_DELETE);
        }

        ProductDetailVo productDetailVo = new ProductDetailVo();
        BeanUtils.copyProperties(product, productDetailVo);
        //敏感数据的处理
        productDetailVo.setStock(product.getStock() > 100 ? 100 : product.getStock());
        return ResponseVo.success(productDetailVo);
    }
}
