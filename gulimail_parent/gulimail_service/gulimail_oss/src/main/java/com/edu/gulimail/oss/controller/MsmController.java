package com.edu.gulimail.oss.controller;


import com.edu.common.utils.R;
import com.edu.common.utils.RandomUtil;
import com.edu.gulimail.oss.service.MsmService;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {

    private final MsmService msmService;
    private final RedisTemplate<String, String> redisTemplate;


    @Autowired
    public MsmController(MsmService msmService, RedisTemplate<String, String> redisTemplate) {
        this.msmService = msmService;
        this.redisTemplate = redisTemplate;
    }

    @GetMapping("/send/{phoneNum}")
    public R getSendPhoneNum(@PathVariable("phoneNum") String phoneNum){
        // 先从redis中进行获取验证码
        String code = redisTemplate.opsForValue().get(phoneNum);
        boolean isSend = false;
        // 判断是否是在redis中有值
        if(StringUtils.isEmpty(code)){
            // 要是获取不到 使用阿里云进行发送
            code = RandomUtil.getSixBitRandom();
            Map<String, String> param = new HashMap<>();
            param.put("code", code);
            // 调用service进行发送：
           isSend = msmService.sendCodeByAly(param, phoneNum);
            if (isSend){
                // 成功发送之后 将数据放到redis中 设置超时时间 5分钟
                long l = System.currentTimeMillis();
                redisTemplate.opsForValue().set(phoneNum, code+"_"+l, 5L, TimeUnit.MINUTES);
            }
        }else{
            String[] s = code.split("_");
            long l = System.currentTimeMillis();
            if (l - Long.parseLong(s[1]) > 60000){
                return  R.error("不能再60秒内重复的发送");
            }

        }
        // 需要注意的一点就是阿里云只能进行验证码的发送 但是验证码的生成是需要手动的实现的
        // 生成验证码
        return R.ok().put("isSend", isSend);
    }
}
