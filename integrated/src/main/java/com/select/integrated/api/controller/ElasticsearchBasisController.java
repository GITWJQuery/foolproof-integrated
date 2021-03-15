package com.select.integrated.api.controller;

import com.select.integrated.api.common.CommonResultEntity;
import com.select.integrated.domain.service.ElasticsearchService;
import com.select.integrated.utils.ClassUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Elasticsearch基础操作接口
 */
@Api(value = "Elasticsearch基础操作接口", tags = "Elasticsearch基础操作接口")
@RestController
@RequestMapping("/api/v1/elasticsearch")
public class ElasticsearchBasisController {

    @Autowired
    private ElasticsearchService elasticsearchService;

    @ApiOperation(value = "获取所有实体类名", notes = "获取所有实体类名")
    @RequestMapping(value = "/all/className", method = RequestMethod.GET)
    public CommonResultEntity allClassName() {
        return CommonResultEntity.success(ClassUtil.getPojoNames());
    }

    @ApiOperation(value = "创建指定索引", notes = "创建指定索引")
    @RequestMapping(value = "/{className}/{synchronizationStatus}", method = RequestMethod.GET)
    public CommonResultEntity createIndex(@PathVariable String className, @PathVariable boolean synchronizationStatus) {
        return CommonResultEntity.success(elasticsearchService.createIndex(ClassUtil.getclass(className)));
    }

    @ApiOperation(value = "删除指定索引", notes = "删除指定索引")
    @RequestMapping(value = "/{className}", method = RequestMethod.DELETE)
    public CommonResultEntity deleteIndex(@PathVariable String className) {
        return CommonResultEntity.success(elasticsearchService.deleteIndex(ClassUtil.getclass(className)));
    }
}
