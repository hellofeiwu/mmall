package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
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

        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.loginUser(username, md5Password);
        if (user == null) {
            return ServerResponse.createByErrorMessage("invalid password");
        }
        user.setPassword("");
        return ServerResponse.createBySuccess("login succeed", user);
    }

    @Override
    public ServerResponse<String> register(User user) {
        int resultCount = userMapper.checkUsername(user.getUsername());
        if (resultCount > 0) {
            return ServerResponse.createByErrorMessage("username already exist");
        }

        resultCount = userMapper.checkEmail(user.getEmail());
        if (resultCount > 0) {
            return ServerResponse.createByErrorMessage("email already exist");
        }

        user.setRole(Const.Role.ROLE_CUSTOMER);

        // MD5 encode
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));

        resultCount = userMapper.insert(user);

        if (resultCount == 0) {
            return ServerResponse.createByErrorMessage("register failed");
        }

        return ServerResponse.createBySuccessMessage("register succeed");
    }
}
