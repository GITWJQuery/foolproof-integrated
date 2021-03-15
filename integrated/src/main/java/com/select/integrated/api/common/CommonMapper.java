package com.select.integrated.api.common;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 共通Mapper
 */
public interface CommonMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
