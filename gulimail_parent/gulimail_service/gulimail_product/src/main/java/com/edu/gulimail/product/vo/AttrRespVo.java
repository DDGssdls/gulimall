package com.edu.gulimail.product.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品属性
 * 
 * @author DDG
 * @email 1306082199@qq.com
 * @date 2020-04-14 19:34:06
 */
@Data
public class AttrRespVo extends AttrVo implements Serializable {
	private static final long serialVersionUID = 1L;
	// 所属的分类的名称
	private String catelogName;
	// 所属的分组名称
	private String groupName;

	private Long[] catlogPath;



}
