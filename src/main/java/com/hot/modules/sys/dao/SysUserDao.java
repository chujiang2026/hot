package com.hot.modules.sys.dao;

import com.hot.modules.sys.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserDao {

	SysUser get(SysUser sysUser);

	List<SysUser> list(SysUser sysUser);

	int insert(SysUser sysUser);

	int update(SysUser sysUser);

	int delete(SysUser sysUser);

	void deleteButt(SysUser user);

	void insertButt(SysUser user);

	void deleteData(SysUser user);

	void insertData(SysUser user);

	void deleteMenu(SysUser user);

	void insertMenu(SysUser user);

	void deleteModu(SysUser user);

	void insertModu(SysUser user);

	SysUser getByLoginname(@Param("loginname")String loginname);

	void updateError(SysUser sysUser);

	void updateLogin(SysUser sysUser);

	int updatePassword(SysUser sysUser);

    SysUser getPhone(@Param("phone")String phone);

	void insertLog(@Param("url")String url, @Param("str")String str, @Param("ip")String ip, @Param("userid")String userid);

	void deleteCountry(SysUser sysUser);

	void insertCountry(SysUser sysUser);
}
