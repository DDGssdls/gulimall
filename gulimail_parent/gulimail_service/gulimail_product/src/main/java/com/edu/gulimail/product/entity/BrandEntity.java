package com.edu.gulimail.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.edu.common.annotation.ListValue;
import com.edu.common.validgroup.AddGroup;
import com.edu.common.validgroup.UpdateGroup;
import lombok.Data;
import org.apache.ibatis.annotations.Update;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;

/**
 * 品牌
 * 
 * @author DDG
 * @email 1306082199@qq.com
 * @date 2020-04-14 19:34:06
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 品牌id
	 */
	@TableId
	@NotNull(message = "添加的时候必须指定品牌的id", groups = {UpdateGroup.class})
	@Null(message = "新增的时候不能指定品牌的id", groups = {AddGroup.class})
	private Long brandId;
	/**
	 * 品牌名
	 */
	@NotBlank(message = "商品的品牌名称不能为空", groups = {AddGroup.class})
	private String name;
	/**
	 * 品牌logo地址 要是添加的是否必须传入 且是一个URL地址 但是
	 */
	//@NotBlank(message = "品牌的logo不能为空 至少需要一个非空字符", groups = {AddGroup.class})
	//@URL(message = "品牌的logo必须是一个合法的URL地址", groups = {AddGroup.class, UpdateGroup.class})
	private String logo;
	/**
	 * 介绍
	 */
	@NotBlank(message = "品牌的介绍不能为空", groups = {AddGroup.class})
	private String descript;
	/**
	 * 显示状态[0-不显示；1-显示]
	 */
	@ListValue(values = {0, 1}, groups = {AddGroup.class})
	private Integer showStatus;
	/**
	 * 检索首字母 需要注意的就是java中正则是不需要使用//
	 */
	@NotNull(message = "检索的首字母不能为空", groups = {AddGroup.class})
	@Pattern(regexp = "^[a-zA-z]$", message = "用于检索的首字母必须是A-Z或者是a-z之间", groups = {AddGroup.class})
	private String firstLetter;
	/**
	 * 排序
	 */
	@NotNull(message = "排序字段不能为空", groups = {AddGroup.class})
	@Min(value = 0, message = "排序必须是大于等于零的数字")
	private Integer sort;

}
