package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/product")
public class ProductManageController {
    @Autowired
    private IUserService iUserService;

    @Autowired
    private IProductService iProductService;

    @RequestMapping("save")
    @ResponseBody
    public ServerResponse saveProduct(HttpSession session, Product product) {
//        User user = (User) session.getAttribute(Const.CURRENT_USER);
//        if(user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "user not logged in, please log in");
//        }
//        if(iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.saveProduct(product);
//        }else {
//            return ServerResponse.createByErrorMessage("not allowed to access");
//        }
    }

    @RequestMapping("setSaleStatus")
    @ResponseBody
    public ServerResponse setSaleStatus(HttpSession session, Integer productId, Integer status) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "user not logged in, please log in");
        }
        if(iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.setSaleStatus(productId, status);
        }else {
            return ServerResponse.createByErrorMessage("not allowed to access");
        }
    }

    @RequestMapping("getDetail")
    @ResponseBody
    public ServerResponse getDetail(HttpSession session, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "user not logged in, please log in");
        }
        if(iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.getDetail(productId);
        }else {
            return ServerResponse.createByErrorMessage("not allowed to access");
        }
    }
}
