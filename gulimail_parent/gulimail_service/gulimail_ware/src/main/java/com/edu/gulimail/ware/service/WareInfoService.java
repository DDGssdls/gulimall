package com.edu.gulimail.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.common.utils.PageUtils;
import com.edu.gulimail.ware.entity.WareInfoEntity;

import java.util.Map;

/**
 * 仓库信息
 *
 * @author DDG
 * @email 1306082199@qq.com
 * @date 2020-04-14 19:35:20
 */
public interface WareInfoService extends IService<WareInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageByCondition(Map<String, Object> params);
}

