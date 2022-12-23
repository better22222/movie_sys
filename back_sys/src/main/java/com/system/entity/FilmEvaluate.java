package com.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.system.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author Byterain
 * @since 2022-12-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_film_evaluate")
public class FilmEvaluate extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 电影主键（关联sys_film
     */
    @TableField("fid")
    private Long fid;

    /**
     * 用户主键（关联sys_user
     */
    @TableField("uid")
    private Long uid;

    /**
     * 评分
     */
    @TableField("star")
    private Integer star;

    /**
     * 评论内容
     */
    @TableField("comment")
    private String comment;

    @TableField(exist = false)
    private String fansName;

    @TableField(exist = false)
    private String filmName;

    @TableField(exist = false)
    private String fansUrl;
}
