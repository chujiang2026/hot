package com.hot.modules.hot.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hot.common.entity.BaseEntity;
import com.hot.common.excel.ExcelField;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 合作单
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class HotCoopReport extends BaseEntity {

    private Integer id;
    @ExcelField(title = "合作单号", align = 2, sort = 10)
    private String docNum;      // 单据号
    @ExcelField(title = "BD名称", align = 2, sort = 20)
    private String bdName;      // BD名称
    @ExcelField(title = "红人id", align = 2, sort = 30)
    private String hotsId;      // 红人id
    @ExcelField(title = "渠道", align = 2, sort = 40)
    private String channel;     // 渠道
    private String country;     // 国家
    private String countrys;     // 国家
    private Integer state;      // 状态码
    @ExcelField(title = "状态", align = 2, sort = 50)
    private String states;      // 状态
    @ExcelField(title = "产品型号", align = 2, sort = 60)
    private String productModel;     // 产品型号
    @ExcelField(title = "产品颜色", align = 2, sort = 70)
    private String productColor;     // 产品颜色
    @ExcelField(title = "发布渠道", align = 2, sort = 80)
    private String releChan;         // 发布渠道
    @ExcelField(title = "发布账号", align = 2, sort = 90)
    private String releAcct;         // 发布账号
    @ExcelField(title = "视频类型", align = 2, sort = 100)
    private String vidType;          // 视频类型（长/短）
    @ExcelField(title = "视频授权用途", align = 2, sort = 110)
    private String videoAuthPur;     // 视频授权用途
    @ExcelField(title = "合作费用", align = 2, sort = 120)
    private String coopFee;          // 合作费用
    @ExcelField(title = "产品销售平台", align = 2, sort = 130)
    private String platForm;         // 产品销售平台
    @ExcelField(title = "寄样单号", align = 2, sort = 140)
    private String sashNo;           // 寄样单号
    @ExcelField(title = "物流商", align = 2, sort = 150)
    private String carrier;          // 运输商
    @ExcelField(title = "物流状态", align = 2, sort = 160)
    private String wlStatus;         // 物流状态
    @ExcelField(title = "UTM追踪链接", align = 2, sort = 170)
    private String utmUrl;           // UTM追踪链接
    @ExcelField(title = "视频草稿链接", align = 2, sort = 180)
    private String vidDraftUrl;      // 视频草稿链接
    @ExcelField(title = "上线链接", align = 2, sort = 190)
    private String onlineUrl;        // 上线链接
    @ExcelField(title = "观看量", align = 2, sort = 200)
    private Long views;              // 观看量
    @ExcelField(title = "点赞数", align = 2, sort = 210)
    private Long likes;              // 点赞数
    @ExcelField(title = "评论数", align = 2, sort = 220)
    private Long comments;           // 评论数
    @ExcelField(title = "分享数", align = 2, sort = 230)
    private Long shares;             // 分享数
    @ExcelField(title = "销售期", align = 2, sort = 240)
    private String salesPeriod;      // 销售期
    @ExcelField(title = "访问量", align = 2, sort = 250)
    private Long clicks;             // 访问量
    @ExcelField(title = "销售数量", align = 2, sort = 260)
    private Long sales;              // 销售数量
    @ExcelField(title = "GMV", align = 2, sort = 270)
    private String gmv;              // GMV
    @ExcelField(title = "ROAS", align = 2, sort = 280)
    private String roas;             // ROAS
    @ExcelField(title = "投放期", align = 2, sort = 290)
    private String period;           // 投放期
    @ExcelField(title = "消耗金额", align = 2, sort = 300)
    private String spend;            // 消耗金额
    @ExcelField(title = "播放量", align = 2, sort = 310)
    private Long plays;              // 播放量
    @ExcelField(title = "点击量", align = 2, sort = 320)
    private Long dclicks;            // 点击量
    @ExcelField(title = "转化量", align = 2, sort = 330)
    private Long conve;              // 转化量
    @ExcelField(title = "广告ROAS", align = 2, sort = 340)
    private String droas;            // 广告 ROAS


}
