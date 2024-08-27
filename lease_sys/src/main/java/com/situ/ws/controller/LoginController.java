package com.situ.ws.controller;

import com.situ.ws.bean.common.CommonResult;
import com.situ.ws.bean.entries.Users;
import com.situ.ws.service.ILoginService;
import com.situ.ws.service.MsgService;
import com.situ.ws.util.JWTUtil;
import com.situ.ws.util.RegexUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/logins")
@Slf4j
@RequiredArgsConstructor
public class LoginController {


    private final ILoginService loginService;



    @PostMapping("/msg")
    public CommonResult sendMessage(@RequestBody Users users){

        loginService.sendMessage(users);
        return CommonResult.success(200,"验证码发送成功！");
    }



    @PostMapping
    public CommonResult login(@RequestBody Users users) {
        log.error("login:{}",users);

        String token = loginService.doLogin(users);

        if (token == null){
            return CommonResult.fail(401,"用户名或密码错误");
        }

        Object username = JWTUtil.decodeJWT(token).get("username");


        Map<String,Object> map = new HashMap<>();
        map.put("username",username);
        map.put("token",token);

        return CommonResult.success(map);
    }

    @GetMapping
    public CommonResult checkToken(HttpServletRequest request) {

        String token = request.getHeader("Authorization");

        if (token == null || !JWTUtil.verifyToken(token)){
            return CommonResult.fail(401,"认证超时");
        }

        return CommonResult.success();
    }
}
