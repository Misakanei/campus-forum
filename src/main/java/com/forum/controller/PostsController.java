package com.forum.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.forum.entity.ApiResponse;
import com.forum.entity.Comments;
import com.forum.entity.Posts;
import com.forum.service.IPostsService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

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
@RestController
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
@RequestMapping("/posts")
public class PostsController {

    @Resource
    private IPostsService postsService;

    /**
     * 获取所有帖子摘要
     *
     * @return {@link List }<{@link Map }<{@link String }, {@link Object }>>
     */
    @GetMapping("/summaries")
    public List<Map<String, Object>> getPostSummaries() {
        return postsService.getPostSummaries();
    }

    /**
     * 获取帖子详细信息
     *
     * @param postId 帖子id
     * @return {@link Map }<{@link String }, {@link Object }>
     */
    @GetMapping("/getPostDetail/{postId}")
    public Map<String, Object> getPostDetails(@PathVariable Integer postId) {
        Posts post = postsService.getPostWithDetails(postId);
        int supportCount = postsService.getSupportCount(postId);
        List<Comments> comments = postsService.getCommentsByPostId(postId);
        Map<String, Object> response = Map.of(
                "post", post,
                "supportCount", supportCount,
                "comments", comments
        );
        return response;
    }

    /**
     * 点赞帖子
     *
     * @param postId 帖子id
     * @return {@link ApiResponse }
     */
    @PostMapping("/support/{postId}")
    public ApiResponse support(@PathVariable Integer postId) {
        try {
            postsService.support(postId);
            return new ApiResponse(200, "SUCCESS", "点赞成功", null);
        } catch (Exception e) {
            return new ApiResponse(500, "ERROR", "发生了未知的错误", null);
        }
    }

    /**
     * 取消点赞帖子
     *
     * @param postId 帖子id
     * @return {@link ApiResponse }
     */
    @PostMapping("/unSupport/{postId}")
    public ApiResponse unSupport(@PathVariable Integer postId) {
        try {
            postsService.unSupport(postId);
            return new ApiResponse(200, "SUCCESS", "取消点赞成功", null);
        } catch (Exception e) {
            return new ApiResponse(500, "ERROR", "发生了未知的错误", null);
        }
    }

    /**
     * 根据用户ID获取帖子
     *
     * @param userId 用户id
     * @return {@link List }<{@link Map }<{@link String }, {@link Object }>>
     */
    @PostMapping("/selectPostsById/{userId}")
    public List<Map<String, Object>> selectPostsById(@PathVariable int userId) {
        return postsService.selectPostsById(userId);
    }

    /**
     * 添加帖子
     *
     * @param postInfo 发布信息
     * @return {@link ApiResponse }
     */
    @PostMapping("/addPost")
    public ApiResponse addPost(@RequestBody Map<String, Object> postInfo) {
        try {
            Integer categoryId = Integer.parseInt(postInfo.get("categoryId").toString());
            String title = (String) postInfo.get("title");
            String content = (String) postInfo.get("content");
            Integer userId = Integer.parseInt(postInfo.get("userId").toString());
            String imagePath = (String) postInfo.get("imagePath"); // 可能为null
            if (title == null || title.trim().isEmpty() ||
                    content == null || content.trim().isEmpty()) {
                return new ApiResponse(400, "ERROR_INCOMPLETE_INFO", "标题或内容不能为空", null);
            }
            boolean success = postsService.addPost(categoryId, userId, title, content, imagePath);
            if (success) {
                return new ApiResponse(200, "SUCCESS", "发布成功", null);
            } else {
                return new ApiResponse(500, "ERROR", "发布失败", null);
            }
        } catch (Exception e) {
            return new ApiResponse(500, "ERROR", "服务器错误", null);
        }
    }

    /**
     * 删除帖子
     *
     * @param postId 帖子ID
     * @return ApiResponse
     */
    @PostMapping("/deletePost/{postId}")
    public ApiResponse deletePost(@PathVariable Integer postId) {
        try {
            boolean success = postsService.deletePost(postId);
            if (success) {
                return new ApiResponse(200, "SUCCESS", "删除成功", null);
            } else {
                return new ApiResponse(500, "ERROR", "删除失败", null);
            }
        } catch (Exception e) {
            return new ApiResponse(500, "ERROR", "服务器错误", null);
        }
    }

    /**
     * 修改帖子
     *
     * @param postInfo 帖子信息
     * @return ApiResponse
     */
    @PostMapping("/updatePost")
    public ApiResponse updatePost(@RequestBody Map<String, Object> postInfo) {
        try {
            Integer postId = Integer.parseInt(postInfo.get("postId").toString());
            Integer categoryId = Integer.parseInt(postInfo.get("categoryId").toString());
            String title = (String) postInfo.get("title");
            String content = (String) postInfo.get("content");
            String imagePath = (String) postInfo.get("imagePath");
            if (title == null || title.trim().isEmpty() ||
                    content == null || content.trim().isEmpty()) {
                return new ApiResponse(400, "ERROR_INCOMPLETE_INFO", "标题或内容不能为空", null);
            }
            boolean success = postsService.updatePost(postId, categoryId, title, content, imagePath);
            if (success) {
                return new ApiResponse(200, "SUCCESS", "修改成功", null);
            } else {
                return new ApiResponse(500, "ERROR", "修改失败", null);
            }
        } catch (Exception e) {
            return new ApiResponse(500, "ERROR", "服务器错误", null);
        }
    }

    /**
     * 搜索帖子
     *
     * @param searchInfo 搜索关键词
     * @return ApiResponse
     */
    @PostMapping("/search")
    public ApiResponse searchPosts(@RequestBody Map<String, String> searchInfo) {
        try {
            String keyword = searchInfo.get("keyword");
            if (keyword == null || keyword.trim().isEmpty()) {
                return new ApiResponse(400, "ERROR", "搜索关键词不能为空", null);
            }

            List<Posts> results = postsService.searchPosts(keyword.trim());
            return new ApiResponse(200, "SUCCESS", "搜索成功", results);
        } catch (Exception e) {
            return new ApiResponse(500, "ERROR", "搜索失败", null);
        }
    }

    /**
     * 分页获取帖子列表
     *
     * @param pageNum 页号
     * @param pageSize 页面大小
     * @return {@link ApiResponse }
     */
    @GetMapping("/page")
    public ApiResponse getPostsByPage(
            @RequestParam(defaultValue = "1") long pageNum,
            @RequestParam(defaultValue = "10") long pageSize) {
        try {
            Page<Posts> page = new Page<>(pageNum, pageSize);
            Page<Posts> result = postsService.page(page);
            return new ApiResponse(200, "SUCCESS", "获取成功", result);
        } catch (Exception e) {
            return new ApiResponse(500, "ERROR", "获取失败", null);
        }
    }
}
