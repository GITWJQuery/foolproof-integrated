package com.select.integrated.api.common;

/**
 * 常用API操作码
 */
public enum CommonResultEnum implements CommonResultInterface {
    /**
     * API响应码
     */
    SUCCESS("200", "请求成功!"),
    BAD_REQUEST("400", "因为语法错误，服务器未能理解请求!"),
    UNAUTHORIZED("401", "身份验证失败!"),
    FORBIDDEN("403", "被请求页面的访问被禁止!"),
    NOT_FOUND("404", "服务器无法找到被请求的页面!"),

    INTERNAL_SERVER_ERROR("500", "服务器内部错误!");


    private String code;
    private String message;

    CommonResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 错误码
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * 错误描述
     */
    @Override
    public String getMessage() {
        return message;
    }
}
