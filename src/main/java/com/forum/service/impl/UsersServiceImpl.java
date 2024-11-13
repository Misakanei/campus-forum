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

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author CBL
 * @since 2024-11-12
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements IUsersService {
    @Resource
    UsersMapper usersMapper;

    @Override
    public boolean register(Users user) {
        user.setPassword(MD5Util.inputPassToFormPass(user.getPassword()));
        return usersMapper.insert(user) > 0;
    }

    @Override
    public Users findUserById(int id) {
        return usersMapper.selectById(id);
    }

    @Override
    public Users findUserByUsername(String username) {
        return usersMapper.selectOne(new QueryWrapper<Users>().eq("username", username));
    }

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
}
