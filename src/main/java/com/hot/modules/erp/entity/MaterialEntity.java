package com.hot.modules.erp.entity;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 金蝶物料精简实体类
 */
@Data
@ToString
public class MaterialEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 物料编码
     */
    @SerializedName("FNUMBER")
    private String FNumber;

    /**
     * 物料名称
     */
    @SerializedName("FNAME")
    private String FName;

    /**
     * 规格型号
     */
    @SerializedName("FSPECIFICATION")
    private String FSpecification;

    /**
     * 颜色
     */
    @SerializedName("FCOLOR")
    private String FColor;

    /**
     * 基本单位
     */
    @SerializedName("FBASEUNITID")
    private String FBaseUnitId;

    /**
     * 物料状态
     */
    @SerializedName("FFORBIDSTATUS")
    private String FForbidStatus;
}
