package com.select.integrated.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;

/**
 * MongoDB操作服务接口。
 */
public interface MongoDBService {

    /**
     * 保存或更新文档
     */
    <T> T saveOrUpdate(T entity);

    /**
     * 根据ID查询文档
     */
    <T> T selectById(Long docId, Class<T> clazz);

    /**
     * 根据关键词查询文档
     */
    <T> Page<T> selectByPage(Query query, Pageable pageable, Class<T> clazz);

    /**
     * 删除文档
     */
    <T> void delete(Long docId, Class<T> clazz);
}
