package com.system.service;

import com.system.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Byterain
 * @since 2022-12-08
 */
public interface CategoryService extends IService<Category> {
    public List<String > getCName(String ids);
}
