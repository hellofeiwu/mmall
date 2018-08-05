package com.mmall.service.impl;

import com.google.common.collect.Sets;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CategoryMapper;
import com.mmall.pojo.Category;
import com.mmall.service.ICategoryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService {

    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryMapper categoryMapper;

    public ServerResponse addCategory(String categoryName, Integer parentId) {
        if (parentId == null || StringUtils.isBlank(categoryName)) {
            return ServerResponse.createByErrorMessage("param error");
        }

        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);

        int rowCount = categoryMapper.insert(category);
        if (rowCount > 0) {
            return ServerResponse.createBySuccess("add category succeed");
        }
        return ServerResponse.createByErrorMessage("failed to add category");
    }

    public ServerResponse setCategoryName(Integer categoryId, String categoryName) {
        if (categoryId == null || StringUtils.isBlank(categoryName)) {
            return ServerResponse.createByErrorMessage("param error");
        }

        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);
        int rowCount = categoryMapper.updateByPrimaryKeySelective(category);

        if (rowCount >0) {
            return ServerResponse.createBySuccess("set category name succeed");
        }
        return ServerResponse.createByErrorMessage("failed to set category name");
    }

    public ServerResponse getChildParallelCategory(Integer categoryId) {

        List<Category> categoryList = categoryMapper.selectCategoryChildByParentId(categoryId);

        if (CollectionUtils.isEmpty(categoryList)) {
            logger.info("no child category found");
        }

        return ServerResponse.createBySuccess(categoryList);

    }

    public ServerResponse getCategoryAndChildId(Integer categoryId) {
        Set<Integer> categorySet = Sets.newHashSet();
        getChildCategory(categorySet, categoryId);
        return ServerResponse.createBySuccess(categorySet);
    }

    private Set<Integer> getChildCategory(Set<Integer> categorySet, Integer categoryId) {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if(category != null) {
            categorySet.add(category.getId());
        }

        List<Category> childCategory = categoryMapper.selectCategoryChildByParentId(categoryId);
        for(Category item : childCategory) {
            getChildCategory(categorySet, item.getId());
        }
        return categorySet;
    }
}
