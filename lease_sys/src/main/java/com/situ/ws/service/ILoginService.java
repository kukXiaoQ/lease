package com.situ.ws.service;

import com.situ.ws.bean.entries.Users;

public interface ILoginService {

    String doLogin(Users users);

    void sendMessage(Users users);
}
