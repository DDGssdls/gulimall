package com.edu.gulimail.cart.interceptor;

import com.edu.common.constant.CartInfo;
import com.edu.common.constant.LoginUser;
import com.edu.common.to.MemberInfoTo;
import com.edu.gulimail.cart.vo.UserInfoTo;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @Author: DDG
 * @Date: 2020/5/31 14:37
 * @Description: CartInterceptor 类：判断用户的登录信息 是否是登录 并且封装用户的登录状态信息
 * 实现Interceptor 进行放行 返回 true 要是不进行放行 返回false
 * 要想使用interceptor 需要在配置中添加 拦截器 使用的是addInterceptors方法 添加之后设置 拦截的路径
 */
@Component
public class CartInterceptor implements HandlerInterceptor {

    private static ThreadLocal<UserInfoTo> threadLocal = new ThreadLocal<>();

    public static ThreadLocal<UserInfoTo> getThreadLocal() {
        return threadLocal;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        UserInfoTo userInfoTo = new UserInfoTo();
        HttpSession session = request.getSession();
        // 这里获取的session是 SpringSession进行包装之后的Session对象 从redis中进行Session的获取
        MemberInfoTo attribute = (MemberInfoTo) session.getAttribute(LoginUser.LOGIN_USER);
        // 进行登录信息的封装
        if (attribute != null){
            userInfoTo.setUserId(attribute.getId());

        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0){
            for (Cookie cookie : cookies) {
                if(CartInfo.TEMP_USER_KEY_NAME.equals(cookie.getName())){
                    userInfoTo.setUserKey(cookie.getValue());
                    userInfoTo.setTempUser(true);
                }
            }
        }
        // 要是第一次使用没有 临时用户 创建一个临时用户进行关联
        if (StringUtils.isEmpty(userInfoTo.getUserKey())){
            String uuid = UUID.randomUUID().toString();
            userInfoTo.setUserKey(uuid);
        }

        // 放行前进行数据的封装
        threadLocal.set(userInfoTo);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
        // 上一步 要是没有 用户创建一个临时用户进行保存
        // 将用户进行保存 不能创建 共享变量获取 而是 使用ThreadLocal进行获取
        UserInfoTo userInfoTo = threadLocal.get();
        Cookie cookie = new Cookie(CartInfo.TEMP_USER_KEY_NAME, userInfoTo.getUserKey());
        // 这里进行判断 不要进行cookie的时间延长
        if (!userInfoTo.isTempUser()){
            cookie.setMaxAge(CartInfo.COOKIE_MAX_AGE);
            cookie.setDomain(CartInfo.DOMAIN);
            response.addCookie(cookie);
        }


    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
