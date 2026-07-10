package com.hot.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hot.common.entity.BaseEntity;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SysData extends BaseEntity {

    private Integer id;
    private String name;
    private String type;
    private String types;
    private Object field;
    private List<SysField> fields;

}
