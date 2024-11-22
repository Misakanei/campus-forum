package com.forum.config;

import org.springframework.context.annotation.Configuration;

/**
 * sa-token配置
 *
 * @author Misaka
 * @date 2024/11/15
 */
@Configuration
public class SaTokenConfigure {
/*    // 注册 Sa-Token 全局过滤器
    @Bean
    public SaServletFilter getSaServletFilter() {
        return new SaServletFilter()
                .addInclude("/**")  // 拦截所有请求
                .addExclude("/user/login", "/user/register")  // 排除登录注册接口
                .setAuth(obj -> {
                    // 登录校验
                    SaRouter.match("/admin/**", r -> StpUtil.checkLogin());
                    // 权限校验 - 后台接口必须具有管理员权限
                    SaRouter.match("/admin/**", r -> {
                        StpUtil.checkRole("admin");
                    });
                    // 其他接口需要登录
                    SaRouter.match("/**", r -> StpUtil.checkLogin());
                })
                .setError(e -> {
                    // 统一异常处理
                    return new ApiResponse(401, "UNAUTHORIZED", e.getMessage(), null);
                });
    }*/
}