package com.hot.modules.hot.dao;

import com.hot.modules.hot.entity.HotFactor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 渠道分摊系数DAO接口
 */
@Mapper
public interface HotFactorDao {

    /**
     * 根据ID查询单条记录
     * @param id 主键ID
     * @return 渠道分摊系数对象
     */
    HotFactor get(@Param("id") Integer id);

    /**
     * 查询列表（支持条件查询）
     * @param hotFactor 查询条件
     * @return 渠道分摊系数列表
     */
    List<HotFactor> list(HotFactor hotFactor);

    /**
     * 新增记录
     * @param hotFactor 渠道分摊系数对象
     * @return 影响行数
     */
    int insert(HotFactor hotFactor);

    /**
     * 更新记录
     * @param hotFactor 渠道分摊系数对象
     * @return 影响行数
     */
    int update(HotFactor hotFactor);

    /**
     * 删除记录（逻辑删除）
     * @param id 主键ID
     * @param updateUser 更新人
     * @return 影响行数
     */
    int delete(@Param("id") Integer id, @Param("updateUser") String updateUser);

    HotFactor getNotId(@Param("id")Integer id);
}
