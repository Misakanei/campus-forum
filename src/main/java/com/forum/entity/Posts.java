package com.forum.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author CBL
 * @since 2024-11-12
 */
@Data
public class Posts implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 帖子id
     */
    @TableId(value = "post_id", type = IdType.AUTO)
    private Integer postId;

    /**
     * 外键关联用户表
     */
    private Integer userId;

    /**
     * 外键关联分类表
     */
    private Integer categoryId;

    /**
     * 帖子标题
     */
    private String title;

    /**
     * 帖子内容
     */
    private String content;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最后修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 阅读量
     */
    private Integer readingVolume;

    /**
     * 逻辑删除标识
     */
    private Integer deleted;

    /**
     * 图片存放路径
     */
    private String imagePath;

    /**
     * 点赞数
     */
    private int support;
}
