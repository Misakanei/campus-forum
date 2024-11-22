package com.forum.exception;

import cn.dev33.satoken.exception.DisableServiceException;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotRoleException;
import com.forum.entity.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author Misaka
 * @date 2024/11/13
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotLoginException.class)
    public ApiResponse handleNotLoginException(NotLoginException e) {
        return new ApiResponse(401, "UNAUTHORIZED", "请先登录", null);
    }

    @ExceptionHandler(NotRoleException.class)
    public ApiResponse handleNotRoleException(NotRoleException e) {
        return new ApiResponse(403, "FORBIDDEN", "无权限访问", null);
    }

    @ExceptionHandler(DisableServiceException.class)
    public ApiResponse handleDisableServiceException(DisableServiceException e) {
        return new ApiResponse(403, "FORBIDDEN", "账号已被禁用", null);
    }
}
