package com.forum.service;

import com.baomidou.mybatisplus.extension.service.IService;
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
}
