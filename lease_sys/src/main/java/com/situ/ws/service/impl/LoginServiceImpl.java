package com.situ.ws.service.impl;

import com.situ.ws.bean.entries.Users;
import com.situ.ws.service.ILoginService;
import com.situ.ws.service.IUsersService;
import com.situ.ws.service.MsgService;
import com.situ.ws.util.DigestUtilsByMe;
import com.situ.ws.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements ILoginService {


    public static void main(String[] args) {
        String pass ="123123124";
//        5f1882ab59c97ac95cdc543493aeb2cc

        String s = DigestUtils.md5Hex(pass);
        System.out.println(s);
    }

    private final IUsersService usersService;
    private final MsgService msgService;

    private boolean eqPassword(String password,String inputData){

//        DigestUtils.

        return password.equals(inputData);
    }

    @Override
    public String doLogin(Users users) {

        // 1.获取数据库的数据
        // 1.1数据库中，用户名需要唯一，或者需要使用手机号登录
        Users user = usersService.getUserByPhone(users);

        if (user == null){
            return null;
        }

        // 比对密码
        boolean flag = DigestUtilsByMe.decode(users.getPassword(),user.getPassword());

        if (!flag){
            return null;
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("username",user.getUsername());
        map.put("password",user.getPassword());
        // 存储用户id
        map.put("userId",user.getId());


        return JWTUtil.createToken(map);
    }

    @Override
    public void sendMessage(Users users) {
        if (users.getPhone() == null || users.getEmail() == null){
            throw new RuntimeException("填写信息不能为空！");
        }

        // 根据用户名、邮箱，查询
        Users user = usersService.getUserByPhone(users);

        // 如果用户不存在，提示失败
        if (user == null)
            throw new RuntimeException("当前用户不存在！");

        System.out.println(user.getEmail());
        // 给用户发送邮件,邮件地址
        msgService.sendMsg(user);
    }
}
