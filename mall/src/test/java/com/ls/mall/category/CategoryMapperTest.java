package com.ls.mall.category;

import com.ls.mall.dao.CategoryMapper;
import com.ls.mall.pojo.Category;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @className: CategoryMapperTest
 * @description:
 * @author: liusCoding
 * @create: 2019-11-21 18:20
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CategoryMapperTest {
    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void findById(){
        Category category = categoryMapper.findById(100003);
        
        log.info("【分类】：{}",category);
        Assert.assertNotNull(category);

    }

    @Test
    public void queryById(){
        Category category = categoryMapper.queryById(100003);
        log.info("【分类】：{}",category);
        Assert.assertNotNull(category);
    }
}
