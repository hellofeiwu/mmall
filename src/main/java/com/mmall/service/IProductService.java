package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;

public interface IProductService {
    ServerResponse saveProduct(Product product);
    ServerResponse setSaleStatus(Integer productId, Integer status);
    ServerResponse getDetail(Integer productId);
    ServerResponse getList(int pageNum, int pageSize);
}
