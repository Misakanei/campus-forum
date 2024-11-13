package com.forum.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forum.entity.Posts;
import com.forum.mapper.PostsMapper;
import com.forum.service.IPostsService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author CBL
 * @since 2024-11-12
 */
@Service
public class PostsServiceImpl extends ServiceImpl<PostsMapper, Posts> implements IPostsService {

    @Resource
    private PostsMapper postsMapper;

    @Override
    public List<Map<String, Object>> getPostSummaries() {
        return postsMapper.getPostSummaries();
    }
}
