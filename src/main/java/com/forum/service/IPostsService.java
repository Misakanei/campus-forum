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
 * @author Misaka
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

    /**
     * 点赞
     *
     * @param postId 帖子id
     */
    void support(Integer postId);

    /**
     * 取消点赞
     *
     * @param postId 帖子id
     */
    void unSupport(Integer postId);

    /**
     * 按ID获取帖子
     *
     * @param userId 用户id
     * @return {@link List }<{@link Map }<{@link String }, {@link Object }>>
     */
    List<Map<String, Object>> selectPostsById(int userId);

    /**
     * 添加帖子
     *
     * @param categoryId 类别id
     * @param userId 用户id
     * @param title 标题
     * @param content 内容
     * @param imagePath 图像路径
     * @return boolean
     */
    boolean addPost(Integer categoryId, Integer userId, String title, String content, String imagePath);

    /**
     * 删除帖子
     *
     * @param postId 帖子id
     * @return boolean
     */
    boolean deletePost(Integer postId);

    /**
     * 更新帖子
     *
     * @param postId 帖子id
     * @param categoryId 类别id
     * @param title 标题
     * @param content 内容
     * @param imagePath 图像路径
     * @return boolean
     */
    boolean updatePost(Integer postId, Integer categoryId, String title, String content, String imagePath);

    /**
     * 搜索帖子
     *
     * @param trim 装饰
     * @return {@link List }<{@link Posts }>
     */
    List<Posts> searchPosts(String trim);

    /**
     * 按分页获取帖子
     *
     * @param pageNum 页号
     * @param pageSize 页面大小
     * @return {@link Page }<{@link Posts }>
     */
    Page<Posts> getPostsByPage(int pageNum, int pageSize);
}
