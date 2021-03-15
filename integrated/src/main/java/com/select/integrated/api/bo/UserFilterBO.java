package com.select.integrated.api.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户查询参数实体
 */
@Data
public class UserFilterBO {
    @ApiModelProperty(value = "姓名")
    private String name;
}
