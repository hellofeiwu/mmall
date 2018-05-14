package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.common.TokenCache;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
        int resultCount;
        if (!validation(user.getUsername(), "username").isSuccess()) {
            return ServerResponse.createByErrorMessage("username already exist");
        }

        if (!validation(user.getEmail(), "email").isSuccess()) {
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

    @Override
    public ServerResponse<String> validation(String str, String type) {
        int resultCount;
        if (type.equals(Const.USERNAME)) {
            resultCount = userMapper.checkUsername(str);
            if (resultCount > 0) {
                return ServerResponse.createByErrorMessage("username already exist");
            }
        }

        if (type.equals(Const.EMAIL)) {
            resultCount = userMapper.checkEmail(str);
            if (resultCount > 0) {
                return ServerResponse.createByErrorMessage("email already exist");
            }
        }
        return ServerResponse.createBySuccessMessage("valid");
    }

    @Override
    public void generateToken(String username) {
        String token = UUID.randomUUID().toString();
        TokenCache.setKey(TokenCache.TOKEN_PREFIX + username, token);
    }
}
