package com.hot.modules.erp.service;

import com.google.gson.Gson;
import com.hot.modules.erp.dao.ErpDao;
import com.hot.modules.erp.entity.ErpMaterial;
import com.hot.modules.erp.entity.MaterialEntity;
import com.kingdee.bos.webapi.entity.QueryParam;
import com.kingdee.bos.webapi.entity.RepoRet;
import com.kingdee.bos.webapi.sdk.K3CloudApi;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ErpService {

    @Autowired
    private ErpDao erpDao;

    @Autowired
    private K3CloudApi k3CloudApi;

    private final Gson gson = new Gson();

    /**
     * 查看单据数据
     * @param formId 表单ID，如 BD_MATERIAL
     * @param jsonData 查询参数
     * @return 查询结果
     */
    public String viewData(String formId, String jsonData) throws Exception {
        String resultJson = k3CloudApi.view(formId, jsonData);
        return resultJson;
    }


    /**
     * 保存单据
     * @param formId 表单ID
     * @param jsonData 保存的数据
     * @return 保存结果
     */
    public String saveData(String formId, String jsonData) throws Exception {
        String resultJson = k3CloudApi.save(formId, jsonData);
        return resultJson;
    }

    /**
     * 提交单据
     * @param formId 表单ID
     * @param jsonData 提交的数据
     * @return 提交结果
     */
    public String submitData(String formId, String jsonData) throws Exception {
        String resultJson = k3CloudApi.submit(formId, jsonData);
        return resultJson;
    }

    /**
     * 审核单据
     * @param formId 表单ID
     * @param jsonData 审核的数据
     * @return 审核结果
     */
    public String auditData(String formId, String jsonData) throws Exception {
        String resultJson = k3CloudApi.audit(formId, jsonData);
        return resultJson;
    }

    /**
     * 删除单据
     * @param formId 表单ID
     * @param jsonData 删除的数据
     * @return 删除结果
     */
    public String deleteData(String formId, String jsonData) throws Exception {
        String resultJson = k3CloudApi.delete(formId, jsonData);
        return resultJson;
    }

    /**
     * 执行查询并解析结果
     * @param formId 表单ID
     * @param jsonData 查询参数
     * @return 解析后的结果对象
     */
    public RepoRet executeView(String formId, String jsonData) throws Exception {
        String resultJson = viewData(formId, jsonData);
        return gson.fromJson(resultJson, RepoRet.class);
    }


    /**
     * 批量查询单据（列表查询）
     * @param queryParam  查询参数
     * @param type 返回实体
     * @return 查询结果
     */
    public <T> List<T> executeBillQuery(QueryParam queryParam, Class<T> type) throws Exception {
        List<T> resultList = k3CloudApi.executeBillQuery(queryParam, type);
        if (resultList != null && !resultList.isEmpty() && type == MaterialEntity.class) {
            try {
                // 转换为数据库实体并保存
                List<ErpMaterial> erpMaterials = convertToErpMaterial((List<MaterialEntity>) resultList);
                erpDao.saveOrUpdateBatch(erpMaterials);
                log.info("成功保存 {} 条物料数据到数据库", erpMaterials.size());
            } catch (Exception e) {
                log.error("保存物料数据到数据库失败", e);
            }
        }
        return resultList;
    }

    /**
     * 将金蝶物料实体转换为数据库物料实体
     */
    private List<ErpMaterial> convertToErpMaterial(List<MaterialEntity> materialEntities) {
        if (materialEntities == null || materialEntities.isEmpty()) {
            return new ArrayList<>();
        }

        List<ErpMaterial> erpMaterials = new ArrayList<>();
        for (MaterialEntity entity : materialEntities) {
            ErpMaterial material = new ErpMaterial();
            // 注意：金蝶返回的字段名是 fnumber, fspecification, fcolor
            material.setMaterialId(entity.getFNumber());
            material.setModelNumber(entity.getFSpecification());
            material.setColor(entity.getFColor());
            // createTime 和 updateTime 在SQL中使用 NOW() 设置
            // delFlag 默认为 '0'
            erpMaterials.add(material);
        }
        return erpMaterials;
    }
}
