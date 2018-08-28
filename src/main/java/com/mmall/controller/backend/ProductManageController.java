package com.mmall.controller.backend;

import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IFileService;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import com.mmall.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/manage/product")
public class ProductManageController {
    @Autowired
    private IUserService iUserService;

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IFileService iFileService;

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

    @RequestMapping("detail")
    @ResponseBody
    public ServerResponse getDetail(HttpSession session, Integer productId) {
//        User user = (User) session.getAttribute(Const.CURRENT_USER);
//        if(user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "user not logged in, please log in");
//        }
//        if(iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.getDetail(productId);
//        }else {
//            return ServerResponse.createByErrorMessage("not allowed to access");
//        }
    }

    @RequestMapping("list")
    @ResponseBody
    public ServerResponse getList(
            HttpSession session,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
//        User user = (User) session.getAttribute(Const.CURRENT_USER);
//        if(user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "user not logged in, please log in");
//        }
//        if(iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.getList(pageNum, pageSize);
//        }else {
//            return ServerResponse.createByErrorMessage("not allowed to access");
//        }
    }

    @RequestMapping("search")
    @ResponseBody
    public ServerResponse searchProduct(
            HttpSession session,
            Integer productId,
            String productName,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
//        User user = (User) session.getAttribute(Const.CURRENT_USER);
//        if(user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "user not logged in, please log in");
//        }
//        if(iUserService.checkAdminRole(user).isSuccess()) {
        return iProductService.searchProduct(productId, productName, pageNum, pageSize);
//        }else {
//            return ServerResponse.createByErrorMessage("not allowed to access");
//        }
    }

    @RequestMapping("upload")
    @ResponseBody
    public ServerResponse upload(MultipartFile file, HttpServletRequest request) {
        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFileName = iFileService.upload(file, path);
        String url = PropertiesUtil.getProperty("") + targetFileName;

        Map fileMap = Maps.newHashMap();
        fileMap.put("uri", targetFileName);
        fileMap.put("url", url);

        return ServerResponse.createBySuccess(fileMap);
    }

//    @RequestMapping("richtextImageUpload")
//    @ResponseBody
//    public ServerResponse richtextImageupload(MultipartFile file, HttpServletRequest request) {
//        String path = request.getSession().getServletContext().getRealPath("upload");
//        String targetFileName = iFileService.upload(file, path);
//        String url = PropertiesUtil.getProperty("") + targetFileName;
//
//        Map fileMap = Maps.newHashMap();
//        fileMap.put("uri", targetFileName);
//        fileMap.put("url", url);
//
//        return ServerResponse.createBySuccess(fileMap);
//    }
}
