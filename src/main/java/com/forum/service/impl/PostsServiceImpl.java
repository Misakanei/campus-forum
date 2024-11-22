package com.forum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forum.entity.Comments;
import com.forum.entity.Posts;
import com.forum.mapper.PostsMapper;
import com.forum.service.IPostsService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class PostsServiceImpl extends ServiceImpl<PostsMapper, Posts> implements IPostsService {

    @Resource
    private PostsMapper postsMapper;

    /**
     * 获取帖子摘要
     *
     * @return {@link List }<{@link Map }<{@link String }, {@link Object }>>
     */
    @Override
    public List<Map<String, Object>> getPostSummaries() {
        return postsMapper.getPostSummaries();
    }

    /**
     * 获取详细信息
     *
     * @param postId 帖子id
     * @return {@link Posts }
     */
    @Override
    public Posts getPostWithDetails(Integer postId) {
        Posts post = postsMapper.getPostWithDetails(postId);
        post.setSupport(postsMapper.getSupportCount(postId));
        postsMapper.plusOneForTheNumberOfReads(postId);
        return post;
    }

    /**
     * 获取点赞数
     *
     * @param postId 帖子id
     * @return int
     */
    @Override
    public int getSupportCount(Integer postId) {
        return postsMapper.getSupportCount(postId);
    }

    /**
     * 通过帖子ID获取评论
     *
     * @param postId 帖子id
     * @return {@link List }<{@link Comments }>
     */
    @Override
    public List<Comments> getCommentsByPostId(Integer postId) {
        return postsMapper.getCommentsByPostId(postId);
    }

    /**
     * 点赞
     *
     * @param postId 帖子id
     */
    @Transactional
    public void support(Integer postId) {
        LambdaUpdateWrapper<Posts> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(Posts::getPostId, postId).set(Posts::getSupport, postsMapper.selectById(postId).getSupport() + 1);
        postsMapper.update(null, updateWrapper);
    }

    /**
     * 取消点赞
     *
     * @param postId 帖子id
     */
    @Override
    public void unSupport(Integer postId) {
        LambdaUpdateWrapper<Posts> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(Posts::getPostId, postId).set(Posts::getSupport, postsMapper.selectById(postId).getSupport() - 1);
        postsMapper.update(null, updateWrapper);
    }

    /**
     * 按ID获取帖子
     *
     * @param userId 用户id
     * @return {@link List }<{@link Map }<{@link String }, {@link Object }>>
     */
    @Override
    public List<Map<String, Object>> selectPostsById(int userId) {
        return postsMapper.selectPostsById(userId);
    }

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
    @Override
    public boolean addPost(Integer categoryId, Integer userId, String title, String content, String imagePath) {
        return postsMapper.addPost(categoryId, userId, title, content, imagePath);
    }

    /**
     * 删除帖子
     *
     * @param postId 帖子id
     * @return boolean
     */
    @Override
    public boolean deletePost(Integer postId) {
        return postsMapper.deleteById(postId) > 0;
    }

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
    @Override
    public boolean updatePost(Integer postId, Integer categoryId, String title, String content, String imagePath) {
        return postsMapper.updatePost(postId, categoryId, title, content, imagePath) > 0;
    }

    /**
     * 搜索帖子
     *
     * @param keyword 关键字
     * @return {@link List }<{@link Posts }>
     */
    @Override
    public List<Posts> searchPosts(String keyword) {
        return postsMapper.searchPosts(keyword);
    }

    /**
     * 按分页获取帖子
     *
     * @param pageNum 页号
     * @param pageSize 页面大小
     * @return {@link Page }<{@link Posts }>
     */
    @Override
    public Page<Posts> getPostsByPage(int pageNum, int pageSize) {
        Page<Posts> page = new Page<>(pageNum, pageSize);
        return postsMapper.selectPage(page, new QueryWrapper<Posts>()
                .orderByDesc("create_time"));
    }
}
