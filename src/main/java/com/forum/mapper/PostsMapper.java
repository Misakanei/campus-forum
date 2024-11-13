package com.forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.forum.entity.Posts;
import org.apache.ibatis.annotations.Select;

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
public interface PostsMapper extends BaseMapper<Posts> {

    /**
     * 获取所有帖子摘要
     *
     * @return {@link List }<{@link Map }<{@link String }, {@link Object }>> 帖子概览信息列表
     */
    @Select("""
            SELECT posts.post_id,
                   posts.category_id,
                   posts.title,
                   username,
                   SUBSTR(posts.content, 1, 40) AS content_preview,
                   posts.image_path,
                   posts.user_id,
                   posts.reading_volume,
                   posts.support,
                   update_time
            from posts
                     join `campus-forum`.users u on u.user_id = posts.user_id
            where posts.deleted = 0
            order by posts.update_time desc
            """)
    List<Map<String, Object>> getPostSummaries();
}