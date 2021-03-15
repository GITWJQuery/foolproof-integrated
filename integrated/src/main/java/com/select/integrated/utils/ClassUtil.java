package com.select.integrated.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.*;

/**
 * 类相关工具类
 */
@Slf4j
public class ClassUtil {
    /**
     * 根据String类型的类名生成类
     */
    public static Class getclass(String className) {
        Class clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            log.error("ClassUtil:根据类名获取class[{}]时异常, 异常信息：[{}]", className, e.getLocalizedMessage());
        }
        return clazz;
    }

    /**
     * 获取所有实体类名
     */
    public static Set<String> getPojoNames() {
        // 基础包路径
        String basePackage = "com.select.integrated.domain.pojo";
        String resourcePattern = "/**/*.class";

        // 返回值
        Set<String> results = new HashSet<>();

        // spring工具类，可以获取指定路径下的全部类
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        try {
            String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                    ClassUtils.convertClassNameToResourcePath(basePackage) + resourcePattern;
            Resource[] resources = resourcePatternResolver.getResources(pattern);
            // MetadataReader 的工厂类
            MetadataReaderFactory readerfactory = new CachingMetadataReaderFactory(resourcePatternResolver);
            for (Resource resource : resources) {
                // 用于读取类信息
                MetadataReader reader = readerfactory.getMetadataReader(resource);
                // 扫描到的class
                String classname = reader.getClassMetadata().getClassName();
                // 移除Builder类
                if (classname.indexOf("$") == -1) {
                    results.add(classname);
                }
            }
        } catch (IOException e) {
            log.info("获取所有实体类名异常");
        }

        return results;
    }
}
