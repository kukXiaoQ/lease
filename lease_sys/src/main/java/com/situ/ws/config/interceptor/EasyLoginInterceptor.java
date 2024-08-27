package com.situ.ws.config.interceptor;

import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.situ.ws.util.JWTUtil;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

public class EasyLoginInterceptor {
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		response.setContentType("application/json;charset=utf-8");
		// 1.获取请求头种的token
		String token = request.getHeader("token");
		// 2.判断token是否为null
		if (StringUtils.hasText(token)) {
			// 校验token是否合法
			if (JWTUtil.verifyToken(token)) {
				return true;
			}
		}
		PrintWriter writer = response.getWriter();
		HashMap<Integer, String> map = new HashMap<>();
		map.put(4001,"未登录");
		String jsonString = JSON.toJSONString(map);
		writer.print(jsonString);
		writer.flush();
		writer.close();
		return false;
	}
}
