package com.edu.gulimail.product.controller;

import java.util.Arrays;
import java.util.Map;


import com.edu.common.validgroup.AddGroup;
import com.edu.common.validgroup.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.edu.gulimail.product.entity.BrandEntity;
import com.edu.gulimail.product.service.BrandService;
import com.edu.common.utils.PageUtils;
import com.edu.common.utils.R;

import javax.validation.Valid;


/**
 * 品牌
 *
 * @author DDG
 * @email 1306082199@qq.com
 * @date 2020-04-14 19:34:06
 */
@RestController
@RequestMapping("product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @GetMapping("/list")
    //@RequiresPermissions("product:brand:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = brandService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{brandId}")
    //@RequiresPermissions("product:brand:info")
    public R info(@PathVariable("brandId") Long brandId){
		BrandEntity brand = brandService.getById(brandId);

        return R.ok().put("brand", brand);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("product:brand:save")
    public R save(@RequestBody @Validated(AddGroup.class) BrandEntity brand){
		brandService.save(brand);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    //@RequiresPermissions("product:brand:update")
    public R update(@RequestBody @Validated(UpdateGroup.class) BrandEntity brand){
		brandService.updateById(brand);

        return R.ok();
    }
    /**
     * 修改显示状态：
     */
    @PostMapping("/update/status")
    //@RequiresPermissions("product:brand:update")
    public R updateStatus(@RequestParam("brandId") Long brandId, @RequestParam("showStatus") Integer showStatus){
        brandService.updateStatusById(brandId, showStatus);

        return R.ok();
    }
    /**
     * 删除
     */
    @DeleteMapping("/delete")
    //@RequiresPermissions("product:brand:delete")
    public R delete(@RequestBody Long[] brandIds){
		brandService.removeByIds(Arrays.asList(brandIds));

        return R.ok();
    }

}
