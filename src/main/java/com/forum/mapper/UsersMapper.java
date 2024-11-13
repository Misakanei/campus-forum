package com.forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.forum.entity.Users;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
}
