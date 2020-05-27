package com.edu.gulimail.member.controller;

import java.util.Arrays;
import java.util.Map;


import com.edu.common.exception.GulimallSysException;
import com.edu.gulimail.member.feign.CouponFeignService;
import com.edu.gulimail.member.vo.UserRegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.edu.gulimail.member.entity.MemberEntity;
import com.edu.gulimail.member.service.MemberService;
import com.edu.common.utils.PageUtils;
import com.edu.common.utils.R;



/**
 * 会员
 *
 * @author DDG
 * @email 1306082199@qq.com
 * @date 2020-04-14 19:27:12
 */
@RestController
@RequestMapping("member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private CouponFeignService couponFeignService;

    /**
     * 列表
     */
    @GetMapping("/list")
    //@RequiresPermissions("member:member:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    //@RequiresPermissions("member:member:info")
    public R info(@PathVariable("id") Long id){
		MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions("member:member:save")
    public R save(@RequestBody MemberEntity member){
		memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    //@RequiresPermissions("member:member:update")
    public R update(@RequestBody MemberEntity member){
		memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    //@RequiresPermissions("member:member:delete")
    public R delete(@RequestBody Long[] ids){
		memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
    /**
     * 获取所有的用户的优惠券
     * */
    @GetMapping("/coupons")
    public R getCoupons(){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setUsername("张三");
        memberEntity.setNickname("张三");
        R r = couponFeignService.memberCoupons();
        return R.ok().put("member", memberEntity).put("coupons", r.get("coupons"));
    }

    @PostMapping("/regist")
    public R userRegister(@RequestBody UserRegisterVo userRegisterVo){
        try {
            memberService.regist(userRegisterVo);
        } catch (GulimallSysException e) {
            R.error(e.getMessage());
        }
        return R.ok();
    }
}
