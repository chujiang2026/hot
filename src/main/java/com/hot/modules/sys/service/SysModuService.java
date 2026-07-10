package com.hot.modules.sys.service;

import com.hot.common.constant.AuthConstant;
import com.hot.common.util.RedisUtils;
import com.hot.modules.sys.dao.SysModuDao;
import com.hot.modules.sys.entity.SysModu;
import com.hot.modules.sys.entity.SysRole;
import com.hot.modules.sys.entity.SysRoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysModuService {

    @Autowired
    private SysModuDao moduDao;

    @Autowired
    private RedisUtils redisUtils;

    public SysModu get(String id) {
        return moduDao.get(id);
    }

    public List<SysModu> list(SysModu modu) {
        return moduDao.list(modu);
    }

    public int save(SysModu modu) {
        clearCache();
        if (modu.getId() == null || modu.getId().equals(0)) {
            return moduDao.insert(modu);
        } else {
            return moduDao.update(modu);
        }
    }

    public int delete(SysModu modu) {
        clearCache();
        return moduDao.delete(modu);
    }

    // 权限
    public List<SysRole> rolelist(SysRole menu) {
        return moduDao.rolelist(menu);
    }

    public SysRole roleget(String id) {
        return moduDao.roleget(id);
    }

    public int rolesave(SysRole menu) {
        clearCache();
        int i;
        if (menu.getId() == null || menu.getId().equals(0)) {
            i = moduDao.insertRole(menu);
        } else {
            i = moduDao.updateRole(menu);
        }
        moduDao.deleteRoleData(menu);
        if (menu.getList() != null && menu.getList().size() > 0) {
            moduDao.insertRoleData(menu);
        }
        return i;
    }

    public int roledelete(SysRole menu) {
        clearCache();
        return moduDao.roledelete(menu);
    }

    public List<SysRoleMenu> menuModu() {
        return moduDao.menuModu();
    }

    private void clearCache() {
        try {
            redisUtils.deleteByPrefix(AuthConstant.MODU_CACHE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
