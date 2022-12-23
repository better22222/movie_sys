package com.system;

import com.alibaba.fastjson.JSON;
import com.system.entity.Doc.FilmDoc;
import com.system.entity.Film;
import com.system.service.CategoryService;
import com.system.service.FilmService;
import com.zaxxer.hikari.util.FastList;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class AdminApplicationTests {

    @Autowired
    FilmService filmService;

    @Autowired
    CategoryService categoryService;

    private RestHighLevelClient client;

    @BeforeEach
    void setUp(){
        this.client = new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://192.168.14.200:9200")
        ));
    }

    @AfterEach
    void tearDown() throws IOException {
        this.client.close();
    }
    @Test
    void testr() throws IOException {
        GetIndexRequest request = new GetIndexRequest("movie");
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    @Test
    void testAddDocument() throws IOException {
        Film byId = filmService.getById(1L);
        FilmDoc filmDoc = new FilmDoc();

        List<String> cName = categoryService.getCName(byId.getCategoryId());
        filmDoc.setId(byId.getId());
        filmDoc.setName(byId.getName());
        filmDoc.setCategory(cName);
        filmDoc.setCover(byId.getCover());
        filmDoc.setRegion(byId.getRegion());
        filmDoc.setSuggestion(Arrays.asList(byId.getName(),String.valueOf(cName)));

        //准备request对象
        IndexRequest request = new IndexRequest("movie").id(byId.getId().toString());
        System.out.println(filmDoc);
        //准备json文档
        request.source(JSON.toJSONString(filmDoc),XContentType.JSON);

        //发送请求
         client.index(request, RequestOptions.DEFAULT);
    }

    //批量导入酒店数据
    @Test
    void testBulkRequest() throws IOException {
        List<Film> films = filmService.list();

        BulkRequest request = new BulkRequest();

        for (Film film : films) {
            FilmDoc filmDoc = new FilmDoc();
            List<String> cName = categoryService.getCName(film.getCategoryId());
            filmDoc.setId(film.getId());
            filmDoc.setName(film.getName());
            filmDoc.setCategory(cName);
            filmDoc.setCover(film.getCover());
            filmDoc.setRegion(film.getRegion());
            filmDoc.setSuggestion(Arrays.asList(film.getName(),String.valueOf(cName)));

            request.add(new IndexRequest("movie")
                .id(filmDoc.getId().toString())
                    .source(JSON.toJSONString(filmDoc),XContentType.JSON)
            );
            client.bulk(request,RequestOptions.DEFAULT);
        }
    }


    @Test
    public void testGet() throws IOException {
        GetRequest request = new GetRequest("movie","1");
        GetResponse response = client.get(request,RequestOptions.DEFAULT);
        String json = response.getSourceAsString();
        FilmDoc filmDoc = JSON.parseObject(json,FilmDoc.class);
        System.out.println(filmDoc);
    }

    @Test
    void testSuggest() throws IOException {
        //1、准备Request
        SearchRequest request = new SearchRequest("movie");
        //2、准备DSL
        request.source().suggest(new SuggestBuilder().addSuggestion(
                "suggestions", //补全名称
                SuggestBuilders
                        .completionSuggestion("suggestion") //补全字段名
                        .prefix("j") //关键字
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
        List<String > filNames = new ArrayList<>();
        List<FilmDoc> filmDocs = new ArrayList<>();
        for(CompletionSuggestion.Entry.Option option : suggestions.getOptions()){
            //4.2 获取一个option中的text，也就是补全的词条,此处为查询出的酒店
            filNames.add(option.getText().string());
        }
        System.out.println(filNames);
    }

    @Test
    void testMatchAll() throws IOException {
        //1、准备Request
        SearchRequest request = new SearchRequest("movie");
        //2、准备SDL
        request.source().query(
                QueryBuilders.matchQuery("all","sha")
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
        for(SearchHit hit : hits){
            //将 search数据转换为json
            String json = hit.getSourceAsString();
            System.out.println(json);
        }
    }


}
