package com.system.entity.Doc;

import com.system.common.BaseController;
import com.system.entity.Film;
import com.system.service.CategoryService;
import com.system.service.impl.CategoryServiceImpl;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
public class FilmDoc {

    @Autowired
    CategoryService categoryService;
    private Long id;
    private String name;
    private String region;
    private String cover;
    private List<String > category;
    private List<String > suggestion;

    public FilmDoc(Film film){
        this.id = film.getId();
        this.name = film.getName();
        this.region = film.getRegion();
        this.cover = film.getCover();
        this.category = categoryService.getCName(film.getCategoryId());
        this.suggestion = Arrays.asList(this.name,String.valueOf(this.category));
    }
}
