package com.forum.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forum.entity.Users;
import com.forum.mapper.UsersMapper;
import com.forum.service.IUsersService;
import com.forum.utils.MD5Util;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Misaka
 * @since 2024-11-12
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {
    @Resource
    UsersMapper usersMapper;

    /**
     * 注册
     *
     * @param user 用户
     * @return boolean
     */
    @Override
    public boolean register(Users user) {
        user.setPassword(MD5Util.inputPassToFormPass(user.getPassword()));
        return usersMapper.insert(user) > 0;
    }

    /**
     * 通过id查找用户
     *
     * @param id ID
     * @return {@link Users }
     */
    @Override
    public Users findUserById(int id) {
        return usersMapper.selectById(id);
    }

    /**
     * 通过用户名查找用户
     *
     * @param username 用户名
     * @return {@link Users }
     */
    @Override
    public Users findUserByUsername(String username) {
        return usersMapper.selectOne(new QueryWrapper<Users>().eq("username", username));
    }

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return boolean
     */
    @Override
    public boolean login(String username, String password) {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        Users users = usersMapper.selectOne(queryWrapper);
        if (users != null && users.getPassword().equals(MD5Util.inputPassToFormPass(password))) {
            StpUtil.login(users.getUserId());
            return true;
        } else {
            return false;
        }
    }

    /**
     * 修改密码
     *
     * @param username 用户名
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return boolean
     */
    @Override
    public boolean updatePassword(String username, String oldPassword, String newPassword) {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<Users>().eq("username", username);
        queryWrapper.eq("password", MD5Util.inputPassToFormPass(oldPassword));
        Users users = usersMapper.selectOne(queryWrapper);
        if (users != null) {
            users.setPassword(MD5Util.inputPassToFormPass(newPassword));
            return usersMapper.updateById(users) > 0;
        } else {
            return false;
        }
    }

    /**
     * 禁用用户
     *
     * @param id ID
     * @return boolean
     */
    @Override
    public boolean deleteUser(int id) {
        return usersMapper.deleteById(id) == 1;
    }

    @Override
    public boolean undeleteUser(int id) {
        Users users = usersMapper.selectUserByIdAndDeleted(id);
        if (users != null) {
            users.setDeleted(0);
            return usersMapper.updateByIdAndUndeleted(users.getUserId()) > 0;
        } else {
            return false;
        }
    }

    /**
     * 注销
     *
     * @param username 用户名
     * @return boolean
     */
    @Override
    public boolean logout(String username) {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        Users users = usersMapper.selectOne(queryWrapper);
        if (users != null) {
            StpUtil.logout(users.getUserId());
            return true;
        } else {
            return false;
        }
    }

    /**
     * 更新个性签名
     *
     * @param userId 用户id
     * @param signature 签名
     * @return boolean
     */
    @Override
    public boolean updateSignature(int userId, String signature) {
        return usersMapper.updateSignature(userId, signature) > 0;
    }

    /**
     * 按id获取评论计数
     *
     * @param userId 用户id
     * @return int
     */
    @Override
    public int getCommentsCountById(int userId) {
        return usersMapper.getCommentsCountById(userId);
    }

    /**
     * 更新用户信息
     *
     * @param user 用户
     * @return boolean
     */
    @Override
    public boolean updateUserInfo(Users user) {
        return usersMapper.updateById(user) > 0;
    }

    /**
     * 更新头像
     *
     * @param userId 用户id
     * @param avatarUrl 头像路径
     * @return boolean
     */
    @Override
    public boolean updateAvatar(Integer userId, String avatarUrl) {
        Users user = this.getById(userId);
        if (user != null) {
            user.setAvatarPath(avatarUrl);
            return this.updateById(user);
        }
        return false;
    }

    /**
     * 获取所有用户
     *
     * @return {@link List }<{@link Users }>
     */
    @Override
    public List<Users> getAllUsers() {
        return usersMapper.getAllUsers();
    }

    /**
     * 重置密码
     *
     * @param userId 用户id
     * @return boolean
     */
    @Override
    public boolean resetPassword(int userId) {
        String password = MD5Util.inputPassToFormPass("123456");
        return usersMapper.resetPassword(password, userId) > 0;
    }
}
