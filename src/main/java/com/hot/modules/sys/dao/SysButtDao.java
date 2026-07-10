package com.hot.modules.sys.dao;

import com.hot.modules.sys.entity.SysButt;
import com.hot.modules.sys.entity.SysRole;
import com.hot.modules.sys.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysButtDao {

    SysButt get(@Param("id") String id);

    List<SysButt> list(SysButt butt);

    int insert(SysButt butt);

    int update(SysButt butt);

    int delete(SysButt butt);

    // 权限（按钮角色）
    List<SysRole> rolelist(SysRole role);

    SysRole roleget(@Param("id") String id);

    int insertRole(SysRole role);

    int updateRole(SysRole role);

    int roledelete(SysRole role);

    int insertRoleData(SysRole role);

    int deleteRoleData(SysRole role);

    List<SysRoleMenu> menuButt();
}
