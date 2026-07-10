package com.hot.modules.hot.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hot.common.entity.BaseEntity;
import lombok.Data;

/**
 * 项目
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Product extends BaseEntity {

    private String id;
    private String erpCode;
    private String name;
    private String model;
    private String color;

}
