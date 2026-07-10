package com.hot.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hot.common.entity.BaseEntity;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SysField extends BaseEntity {

    private Integer id;
    private Integer role;
    private String type;
    private String table;
    private String fields;
    private String fieldMsg;

}
