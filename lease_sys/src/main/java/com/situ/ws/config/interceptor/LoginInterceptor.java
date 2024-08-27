package com.situ.ws.config.interceptor;

import com.alibaba.fastjson.JSON;
import com.situ.ws.bean.common.CommonResult;
import com.situ.ws.util.JWTUtil;
import com.situ.ws.util.LeaseThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class LoginInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 需要放行
        String token = request.getHeader("Authorization");

        // 解析token,是否有效
        if (token != null && JWTUtil.verifyToken(token)){

            // 获取userId
            Map<String, Object> map = JWTUtil.decodeJWT(token);
            LeaseThreadLocal.set((Integer) map.get("userId"));

            // 1.登录，2.忘记密码，3.获取验证码
            return true;
        }

        CommonResult result = CommonResult.fail(400,"未登录");
        String s = JSON.toJSONString(result);

        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(s);

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
