package com.hot.modules.sys.dao;

import com.hot.modules.sys.entity.SysData;
import com.hot.modules.sys.entity.SysField;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysDataDao {

    SysData get(@Param("id") Integer id);

    List<SysField> fieldList(@Param("id") Integer id);

    List<SysData> list(SysData data);

    int insert(SysData data);

    int update(SysData data);

    int delete(SysData data);

    void deleteFields(@Param("id")Integer id);

    void insertFields(@Param("list")List<SysField> list, @Param("id")Integer id);

    List<SysField> fieldListType(@Param("type")String type);
}
