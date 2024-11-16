package com.forum.controller;

import com.forum.entity.ApiResponse;
import com.forum.entity.Users;
import com.forum.service.IUsersService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
@RequestMapping("/user")
public class UsersController {
    @Resource
    private IUsersService usersService;

    @PostMapping("/getAllUsers")
    public ApiResponse getAllUsers() {
        try {
            List<Users> users = usersService.getAllUsers();
            return new ApiResponse(200, "SUCCESS", "获取所有用户信息成功", users);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(500, "ERROR", "服务器内部错误", null);
        }
    }

    /**
     * 根据id查询用户信息
     *
     * @param id ID
     * @return {@link String }
     */
    @GetMapping("/findUserById/{id}")
    public Users findUserById(@PathVariable int id) {
        Users user = usersService.findUserById(id);
        user.setPassword(null);
        return user;
    }

    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     * @return {@link String }
     */
    @GetMapping("/findUserByUsername/{username}")
    public String findUserByUsername(@PathVariable String username) {
        Users user = usersService.findUserByUsername(username);
        if (user != null) {
            return "true";
        } else {
            return "false";
        }
    }

    /**
     * 用户注册功能接口
     *
     * @param user 用户
     * @return {@link ApiResponse }
     */
    @PostMapping("/register")
    public ApiResponse register(@RequestBody Users user) {
        if (user != null) {
            Users userByUsername = usersService.findUserByUsername(user.getUsername());
            System.out.println(userByUsername);
            if (userByUsername == null) {
                boolean saveResult = usersService.register(user);
                if (saveResult) {
                    return new ApiResponse(200, "SUCCESS", "注册成功", null);
                } else {
                    return new ApiResponse(500, "ERROR", "服务器内部错误", null);
                }
            } else {
                return new ApiResponse(400, "ERROR_USER_EXISTS", "用户已存在", null);
            }
        } else {
            return new ApiResponse(401, "ERROR_USER_IS_EMPTY", "传入的数据不能为空", null);
        }
    }


    /**
     * 更新用户信息接口
     *
     * @param user 用户
     * @return {@link String }
     */
    @PostMapping("/updateUserInfo")
    public String updateUserInfo(@RequestBody Users user) {
        boolean result = usersService.updateUserInfo(user);
        if (result) {
            return "success";
        } else {
            return "error";
        }
    }


    /**
     * 登录
     *
     * @param loginInfo 登录信息
     * @return {@link ApiResponse }
     */
    @PostMapping("/login")
    public ApiResponse login(@RequestBody Map<String, String> loginInfo) {
        if (loginInfo.get("username") != null && loginInfo.get("password") != null) {
            String username = loginInfo.get("username");
            String password = loginInfo.get("password");
            boolean isLoginSuccess = usersService.login(username, password);
            if (isLoginSuccess) {
                Users user = usersService.findUserByUsername(username);
                user.setPassword(null);
                return new ApiResponse(200, "SUCCESS", "登录成功", user);
            } else {
                return new ApiResponse(401, "ERROR_USER_OR_PASSWORD_INCORRECT", "用户名或密码错误", null);
            }
        } else {
            return new ApiResponse(401, "ERROR_USER_IS_EMPTY", "传入的数据不能为空", null);
        }
    }

    /**
     * 修改密码
     *
     * @param password map集合，需要三个键值对分别为username，oldPassword，newPassword
     * @return {@link ApiResponse }
     */
    @PostMapping("/updatePassword")
    public ApiResponse updatePassword(@RequestBody Map<String, String> password) {
        String username = password.get("username");
        String oldPassword = password.get("oldPassword");
        String newPassword = password.get("newPassword");
        boolean isUpdateSuccess = usersService.updatePassword(username, oldPassword, newPassword);
        if (isUpdateSuccess) {
            return new ApiResponse(200, "SUCCESS", "修改成功", null);
        } else {
            return new ApiResponse(200, "SUCCESS", "旧密码错误", null);
        }
    }

    @GetMapping("/deleteUser")
    public ApiResponse deleteUser(@RequestParam int id) {
        boolean deleteUserSuccess = usersService.deleteUser(id);
        if (deleteUserSuccess) {
            return new ApiResponse(200, "SUCCESS", "删除成功", null);
        } else {
            return new ApiResponse(500, "ERROR", "服务器内部错误", null);
        }
    }

    @GetMapping("/undeleteUser")
    public ApiResponse undeleteUser(@RequestParam int id) {
        boolean deleteUserSuccess = usersService.undeleteUser(id);
        if (deleteUserSuccess) {
            return new ApiResponse(200, "SUCCESS", "启用成功", null);
        } else {
            return new ApiResponse(500, "ERROR", "服务器内部错误", null);
        }
    }

    @GetMapping("/logout/{username}")
    public ApiResponse logout(@PathVariable String username) {
        boolean isLogoutSuccess = usersService.logout(username);
        if (isLogoutSuccess) {
            return new ApiResponse(200, "SUCCESS", "退出登录成功", null);
        } else {
            return new ApiResponse(500, "ERROR_USER_IS_NOT_EXISTS", "用户不存在", null);
        }
    }

    /**
     * 修改个性签名
     *
     * @param updateSignatureInfo 更新签名信息
     * @return {@link ApiResponse }
     */
    @PostMapping("/updateSignature")
    public ApiResponse updateSignature(@RequestBody Map<String, String> updateSignatureInfo) {
        int userId = Integer.parseInt(updateSignatureInfo.get("userId"));
        String signature = updateSignatureInfo.get("signature");
        boolean flag = usersService.updateSignature(userId, signature);
        if (flag) {
            return new ApiResponse(200, "SUCCESS", "修改成功", null);
        } else {
            return new ApiResponse(500, "ERROR", "服务器内部错误", null);
        }
    }

    /**
     * 按ID获取评论计数
     *
     * @param userId 用户id
     * @return {@link ApiResponse }
     */
    @PostMapping("getCommentsCountById/{userId}")
    public ApiResponse getCommentsCountById(@PathVariable int userId) {
        try {
            int CommentCount = usersService.getCommentsCountById(userId);
            return new ApiResponse(200, "SUCCESS", "查询成功", CommentCount);
        } catch (Exception e) {
            return new ApiResponse(500, "ERROR", "服务器内部错误", e);
        }
    }

    @Value("${file.upload.path}")
    private String uploadPath;

    /**
     * 上传头像
     *
     * @param file 文件
     * @param userId 用户id
     * @return {@link ApiResponse }
     */
    @PostMapping("/uploadAvatar")
    public ApiResponse uploadAvatar(@RequestParam("avatar") MultipartFile file, @RequestParam("userId") Integer userId) {
        try {
            String projectPath = System.getProperty("user.dir");
            String avatarPath = projectPath + "/uploads/images/avatars/";
            File dir = new File(avatarPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String fileName = "avatar_" + userId + "_" + UUID.randomUUID().toString() + getFileExtension(file.getOriginalFilename());
            File dest = new File(avatarPath + fileName);
            file.transferTo(dest);
            String avatarUrl = "/uploads/images/avatars/" + fileName;
            return new ApiResponse(200, "SUCCESS", "上传成功", avatarUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(500, "ERROR", "上传失败: " + e.getMessage(), null);
        }
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    @PostMapping("/resetPassword/{userId}")
    public ApiResponse resetPassword(@PathVariable int userId) {
        boolean isResetSuccess = usersService.resetPassword(userId);
        if (isResetSuccess) {
            return new ApiResponse(200, "SUCCESS", "重置成功", null);
        } else {
            return new ApiResponse(500, "ERROR", "重置失败", null);
        }
    }
}
