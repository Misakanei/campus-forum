package com.forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.forum.entity.Comments;
import com.forum.entity.Posts;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Misaka
 * @since 2024-11-12
 */
public interface PostsMapper extends BaseMapper<Posts> {

    /**
     * 获取所有帖子摘要
     *
     * @return {@link List }<{@link Map }<{@link String }, {@link Object }>> 帖子概览信息列表
     */
    @Select("""
            SELECT
                posts.post_id,
                posts.category_id,
                posts.title,
                username,
                SUBSTR(posts.content, 1, 30) AS content_preview,
                posts.user_id,
                posts.create_time,
                posts.reading_volume,
                posts.support,
                posts.update_time,
                COALESCE(comment_counts.comment_count, 0) AS comment_count
            FROM
                posts
                    JOIN `campus-forum`.users u ON u.user_id = posts.user_id
                    LEFT JOIN (
                    SELECT
                        post_id,
                        COUNT(*) AS comment_count
                    FROM
                        Comments
                    WHERE
                        deleted = 0
                    GROUP BY
                        post_id
                ) comment_counts ON posts.post_id = comment_counts.post_id
            WHERE
                posts.deleted = 0
            ORDER BY
                posts.update_time DESC
            """)
    List<Map<String, Object>> getPostSummaries();

    /**
     * 获取帖子详细信息
     *
     * @param postId 帖子id
     * @return {@link Posts }
     */
    @Select("SELECT p.*, u.username AS user_name,image_path, COUNT(c.comment_id) AS comment_count " +
            "FROM posts p " +
            "LEFT JOIN users u ON p.user_id = u.user_id " +
            "LEFT JOIN comments c ON p.post_id = c.post_id " +
            "WHERE p.post_id = #{postId} " +
            "GROUP BY p.post_id")
    Posts getPostWithDetails(Integer postId);

    /**
     * 获取点赞数
     *
     * @param postId 帖子id
     * @return int
     */
    @Select("SELECT support as supportCount FROM posts WHERE post_id = #{postId}")
    int getSupportCount(Integer postId);

    /**
     * 通过帖子ID获取评论
     *
     * @param postId 帖子id
     * @return {@link List }<{@link Comments }>
     */
    @Select("SELECT c.*, u.username AS user_name " +
            "FROM comments c " +
            "LEFT JOIN users u ON c.user_id = u.user_id " +
            "WHERE c.post_id = #{postId}")
    List<Comments> getCommentsByPostId(Integer postId);

    /**
     * 按ID获取帖子
     *
     * @param userId 用户id
     * @return {@link List }<{@link Map }<{@link String }, {@link Object }>>
     */
    @Select("SELECT * FROM posts WHERE user_id = #{userId} and deleted = 0;")
    List<Map<String, Object>> selectPostsById(int userId);

    @Insert("INSERT INTO posts (category_id, user_id, title, content, image_path, create_time, update_time) " +
            "VALUES (#{categoryId}, #{userId}, #{title}, #{content}, #{imagePath}, NOW(), NOW())")
    boolean addPost(@Param("categoryId") Integer categoryId,
                    @Param("userId") Integer userId,
                    @Param("title") String title,
                    @Param("content") String content,
                    @Param("imagePath") String imagePath);

    /**
     * 更新帖子
     *
     * @param postId 帖子id
     * @param categoryId 类别id
     * @param title 标题
     * @param content 内容
     * @param imagePath 图像路径
     * @return int
     */
    @Update("UPDATE posts SET " +
            "category_id = #{categoryId}, " +
            "title = #{title}, " +
            "content = #{content}, " +
            "image_path = #{imagePath}, " +
            "update_time = NOW() " +
            "WHERE post_id = #{postId}")
    int updatePost(@Param("postId") Integer postId,
                   @Param("categoryId") Integer categoryId,
                   @Param("title") String title,
                   @Param("content") String content,
                   @Param("imagePath") String imagePath);

    @Select("SELECT * FROM posts " +
            "WHERE (title LIKE CONCAT('%', #{keyword}, '%') " +
            "OR content LIKE CONCAT('%', #{keyword}, '%')) " +
            "AND deleted = 0 " +
            "ORDER BY create_time DESC")
    List<Posts> searchPosts(@Param("keyword") String keyword);

    /**
     * 阅读量 + 1
     *
     * @param postId 帖子id
     * @return int
     */
    @Update("UPDATE posts SET reading_volume = reading_volume + 1 WHERE post_id = #{postId}")
    int plusOneForTheNumberOfReads(int postId);
}