package com.forum.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${file.upload.path}")
    private String uploadPath;

    /*    @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            // 配置图片访问路径
            registry.addResourceHandler("/uploads/**")
                    .addResourceLocations("file:" + System.getProperty("user.dir") + "/uploads/");
        }*/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 获取项目根目录
        String projectPath = System.getProperty("user.dir");

        // 配置图片访问路径
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + projectPath + "/uploads/")
                .setCacheControl(CacheControl.maxAge(1, TimeUnit.HOURS));
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
