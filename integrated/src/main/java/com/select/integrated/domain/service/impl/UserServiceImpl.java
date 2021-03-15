package com.select.integrated.domain.service.impl;

import com.select.integrated.api.bo.UserFilterBO;
import com.select.integrated.domain.mapper.UserMapper;
import com.select.integrated.domain.pojo.User;
import com.select.integrated.domain.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * 用户服务实现类
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 创建用户
     */
    @Override
    public void create(User user) throws Exception {
        Date date = new Date();
        user.setCreated(date);
        user.setUpdated(date);
        user.setDeleted(false);
        this.userMapper.insert(user);
    }

    /**
     * 根据ID查询用户
     */
    @Override
    public User findById(Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    /**
     * 根据ID修改用户
     */
    @Override
    public void update(User dbUser, User user) throws Exception {
        BeanUtils.copyProperties(user, dbUser, "id");
        userMapper.updateByPrimaryKey(dbUser);
    }

    /**
     * 根据ID删除用户
     */
    @Override
    public void deleteById(Long userId) {
        userMapper.deleteByPrimaryKey(userId);
    }

    /**
     * 查询用户列表
     */
    @Override
    public List<User> findAll(UserFilterBO userFilterBO) {
        Example userExample = new Example(User.class);
        userExample.createCriteria().andEqualTo("name", userFilterBO.getName());
        return userMapper.selectByExample(userExample);
    }
}
