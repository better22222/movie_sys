package com.system.mapper;

import com.system.entity.Film;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.system.entity.User;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Byterain
 * @since 2022-12-08
 */
public interface FilmMapper extends BaseMapper<Film> {
    List<HashMap<String,Long>> getFilmByRegion();
}
