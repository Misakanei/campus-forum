package com.forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.forum.entity.Users;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author CBL
 * @since 2024-11-12
 */
public interface UsersMapper extends BaseMapper<Users> {

    /**
     * 根据id查询已删除的用户
     *
     * @param id ID
     * @return {@link Users }
     */
    @Select("SELECT user_id,username,password,email,created_time,updated_time,deleted FROM users WHERE user_id = #{id} and deleted = 1")
    Users selectUserByIdAndDeleted(int id);

    /**
     * 根据id恢复启用已删除的用户
     *
     * @param users 用户
     * @return int
     */
    @Update("UPDATE users SET deleted = 0 WHERE user_id = #{id}")
    int updateByIdAndUndeleted(int id);


    /**
     * 修改个性签名
     *
     * @param userId 用户id
     * @param signature 签名
     * @return boolean
     */
    @Update("UPDATE users SET signature = #{signature} WHERE user_id = #{userId}")
    int updateSignature(int userId, String signature);

    /**
     * 按id获取评论计数
     *
     * @param userId 用户id
     * @return int
     */
    @Select("""
            SELECT COUNT(*) AS total_comments
            FROM comments
            WHERE user_id = #{userId};
            """)
    int getCommentsCountById(int userId);

    /**
     * 更新头像
     *
     * @param userId 用户id
     * @param avatarPath 化身路径
     * @return int
     */
    @Update("UPDATE users SET avatar_path = #{avatarPath} WHERE user_id = #{userId}")
    int updateAvatar(@Param("userId") Integer userId, @Param("avatarPath") String avatarPath);

    /**
     * 获取所有用户
     *
     * @return {@link List }<{@link Users }>
     */
    @Select("SELECT * FROM users")
    List<Users> getAllUsers();

    /**
     * 重置密码
     *
     * @param password 密码
     * @param userId 用户id
     * @return boolean
     */
    @Update("UPDATE users SET password = #{password} WHERE user_id = #{userId}")
    int resetPassword(String password, int userId);
}
