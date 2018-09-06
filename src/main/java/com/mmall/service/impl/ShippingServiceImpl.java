package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.mmall.common.ServerResponse;
import com.mmall.dao.ShippingMapper;
import com.mmall.pojo.Shipping;
import com.mmall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    public ServerResponse add (Integer userId, Shipping shipping) {
        shipping.setUserId(userId);
        int count = shippingMapper.insert(shipping);
        if(count > 0) {
            Map result = Maps.newHashMap();
            result.put("ShippingId", shipping.getId());
            return ServerResponse.createBySuccess("add succeed", result);
        }else {
            return ServerResponse.createByErrorMessage("add failed");
        }
    }

    public ServerResponse delete (Integer userId, Integer shippingId) {
        int count = shippingMapper.deleteByPrimaryKeyUserId(userId, shippingId);
        if(count > 0) {
            return ServerResponse.createBySuccessMessage("delete succeed");
        }else {
            return ServerResponse.createByErrorMessage("delete failed");
        }
    }

    public ServerResponse update (Integer userId, Shipping shipping) {
        if(shipping.getId() == null) {
            return ServerResponse.createByErrorMessage("shippingId cannot be null");
        }

        shipping.setUserId(userId); // avoid sending fake userId

        int count = shippingMapper.updateByPrimaryKeySelective(userId, shipping);
        if(count > 0) {
            return ServerResponse.createBySuccessMessage("update succeed");
        }else {
            return ServerResponse.createByErrorMessage("update failed");
        }
    }

    public ServerResponse<Shipping> getByUserIdShippingId (Integer userId, Integer shippingId) {
        if(shippingId == null) {
            return ServerResponse.createByErrorMessage("shippingId cannot be null");
        }
        Shipping shipping = shippingMapper.selectByPrimaryKeyUserId(userId, shippingId);
        return ServerResponse.createBySuccess(shipping);
    }

    public ServerResponse<PageInfo> list(
            Integer userId,
            int pageNum,
            int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Shipping> shippingList = shippingMapper.selectByUserId(userId);
        PageInfo pageResult = new PageInfo(shippingList);
        return ServerResponse.createBySuccess(pageResult);
    }
}
