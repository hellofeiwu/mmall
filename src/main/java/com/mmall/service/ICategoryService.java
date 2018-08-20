package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;

import java.util.List;
import java.util.Set;

public interface ICategoryService {
    ServerResponse addCategory(String categoryName, Integer parentId);
    ServerResponse setCategoryName(Integer categoryId, String categoryName);
    ServerResponse<List<Category>> getChildParallelCategory(Integer categoryId);
    ServerResponse<Set<Integer>> getCategoryAndChildId(Integer categoryId);
}
