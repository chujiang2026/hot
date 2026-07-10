package com.hot.modules.erp.controller;

import com.hot.common.result.Result;
import com.hot.common.result.ResultCode;
import com.hot.modules.erp.entity.MaterialEntity;
import com.hot.modules.erp.service.ErpService;
import com.kingdee.bos.webapi.entity.QueryParam;
import com.kingdee.bos.webapi.entity.RepoRet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 拉取Erp物料数据接口
 */
@RestController
@RequestMapping("/erp")
@Slf4j
public class ErpController {
    @Autowired
    private ErpService erpService;

    /**
     * 查询物料信息
     */
    @GetMapping("/material/view")
    public Result<Map<String, Object>> viewMaterial() {
        try {
            String jsonData = "{\"CreateOrgId\":0,\"Number\":\"\",\"Id\":\"\",\"IsSortBySeq\":\"false\"}";
            String formId = "BD_MATERIAL";

            RepoRet repoRet = erpService.executeView(formId, jsonData);

            if (repoRet.getResult().getResponseStatus().isIsSuccess()) {
                Map<String, Object> data = new HashMap<>();
                data.put("result", repoRet.getResult());
                return Result.success(data);
            } else {
                String errorMsg = repoRet.getResult().getResponseStatus().getErrors().toString();
                return Result.fail(ResultCode.FAIL, errorMsg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(ResultCode.SERVER_ERR, e.getMessage());
        }
    }

    /**
     * 通用查询接口
     * @param formId 表单ID
     * @param jsonData 查询参数（JSON字符串）
     */
    @PostMapping("/view")
    public Result<String> view(@RequestParam String formId, @RequestBody String jsonData) {
        try {
            String resultJson = erpService.viewData(formId, jsonData);
            return Result.success(resultJson);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(ResultCode.SERVER_ERR, e.getMessage());
        }
    }


    /**
     * 批量查询接口 - 使用 QueryParam 对象（推荐）
     * @param queryParam 查询参数对象
     * @return 查询结果列表
     */
    @PostMapping("/material/queryBatch")
    public Result<List<MaterialEntity>> executeBillQuery(@RequestBody QueryParam queryParam) {
        try {

            // 执行查询
            List<MaterialEntity> resultList = erpService.executeBillQuery(queryParam, MaterialEntity.class);

            if (resultList != null && !resultList.isEmpty()) {
                return Result.success(resultList,"erp物料数据同步成功，成功更新" + resultList.size() + "条数据到数据库");
            } else {
                log.info("erp同步失败，无数据");
                return Result.fail(ResultCode.FAIL, "erp同步失败，无数据");
            }
        } catch (Exception e) {
            log.error("erp同步发生错误，原因：{}", e.getMessage());
            e.printStackTrace();
            return Result.fail(ResultCode.SERVER_ERR, e.getMessage());
        }
    }



}
