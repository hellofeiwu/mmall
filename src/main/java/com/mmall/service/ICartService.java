package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.vo.CartVo;

public interface ICartService {
    ServerResponse<CartVo> add(Integer userId, Integer count, Integer productId);
    ServerResponse<CartVo> update(Integer userId, Integer productId, Integer count);
    ServerResponse<CartVo> delete(Integer userId, String productIds);
    ServerResponse<CartVo> list(Integer userId);
    ServerResponse<CartVo> selectDeselect(Integer userId, Integer productId, Integer checked);
    ServerResponse getCount(Integer userId);
}
