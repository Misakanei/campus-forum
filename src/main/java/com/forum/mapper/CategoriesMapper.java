package com.forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.forum.entity.Categories;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author CBL
 * @since 2024-11-12
 */
public interface CategoriesMapper extends BaseMapper<Categories> {

    /**
     * 查询所有板块信息
     *
     * @return {@link Map }<{@link String }, {@link String }>
     */
    @Select("""
                SELECT categories.category_id,description, categories.name categories_name, categories.created_time categories_created_time
                FROM categories
                WHERE deleted = 0
            """)
    List<Map<String, Object>> selectAllCategory();

    /**
     * 添加板块
     *
     * @param categoryName 类别名称
     * @param description 描述
     * @return int
     */
    @Insert("INSERT INTO categories (name,description,created_time) VALUES (#{categoryName},#{description},NOW())")
    int addCategory(String categoryName, String description);

    /**
     * 更新板块信息
     *
     * @param categoryId 类别id
     * @param categoryName 类别名称
     * @param description 描述
     * @return int
     */
    @Update("UPDATE categories SET name = #{categoryName},description = #{description} WHERE category_id = #{categoryId}")
    int updateCategory(int categoryId, String categoryName, String description);
}
