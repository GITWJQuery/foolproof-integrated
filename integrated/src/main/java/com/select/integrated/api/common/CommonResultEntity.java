package com.select.integrated.api.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 通用返回对象
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommonResultEntity<T> {

    /**
     * 状态码
     */
    private String code;

    /**
     * 消息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 请求成功，返回数据
     *
     * @param data 获取的数据
     */
    public static <T> CommonResultEntity<T> success(T data) {
        return new CommonResultEntity(CommonResultEnum.SUCCESS.getCode(), CommonResultEnum.SUCCESS.getMessage(), data);
    }

    /**
     * 请求成功，不返回数据
     */
    public static <T> CommonResultEntity<T> success() {
        return new CommonResultEntity(CommonResultEnum.SUCCESS.getCode(), CommonResultEnum.SUCCESS.getMessage(), null);
    }

    /**
     * 请求失败
     *
     * @param errorCode 错误码
     */
    public static <T> CommonResultEntity<T> failed(CommonResultInterface errorCode) {
        return new CommonResultEntity(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 请求失败
     *
     * @param message 异常信息
     */
    public static <T> CommonResultEntity<T> failed(String message) {
        return new CommonResultEntity(CommonResultEnum.BAD_REQUEST.getCode(), message, null);
    }
}
