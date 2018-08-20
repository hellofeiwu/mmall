package com.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Category;
import com.mmall.pojo.Product;
import com.mmall.service.ICategoryService;
import com.mmall.service.IProductService;
import com.mmall.util.DateTimeUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.ProductDetailVo;
import com.mmall.vo.ProductListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service("iProductService")
public class ProductServiceImpl implements IProductService{

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ICategoryService iCategoryService;

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

    public ServerResponse<ProductDetailVo> getDetail(Integer productId) {
        if(productId == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product == null) {
            return ServerResponse.createByErrorMessage("product not found");
        }
        ProductDetailVo productDetailVo = setProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVo);
    }

    public ServerResponse<ProductDetailVo> getProductDetail(Integer productId) {
        if(productId == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product == null
                || product.getStatus() != Const.ProductStatusEnum.ON_SALE.getCode()) {
            return ServerResponse.createByErrorMessage("product not found");
        }
        ProductDetailVo productDetailVo = setProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVo);
    }

    private ProductDetailVo setProductDetailVo(Product product) {
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setName(product.getName());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setStock(product.getStock());
        productDetailVo.setStatus(product.getStatus());

        productDetailVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix", "http://img.happymmall.com/"));
        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if(category == null) {
            productDetailVo.setParentCategoryId(0);
        }else {
            productDetailVo.setParentCategoryId(category.getParentId());
        }

        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));

        return productDetailVo;
    }

    public ServerResponse<PageInfo> getList(int pageNum, int pageSize) {
        // PageHelper startPage
        // sql
        // PageHelper end
        PageHelper.startPage(pageNum, pageSize);
        List<Product> productList = productMapper.selectList();
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for(Product item : productList) {
            productListVoList.add(setProductListVo(item));
        }

        PageInfo pageResult= new PageInfo(productList); // init page structure
        pageResult.setList(productListVoList); // put in real content
        return ServerResponse.createBySuccess(pageResult);
    }

    private ProductListVo setProductListVo(Product product) {
        ProductListVo productListVo = new ProductListVo();

        productListVo.setId(product.getId());
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setName(product.getName());
        productListVo.setStatus(product.getStatus());
        productListVo.setMainImage(product.getMainImage());
        productListVo.setPrice(product.getPrice());
        productListVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix", "http://img.happymmall.com/"));

        return productListVo;
    }

    public ServerResponse<PageInfo> searchProduct(Integer productId, String productName, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        if(StringUtils.isNoneBlank(productName)) {
            productName = new StringBuilder().append("%").append(productName).append("%").toString();
        }

        List<Product> productList = productMapper.selectByNameAndId(productId, productName);
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for(Product item : productList) {
            productListVoList.add(setProductListVo(item));
        }

        PageInfo pageResult= new PageInfo(productList); // init page structure
        pageResult.setList(productListVoList); // put in real content
        return ServerResponse.createBySuccess(pageResult);
    }

    public ServerResponse<PageInfo> getProductByKeywordCategoryId(String keyword,
                                                                  Integer categoryId,
                                                                  int pageNum,
                                                                  int pageSize,
                                                                  String orderBy) {
        if(StringUtils.isBlank(keyword) && categoryId == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        Set<Integer> categoryIdSet = Sets.newHashSet();

        PageHelper.startPage(pageNum, pageSize);
        if (categoryId != null) {
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            if(category == null && StringUtils.isBlank(keyword)) {
                List<ProductListVo> productListVoList = Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(productListVoList);
                return ServerResponse.createBySuccess(pageInfo);
            }
            categoryIdSet = iCategoryService.getCategoryAndChildId(categoryId).getData();
        }

        if(StringUtils.isNoneBlank(keyword)) {
            keyword = new StringBuilder().append("%").append(keyword).append("%").toString();
        }
        if(StringUtils.isNoneBlank(orderBy)) {

        }

        List<Product> productList = productMapper.selectByNameAndId(productId, productName);
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for(Product item : productList) {
            productListVoList.add(setProductListVo(item));
        }

        PageInfo pageResult= new PageInfo(productList); // init page structure
        pageResult.setList(productListVoList); // put in real content
        return ServerResponse.createBySuccess(pageResult);
    }
}
