package com.forum.controller;

import com.forum.entity.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@RestController
@RequestMapping("/upload")
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
public class UploadController {

    @Value("${file.upload.path}")
    private String uploadPath;

    @PostMapping("/image")
    public ApiResponse uploadImage(@RequestParam("image") MultipartFile file) {
        try {
            // 获取项目根目录的绝对路径
            String projectPath = System.getProperty("user.dir");
            // 确保上传目录存在
            File uploadDir = new File(projectPath + File.separator + uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // 生成文件名
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileName = UUID.randomUUID().toString() + extension;

            // 保存文件
            File dest = new File(uploadDir, fileName);
            file.transferTo(dest);

            // 返回文件访问路径
            String filePath = "/uploads/images/" + fileName;
            return new ApiResponse(200, "SUCCESS", "上传成功", filePath);

        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(500, "ERROR", "上传失败: " + e.getMessage(), null);
        }
    }
}
