package com.hot.modules.hot.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hot.common.entity.BaseEntity;
import com.hot.common.excel.ExcelField;
import lombok.Data;

import java.util.List;

/**
 * 合作邀约单
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class HotInvitReport extends BaseEntity {

    @ExcelField(title = "邀约单号", align = 2, sort = 10)
    private String docNum;      // 单据号
    @ExcelField(title = "建联时间", align = 2, sort = 20)
    private String connTime;    // 建联时间
    @ExcelField(title = "同意沟通合作时间", align = 2, sort = 25)
    private String agccTime;
    @ExcelField(title = "BD名称", align = 2, sort = 30)
    private String bdName;      // BD名称
    @ExcelField(title = "红人id", align = 2, sort = 40)
    private String hotsId;      // 红人id
    @ExcelField(title = "渠道", align = 2, sort = 50)
    private String channel;     // 渠道
    private String country;     // 国家
    @ExcelField(title = "国家", align = 2, sort = 55)
    private String countrys;     // 国家
    private Integer state;      // 状态
    @ExcelField(title = "状态", align = 2, sort = 60)
    private String states;      // 状态
    @ExcelField(title = "产品型号", align = 2, sort = 70)
    private String productModel;    // 产品型号
    @ExcelField(title = "产品颜色", align = 2, sort = 80)
    private String productColor;    // 产品颜色
    @ExcelField(title = "合作渠道", align = 2, sort = 85)
    private String coopChannel;
    @ExcelField(title = "发布账号", align = 2, sort = 90)
    private String releAcc;         // 发布账号
    @ExcelField(title = "发布数量", align = 2, sort = 100)
    private Integer releNum;        // 发布数量
    @ExcelField(title = "视频类型", align = 2, sort = 110)
    private String videoType;       // 视频类型
    @ExcelField(title = "视频授权", align = 2, sort = 120)
    private String videoAuth;       // 视频授权
    @ExcelField(title = "视频授权用途", align = 2, sort = 130)
    private String authPupo;        // 视频授权用途
    @ExcelField(title = "广告授权", align = 2, sort = 140)
    private String adAuth;          // 广告授权
    @ExcelField(title = "合作形式", align = 2, sort = 150)
    private String coopForm;        // 合作形式
    @ExcelField(title = "佣金比例", align = 2, sort = 160)
    private String commRate;        // 佣金比例
    @ExcelField(title = "合作费用", align = 2, sort = 170)
    private String coopFee;         // 合作的费用
    @ExcelField(title = "产品销售平台", align = 2, sort = 180)
    private String platForm;        // 产品销售平台


}
