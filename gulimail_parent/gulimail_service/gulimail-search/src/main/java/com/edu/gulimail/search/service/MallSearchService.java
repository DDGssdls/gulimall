package com.edu.gulimail.search.service;

import com.edu.gulimail.search.vo.SearchParamVo;
import com.edu.gulimail.search.vo.SearchResult;

import java.io.IOException;

/**
 * @Author: DDG
 * @Date: 2020/5/21 13:05
 * @Description: MallSearchService 接口
 */
public interface MallSearchService {

    SearchResult search(SearchParamVo paramVo) ;
}
