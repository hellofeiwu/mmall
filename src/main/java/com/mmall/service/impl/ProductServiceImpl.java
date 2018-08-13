package com.mmall.service.impl;

import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Product;
import com.mmall.service.IProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iProductService")
public class ProductServiceImpl implements IProductService{

    @Autowired
    private ProductMapper productMapper;

    public ServerResponse saveProduct(Product product) {
        if(product != null) {

            if(StringUtils.isNoneBlank(product.getSubImages())) {
                String[] subImageArray = product.getSubImages().split(",");
                if(subImageArray.length > 0) {
                    product.setMainImage(subImageArray[0]);
                }
            }

            if(product.getId() != null) {
                int rowCount = productMapper.updateByPrimaryKey(product);
                if(rowCount > 0) {
                    return ServerResponse.createBySuccessMessage("update succeed");
                }
                return ServerResponse.createByErrorMessage("update failed");
            }else {
                int rowCount = productMapper.insert(product);
                if(rowCount > 0) {
                    return ServerResponse.createBySuccessMessage("save succeed");
                }
                return ServerResponse.createByErrorMessage("save failed");
            }
        }
        return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
    }

    public ServerResponse setSaleStatus(Integer productId, Integer status) {
        if (productId == null || status == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }else {
            Product product = new Product();
            product.setId(productId);
            product.setStatus(status);
            int rowCount = productMapper.updateByPrimaryKeySelective(product);
            if (rowCount > 0) {
                return ServerResponse.createBySuccessMessage("sale status update succeed");
            }
            return ServerResponse.createByErrorMessage("sale status update failed");
        }
    }
}
