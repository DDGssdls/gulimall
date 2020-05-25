package com.edu.gulimail.product.service.impl;

import com.edu.gulimail.product.dao.BrandDao;
import com.edu.gulimail.product.dao.CategoryDao;
import com.edu.gulimail.product.entity.BrandEntity;
import com.edu.gulimail.product.entity.CategoryEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.common.utils.PageUtils;
import com.edu.common.utils.Query;

import com.edu.gulimail.product.dao.CategoryBrandRelationDao;
import com.edu.gulimail.product.entity.CategoryBrandRelationEntity;
import com.edu.gulimail.product.service.CategoryBrandRelationService;

import javax.annotation.Resource;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

    @Resource
    private BrandDao brandDao;
    @Resource
    private CategoryDao categoryDao;



    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveDatil(CategoryBrandRelationEntity categoryBrandRelation) {
        // 通过这两个id进行查询
        Long brandId = categoryBrandRelation.getBrandId();
        Long catelogId = categoryBrandRelation.getCatelogId();
        BrandEntity brandEntity = brandDao.selectById(brandId);
        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
        categoryBrandRelation.setBrandName(brandEntity.getName());
        categoryBrandRelation.setCatelogName(categoryEntity.getName());
        // 使用service进行保存
        this.save(categoryBrandRelation);

    }

    @Override
    public List<CategoryBrandRelationEntity> getBrandListByCatId(Long catId) {
        QueryWrapper<CategoryBrandRelationEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("catelog_id", catId);

        List<CategoryBrandRelationEntity> categoryBrandRelationEntities = baseMapper.selectList(wrapper);
        return categoryBrandRelationEntities;


    }

}