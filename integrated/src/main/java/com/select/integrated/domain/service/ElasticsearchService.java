package com.select.integrated.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;

/**
 * Elasticsearch操作服务接口。
 */
public interface ElasticsearchService {
    /**
     * 创建索引
     */
    <T> boolean createIndex(Class<T> clazz);

    /**
     * 删除索引
     */
    <T> boolean deleteIndex(Class<T> clazz);

    /**
     * 保存或更新文档
     */
    <T1, T2> boolean saveOrUpdateDocument(T1 entity, Class<T2> clazz);

    /**
     * 根据关键词查询文档
     */
    <T> Page<T> selectDocumentByKeyWord(NativeSearchQuery query, Class<T> clazz);

    /**
     * 删除文档
     */
    <T> boolean deleteDocument(String docId, Class<T> clazz);
}
