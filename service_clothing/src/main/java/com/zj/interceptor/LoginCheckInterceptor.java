package com.zj.interceptor;


import com.zj.annotation.UserAuthentication;
import com.zj.common.BaseContext;
import com.zj.common.Code;
import com.zj.entity.User;
import com.zj.exception.BusinessException;
import com.zj.utils.JwtUtil;
import com.zj.utils.RedisUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //跨域时会发送两个请求，先发送预检请求options，通过后再发送正确的请求，因此要先放行options
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }


        // 1. 获取token
        String token = request.getHeader("token");

        // 2.判断token是否为空
        if (ObjectUtils.isEmpty(token)) {
            throw new BusinessException(Code.LOGIN_ERROR, "未登录，请先登录");
        }

        //3.验证token，当验证失败时，抛出异常
        Claims tokenInfo = null;
        try {
            tokenInfo = JwtUtil.getTokenClaim(token);
        } catch (Exception e) {
            throw new BusinessException(Code.LOGIN_ERROR, "登录状态异常，请重新登录", e);
        }

        //4. 获取用户id
        Integer userId = Integer.parseInt(tokenInfo.get("userId").toString());

        //5. 根据用户id查询redis
        User user = redisUtils.getObject("user:" + userId, User.class);
        if (ObjectUtils.isEmpty(user)) {
            throw new BusinessException(Code.LOGIN_ERROR, "登录状态异常，请重新登录");
        }

        //6.判断是否有注解
        boolean haveAnnotataion = handler.getClass().isAssignableFrom(HandlerMethod.class);
        if (haveAnnotataion) {
            //如果有注解，判断是否是MyAnnotation
            UserAuthentication userAuthentication = ((HandlerMethod) handler).getMethodAnnotation(UserAuthentication.class);
            //如果存在该注解并且没有管理员权限
            if (userAuthentication != null && user.getUserRole().equals("USER")) {
                throw new BusinessException(Code.ERROR, "您没有权限访问该资源");
            }
        }

        //7 将用户redis key存入线程中，方便后面调用
        // BaseContext.setCurrentId("user:" + userId);
        BaseContext.setCurrentId(userId);
        return true;
    }
}
