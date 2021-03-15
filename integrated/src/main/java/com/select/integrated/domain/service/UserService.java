package com.select.integrated.domain.service;

import com.select.integrated.api.bo.UserFilterBO;
import com.select.integrated.domain.pojo.User;

import java.util.List;

/**
 * 用户服务类接口
 */
public interface UserService {
    /**
     * 创建用户
     */
    void create(User user) throws Exception;

    /**
     * 根据ID查询用户
     */
    User findById(Long userId);

    /**
     * 根据ID修改用户
     */
    void update(User dbUser, User user) throws Exception;

    /**
     * 根据ID删除用户
     */
    void deleteById(Long userId);

    /**
     * 查询用户列表
     */
    List<User> findAll(UserFilterBO userFilterBO);
}
