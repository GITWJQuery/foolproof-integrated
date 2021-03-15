package com.select.integrated.domain.es;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.Serializable;
import java.util.Date;

/**
 * ES基础实体模型。
 */
@Data
@ApiIgnore()
public abstract class EsCommonModel implements Serializable {

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间", hidden = true)
    private Date created;

    @Field(type = FieldType.Keyword)
    @ApiModelProperty(value = "创建人", hidden = true)
    private String createdBy;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "更新时间", hidden = true)
    private Date updated;

    @Field(type = FieldType.Keyword)
    @ApiModelProperty(value = "更新人", hidden = true)
    private String updatedBy;

    @Field(type = FieldType.Boolean)
    @ApiModelProperty(value = "删除标志", hidden = true)
    private Boolean deleted;
}
