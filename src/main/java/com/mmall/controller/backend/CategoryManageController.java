package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;

    @RequestMapping("add_category")
    @ResponseBody
    public ServerResponse addCategory(HttpSession session,
                                      String categoryName,
                                      @RequestParam(value = "parentId", defaultValue = "0") int parentId) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "user not logged in, please log in");
        }

        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iCategoryService.addCategory(categoryName, parentId);
        }else {
            return ServerResponse.createByErrorMessage("not allowed to access");
        }
    }

    @RequestMapping("set_category_name")
    @ResponseBody
    public ServerResponse setCategoryName(HttpSession session,
                                          Integer categoryId,
                                          String categoryName
                                          ) {
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "user not logged in, please log in");
        }

        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iCategoryService.setCategoryName(categoryId, categoryName);
        }else {
            return ServerResponse.createByErrorMessage("not allowed to access");
        }
    }
}
