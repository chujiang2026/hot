package com.hot.modules.hot.service;

import com.hot.modules.hot.dao.HotFactorDao;
import com.hot.modules.hot.entity.HotFactor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 渠道分摊系数服务类
 */
@Service
public class HotFactorService {

    @Autowired
    private HotFactorDao hotFactorDao;

    /**
     * 根据ID查询单条记录
     * @param id 主键ID
     * @return 渠道分摊系数对象
     */
    public HotFactor get(Integer id) {
        return hotFactorDao.get(id);
    }

    /**
     * 查询列表（支持条件查询）
     * @param hotFactor 查询条件
     * @return 渠道分摊系数列表
     */
    public List<HotFactor> list(HotFactor hotFactor) {
        return hotFactorDao.list(hotFactor);
    }

    /**
     * 新增或更新记录
     * @param hotFactor 渠道分摊系数对象
     * @return 影响行数
     */
    public int save(HotFactor hotFactor) {
        return hotFactorDao.update(hotFactor);
    }

    /**
     * 删除记录（逻辑删除）
     * @param id 主键ID
     * @param userId 当前用户ID
     * @return 影响行数
     */
    public int delete(Integer id, String userId) {
        return hotFactorDao.delete(id, userId);
    }

    public HotFactor getNotId(Integer id) {
        return hotFactorDao.getNotId(id);
    }

}
