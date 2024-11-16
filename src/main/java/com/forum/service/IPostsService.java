package com.forum.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.forum.entity.Comments;
import com.forum.entity.Posts;

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
public interface IPostsService extends IService<Posts> {

    /**
     * 获取帖子摘要
     *
     * @return {@link List }<{@link Map }<{@link String }, {@link Object }>>
     */
    List<Map<String, Object>> getPostSummaries();


    /**
     * 获取详细信息
     *
     * @param postId 帖子id
     * @return {@link Posts }
     */
    Posts getPostWithDetails(Integer postId);

    /**
     * 获取点赞数
     *
     * @param postId 帖子id
     * @return int
     */
    int getSupportCount(Integer postId);

    /**
     * 通过帖子ID获取评论
     *
     * @param postId 帖子id
     * @return {@link List }<{@link Comments }>
     */
    List<Comments> getCommentsByPostId(Integer postId);

    void support(Integer postId);

    void unSupport(Integer postId);

    List<Map<String, Object>> selectPostsById(int userId);

    boolean addPost(Integer categoryId, Integer userId, String title, String content, String imagePath);

    boolean deletePost(Integer postId);

    boolean updatePost(Integer postId, Integer categoryId, String title, String content, String imagePath);

    List<Posts> searchPosts(String trim);

    Page<Posts> getPostsByPage(int pageNum, int pageSize);
}
