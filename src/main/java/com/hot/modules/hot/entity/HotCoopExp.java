package com.hot.modules.hot.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hot.common.excel.ExcelField;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class HotCoopExp {

    @ExcelField(title = "合作单号", align = 2, sort = 10)
    private String docNum;      // 单据号
    @ExcelField(title = "BD名称", align = 2, sort = 20)
    private String bdName;      // BD名称
    @ExcelField(title = "红人id", align = 2, sort = 30)
    private String hotsId;      // 红人id
    @ExcelField(title = "渠道", align = 2, sort = 40)
    private String channel;     // 渠道
    @ExcelField(title = "国家", align = 2, sort = 50)
    private String country;     // 国家
    @ExcelField(title = "合作邀约单号", align = 2, sort = 60)
    private String invitDocNum; // 合作邀约单号
    @ExcelField(title = "状态", align = 2, sort = 70)
    private String state;      // 状态
    @ExcelField(title = "产品型号", align = 2, sort = 90)
    private String productModel;
    @ExcelField(title = "产品颜色", align = 2, sort = 100)
    private String productColor;
    @ExcelField(title = "视频授权", align = 2, sort = 110)
    private String videoAuth;
    @ExcelField(title = "视频授权用途", align = 2, sort = 120)
    private String videoAuthPur;
    @ExcelField(title = "广告授权", align = 2, sort = 130)
    private String adAuth;
    @ExcelField(title = "合作的形式", align = 2, sort = 140)
    private String coopForm;
    @ExcelField(title = "佣金比例", align = 2, sort = 150)
    private String commRate;
    @ExcelField(title = "付款方式", align = 2, sort = 160)
    private String payMethod;
    @ExcelField(title = "产品销售平台", align = 2, sort = 170)
    private String platForm;
    @ExcelField(title = "收款信息", align = 2, sort = 180)
    private String payInfo;
    @ExcelField(title = "收款地址", align = 2, sort = 190)
    private String payAddres;
    @ExcelField(title = "合作费用", align = 2, sort = 200)
    private String coopFee;
    @ExcelField(title = "寄样单号", align = 2, sort = 210)
    private String sashNo;
    @ExcelField(title = "运输商", align = 2, sort = 220)
    private String carrier;
    @ExcelField(title = "寄样时间", align = 2, sort = 230)
    private String sashTime;
    @ExcelField(title = "物流状态", align = 2, sort = 240)
    private String wlStatus;
    @ExcelField(title = "到货日期", align = 2, sort = 250)
    private String dhDate;
    @ExcelField(title = "UTM追踪链接", align = 2, sort = 260)
    private String utmUrl;
    @ExcelField(title = "销售期", align = 2, sort = 270)
    private String salesPeriod;
    @ExcelField(title = "访问量/点击量", align = 2, sort = 280)
    private String clicks;
    @ExcelField(title = "销售数量", align = 2, sort = 290)
    private String sales;
    @ExcelField(title = "折扣代码", align = 2, sort = 300)
    private String disCode;
    @ExcelField(title = "GMV", align = 2, sort = 310)
    private String gmv;
    @ExcelField(title = "ROAS", align = 2, sort = 320)
    private String roas;
    @ExcelField(title = "结算-申请日期", align = 2, sort = 330)
    private String settApplyTime;
    @ExcelField(title = "结算-完成付款时间", align = 2, sort = 340)
    private String settPayTime;
    @ExcelField(title = "结算-付款金额", align = 2, sort = 350)
    private String settPayAmount;
    @ExcelField(title = "内容-发布渠道", align = 2, sort = 360)
    private String dataReleChan;         // 发布渠道
    @ExcelField(title = "内容-发布账号", align = 2, sort = 370)
    private String dataReleAcct;         // 发布账号
    @ExcelField(title = "内容-付款金额", align = 2, sort = 380)
    private String dataAmount;       // 付款金额
    @ExcelField(title = "内容-请款时间", align = 2, sort = 390)
    private String dataApplyTime;        // 请款时间
    @ExcelField(title = "内容-完成付款时间", align = 2, sort = 400)
    private String dataPayTime;          // 完成付款时间
    @ExcelField(title = "内容-需求发布时间", align = 2, sort = 410)
    private String dataRelesDate;        // 需求的发布排期
    @ExcelField(title = "内容-视频类型", align = 2, sort = 420)
    private String dataVidType;          // 视频类型（长/短）
    @ExcelField(title = "内容-视频草稿链接", align = 2, sort = 430)
    private String dataVidDraftUrl;      // 视频草稿链接
    @ExcelField(title = "内容-素材名称", align = 2, sort = 440)
    private String dataMateName;         // 广告素材名称
    @ExcelField(title = "内容-提供草稿时间", align = 2, sort = 450)
    private String dataProDraftTime;     // 提供草稿时间
    @ExcelField(title = "内容-发布时间", align = 2, sort = 460)
    private String dataReleTime;         // 发布时间
    @ExcelField(title = "内容-上线链接", align = 2, sort = 470)
    private String dataOnlineUrl;        // 上线链接
    @ExcelField(title = "内容-广告授权码", align = 2, sort = 480)
    private String dataAdCode;           // 广告授权码
    @ExcelField(title = "内容-广告授权码有效期", align = 2, sort = 490)
    private String dataAdCodeExpiry;     // 广告授权码有效期
    @ExcelField(title = "内容-监控时长", align = 2, sort = 500)
    private String dataDuration;         // 监控时长
    @ExcelField(title = "内容-创意度", align = 2, sort = 510)
    private String dataCreativ;          // 内容创意度
    @ExcelField(title = "内容-评分", align = 2, sort = 520)
    private String dataScore;            // 评分
    @ExcelField(title = "内容-评分等级", align = 2, sort = 530)
    private String dataScoreLevel;       // 评分等级
    @ExcelField(title = "内容-观看量", align = 2, sort = 540)
    private String dataViews;              // 观看量
    @ExcelField(title = "内容-点赞数", align = 2, sort = 550)
    private String dataLikes;              // 点赞数
    @ExcelField(title = "内容-评论数", align = 2, sort = 560)
    private String dataComments;           // 评论数
    @ExcelField(title = "内容-分享数", align = 2, sort = 570)
    private String dataShares;             // 分享数
    @ExcelField(title = "内容-收藏数", align = 2, sort = 580)
    private String dataFavors;             // 收藏数
    @ExcelField(title = "内容-互动率", align = 2, sort = 590)
    private String dataRateNox;          // 互动率 Nox
    @ExcelField(title = "内容-短链点击数", align = 2, sort = 600)
    private String dataDljClicks;          // 短链点击数
    @ExcelField(title = "内容-CPM千次展示成本", align = 2, sort = 610)
    private String dataCpm;              // CPM 千次展示成本
    @ExcelField(title = "内容-CPC单次点击成本", align = 2, sort = 620)
    private String dataCpc;              // CPC 单次点击成本
    @ExcelField(title = "内容-投放期", align = 2, sort = 630)
    private String dataPeriod;           // 投放期
    @ExcelField(title = "内容-消耗金额", align = 2, sort = 640)
    private String dataSpend;            // 消耗金额
    @ExcelField(title = "内容-播放量", align = 2, sort = 650)
    private String dataPlays;              // 播放量
    @ExcelField(title = "内容-点击量", align = 2, sort = 660)
    private String dataClicks;             // 点击量
    @ExcelField(title = "内容-转化量", align = 2, sort = 670)
    private String dataConve;              // 转化量
    @ExcelField(title = "内容-广告ROAS", align = 2, sort = 680)
    private String dataRoas;             // 广告 ROAS

}
