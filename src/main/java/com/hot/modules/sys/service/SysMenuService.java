package com.hot.modules.sys.service;

import com.hot.common.constant.AuthConstant;
import com.hot.common.util.RedisUtils;
import com.hot.modules.sys.dao.SysMenuDao;
import com.hot.modules.sys.entity.SysMenu;
import com.hot.modules.sys.entity.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysMenuService {

    @Autowired
    private SysMenuDao menuDao;

    @Autowired
    private RedisUtils redisUtils;

    public SysMenu get(String id) {
        return menuDao.get(id);
    }

    public List<SysMenu> list() {
        List<SysMenu> list = menuDao.list();
        List<SysMenu> list0 = new ArrayList<SysMenu>();
        for (SysMenu s : list) {
            if (s.getParentId().equals(0)) {
                list0.add(recursion(list, s));
            }
        }
        return list0;
    }

    private SysMenu recursion(List<SysMenu> list, SysMenu s) {
        List<SysMenu> list0 = new ArrayList<SysMenu>();
        for (SysMenu sysMenu : list) {
            if (sysMenu.getParentId().equals(s.getId())) {
                list0.add(recursion(list, sysMenu));
            }
        }
        s.setChildren(list0);
        return s;
    }

    public int save(SysMenu menu) {
        clearCache();
        if (menu.getParentId() != null && menu.getParentId() > 0) {
            SysMenu ms = menuDao.get(menu.getParentId() + "");
            menu.setParentIds(ms.getParentIds() + "," + menu.getParentId());
        } else {
            menu.setParentIds("0");
        }
        if (menu.getId() == null || menu.getId().equals(0)) {
            return menuDao.insert(menu);
        } else {
            return menuDao.update(menu);
        }
    }

    public int delete(SysMenu menu) {
        clearCache();
        return menuDao.delete(menu);
    }

    // 权限
    public List<SysRole> rolelist(SysRole menu) {
        return menuDao.rolelist(menu);
    }

    public SysRole roleget(String id) {
        return menuDao.roleget(id);
    }

    public int rolesave(SysRole menu) {
        clearCache();
        int i;
        if (menu.getId() == null || menu.getId().equals(0)) {
            i = menuDao.insertRole(menu);
        } else {
            i = menuDao.updateRole(menu);
        }
        menuDao.deleteRoleData(menu);
        if (menu.getList() != null && menu.getList().size() > 0) {
            menuDao.insertRoleData(menu);
        }
        return i;
    }

    public int roledelete(SysRole menu) {
        clearCache();
        return menuDao.roledelete(menu);
    }

    private void clearCache() {
        try {
            redisUtils.deleteByPrefix(AuthConstant.MENU_CACHE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
