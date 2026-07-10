package com.hot.modules.hot.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hot.common.entity.BaseEntity;
import com.hot.common.excel.ExcelField;
import lombok.Data;

/**
 * 红人信息表
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class HotBasic extends BaseEntity {

    private Integer id;
    private Integer hotId;
    @ExcelField(title = "渠道", align = 2, sort = 10, dictType = "hot_channel", dropList = "hot_channel")
    private String channel;     // 渠道
    @ExcelField(title = "红人ID", align = 2, sort = 20)
    private String hotsId;      // 红人id
    @ExcelField(title = "红人昵称", align = 2, sort = 30)
    private String nickname;    // 昵称
    @ExcelField(title = "国家", align = 2, sort = 40, dictType = "a_country", dropList = "a_country")
    private String country;     // 国家
    private String countrys;     // 国家
    @ExcelField(title = "联系方式", align = 2, sort = 50)
    private String contact;     // 联系方式
    @ExcelField(title = "红人量级", align = 2, sort = 60, dictType = "b_follower", dropList = "b_follower")
    private String hotLevel;    // 红人量级
    private String hotType;     // 红人类型
    @ExcelField(title = "红人类型", align = 2, sort = 70, dropList = "hot_type")
    private String hotTypes;     // 红人类型
    @ExcelField(title = "粉丝量", align = 2, sort = 80)
    private Long follower;      // 粉丝量
    @ExcelField(title = "聚星平台链接", align = 2, sort = 90)
    private String juxingUrl;   // 聚星平台链接
    @ExcelField(title = "URL链接", align = 2, sort = 100)
    private String urlLink;     // URL链接
    @ExcelField(title = "平均播放量", align = 2, sort = 110)
    private Long avgPlayCount;  // 平均播放量
    @ExcelField(title = "平均互动率", align = 2, sort = 120)
    private String avgIactRate; // 平均互动率
    @ExcelField(title = "最近10个长视频平均观看量", align = 2, sort = 130)
    private Long longAvgViews;  // 最近10个长视频平均观看量
    @ExcelField(title = "最近10个长视频平均互动率", align = 2, sort = 140)
    private String longIactRate;// 最近10个长视频平均互动率
    @ExcelField(title = "最近10个短视频平均观看量", align = 2, sort = 150)
    private Long shortAvgViews; // 最近10个短视频平均观看量
    @ExcelField(title = "最近10个短视频平均互动率", align = 2, sort = 160)
    private String shortIactRate;
    private String scoreLevel;  // 评分等级
    private String isMatch;
    private String isMatchs;
    private Integer isHotp;

    private Integer[] hotTypeArr;

}
