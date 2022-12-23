package com.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.system.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Byterain
 * @since 2022-12-12
 */
@Data
@TableName("sys_orders")
public class Orders  {

    @TableField("id")
    private Long id;
    @TableField("created")
    private LocalDateTime created;
    @TableField("updated")
    private LocalDateTime updated;
    @TableField("statu")
    private Integer statu;

    @TableField("uid")
    private String uid;

    @TableField("phone")
    private String phone;

    @TableField("aid")
    private String aid;

    @TableField("seats")
    private String seats;

    @TableField("price")
    private BigDecimal price;

    @TableField("pay_at")
    private LocalDateTime payAt;


    @TableField(exist = false)
    private String filmCover;
    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private  String filmName;


}
