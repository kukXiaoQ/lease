package com.situ.ws.service;

import com.situ.ws.bean.entries.Users;

public interface MsgService {

    // 发送邮件
    void sendMsg(Users email);

}
