package com.sy.interceptor;

import com.alibaba.fastjson.JSON;
import com.sy.util.JwtTokenUtil;
import com.sy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenInterceptor implements HandlerInterceptor {
    @Autowired
    JwtTokenUtil JwtTokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        System.out.println(requestURI);
        Result result = new Result();
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            System.out.println("OPTIONS请求，放行");
            return true;
        }
        //1.获取token,先校验是否合法，再校验是否过期
        String token = request.getHeader("authorization");
        if (token == null) {
            result.setMsg("please login!!");
        } else {
            //2.如果不合法或已过期或没有token，则让其重新登录
            Boolean valid = JwtTokenUtil.validateToken(token);
            if (valid) {
                //有效,则放行
                System.out.println("成功！！！");
                return true;
            } else {
                Boolean tokenExpired = JwtTokenUtil.isTokenExpired(token);
                if (tokenExpired) {
                    result.setMsg("token expired,please login!!");
                } else {
                    result.setMsg("token is invalid,please login!!");
                }
            }
        }
        result.setCode(1);
        response.getWriter().write(JSON.toJSONString(result));
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
