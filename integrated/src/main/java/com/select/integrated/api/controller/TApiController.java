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

/**
 * 用户相关控制器
 */
public class TApiController extends ElasticsearchApiController{

    @ApiOperation(value = "根据ID删除用户信息", notes = "根据ID删除用户信息")
    @RequestMapping(value = "/t", method = RequestMethod.GET)
    public CommonResultEntity findByKeyWord() {
        System.out.println("1111111111111111");
        return CommonResultEntity.success(null);
    }
}
