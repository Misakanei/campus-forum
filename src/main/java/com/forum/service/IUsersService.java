package com.forum.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.forum.entity.Users;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author CBL
 * @since 2024-11-12
 */
public interface IUsersService extends IService<Users> {
    /**
     * 注册用户接口
     * @param user user对象
     * @return 成功返回true失败返回false
     */
    boolean register(Users user);

    /**
     * 根据id查找用户接口
     * @param id 用户id
     * @return users对象
     */
    Users findUserById(int id);

    /**
     * 根据用户名查找用户接口
     * @param username 用户id
     * @return users对象
     */
    Users findUserByUsername(String username);


    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return boolean
     */
    boolean login(String username, String password);

    /**
     * 更新密码
     *
     * @param username 用户名
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return boolean
     */
    boolean updatePassword(String username, String oldPassword, String newPassword);

    /**
     * 删除用户
     *
     * @param id ID
     */
    boolean deleteUser(int id);

    /**
     * 启用用户
     *
     * @param id ID
     * @return boolean
     */
    boolean undeleteUser(int id);

    /**
     * 退出登录
     *
     * @param username 用户名
     * @return boolean
     */
    boolean logout(String username);

    /**
     * 修改个性签名
     *
     * @param userId 用户id
     * @param signature 签名
     * @return boolean
     */
    boolean updateSignature(int userId, String signature);

    /**
     * 根据用户ID获得评论总数
     *
     * @param userId 用户id
     * @return int
     */
    int getCommentsCountById(int userId);

    /**
     * 更新用户信息
     *
     * @param user 用户
     * @return boolean
     */
    boolean updateUserInfo(Users user);

    boolean updateAvatar(Integer userId, String avatarUrl);

    List<Users> getAllUsers();

    boolean resetPassword(int userId);
}
