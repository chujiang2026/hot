package com.hot.modules.hot.service;

import com.hot.modules.hot.dao.HotTargetDao;
import com.hot.modules.hot.entity.HotTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * 红人合作目标服务类
 */
@Service
public class HotTargetService {

    @Autowired
    private HotTargetDao hotTargetDao;

    /**
     * 根据ID查询单条记录
     * @param id 主键ID
     * @return 红人合作目标对象
     */
    public HotTarget get(Integer id) {
        return hotTargetDao.get(id);
    }

    /**
     * 查询列表（支持条件查询）
     * @param hotTarget 查询条件
     * @return 红人合作目标列表
     */
    public List<HotTarget> list(HotTarget hotTarget) {
        return hotTargetDao.list(hotTarget);
    }

    /**
     * 新增或更新记录
     * @param hotTarget 红人合作目标对象
     * @return 影响行数
     */
    public int save(HotTarget hotTarget) {
        System.out.println(hotTarget.toString());
        if (hotTarget.getViewTarget() == null) hotTarget.setViewTarget("0");
        if (hotTarget.getIactrTarget() == null) hotTarget.setIactrTarget("0");
        if (hotTarget.getClickrTarget() == null) hotTarget.setClickrTarget("0");
        if (hotTarget.getConverTarget() == null) hotTarget.setConverTarget("0");
        if (hotTarget.getCpmTarget() == null) hotTarget.setCpmTarget("0");
        if (hotTarget.getRoasTarget() == null) hotTarget.setRoasTarget("0");

        if (hotTarget.getId() == null) {
            // 新增操作
            return hotTargetDao.insert(hotTarget);
        } else {
            // 更新操作
            return hotTargetDao.update(hotTarget);
        }
    }

    /**
     * 删除记录（逻辑删除）
     * @param id 主键ID
     * @param userId 当前用户ID
     * @return 影响行数
     */
    public int delete(Integer id, String userId) {
        return hotTargetDao.delete(id, userId);
    }

    public Integer getTarget(HotTarget h) {
        return hotTargetDao.getTarget(h);
    }
}
