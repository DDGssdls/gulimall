package com.edu.gulimail.member.dao;

import com.edu.gulimail.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author DDG
 * @email 1306082199@qq.com
 * @date 2020-04-14 19:27:12
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
