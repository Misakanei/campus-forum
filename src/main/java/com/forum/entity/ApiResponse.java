package com.forum.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 用于向前端响应状态信息等
 */
@Data
@AllArgsConstructor
public class ApiResponse {
    private int status;
    private String errorCode;
    private String message;
    private Object data;
}
