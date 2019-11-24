package com.ls.mall.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @className: Category
 * @description: 类别
 * @author: liusCoding
 * @create: 2019-11-21 17:51
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    private Integer id;

    /** 父类别id当id=0时说明是根节点,一级类别 */
    private Integer parentId;

    /** 分类名称 */
    private String name;

    /** 类别状态1-正常,2-已废弃 */
    private Integer status;

    /** 排序编号,同类展示顺序,数值相等则自然排序 */
    private Integer sortOrder;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;
}
