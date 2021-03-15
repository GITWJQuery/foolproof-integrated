package com.select.integrated.domain.service.impl;

import com.select.integrated.domain.service.ElasticsearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Elasticsearch操作服务实现类
 */
@Service
@Slf4j
public class ElasticsearchServiceImpl implements ElasticsearchService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * 创建索引
     */
    @Override
    public <T> boolean createIndex(Class<T> clazz) {
        System.out.println(clazz.getSimpleName());
        boolean exists = elasticsearchRestTemplate.indexOps(clazz).exists();
        if (exists) {
            log.info("ElasticsearchServiceImpl：当前索引[{}]已存在", clazz.getCanonicalName());
            return false;
        }
        // 创建索引
        elasticsearchRestTemplate.indexOps(clazz).create();
        // 创建映射
        Document mappings = elasticsearchRestTemplate.indexOps(clazz).createMapping();
        elasticsearchRestTemplate.indexOps(clazz).putMapping(mappings);
        log.info("ElasticsearchServiceImpl：当前索引[{}]创建成功", clazz.getCanonicalName());
        return true;
    }

    /**
     * 删除索引
     */
    @Override
    public <T> boolean deleteIndex(Class<T> clazz) {
        boolean result = elasticsearchRestTemplate.indexOps(clazz).delete();
        log.info("ElasticsearchServiceImpl：当前索引[{}]已删除", clazz.getCanonicalName());
        return result;
    }

    /**
     * 保存或更新文档
     */
    @Override
    public <T1, T2> boolean saveOrUpdateDocument(T1 entity, Class<T2> clazz) {
        if (entity == null) {
            return false;
        }
        T2 esModel = this.getInstance(clazz);
        if (esModel == null) {
            return false;
        }
        this.copyProperties(entity, esModel);
        T2 result = elasticsearchRestTemplate.save(esModel);
        return result != null;
    }

    /**
     * 根据ID查询文档
     */
    @Override
    public <T> Page<T> selectDocumentByKeyWord(NativeSearchQuery query, Class<T> clazz) {
        SearchHits<T> searchHits = elasticsearchRestTemplate.search(query, clazz);
        List<T> results = new ArrayList<>();
        for (SearchHit<T> searchHit : searchHits) {
            T content = searchHit.getContent();
            results.add(content);
        }
        Page<T> userPage = new PageImpl<>(results, query.getPageable(), searchHits.getTotalHits());
        return userPage;
    }

    /**
     * 删除文档
     */
    @Override
    public <T> boolean deleteDocument(String docId, Class<T> clazz) {
        String result = elasticsearchRestTemplate.delete(docId, clazz);
        return result != null;
    }

    /***
     * 创建指定类型的实例。
     */
    private <T> T getInstance(Class<T> clazz) {
        T object = null;
        try {
            object = clazz.newInstance();
        } catch (InstantiationException e) {
            log.error("创建[" + clazz.getName() + "]实例化对象时异常：" + e.getLocalizedMessage());
        } catch (IllegalAccessException e) {
            log.error("创建[" + clazz.getName() + "]实例化对象时异常：" + e.getLocalizedMessage());
        }
        return object;
    }

    /***
     * BeanCopy 属性赋值。
     */
    private <T1, T2> void copyProperties(T1 icsEntity, T2 esModel) {
        BeanUtils.copyProperties(icsEntity, esModel);
    }
}
