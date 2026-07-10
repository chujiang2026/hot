package com.hot.modules.sys.service;

import com.hot.common.constant.AuthConstant;
import com.hot.common.util.RedisUtils;
import com.hot.modules.hot.entity.Product;
import com.hot.modules.sys.dao.BasicDao;
import com.hot.modules.sys.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BasicService {

    @Autowired
    private BasicDao basicDao;

    @Autowired
    private RedisUtils redisUtils;

    public List<BasicTree> tree(BasicData bd) {
        List<BasicTree> list = basicDao.tree(bd);
        List<BasicTree> list0 = new ArrayList<>();
        for (BasicTree s : list) {
            if (s.getParentId().equals(0)) {
                list0.add(recursion(list, s));
            }
        }
        return list0;
    }

    private BasicTree recursion(List<BasicTree> list, BasicTree s) {
        List<BasicTree> list0 = new ArrayList<BasicTree>();
        for (BasicTree tree : list) {
            if (tree.getParentId().equals(s.getId())) {
                list0.add(recursion(list, tree));
            }
        }
        s.setChildren(list0);
        return s;
    }

    public List<Map<String, Object>> data(BasicData data) {
        return basicDao.data(data);
    }

    public SysUser user(String id) {
        SysUser user = null;
        try {
            Object cachedUser = redisUtils.get(AuthConstant.USER_CACHE + id);
            if (cachedUser instanceof SysUser) {
                user = (SysUser) cachedUser;
            }
            if (user == null) {
                // 从数据库重新加载用户信息
                user = basicDao.user(id);
                if (user != null) {
                    // 放入缓存
                    redisUtils.set(AuthConstant.USER_CACHE + id, user);
                }
            }
        } catch (Exception e) {
            // 记录日志或进行其他异常处理
            e.printStackTrace();
        }
        return user;
    }

    @SuppressWarnings("unchecked")
    public List<SysMenu> menu(String userid) {
        List<SysMenu> list0 = null;
        try {
            Object cached = redisUtils.get(AuthConstant.MENU_CACHE + userid);
            if (cached instanceof List) {
                list0 = (List<SysMenu>) cached;
            }
            if (list0 == null) {
                // 从数据库重新加载并构建菜单树
                List<SysMenu> list = basicDao.menu(userid);
                list0 = new ArrayList<>();
                Map<Integer, SysMenu> map = new HashMap<>();

                // 先将所有节点放入 map 中
                for (SysMenu s : list) {
                    map.put(s.getId(), s);
                }

                // 构建树结构
                for (SysMenu s : list) {
                    if (s.getParentId().equals(Integer.valueOf(0))) {
                        list0.add(s);
                    } else {
                        SysMenu parent = map.get(s.getParentId());
                        if (parent != null) {
                            if (parent.getChildren() == null) {
                                parent.setChildren(new ArrayList<>());
                            }
                            parent.getChildren().add(s);
                        }
                    }
                }

                if (!list0.isEmpty()) {
                    redisUtils.set(AuthConstant.MENU_CACHE + userid, list0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list0;
    }

    @SuppressWarnings("unchecked")
    public List<SysMenu> modu(String userid) {
        List<SysMenu> list = null;
        try {
            Object cached = redisUtils.get(AuthConstant.MODU_CACHE + userid);
            if (cached instanceof List) {
                list = (List<SysMenu>) cached;
            }
            if (list == null) {
                list = basicDao.modu(userid);
                if (!list.isEmpty()) {
                    redisUtils.set(AuthConstant.MODU_CACHE + userid, list);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    public List<SysMenu> butt(String userid) {
        List<SysMenu> list = null;
        try {
            Object cached = redisUtils.get(AuthConstant.BUTT_CACHE + userid);
            if (cached instanceof List) {
                list = (List<SysMenu>) cached;
            }
            if (list == null) {
                list = basicDao.butt(userid);
                if (!list.isEmpty()) {
                    redisUtils.set(AuthConstant.BUTT_CACHE + userid, list);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Product> product(Product product) {
        return basicDao.product(product);
    }

    public List<SysData> field(String userid) {
        List<SysData> list = new ArrayList<>();
        List<SysField> arr = basicDao.field(userid);
        List<SysField> cooparr = arr.stream().filter(field -> field.getType().equals("a_hot_coop")).collect(Collectors.toList());
        if (cooparr != null && cooparr.size() > 0) {
            SysData d = new SysData();
            d.setType("a_hot_coop");
            SysCoopField coop = new SysCoopField();
            List<SysField> coopList = cooparr.stream().filter(field -> field.getTable().equals("a_hot_coop")).collect(Collectors.toList());
            List<SysField> dataList = cooparr.stream().filter(field -> field.getTable().equals("a_hot_coop_data")).collect(Collectors.toList());
            List<SysField> productList = cooparr.stream().filter(field -> field.getTable().equals("a_hot_coop_product")).collect(Collectors.toList());
            List<SysField> settList = cooparr.stream().filter(field -> field.getTable().equals("a_hot_coop_sett")).collect(Collectors.toList());
            coop.setCoop(coopList);
            coop.setData(dataList);
            coop.setProduct(productList);
            coop.setSett(settList);
            d.setField(coop);
            list.add(d);
        }
        return list;
    }

    public String getserial(String hotCoop) {
        return basicDao.getserial(hotCoop);
    }
}
