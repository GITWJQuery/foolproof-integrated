package com.select.integrated.api.controller;

import com.select.integrated.api.common.CommonResultEntity;
import com.select.integrated.domain.service.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Redis相关接口
 */
@Api(value = "Redis相关接口", tags = "Redis相关接口")
@RestController
@RequestMapping("/api/v1/redis")
public class RedisApiController {

    @Autowired
    private RedisService redisService;

    @ApiOperation(value = "添加键值对", notes = "添加键值对")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public CommonResultEntity addKeyValue(@RequestParam String key, @RequestParam String value) {
        redisService.set(key, value);
        return CommonResultEntity.success(null);
    }
}
