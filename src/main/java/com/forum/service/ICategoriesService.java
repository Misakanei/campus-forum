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
 * @author Misaka
 * @since 2024-11-12
 */
public interface ICategoriesService extends IService<Categories> {

    /**
     * 查询所有板块
     *
     * @return {@link List }<{@link Map }<{@link String }, {@link Object }>>
     */
    List<Map<String, Object>> selectAllCategory();

    /**
     * 添加板块
     *
     * @param categoryName 类别名称
     * @param description 描述
     * @return boolean
     */
    boolean addCategory(String categoryName, String description);

    /**
     * 删除板块
     *
     * @param categoryId 类别id
     * @return boolean
     */
    boolean deleteCategory(int categoryId);

    /**
     * 更新板块信息
     *
     * @param categoryId 类别id
     * @param categoryName 类别名称
     * @param description 描述
     * @return boolean
     */
    boolean updateCategory(int categoryId, String categoryName, String description);
}
