package com.example.controller;

import com.example.common.Const;
import com.example.pojo.User;
import com.example.pojo.UserInfo;
import com.example.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import utils.ServerResponse;

import javax.servlet.http.HttpSession;

@RestController //所有方法的返回值都是json
public class UserController {

    @Autowired
    IUserService userService;

    @RequestMapping(value = "/portal/user/login.do")
    public ServerResponse login(String username, String password, HttpSession session){
        ServerResponse serverResponse = userService.loginLogic(username, password);
        if(serverResponse.isSuccess()){
            //判断用户是否登录成功
            session.setAttribute(Const.CURRENT_USER, serverResponse.getData());
        }
        return serverResponse;
    }

    //注册
    @RequestMapping(value = "/portal/user/register.do")
    public ServerResponse register(User user){
        return userService.registerLogic(user);
    }

    @Value("${jdbc.url}")
    private String jdbcUrl;



    @RequestMapping(value = {"/restful/login/{username}/{password}"})
    public UserInfo loginRestful(@PathVariable("username") String username,@PathVariable("password") String password){

        UserInfo userInfo = new UserInfo();
        userInfo.setId(1);
        userInfo.setUsername(username);
        userInfo.setPassword(password);

        return userInfo;
    }

    @RequestMapping("/test")
    public String test(){
        return jdbcUrl;
    }
}
