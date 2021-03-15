package com.select.integrated.api.controller;

import com.select.integrated.api.bo.UserFilterBO;
import com.select.integrated.api.common.CommonResultEntity;
import com.select.integrated.api.common.CommonResultEnum;
import com.select.integrated.domain.pojo.User;
import com.select.integrated.domain.service.ElasticsearchService;
import com.select.integrated.domain.service.MongoDBService;
import com.select.integrated.domain.service.UserService;
import com.select.integrated.exception.BizException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户相关控制器
 */
@Api(value = "用户相关接口(MySQL)", tags = "用户相关接口(MySQL)")
@RestController
@RequestMapping("/api/v1/user/mysql")
public class MySQLApiController {

    @Autowired
    UserService userService;

    @Autowired
    private ElasticsearchService elasticsearchService;

    @Autowired
    MongoDBService mongoDBService;

    @ApiOperation(value = "创建用户", notes = "创建用户")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public CommonResultEntity create(@RequestBody User user) throws Exception {
        userService.create(user);
        elasticsearchService.saveOrUpdateDocument(user, User.class);
        mongoDBService.saveOrUpdate(user);
        return CommonResultEntity.success(null);
    }

    @ApiOperation(value = "根据用户ID查询用户信息", notes = "根据用户ID查询用户信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CommonResultEntity findById(@PathVariable Long id) {
        User user = userService.findById(id);
        return CommonResultEntity.success(user);
    }

    @ApiOperation(value = "根据用户ID更新用户信息", notes = "根据用户ID更新用户信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public CommonResultEntity update(@PathVariable Long id, @RequestBody User user) throws Exception {
        User dbUser = userService.findById(id);
        if (dbUser == null) {
            return CommonResultEntity.failed("该用户不存在");
        }
        userService.update(dbUser, user);
        return CommonResultEntity.success(null);
    }

    @ApiOperation(value = "根据用户ID删除用户", notes = "根据用户ID删除用户")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public CommonResultEntity deleteById( @PathVariable Long id) {
        userService.deleteById(id);
        return CommonResultEntity.success(null);
    }

    @ApiOperation(value = "获取用户列表（不分页）", notes = "获取系统中全部有效的用户列表")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public CommonResultEntity findAll(UserFilterBO userFilterBO) {
        List<User> users = userService.findAll(userFilterBO);
        return CommonResultEntity.success(users);
    }
}
