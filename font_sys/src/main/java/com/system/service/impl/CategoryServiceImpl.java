package com.system.service.impl;

import com.system.entity.Category;
import com.system.mapper.CategoryMapper;
import com.system.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Byterain
 * @since 2022-12-08
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Override
    public List<String> getCName(String ids) {
        String[] split = ids.split(",");
        List<String> cNames = new ArrayList<>();
        System.out.println(split[0]);
        for (String s : split) {
            Category byId = this.getById(s);
            String name = byId.getName();
            cNames.add(name);
        }
        return cNames;
    }
}
