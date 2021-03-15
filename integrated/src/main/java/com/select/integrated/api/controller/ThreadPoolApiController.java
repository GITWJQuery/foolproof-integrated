package com.select.integrated.api.controller;

import com.select.integrated.annotation.RecordLog;
import com.select.integrated.api.bo.UserFilterBO;
import com.select.integrated.api.common.CommonResultEntity;
import com.select.integrated.domain.pojo.User;
import com.select.integrated.domain.service.ElasticsearchService;
import com.select.integrated.domain.service.MongoDBService;
import com.select.integrated.domain.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 线程池相关接口
 */
@Api(value = "线程池相关接口", tags = "线程池相关接口")
@RestController
@RequestMapping("/api/v1/user/threads")
@Slf4j
public class ThreadPoolApiController {

    @Autowired
    private ElasticsearchService elasticsearchService;

    @Autowired
    private MongoDBService mongoDBService;

    @Autowired
    private UserService userService;

    /**
     * 看门狗
     */
    protected volatile CountDownLatch countDownLatch;

    /**
     * ES
     */
    protected volatile AtomicReference<Object> esObject;

    /**
     * Mongo
     */
    protected volatile AtomicReference<Object> mongoObject;

    /**
     * MySQL
     */
    protected volatile AtomicReference<Object> mysqlObject;

    @Autowired
    @Qualifier("queryExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @ApiOperation(value = "测试线程池", notes = "测试线程池")
    @RequestMapping(value = "", method = RequestMethod.GET)
    @RecordLog(description = "测试线程池")
    public CommonResultEntity threadPool(@RequestParam String keyWord, Pageable pageable) throws InterruptedException {
        // 初始化返回结果集
        List<Object> results = Collections.synchronizedList(new ArrayList<>(5));

        // 初始化任务列表
        List<Runnable> runnableList = Collections.synchronizedList(new ArrayList<>(5));

        // 任务列表增加任务
        NativeSearchQuery esquery =
                new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchQuery("name", keyWord))
                        .withPageable(pageable)
                        .withSort(SortBuilders.fieldSort("age").order(SortOrder.DESC))
                        .build();
        runnableList.add(() -> {
            esObject = new AtomicReference(elasticsearchService.selectDocumentByKeyWord(esquery, User.class));
            results.add(esObject);
            countDownLatch.countDown();
        });

        Query mongoquery = new Query(Criteria.where("name").regex(keyWord))
                .with(Sort.by("deleted").ascending())
                .with(Sort.by("created").descending())
                .with(pageable);
        runnableList.add(() -> {
            mongoObject = new AtomicReference(mongoDBService.selectByPage(mongoquery, pageable, User.class));
            results.add(mongoObject);
            countDownLatch.countDown();
        });

        UserFilterBO userFilterBO = new UserFilterBO();
        userFilterBO.setName(keyWord);
        runnableList.add(() -> {
            mysqlObject = new AtomicReference(userService.findAll(userFilterBO));
            results.add(mysqlObject);
            countDownLatch.countDown();
        });

        // 开启线程查询
        countDownLatch = new CountDownLatch(runnableList.size());
        log.info("共启用[{}]个线程", countDownLatch.getCount());
        runnableList.forEach(item -> {
            threadPoolTaskExecutor.submit(item);
        });

        // 等待所有线程执行完毕
        countDownLatch.await(1, TimeUnit.HOURS);

        return CommonResultEntity.success(results);
    }
}
