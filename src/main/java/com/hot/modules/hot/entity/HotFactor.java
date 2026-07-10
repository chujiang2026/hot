package com.hot.modules.hot.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hot.common.entity.BaseEntity;
import lombok.Data;

/**
 * 渠道分摊系数实体类
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class HotFactor extends BaseEntity {

    private Integer id;           // 主键ID
    private String channel;       // 渠道(YT/IG/TK等)
    private Integer videoType;     // 视频类型
    private String videoTypes;     // 视频类型
    private Integer salesK1;       // 销售数据分摊系数K1
    private Integer coopK2;        // 合作费用分摊系数K2

}
