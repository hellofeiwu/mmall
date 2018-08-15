package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.vo.ProductDetailVo;

public interface IProductService {
    ServerResponse saveProduct(Product product);
    ServerResponse setSaleStatus(Integer productId, Integer status);
    ServerResponse<ProductDetailVo> getDetail(Integer productId);
    ServerResponse<PageInfo> getList(int pageNum, int pageSize);
}
