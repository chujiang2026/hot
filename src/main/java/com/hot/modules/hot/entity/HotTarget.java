package com.hot.modules.hot.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hot.common.entity.BaseEntity;
import com.hot.common.excel.ExcelField;
import lombok.Data;

import java.math.BigInteger;

/**
 * 红人合作目标实体类
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class HotTarget extends BaseEntity {

    private Integer id;            // 内容目标配置ID
    @ExcelField(title = "国家", align = 2, sort = 10, dictType = "a_country", dropList = "a_country")
    private String country;        // 国家
    private String countrys;        // 国家
    @ExcelField(title = "渠道", align = 2, sort = 20, dictType = "hot_channel", dropList = "hot_channel")
    private String channel;        // 渠道(YT/IG/TK等)
    @ExcelField(title = "红人类型", align = 2, sort = 30, dictType = "hot_types", dropList = "hot_types")
    private Integer hotType;        // 红人类型
    private String hotTypes;
    @ExcelField(title = "红人量级", align = 2, sort = 40, dictType = "b_target_level", dropList = "b_target_level")
    private String hotLevel;       // 红人量级
    private String hotLevels;
    @ExcelField(title = "视频类型", align = 2, sort = 50, dictType = "vid_type", dropList = "vid_type")
    private Integer videoType;     // 视频类型
    private String videoTypes;     // 视频类型
    @ExcelField(title = "观看量目标", align = 2, sort = 60)
    private String viewTarget;     // 观看量目标
    @ExcelField(title = "互动率目标", align = 2, sort = 70)
    private String iactrTarget;    // 互动率目标
    @ExcelField(title = "点击率目标", align = 2, sort = 80)
    private String clickrTarget;   // 点击率目标
    @ExcelField(title = "转化率目标", align = 2, sort = 90)
    private String converTarget;   // 转化率目标
    @ExcelField(title = "CPM目标", align = 2, sort = 100)
    private String cpmTarget;      // CPM目标
    @ExcelField(title = "ROAS目标", align = 2, sort = 110)
    private String roasTarget;     // ROAS目标

}
