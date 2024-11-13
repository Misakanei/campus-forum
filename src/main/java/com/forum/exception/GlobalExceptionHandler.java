package com.forum.exception;

import com.forum.entity.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserAlreadyExistsException.class)

    // 用户已存在
    public ApiResponse handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        return new ApiResponse(400, "ERROR_USER_EXISTS", e.getMessage(), null);
    }

    // 传入的用户信息为空
    public ApiResponse handUserInformationIsEmptyException(UserAlreadyExistsException e) {
        return new ApiResponse(401, "ERROR_USER_IS_EMPTY", e.getMessage(), null);
    }
}
