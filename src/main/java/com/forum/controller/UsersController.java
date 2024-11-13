package com.forum.controller;

import com.forum.entity.ApiResponse;
import com.forum.entity.Users;
import com.forum.service.IUsersService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author CBL
 * @since 2024-11-12
 */
@Controller
@RequestMapping("/user")
public class UsersController {
    @Resource
    private IUsersService usersService;

    /**
     * 根据id查询用户信息
     *
     * @param id ID
     * @return {@link String }
     */
    @ResponseBody
    @GetMapping("/findUserById")
    public String findUserById(@RequestBody int id) {
        Users user = usersService.findUserById(id);
        return user.toString();
    }

    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     * @return {@link String }
     */
    @ResponseBody
    @GetMapping("/findUserByUsername")
    public String findUserByUsername(@RequestParam String username) {
        Users user = usersService.findUserByUsername(username);
        return user.toString();
    }

    /**
     * 用户注册功能接口
     *
     * @param user 用户
     * @return {@link ApiResponse }
     */
    @ResponseBody
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
    @ResponseBody
    @PostMapping("/updateInfo")
    public String updateInfo(@RequestBody Users user) {
        boolean result = usersService.updateById(user);
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
    @ResponseBody
    @PostMapping("/login")
    public ApiResponse login(@RequestBody Map<String, String> loginInfo) {
        if (loginInfo.get("username") != null && loginInfo.get("password") != null) {
            String username = loginInfo.get("username");
            String password = loginInfo.get("password");
            boolean isLoginSuccess = usersService.login(username, password);
            if (isLoginSuccess) {
                return new ApiResponse(200, "SUCCESS", "登录成功", null);
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
    @ResponseBody
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

    @ResponseBody
    @GetMapping("/deleteUser")
    public ApiResponse deleteUser(@RequestParam int id) {
        boolean deleteUserSuccess = usersService.deleteUser(id);
        if (deleteUserSuccess) {
            return new ApiResponse(200, "SUCCESS", "删除成功", null);
        } else {
            return new ApiResponse(500, "ERROR", "服务器内部错误", null);
        }
    }

    @ResponseBody
    @GetMapping("/undeleteUser")
    public ApiResponse undeleteUser(@RequestParam int id) {
        boolean deleteUserSuccess = usersService.undeleteUser(id);
        if (deleteUserSuccess) {
            return new ApiResponse(200, "SUCCESS", "启用成功", null);
        } else {
            return new ApiResponse(500, "ERROR", "服务器内部错误", null);
        }
    }
}
