package com.select.integrated.domain.pojo;

import com.select.integrated.consts.IntegratedConsts;
import com.select.integrated.domain.es.EsCommonModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = IntegratedConsts.ELASTICSEARCH_INDEX_NAME_USER, shards = 5)
@org.springframework.data.mongodb.core.mapping.Document(collection = IntegratedConsts.ELASTICSEARCH_INDEX_NAME_USER)
public class User extends EsCommonModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Field(type = FieldType.Long)
    @ApiModelProperty(value = "主键ID", hidden = true)
    private Long id;

    @Field(type = FieldType.Keyword)
    @ApiModelProperty(value = "姓名")
    private String name;

    @Field(type = FieldType.Keyword)
    @ApiModelProperty(value = "年龄")
    private Integer age;

    @Field(type = FieldType.Keyword)
    @ApiModelProperty(value = "邮箱")
    private String email;
}