package com.select.integrated.api.controller;

import com.select.integrated.annotation.RecordLog;
import com.select.integrated.api.common.CommonResultEntity;
import com.select.integrated.domain.pojo.User;
import com.select.integrated.domain.service.ElasticsearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Inherited;

/**
 * 用户相关控制器
 */
@Api(value = "用户相关接口(ES)", tags = "用户相关接口(ES)")
@RestController
@RequestMapping("/api/v1/user/elasticsearch")
public class ElasticsearchApiController {

    @Autowired
    private ElasticsearchService elasticsearchService;

    @ApiOperation(value = "根据关键词查询用户信息", notes = "根据关键词查询用户信息")
    @RequestMapping(value = "", method = RequestMethod.GET)
    @RecordLog(description = "根据关键词查询用户信息")
    public CommonResultEntity findByKeyWord(@RequestParam String keyWord, Pageable pageable) {
        NativeSearchQuery query =
                new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchQuery("name", keyWord))
                        .withPageable(pageable)
                        .withSort(SortBuilders.fieldSort("age").order(SortOrder.DESC))
                        .build();
        Page<User> pageUsers = elasticsearchService.selectDocumentByKeyWord(query, User.class);
        return CommonResultEntity.success(pageUsers);
    }

    @ApiOperation(value = "根据ID删除用户信息", notes = "根据ID删除用户信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public CommonResultEntity findByKeyWord(@PathVariable String id) {
        return CommonResultEntity.success(elasticsearchService.deleteDocument(id, User.class));
    }
}
