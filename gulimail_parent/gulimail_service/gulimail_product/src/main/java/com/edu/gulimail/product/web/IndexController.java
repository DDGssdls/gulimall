package com.edu.gulimail.product.web;

import com.edu.common.constant.CategoryLevel;
import com.edu.gulimail.product.entity.CategoryEntity;
import com.edu.gulimail.product.service.CategoryService;
import com.edu.gulimail.product.vo.CategoryLevelTwo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author: DDG
 * @Date: 2020/5/17 15:43
 * @Description:
 */
@Controller
public class IndexController {

    @Resource
    private CategoryService categoryService;


    @GetMapping(value = {"/index.html", "/"})
    public String index(Model model){
        // 使用视图解析解析器进行视图的解析 在 jsp中已经是非常的了解了 这里就是不再详述
        // 就是 从 classpath： templates + 逻辑视图名称 + .html 要是internalViewResolver 就是 + .jsp
       /* modelAndView.setViewName();
        modelAndView.addObject();*/
       // 进行的是一级分类的查询： 导入dev tools  使用ctrl + shift + f9进行重新编译
       List<CategoryEntity> categoryEntities =  categoryService.getLevelOne(CategoryLevel.LEVEL_ONE);
       //将数据放入到 model 中
        model.addAttribute("categoryOne", categoryEntities);
        return "index";
    }
    @ResponseBody
    @GetMapping("/index/catalog.json")
    public Map<String, List<CategoryLevelTwo>> getCatalogJson(){

        Map<String, List<CategoryLevelTwo>> catalogMap =  categoryService.getCataLogJson();
        return catalogMap;

    }
}
