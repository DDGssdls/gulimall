package com.eud.gulimall.auth.controllerr;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.edu.common.constant.LoginUser;
import com.edu.common.utils.R;
import com.eud.gulimall.auth.fegin.MemberRegisterService;
import com.eud.gulimall.auth.fegin.OssFeignService;
import com.eud.gulimall.auth.vo.MemberVo;
import com.eud.gulimall.auth.vo.UserInfoVo;
import com.eud.gulimall.auth.vo.UserLoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: DDG
 * @Date: 2020/5/26 10:48
 * @Description:
 */
@Controller
public class LoginController {

    @Resource
    private OssFeignService ossFeignService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private MemberRegisterService memberRegisterService;


    @GetMapping(value = {"/login.html", "/index.html", "/"})
    public String login(HttpSession session){
        Object attribute = session.getAttribute(LoginUser.LOGIN_USER);
        return attribute == null? "login": "redirect:http://gulimall.com";

    }
    @GetMapping(value = {"/registor.html"})
    public String registor(){
        return "registor";
    }

   @GetMapping("/sms/sendcode/{phone}")
   @ResponseBody
    public R sendCode(@PathVariable("phone") String phone){
       return ossFeignService.getSendPhoneNum(phone);
   }
    // 这里不是使用json 进行数据的提交 而是使用form表单 不能加上 requestBody

    /**
     * TODO 分布式的session 会出现问题
     * @param userInfoVo
     * @param bindingResult
     * @param redirectAttributes 使用重定向 将表单 进行提交 防止重复提交 这个参数就是 重定向
     *                           是两次请求 能将数据 放到重定向的中
     *                           将数据放到其实就是放到session中
     *                           map<String , Object> model modelMap modelandview
     * @return
     */
    @PostMapping("/regist")
    public String register(@Valid UserInfoVo userInfoVo, BindingResult bindingResult, RedirectAttributes redirectAttributes){
       if (bindingResult.hasErrors()){
           Map<String, String> collect = bindingResult.getFieldErrors()
                   .stream()
                   .collect(Collectors.toMap(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage));
           System.out.println(collect);
           redirectAttributes.addFlashAttribute("errorMsg", collect);
           return "redirect:http://auth.gulimall.com/registor.html";
       }
        // 上述步骤没有问题 进行验证码的确认 ： 使用的是redis
        String s = stringRedisTemplate.opsForValue().get(userInfoVo.getPhoneNum());
       if (!StringUtils.isEmpty(s)){
           String[] code_time = s.split("_");
           String redisStoreCode = code_time[0];
           if (userInfoVo.getCode().equals(redisStoreCode)){
               // 进行远程服务的调用进行注册 首先就是将令牌进行删除
               stringRedisTemplate.delete(userInfoVo.getPhoneNum());
               // 远程服务调用：
               R r = memberRegisterService.userRegister(userInfoVo);
               if (r.getCode() == 0){
                   return "redirect:http://auth.gulimall.com/login.html";
               }else{
                   redirectAttributes.addFlashAttribute("errorMsg", r.get("msg"));
                   return "redirect:http://auth.gulimall.com/registor.html";
               }
           }
       }
       Map<String, Object> map = new HashMap<>();
       map.put("codeError", "您输入的验证码 过期 或者是错误");
        redirectAttributes.addFlashAttribute("errorMsg", map);
        return "redirect:http://auth.gulimall.com/registor.html";
   }

    @PostMapping("/login")
    public String login(UserLoginVo userLoginVo, RedirectAttributes redirectAttributes, HttpSession session){
        // 调用远程服务进行登录
        R login = memberRegisterService.login(userLoginVo);
        if (login.getCode() == 0){
            Object login1 =  login.get("login");
            // 进行逆转
            String s = JSON.toJSONString(login1);
            MemberVo memberVo = JSON.parseObject(s, new TypeReference<MemberVo>() {
            });
            session.setAttribute(LoginUser.LOGIN_USER, memberVo);
            return "redirect:http://gulimall.com";
        }else{
            redirectAttributes.addFlashAttribute("errorMsg", login.get("msg"));
            return "redirect:http://auth.gulimall.com/login.html";
            //return "login";
        }
    }
}
