package com.forum.controller;

import com.forum.entity.ApiResponse;
import com.forum.entity.Comments;
import com.forum.service.ICommentsService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Misaka
 * @since 2024-11-12
 */
@RestController
@RequestMapping("/comments")
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
public class CommentsController {
    @Resource
    private ICommentsService commentsService;

    /**
     * 添加评论
     *
     * @param commentInfo 注释信息
     * @return {@link ApiResponse }
     */
    @PostMapping("/addComment")
    public ApiResponse addComment(@RequestBody Map<String, String> commentInfo) {
        System.out.println(commentInfo);
        int userId = Integer.parseInt(commentInfo.get("userId"));
        int postId = Integer.parseInt(commentInfo.get("postId"));
        String comment = commentInfo.get("comment");
        if (comment == null || comment.equals("")) {
            return new ApiResponse(401, "COMMENTS_CAN_NOT_BE_EMPTY", "评论内容不能为空", null);
        } else {
            boolean flag = commentsService.addComment(userId, postId, comment);
            if (flag) {
                return new ApiResponse(200, "SUCCESS", "评论成功", null);
            } else {
                return new ApiResponse(500, "SUCCESS", "发生了未知的错误", null);
            }
        }
    }

    /**
     * 获取所有评论
     *
     * @return {@link ApiResponse }
     */
    @PostMapping("/getAllComments")
    public ApiResponse getAllComments() {
        try {
            List<Comments> comments = commentsService.getAllComments();
            return new ApiResponse(200, "SUCCESS", "获取成功", comments);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(500, "ERROR", "服务器内部错误", null);
        }
    }

    /**
     * 删除评论
     *
     * @param commentId 注释id
     * @return {@link ApiResponse }
     */
    @PostMapping("/deleteComment/{commentId}")
    public ApiResponse deleteComment(@PathVariable int commentId) {
        try {
            boolean flag = commentsService.deleteComment(commentId);
            if (flag) {
                return new ApiResponse(200, "SUCCESS", "删除成功", null);
            } else {
                return new ApiResponse(500, "ERROR", "删除失败", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(500, "ERROR", "服务器内部错误", null);
        }
    }
}
