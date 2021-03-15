package com.select.integrated.domain.service.impl;

import com.select.integrated.domain.service.MongoDBService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MongoDB操作服务接口实现类
 */
@Service
@Slf4j
public class MongoDBServiceImpl implements MongoDBService {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 保存或更新文档
     */
    @Override
    public <T> T saveOrUpdate(T entity) {
        return mongoTemplate.save(entity);
    }

    /**
     * 根据ID查询文档
     */
    @Override
    public <T> T selectById(Long docId, Class<T> clazz) {
        return mongoTemplate.findById(docId, clazz);
    }

    /**
     * 根据关键词查询文档
     */
    @Override
    public <T> Page<T> selectByPage(Query query, Pageable pageable, Class<T> clazz) {
        long count = mongoTemplate.count(query, clazz);
        List<T> results = mongoTemplate.find(query, clazz);
        return new PageImpl<>(results, pageable, count);
    }

    /**
     * 删除文档
     */
    @Override
    public <T> void delete(Long docId, Class<T> clazz) {
        mongoTemplate.remove(new Query(new Criteria().and("_id").is(docId)), clazz);
    }
}
