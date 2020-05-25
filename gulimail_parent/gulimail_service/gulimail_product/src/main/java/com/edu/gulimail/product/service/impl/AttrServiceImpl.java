package com.edu.gulimail.product.service.impl;

import com.edu.gulimail.product.entity.AttrAttrgroupRelationEntity;
import com.edu.gulimail.product.entity.AttrGroupEntity;
import com.edu.gulimail.product.entity.CategoryEntity;
import com.edu.gulimail.product.service.AttrAttrgroupRelationService;
import com.edu.gulimail.product.service.AttrGroupService;
import com.edu.gulimail.product.service.CategoryService;
import com.edu.gulimail.product.vo.AttrRespVo;
import com.edu.gulimail.product.vo.AttrVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.common.utils.PageUtils;
import com.edu.common.utils.Query;

import com.edu.gulimail.product.dao.AttrDao;
import com.edu.gulimail.product.entity.AttrEntity;
import com.edu.gulimail.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Resource
    private AttrAttrgroupRelationService attrAttrgroupRelationService;

    @Resource
    private AttrGroupService attrGroupService;

    @Resource
    private CategoryService categoryService;



    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageByCategoryId(Map<String, Object> params, Long catelogId, String attrType) {
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (StringUtils.isEmpty(key)){
            queryWrapper.and((wrapper) ->{ wrapper.eq("attr_id", key)
                    .or().likeRight("attr_name", key);});
        }
        IPage<AttrEntity> page;
        if (catelogId == 0){
            page = this.page(new Query<AttrEntity>().getPage(params),
                    queryWrapper);
        }else{
            page = this.page(new Query<AttrEntity>().getPage(params),
                    queryWrapper.eq("catelog_id", catelogId));

        }
        PageUtils pageUtils = new PageUtils(page);
        List<AttrEntity> records = page.getRecords();
        List<AttrRespVo> attr_id = records.stream().map(attrEntity -> {
            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtils.copyProperties(attrEntity, attrRespVo);
            // 进行中间的表的查询 进行分组的查询
            AttrAttrgroupRelationEntity one = attrAttrgroupRelationService.
                    getOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));
            // 使用分类的组id进行组名称的查询
            if (one != null) {
                Long attrGroupId = one.getAttrGroupId();
                AttrGroupEntity byId = attrGroupService.getById(attrGroupId);
                String attrGroupName = byId.getAttrGroupName();
                // 进行组名称的设置：
                attrRespVo.setGroupName(attrGroupName);
                System.out.println(attrGroupName);
            }
            CategoryEntity byId = categoryService.getById(attrEntity.getCatelogId());
            if (byId != null) {
                // 进行分类的设置
                String name = byId.getName();
                attrRespVo.setCatelogName(name);
            }
            return attrRespVo;
        }).collect(Collectors.toList());
        pageUtils.setList(attr_id);

        return pageUtils;
    }
    @Transactional
    @Override
    public void saveAttrVo(AttrVo attr) {
        // 将页面中的值进行封装
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        // 进行基本的数据的保存：
        this.save(attrEntity);
        // 保存关联关系：
        AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
        attrAttrgroupRelationEntity.setAttrGroupId(attr.getAttrGroupId());
        attrAttrgroupRelationEntity.setAttrId(attr.getAttrId());
        attrAttrgroupRelationService.save(attrAttrgroupRelationEntity);
    }

    @Override
    public AttrRespVo getAttrInfo(Long attrId) {
        AttrRespVo attrRespVo = new AttrRespVo();
        AttrEntity byId = this.getById(attrId);
        BeanUtils.copyProperties(byId, attrRespVo);
        return null;



    }

    @Override
    public List<Long> getCanSearchAttrIds(List<Long> attrIds) {
        List<Long> attrs = baseMapper.getCanSearchAttrIds(attrIds);
        return attrs;
    }


}