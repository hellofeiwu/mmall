package com.mmall.service.impl;

import com.mmall.common.ServerResponse;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);
        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("username not found");
        }

        //todo password MD5
        User user = userMapper.loginUser(username, password);
        if (user == null) {
            return ServerResponse.createByErrorMessage("invalid password");
        }
        user.setPassword("");
        return ServerResponse.createBySuccess("login succeeded", user);
    }
}
