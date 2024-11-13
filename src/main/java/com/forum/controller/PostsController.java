package com.forum.controller;

import com.forum.service.IPostsService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author CBL
 * @since 2024-11-12
 */
@Controller
@RequestMapping("/posts")
public class PostsController {

    @Resource
    IPostsService postsService;

    /**
     * 获取所有帖子摘要
     *
     * @return {@link List }<{@link Map }<{@link String }, {@link Object }>>
     */
    @ResponseBody
    @GetMapping("/summaries")
    public List<Map<String, Object>> getPostSummaries() {
        return postsService.getPostSummaries();
    }
}
