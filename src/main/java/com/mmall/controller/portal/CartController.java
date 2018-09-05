package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart/")
public class CartController {

    @Autowired
    private ICartService iCartService;

    @RequestMapping("add")
    @ResponseBody
    public ServerResponse add(HttpSession session, Integer productId, Integer count) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "user not logged in, please log in");
        }
        return iCartService.add(user.getId(), productId, count);
    }

    @RequestMapping("update")
    @ResponseBody
    public ServerResponse update(HttpSession session, Integer productId, Integer count) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "user not logged in, please log in");
        }
        return iCartService.update(user.getId(), productId, count);
    }

    @RequestMapping("delete")
    @ResponseBody
    public ServerResponse delete(HttpSession session, String productIds) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "user not logged in, please log in");
        }
        return iCartService.delete(user.getId(), productIds);
    }

    @RequestMapping("list")
    @ResponseBody
    public ServerResponse list(HttpSession session) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "user not logged in, please log in");
        }
        return iCartService.list(user.getId());
    }

    @RequestMapping("select_all")
    @ResponseBody
    public ServerResponse selectAll(HttpSession session) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "user not logged in, please log in");
        }
        return iCartService.selectDeselect(user.getId(), null, Const.Cart.CHECKED);
    }

    @RequestMapping("deselect_all")
    @ResponseBody
    public ServerResponse deselectAll(HttpSession session) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "user not logged in, please log in");
        }
        return iCartService.selectDeselect(user.getId(), null, Const.Cart.UN_CHECKED);
    }

    @RequestMapping("select")
    @ResponseBody
    public ServerResponse select(HttpSession session, Integer productId) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "user not logged in, please log in");
        }
        return iCartService.selectDeselect(user.getId(), productId, Const.Cart.CHECKED);
    }

    @RequestMapping("deselect")
    @ResponseBody
    public ServerResponse deselect(HttpSession session, Integer productId) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "user not logged in, please log in");
        }
        return iCartService.selectDeselect(user.getId(), productId, Const.Cart.UN_CHECKED);
    }

    @RequestMapping("count")
    @ResponseBody
    public ServerResponse deselect(HttpSession session) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "user not logged in, please log in");
        }
        return iCartService.getCount(user.getId());
    }
}
