package com.forum.controller;

import com.forum.entity.ApiResponse;
import com.forum.service.ICategoriesService;
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
@RequestMapping("/categories")
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
public class CategoriesController {
    @Resource
    private ICategoriesService categoriesService;

    /**
     * 查询所有板块
     *
     * @return {@link List }<{@link Map }<{@link String }, {@link Object }>>
     */
    @GetMapping("/selectAllCategory")
    public List<Map<String, Object>> selectAllCategory() {
        return categoriesService.selectAllCategory();
    }

    /**
     * 添加板块
     *
     * @param param param
     * @return {@link ApiResponse }
     */
    @PostMapping("/addCategory")
    public ApiResponse addCategory(@RequestBody Map<String, String> param) {
        try {
            String categoryName = param.get("categoryName");
            String description = param.get("description");
            boolean flag = categoriesService.addCategory(categoryName, description);
            if (flag) {
                return new ApiResponse(200, "SUCCESS", "添加成功", null);
            } else {
                return new ApiResponse(500, "ERROR", "服务器内部错误", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(500, "ERROR", "服务器内部错误", null);
        }
    }

    /**
     * 删除板块
     *
     * @param categoryId 类别id
     * @return {@link ApiResponse }
     */
    @PostMapping("/deleteCategory/{categoryId}")
    public ApiResponse deleteCategory(@PathVariable int categoryId) {
        try {
            boolean flag = categoriesService.deleteCategory(categoryId);
            if (flag) {
                return new ApiResponse(200, "SUCCESS", "删除成功", null);
            } else {
                return new ApiResponse(500, "ERROR", "服务器内部错误", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(500, "ERROR", "服务器内部错误", null);
        }
    }

    /**
     * 修改板块信息
     *
     * @param param param
     * @return {@link ApiResponse }
     */
    @PostMapping("/updateCategory")
    public ApiResponse updateCategory(@RequestBody Map<String, String> param) {
        try {
            int categoryId = Integer.parseInt(param.get("categoryId"));
            String categoryName = param.get("categoryName");
            String description = param.get("description");
            boolean flag = categoriesService.updateCategory(categoryId, categoryName, description);
            if (flag) {
                return new ApiResponse(200, "SUCCESS", "删除成功", null);
            } else {
                return new ApiResponse(500, "ERROR", "服务器内部错误", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(500, "ERROR", "服务器内部错误", null);
        }
    }
}
