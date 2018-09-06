package com.mmall.dao;

import com.mmall.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShippingMapper {
    int deleteByPrimaryKeyUserId(@Param("userId") Integer userId, @Param("id") Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKeyUserId(@Param("userId") Integer userId, @Param("id") Integer id);

    int updateByPrimaryKeySelective(@Param("userId") Integer userId, @Param("record") Shipping record);

    int updateByPrimaryKey(Shipping record);

    List<Shipping> selectByUserId(Integer userId);
}