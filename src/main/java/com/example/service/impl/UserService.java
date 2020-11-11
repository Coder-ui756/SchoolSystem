package com.example.service.impl;

import com.example.common.ResponseCode;
import com.example.dao.UserMapper;
import com.example.pojo.User;
import com.example.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.MD5Utils;
import utils.ServerResponse;

@Service
public class UserService implements IUserService {

    @Autowired
    UserMapper userMapper;
    @Override
    public ServerResponse loginLogic(String username, String password){
        //step1: 用户名和密码的非空判断
        if(StringUtils.isBlank(username)){
            return ServerResponse.createServerResponseByFail(ResponseCode.USERNAME_NOT_EMPTY.getCode(),ResponseCode.USERNAME_NOT_EMPTY.getMsg());
        }
        if((StringUtils.isBlank(password))){
            return ServerResponse.createServerResponseByFail(ResponseCode.PASSWORD_NOT_EMPTY.getCode(),ResponseCode.PASSWORD_NOT_EMPTY.getMsg());
        }
        //step2: 用户名是否存在
        int count = userMapper.findByUsername(username);
        if(count==0){
            //用户名不存在
            return ServerResponse.createServerResponseByFail(ResponseCode.USERNAME_NOT_EXISTS.getCode(),ResponseCode.USERNAME_NOT_EXISTS.getMsg());
        }
        //step3: 根据用户名和密码查询
        User user = userMapper.findByUsernameAndPassword(username, MD5Utils.getMD5Code(password));
        if(user==null){
            return ServerResponse.createServerResponseByFail(ResponseCode.PASSWORD_ERROR.getCode(), ResponseCode.PASSWORD_ERROR.getMsg());
        }
        //step4: 返回结果

        return ServerResponse.createServerResponseBySuccess(user);
    }

    @Override
    public ServerResponse registerLogic(User user) {
        if(user==null){
            return ServerResponse.createServerResponseByFail(ResponseCode.PARAMTER_NOT_EMPTY.getCode(),ResponseCode.PARAMTER_NOT_EMPTY.getMsg());
        }

        String username = user.getUsername();
        String password = user.getPassword();
        String email = user.getEmail();
        String phone = user.getPhone();
        int role = user.getRole();

        //非空判断
        if(StringUtils.isBlank(username)){
            return ServerResponse.createServerResponseByFail(ResponseCode.USERNAME_NOT_EMPTY.getCode(),ResponseCode.USERNAME_NOT_EMPTY.getMsg());
        }
        if(StringUtils.isBlank(password)){
            return ServerResponse.createServerResponseByFail(ResponseCode.PASSWORD_NOT_EMPTY.getCode(),ResponseCode.PASSWORD_NOT_EMPTY.getMsg());
        }
        if(StringUtils.isBlank(email)){
            return ServerResponse.createServerResponseByFail(ResponseCode.EMAIL_NOT_EMPTY.getCode(),ResponseCode.EMAIL_NOT_EMPTY.getMsg());
        }
        if(StringUtils.isBlank(phone)){
            return ServerResponse.createServerResponseByFail(ResponseCode.PHONE_NOT_EMPTY.getCode(),ResponseCode.PHONE_NOT_EMPTY.getMsg());
        }
        if(role==0){
            return ServerResponse.createServerResponseByFail(ResponseCode.ROLE_NOT_EMPTY.getCode(),ResponseCode.ROLE_NOT_EMPTY.getMsg());
        }

        //判断用户名是否存在
        int count1 = userMapper.findByUsername(username);
        if(count1>0){
            //用户名存在
            return ServerResponse.createServerResponseByFail(ResponseCode.USERNAME_EXISTS.getCode(),ResponseCode.USERNAME_EXISTS.getMsg());
        }

        //判断邮箱是否存在
        int count2 = userMapper.findByEmail(email);
        if(count2>0){
            //邮箱存在
            return ServerResponse.createServerResponseByFail(ResponseCode.EMAIL_EXISTS.getCode(),ResponseCode.EMAIL_EXISTS.getMsg());
        }

        //注册
        user.setPassword(MD5Utils.getMD5Code(user.getPassword()));

        Integer result = userMapper.insert(user);
        if(result == 0){
            //注册失败
            return ServerResponse.createServerResponseByFail(ResponseCode.REGISTER_FAIL.getCode(),ResponseCode.REGISTER_FAIL.getMsg());
        }
        return ServerResponse.createServerResponseBySuccess();
    }
}
