package com.hot.modules.sys.dao;

import com.hot.modules.sys.entity.SysMenu;
import com.hot.modules.sys.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysMenuDao {

    SysMenu get(@Param("id") String id);

    List<SysMenu> list();

    int insert(SysMenu menu);

    int update(SysMenu menu);

    int delete(SysMenu menu);

    // 权限（菜单角色）
    List<SysRole> rolelist(SysRole role);

    SysRole roleget(@Param("id") String id);

    int insertRole(SysRole role);

    int updateRole(SysRole role);

    int roledelete(SysRole role);

    int insertRoleData(SysRole role);

    int deleteRoleData(SysRole role);
}
