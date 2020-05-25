package com.edu.gulimail.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.common.utils.PageUtils;
import com.edu.gulimail.product.entity.CategoryEntity;
import com.edu.gulimail.product.vo.CategoryLevelTwo;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author DDG
 * @email 1306082199@qq.com
 * @date 2020-04-14 19:34:06
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List listWithTree();
    List<CategoryEntity> getListTree();

    void removeMenuByIds(List<Long> asList);

    Long[] findCategoryPath(Long catelogId);

    List<CategoryEntity> getLevelOne(int level);

    Map<String, List<CategoryLevelTwo>> getCataLogJson();

}

