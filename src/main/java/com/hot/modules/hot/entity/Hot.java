package com.hot.modules.hot.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hot.common.entity.BaseEntity;
import com.hot.common.excel.ExcelField;
import lombok.Data;

import java.util.List;

/**
 * 红人
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Hot extends BaseEntity {

    @ExcelField(title = "红人昵称", align = 2, sort = 10)
    private String nickname;    // 昵称
    @ExcelField(title = "状态", align = 2, sort = 20)
    private String states;      // 状态
    @ExcelField(title = "建档时间", align = 2, sort = 30)
    private String createTime;  // 建档时间
    @ExcelField(title = "渠道", align = 2, sort = 40)
    private String channel;     // 渠道
    private String country;     // 国家
    @ExcelField(title = "国家", align = 2, sort = 50)
    private String countrys;     // 国家
    @ExcelField(title = "平均播放数", align = 2, sort = 60)
    private Long avgPlayCount;  // 平均播放数
    @ExcelField(title = "评分等级", align = 2, sort = 70)
    private String scoreLevel;  // 评分等级
    private Integer id;
    private Integer hotId;
    private String name;        // 姓名
    private String archName;    // 档案名
    private String state;      // 状态码
    private String hotLevel;    // 红人量级
    private String hotType;     // 红人类型
    private String hotsId;      // 红人ID
    private String productName; // 合作产品
    private String coopFee;     // 合作费用
    private Integer isMatch;    // 是否匹配
    private String isMatchs;    // 是否匹配
    private String deadDate;    // 截止时间
    private String reason;      // 变更状态原因
    private String follower;
    private Integer[] ids;

    private HotBasic basicyt;
    private HotBasic basicig;
    private HotBasic basictk;

}
