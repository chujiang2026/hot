package com.hot.modules.erp.entity;

import lombok.Data;

import java.util.Date;

/**
 * ERP物料实体类 （数据库）
 */
@Data
public class ErpMaterial {
    private String materialId;      // 物料编码（主键）
    private String modelNumber;     // 产品型号
    private String color;           // 颜色
    private Date createTime;        // 数据拉取时间
    private Date updateTime;        // 数据更新时间
    private String delFlag;         // 删除标记：0-未删除，1-已删除
}
