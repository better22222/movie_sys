package com.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.system.entity.Orders;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Byterain
 * @since 2022-12-10
 */
public interface OrdersMapper extends BaseMapper<Orders> {
    List<HashMap<String,Long>> getOrderByTime();
}
