package com.forum.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forum.entity.Comments;
import com.forum.mapper.CommentsMapper;
import com.forum.service.ICommentsService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author CBL
 * @since 2024-11-12
 */
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements ICommentsService {

    @Resource
    private CommentsMapper commentsMapper;

    /**
     * 添加评论
     *
     * @param userId 用户id
     * @param postId 帖子id
     * @param comment 评论
     * @return boolean
     */
    @Override
    public boolean addComment(int userId, int postId, String comment) {
        Comments comments = new Comments();
        comments.setPostId(postId);
        comments.setUserId(userId);
        comments.setContent(comment);
        return commentsMapper.insert(comments) > 0;
    }

    @Override
    public List<Comments> getAllComments() {
        return commentsMapper.selectList(null);
    }

    @Override
    public boolean deleteComment(int commentId) {
        return commentsMapper.deleteById(commentId) > 0;
    }


}
