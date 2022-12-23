package com.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
@TableName("sys_arrangement")
public class Arrangement extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 电影组件（关联sys_film表
     */
    @TableField("fid")
    private Long fid;

    /**
     * 电影名
     */
    @TableField("name")
    private String name;

    /**
     * 开放座位
     */
    @TableField("seat_number")
    private Integer seatNumber;

    /**
     * 票房统计
     */
    @TableField("box_office")
    private Integer boxOffice;

    /**
     * 价格
     */
    @TableField("price")
    private Integer price;

    /**
     * 放映类型
     */
    @TableField("type")
    private String type;

    /**
     * 放映日期
     */
    @TableField("date")
    private LocalDate date;

    @TableField("start_time")
    private LocalDateTime startTime;

    @TableField("end_time")
    private LocalDateTime endTime;


}
