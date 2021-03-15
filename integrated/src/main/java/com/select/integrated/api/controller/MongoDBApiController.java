package com.select.integrated.api.controller;

import com.select.integrated.api.common.CommonResultEntity;
import com.select.integrated.domain.pojo.User;
import com.select.integrated.domain.service.MongoDBService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

/**
 * 用户相关控制器
 */
@Api(value = "用户相关接口(MongoDB)", tags = "用户相关接口(MongoDB)")
@RestController
@RequestMapping("/api/v1/user/mongodb")
public class MongoDBApiController {

    @Autowired
    MongoDBService mongoDBService;

    @ApiOperation(value = "根据用户ID查询用户信息", notes = "根据用户ID查询用户信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CommonResultEntity findById(@PathVariable Long id) {
        User user = mongoDBService.selectById(id, User.class);
        return CommonResultEntity.success(user);
    }

    @ApiOperation(value = "根据用户ID删除用户", notes = "根据用户ID删除用户")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public CommonResultEntity deleteById(@PathVariable Long id) {
        mongoDBService.delete(id, User.class);
        return CommonResultEntity.success(null);
    }

    @ApiOperation(value = "根据关键词查询用户信息", notes = "根据关键词查询用户信息")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public CommonResultEntity findByKeyWord(@RequestParam String keyWord, Pageable pageable) {
        Query query = new Query(Criteria.where("name").regex(keyWord))
                .with(Sort.by("deleted").ascending())
                .with(Sort.by("created").descending())
                .with(pageable);
        Page<User> pageUsers = mongoDBService.selectByPage(query, pageable, User.class);
        return CommonResultEntity.success(pageUsers);
    }
}
