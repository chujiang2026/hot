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
public class HotInvit extends BaseEntity {

    private Integer id;
    @ExcelField(title = "邀约单号", align = 2, sort = 10)
    private String docNum;      // 单据号
    @ExcelField(title = "BD名称", align = 2, sort = 20)
    private String bdName;      // BD名称
    @ExcelField(title = "建联时间", align = 2, sort = 30)
    private String connTime;    // 建联时间
    private String agccTime;    // 同意沟通合作时间
    private Integer hotbId;     // 红人详情id
    @ExcelField(title = "红人id", align = 2, sort = 40)
    private String hotsId;      // 红人id
    @ExcelField(title = "渠道", align = 2, sort = 50)
    private String channel;     // 渠道
    private String country;     // 国家
    private String countrys;     // 国家
    private Integer state;      // 状态码
    @ExcelField(title = "状态", align = 2, sort = 60)
    private String states;      // 状态
    private String[] ids;

    private String sdate;
    private String edate;

    private List<HotInvit> hotsList;
    private List<HotInvitDema> demaList;


}
