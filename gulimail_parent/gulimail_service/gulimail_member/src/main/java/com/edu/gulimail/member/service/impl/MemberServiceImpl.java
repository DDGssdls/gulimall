package com.edu.gulimail.member.service.impl;

import com.edu.common.exception.GulimallSysException;
import com.edu.gulimail.member.dao.MemberLevelDao;
import com.edu.gulimail.member.entity.MemberLevelEntity;
import com.edu.gulimail.member.vo.UserRegisterVo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.common.utils.PageUtils;
import com.edu.common.utils.Query;

import com.edu.gulimail.member.dao.MemberDao;
import com.edu.gulimail.member.entity.MemberEntity;
import com.edu.gulimail.member.service.MemberService;

import javax.annotation.Resource;


@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Resource
    private MemberLevelDao memberLevelDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void regist(UserRegisterVo userRegisterVo) throws GulimallSysException {
        MemberEntity memberEntity = new MemberEntity();
        // 进行用户名称 和手机号的检查 同时将 其加密保存
        String userName = userRegisterVo.getUserName();
        String phoneNum = userRegisterVo.getPhoneNum();
        if (checkUserNameUnique(userName)){
            memberEntity.setUsername(userName);
        }
        if (checkPhoneNumUnique(phoneNum)){
            memberEntity.setMobile(phoneNum);
        }
        // 进行加密操作
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        memberEntity.setPassword(bCryptPasswordEncoder.encode(userRegisterVo.getPassword()));
        memberEntity.setMobile(userRegisterVo.getPhoneNum());
        MemberLevelEntity levelEntity =  memberLevelDao.getDefaultLevel();
        memberEntity.setLevelId(levelEntity.getId());
        baseMapper.insert(memberEntity);

    }

    @Override
    public boolean checkUserNameUnique(String userName) throws GulimallSysException {
        Integer count = baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("username", userName));
        if (count == 0){
            return true;
        }else {
            throw  new GulimallSysException("用户名称需要唯一");
        }

    }

    @Override
    public boolean checkPhoneNumUnique(String phoneNum) throws GulimallSysException {
        Integer count = baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("mobile", phoneNum));
        if (count == 0){
            return true;
        }else {
            throw  new GulimallSysException("用户手机不能注册多个");
        }
    }

}