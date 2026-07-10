package com.hot.modules.hot.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hot.common.entity.BaseEntity;
import com.hot.common.excel.ExcelField;
import lombok.Data;

import java.util.List;

/**
 * 合作单
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class HotCoop extends BaseEntity {

    private Integer id;
    @ExcelField(title = "合作单号", align = 2, sort = 10)
    private String docNum;      // 单据号
    @ExcelField(title = "BD名称", align = 2, sort = 20)
    private String bdName;      // BD名称
    private Integer hotbId;     // 红人详情id
    @ExcelField(title = "红人id", align = 2, sort = 30)
    private String hotsId;      // 红人id
    @ExcelField(title = "渠道", align = 2, sort = 40)
    private String channel;     // 渠道
    private String country;     // 国家
    private String countrys;     // 国家
    private Integer invitId;    // 合作邀约单id
    @ExcelField(title = "合作邀约单号", align = 2, sort = 50)
    private String invitDocNum; // 合作邀约单号
    private Integer projectId;  // 项目Id
    @ExcelField(title = "项目名称", align = 2, sort = 60)
    private String projectName; // 项目名称
    private Integer state;      // 状态码
    @ExcelField(title = "状态", align = 2, sort = 70)
    private String states;      // 状态
    private String[] ids;

    private String sdate;
    private String edate;
    private String productModel;     // 产品型号
    private String productColor;     // 产品颜色

    private List<HotCoopProduct> prodList;


}
