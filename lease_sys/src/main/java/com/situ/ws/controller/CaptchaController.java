package com.situ.ws.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.situ.ws.config.CaptchaProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    @Autowired
    private CaptchaProperties captchaProp;

    @RequestMapping("/get")
    public void getCaptcha(HttpServletResponse response, HttpSession session) {
        //定义图形验证码的长和宽(配置默认值)
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(captchaProp.getWidth(), captchaProp.getHeight());
        //设置返回类型
        response.setContentType("image/jpeg");
        //静止缓存
        response.setHeader("Progma", "No-cache");
        try {
            //图形验证码写出，可以写出到文件，也可以写出到流
            lineCaptcha.write(response.getOutputStream());
            //同时将验证码内容和当前时间戳存储到Session中
            //此处Session的键可以配置成常量
            session.setAttribute(captchaProp.getSession().getKey(), lineCaptcha.getCode());
            session.setAttribute(captchaProp.getSession().getDate(), new Date());
            //关流
            response.getOutputStream().close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //验证码生效时间限制
    private static final long VALID_MILLIS_TIME = 60 * 1000;

    @RequestMapping("/check")
    public boolean checkCaptcha(String captcha, HttpSession session) {
        //保证传过来的参数是合法的
        if (StringUtils.hasLength(captcha)) {
            //根据配置的默认session信息获取key和date
            String key = (String) session.getAttribute(captchaProp.getSession().getKey());
            Date date = (Date) session.getAttribute(captchaProp.getSession().getDate());

            System.out.println("验证码为=====>"+key);
            System.out.println("前端传入验证码为=====>"+captcha);
            //1.验证码正确(不区分大小写)  2.验证码还未失效
            return captcha.equalsIgnoreCase(key)
                    && System.currentTimeMillis() - date.getTime() < VALID_MILLIS_TIME;
        }
        return false;
    }
}