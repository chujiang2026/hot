package com.hot.modules.sys.service;

import com.hot.common.constant.AuthConstant;
import com.hot.common.util.RedisUtils;
import com.hot.modules.sys.dao.SysButtDao;
import com.hot.modules.sys.entity.SysButt;
import com.hot.modules.sys.entity.SysRole;
import com.hot.modules.sys.entity.SysRoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysButtService {

    @Autowired
    private SysButtDao buttDao;

    @Autowired
    private RedisUtils redisUtils;

    public SysButt get(String id) {
        return buttDao.get(id);
    }

    public List<SysButt> list(SysButt butt) {
        return buttDao.list(butt);
    }

    public int save(SysButt butt) {
        clearCache();
        if (butt.getId() == null || butt.getId().equals(0)) {
            return buttDao.insert(butt);
        } else {
            return buttDao.update(butt);
        }
    }

    public int delete(SysButt butt) {
        clearCache();
        return buttDao.delete(butt);
    }

    // 权限
    public List<SysRole> rolelist(SysRole menu) {
        return buttDao.rolelist(menu);
    }

    public SysRole roleget(String id) {
        return buttDao.roleget(id);
    }

    public int rolesave(SysRole menu) {
        clearCache();
        int i;
        if (menu.getId() == null || menu.getId().equals(0)) {
            i = buttDao.insertRole(menu);
        } else {
            i = buttDao.updateRole(menu);
        }
        buttDao.deleteRoleData(menu);
        if (menu.getList() != null && menu.getList().size() > 0) {
            buttDao.insertRoleData(menu);
        }
        return i;
    }

    public int roledelete(SysRole menu) {
        clearCache();
        return buttDao.roledelete(menu);
    }

    public List<SysRoleMenu> menuButt() {
        return buttDao.menuButt();
    }

    private void clearCache() {
        try {
            redisUtils.deleteByPrefix(AuthConstant.BUTT_CACHE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
