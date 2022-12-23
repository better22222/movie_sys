package com.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.system.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Byterain
 * @since 2022-12-08
 */
@Data
@TableName("sys_fans")
public class Fans {

    @TableField("id")
    private Long id;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    @TableField("avatar")
    private String avatar;

    @TableField("sex")
    private Integer sex;

    @TableField("email")
    private String email;

    @TableField("delTag")
    private Integer deltag;

    @TableField("created")
    private LocalDateTime created;
    @TableField("updated")
    private LocalDateTime updated;
    @TableField("statu")
    private Integer statu;


}
