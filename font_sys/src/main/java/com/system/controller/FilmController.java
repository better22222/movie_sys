package com.system.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.common.R;
import com.system.entity.Film;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.system.common.BaseController;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Byterain
 * @since 2022-12-08
 */
@RestController
@RequestMapping("/film")
@Slf4j
public class FilmController extends BaseController {

    @Autowired
    private RestHighLevelClient client;

    @GetMapping("/list/{type}")
    public R list(@PathVariable Integer type, String filmName){
        log.info("type:"+type);
        log.info("current:"+getPage().getCurrent());
        log.info("pages"+getPage().getPages());

        //判断type==1，查询热映
        if(type == 1){
            Page<Film> filmPage = filmService.page(getPage(),new QueryWrapper<Film>()
                    .le("release_time", LocalDateTime.now())
                    .like(StrUtil.isNotBlank(filmName),"name",filmName)
            );
        }
        //是否添加gt条件，type==2添加，查询即将上映，否，查询全部
        Page<Film> filmPage = filmService.page(getPage(), new QueryWrapper<Film>()
                .gt(type == 2, "release_time", LocalDateTime.now())
                .like(StrUtil.isNotBlank(filmName), "name", filmName)
        );

        return R.ok().data("films", filmPage);
    }

    @GetMapping("/info/{fid}")
    public R info(@PathVariable Long fid){
        Film byId = filmService.getById(fid);
        return R.ok().data("film",byId);
    }

    @GetMapping("/completion/{value}")
    public R completion(@PathVariable String value) throws IOException {
        //1、准备Request
        SearchRequest request = new SearchRequest("movie");
        //2、准备DSL
        request.source().suggest(new SuggestBuilder().addSuggestion(
                "suggestions", //补全名称
                SuggestBuilders
                        .completionSuggestion("suggestion") //补全字段名
                        .prefix(value) //关键字
                        .skipDuplicates(true) //去重
                        .size(10)
        ));
        //3、发起请求
        SearchResponse search = client.search(request, RequestOptions.DEFAULT);
        //4、解析结果
        Suggest suggest = search.getSuggest();
        //4.1 根据名称获取补全结果
        CompletionSuggestion suggestions =suggest.getSuggestion("suggestions");
        //4.2 获取options并遍历
        List<Map> filNames = new ArrayList<>();
        for(CompletionSuggestion.Entry.Option option : suggestions.getOptions()){
            //4.2 获取一个option中的text，也就是补全的词条,此处为查询出的酒店名
            filNames.add(option.getHit().getSourceAsMap());
            System.out.println(option.getHit());
        }

        return R.ok().data("completion",filNames);
    }

    @GetMapping("/search/{value}")
    public R search(@PathVariable String value) throws IOException {
        //1、准备Request
        SearchRequest request = new SearchRequest("movie");
        //2、准备SDL
        request.source().query(
                QueryBuilders.matchQuery("all",value)
        );
        //3、发送请求
        SearchResponse search = client.search(request, RequestOptions.DEFAULT);
        System.out.println(search);
        //4、解析结果
        SearchHits searchHits = search.getHits();
        //4.1、查询的总条数
        long total = searchHits.getTotalHits().value;
        //4.2、查询的结果数组
        SearchHit[] hits = searchHits.getHits();
        List<String> strings = new ArrayList<>();
        for(SearchHit hit : hits){
            //将 search数据转换为json
            String json = hit.getSourceAsString();
            strings.add(json);
        }
        return R.ok().data("search",strings);
    }

    @GetMapping("/list2/{type2}")
    public R list2(@PathVariable Integer type2, String filmName){
        log.info("type:"+type2);
        log.info("current:"+getPage().getCurrent());
        log.info("pages"+getPage().getPages());

        //判断type2==1，查询电影类型
        if(type2 == 1){
            Page<Film> filmPage = filmService.page(getPage(),new QueryWrapper<Film>()
                    .eq("category_id", 1)
                    .like(StrUtil.isNotBlank(filmName), "name", filmName)

            ); return R.ok().data("films", filmPage);
        }
        //是否添加gt条件，type==2添加，查询即将上映，否，查询全部
        if(type2 == 2) {
            Page<Film> filmPage = filmService.page(getPage(), new QueryWrapper<Film>()
                    .eq("category_id", 2)
                    .like(StrUtil.isNotBlank(filmName), "name", filmName)

            ); return R.ok().data("films", filmPage);
        }
        if(type2 == 3) {
            Page<Film> filmPage = filmService.page(getPage(), new QueryWrapper<Film>()
                    .eq("category_id", 3)
                    .like(StrUtil.isNotBlank(filmName), "name", filmName)
            ); return R.ok().data("films", filmPage);
        }
        if(type2 == 4) {
            Page<Film> filmPage = filmService.page(getPage(), new QueryWrapper<Film>()
                    .eq("category_id", 4)
                    .like(StrUtil.isNotBlank(filmName), "name", filmName)
            ); return R.ok().data("films", filmPage);
        }
        if(type2 == 5) {
            Page<Film> filmPage = filmService.page(getPage(), new QueryWrapper<Film>()
                    .eq("category_id", 5)
                    .like(StrUtil.isNotBlank(filmName), "name", filmName)
            ); return R.ok().data("films", filmPage);
        }


        Page<Film> filmPage = filmService.page(getPage(), new QueryWrapper<Film>()
                .gt(type2 == 0, "category_id", 0)
                .like(StrUtil.isNotBlank(filmName), "name", filmName)

        ); return R.ok().data("films", filmPage);


    }




}
