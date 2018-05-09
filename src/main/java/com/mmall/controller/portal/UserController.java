package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService iUserService;

    /**
     * user login
     * @param username
     * @param password
     * @param sesion
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession sesion) {
        ServerResponse<User> response = iUserService.login(username, password);
        if (response.isSuccess()) {
            sesion.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    public ServerResponse<String> register(User user) {
        return iUserService.register(user);
    }
}
