package com.hot.modules.hot.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hot.common.entity.BaseEntity;
import lombok.Data;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Country extends BaseEntity {

    private Integer id;
    private String name;

}
