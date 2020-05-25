package com.edu.gulimail.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


import com.edu.gulimail.product.service.CategoryService;
import com.edu.gulimail.product.vo.AttrGroupVo;
import com.edu.gulimail.product.vo.AttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.edu.gulimail.product.entity.AttrGroupEntity;
import com.edu.gulimail.product.service.AttrGroupService;
import com.edu.common.utils.PageUtils;
import com.edu.common.utils.R;



/**
 * 属性分组
 *
 * @author DDG
 * @email 1306082199@qq.com
 * @date 2020-04-14 19:34:06
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 列表
     */
    @GetMapping("/list/{catelogId}")
    //@RequiresPermissions("product:attrgroup:list")
    public R list(@PathVariable("catelogId") Long catelogId,
                  @RequestParam Map<String, Object> params){
        //PageUtils page = attrGroupService.queryPage(params);
        PageUtils page = attrGroupService.queryPageByID(params, catelogId);


        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{attrGroupId}")
    //@RequiresPermissions("product:attrgroup:info")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
		AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        Long catelogId = attrGroup.getCatelogId();
        Long[] catelogIds = categoryService.findCategoryPath(catelogId);

		attrGroup.setCatelogPath(catelogIds);

        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("product:attrgroup:save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    //@RequiresPermissions("product:attrgroup:update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    //@RequiresPermissions("product:attrgroup:delete")
    public R delete(@RequestBody Long[] attrGroupIds){
		attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

    /**
     * /product/attrgroup/{catelogId}/withattr
     */

    @GetMapping("/{catelogId}/withattr")
    public R getWithAttrByCatId(@PathVariable("catelogId") Long catId){
        List<AttrGroupVo> attrVoList = attrGroupService.getWithAttrByCatId(catId);

        return R.ok().put("data", attrVoList);
    }

}
