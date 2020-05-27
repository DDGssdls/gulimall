package com.edu.gulimail.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.common.exception.GulimallSysException;
import com.edu.common.utils.PageUtils;
import com.edu.gulimail.member.entity.MemberEntity;
import com.edu.gulimail.member.vo.UserRegisterVo;

import java.util.Map;

/**
 * 会员
 *
 * @author DDG
 * @email 1306082199@qq.com
 * @date 2020-04-14 19:27:12
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void regist(UserRegisterVo userRegisterVo) throws GulimallSysException;

    boolean checkUserNameUnique(String userName) throws GulimallSysException;
    boolean checkPhoneNumUnique(String phoneNum) throws GulimallSysException;
}

