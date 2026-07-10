package com.hot.modules.erp.dao;

import com.hot.modules.erp.entity.ErpMaterial;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 金蝶erp 数据接口
 */
@Mapper
public interface ErpDao {

    /**
     * 批量保存物料数据
     */
    void saveBatch(List<ErpMaterial> list);

    /**
     * 批量保存或更新物料数据（存在则更新）
     */
    void saveOrUpdateBatch(List<ErpMaterial> list);

    /**
     * 清空物料表（可选，根据业务需求）
     */
    void truncateMaterial();

    /**
     * 根据物料编码查询是否存在
     */
    int countByMaterialId(String materialId);
}
