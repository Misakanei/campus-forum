package com.forum.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.forum.entity.Categories;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author CBL
 * @since 2024-11-12
 */
public interface ICategoriesService extends IService<Categories> {

    List<Map<String, Object>> selectAllCategory();

    boolean addCategory(String categoryName, String description);

    boolean deleteCategory(int categoryId);

    boolean updateCategory(int categoryId, String categoryName, String description);
}
