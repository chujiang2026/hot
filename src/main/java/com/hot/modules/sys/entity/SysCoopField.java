package com.hot.modules.sys.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SysCoopField {

    private List<SysField> coop;
    private List<SysField> data;
    private List<SysField> product;
    private List<SysField> sett;

}
