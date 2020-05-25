package com.edu.gulimail.search.controller;

import com.edu.gulimail.search.service.MallSearchService;
import com.edu.gulimail.search.vo.SearchParamVo;
import com.edu.gulimail.search.vo.SearchResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * @Author: DDG
 * @Date: 2020/5/21 12:04
 * @Description:
 */
@Controller
public class ListController {

    @Resource
    private MallSearchService mallSearchService;

    @GetMapping("/list.html")
    public String listPage( SearchParamVo searchParamVo, Model model){
        System.out.println(searchParamVo);
        SearchResult search = mallSearchService.search(searchParamVo);
        model.addAttribute("search", search);
        /*将页面提交的请求参数封装成*/
        return "index";
    }
    @GetMapping("hello.html")
    public String hello(){
        return "hello";
    }
}
