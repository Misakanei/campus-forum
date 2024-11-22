package com.forum.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forum.entity.Categories;
import com.forum.mapper.CategoriesMapper;
import com.forum.service.ICategoriesService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Misaka
 * @since 2024-11-12
 */
@Service
public class CategoriesServiceImpl extends ServiceImpl<CategoriesMapper, Categories> implements ICategoriesService {

    @Resource
    CategoriesMapper categoriesMapper;

    @Override
    public List<Map<String, Object>> selectAllCategory() {
        return categoriesMapper.selectAllCategory();
    }

    @Override
    public boolean addCategory(String categoryName, String description) {
        return categoriesMapper.addCategory(categoryName, description) > 0;
    }

    @Override
    public boolean deleteCategory(int categoryId) {
        return categoriesMapper.deleteById(categoryId) > 0;
    }

    @Override
    public boolean updateCategory(int categoryId, String categoryName, String description) {
        return categoriesMapper.updateCategory(categoryId, categoryName, description) > 0;
    }

}
