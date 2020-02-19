package com.ls.mall.service.impl;

import com.ls.mall.constants.MallConst;
import com.ls.mall.dao.CategoryMapper;
import com.ls.mall.pojo.Category;
import com.ls.mall.service.ICategoryService;
import com.ls.mall.vo.CategoryVo;
import com.ls.mall.vo.ResponseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @className: CategoryServiceImpl
 * @description: 分类ServiceImpl
 * @author: liusCoding
 * @create: 2020-02-16 17:42
 */

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 查询所有分类
     *
     * @date: 2020/2/16
     * @return: com.ls.mall.vo.ResponseVo<java.util.List < com.ls.mall.vo.CategoryVo>>
     **/
    @Override
    public ResponseVo<List<CategoryVo>> selectAll() {
        List<Category> categories = categoryMapper.selectAll();

        //查找父目录
        List<CategoryVo> categoryVoList = categories.stream()
                .filter(category -> category.getParentId().equals(MallConst.ROOT_PARENT_ID))
                .map(this::category2CategoryVo)
                .sorted(Comparator.comparing(CategoryVo::getSortOrder).reversed())
                .collect(Collectors.toList());

        //查找子目录
        findSubCategory(categoryVoList, categories);
        return ResponseVo.success(categoryVoList);
    }

    /**
     * 查找子类
     *
     * @param id
     * @param resultSet
     * @date: 2020/2/16
     * @return: void
     **/
    @Override
    public void findSubCategoryId(Integer id, Set<Integer> resultSet) {
        List<Category> categories = categoryMapper.selectAll();
        findSubCategoryId(id, resultSet, categories);

    }

    public void findSubCategoryId(Integer id, Set<Integer> resultSet, List<Category> categories) {
        for (Category category : categories) {
            if (category.getParentId().equals(id)) {
                resultSet.add(category.getId());

                findSubCategoryId(category.getId(), resultSet, categories);
            }
        }
    }


    /**
     * 查找子分类
     *
     * @param categoryVoList 父分类
     * @param categories     所有分类
     * @date: 2020/2/16
     * @return: void
     **/
    public void findSubCategory(List<CategoryVo> categoryVoList, List<Category> categories) {
        for (CategoryVo categoryVo : categoryVoList) {
            List<CategoryVo> subCategoryVoList = new ArrayList<>();
            for (Category category : categories) {
                //如果查到内容，设置subCategory，继续往下查
                if (categoryVo.getId().equals(category.getParentId())) {
                    CategoryVo subCategoryVo = category2CategoryVo(category);
                    subCategoryVoList.add(subCategoryVo);
                }
                //按sortOrder倒叙排列
                subCategoryVoList.sort(Comparator.comparing(CategoryVo::getSortOrder).reversed());
                categoryVo.setSubCategories(subCategoryVoList);
                //递归查询
                findSubCategory(subCategoryVoList, categories);
            }
        }
    }

    /**
     * 分类转换成VO
     *
     * @param category 分类
     * @date: 2020/2/16
     * @return: com.ls.mall.vo.CategoryVo
     **/
    private CategoryVo category2CategoryVo(Category category) {
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }
}
