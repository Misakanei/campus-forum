package com.forum.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.forum.entity.Comments;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Misaka
 * @since 2024-11-12
 */
public interface ICommentsService extends IService<Comments> {

    /**
     * 添加评论
     *
     * @param userId 用户id
     * @param postId 帖子id
     * @param comment 评论
     * @return boolean
     */
    boolean addComment(int userId, int postId, String comment);

    /**
     * 获取所有评论
     *
     * @return {@link Comments }
     */
    List<Comments> getAllComments();

    /**
     * 删除评论
     *
     * @param commentId 注释id
     * @return boolean
     */
    boolean deleteComment(int commentId);
}
