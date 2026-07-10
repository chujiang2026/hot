package com.hot.modules.sys.dao;

import com.hot.modules.sys.entity.SysModu;
import com.hot.modules.sys.entity.SysRole;
import com.hot.modules.sys.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysModuDao {

    SysModu get(@Param("id") String id);

    List<SysModu> list(SysModu modu);

    int insert(SysModu modu);

    int update(SysModu modu);

    int delete(SysModu modu);

    // 权限（模块角色）
    List<SysRole> rolelist(SysRole role);

    SysRole roleget(@Param("id") String id);

    int insertRole(SysRole role);

    int updateRole(SysRole role);

    int roledelete(SysRole role);

    int insertRoleData(SysRole role);

    int deleteRoleData(SysRole role);

    List<SysRoleMenu> menuModu();
}
