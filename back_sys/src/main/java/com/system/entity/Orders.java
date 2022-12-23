package com.system.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
 * @since 2022-12-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_orders")
public class Orders extends BaseEntity {

    private static final long serialVersionUID = 1L;

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
    private String payAt;

    @TableField("statu")
    private Integer statu;


    @TableField("created")
    private LocalDateTime created;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private  String filmName;

    @TableField(exist = false)
    private String filmCover;


}
