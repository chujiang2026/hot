package com.hot.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hot.common.entity.BaseEntity;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class BasicData extends BaseEntity {

    private String userid;
    private String id;
    private String name;
    private String param;

}
