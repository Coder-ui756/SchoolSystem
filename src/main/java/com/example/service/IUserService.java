package com.example.service;

import com.example.pojo.User;
import utils.ServerResponse;

public interface IUserService {

    /**
     * 登录
     **/
    public ServerResponse loginLogic(String username, String password);
    /**
     * 注册
     */
    public ServerResponse registerLogic(User user);
}
