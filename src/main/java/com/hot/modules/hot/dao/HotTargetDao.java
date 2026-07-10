package com.hot.modules.hot.dao;

import com.hot.modules.hot.entity.HotTarget;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 红人合作目标DAO接口
 */
@Mapper
public interface HotTargetDao {

    /**
     * 根据ID查询单条记录
     * @param id 主键ID
     * @return 红人合作目标对象
     */
    HotTarget get(@Param("id") Integer id);

    /**
     * 查询列表（支持条件查询）
     * @param hotTarget 查询条件
     * @return 红人合作目标列表
     */
    List<HotTarget> list(HotTarget hotTarget);

    /**
     * 新增记录
     * @param hotTarget 红人合作目标对象
     * @return 影响行数
     */
    int insert(HotTarget hotTarget);

    /**
     * 更新记录
     * @param hotTarget 红人合作目标对象
     * @return 影响行数
     */
    int update(HotTarget hotTarget);

    /**
     * 删除记录（逻辑删除）
     * @param id 主键ID
     * @param updateUser 更新人
     * @return 影响行数
     */
    int delete(@Param("id") Integer id, @Param("updateUser") String updateUser);

    Integer getTarget(HotTarget hotTarget);

}
