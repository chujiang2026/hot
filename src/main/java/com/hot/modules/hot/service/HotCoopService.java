package com.hot.modules.hot.service;

import com.hot.modules.hot.dao.HotCoopDao;
import com.hot.modules.hot.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional(rollbackFor = Exception.class)
@Service
public class HotCoopService {

    @Autowired
    private HotCoopDao coopDao;

    public HotCoop get(Integer id) {
        return coopDao.get(id);
    }

    public List<HotCoop> list(HotCoop coop) {
        return coopDao.list(coop);
    }

    public int save(HotCoop coop) {
        int i = 0;
        Integer id = coop.getId();
        if (coop.getId() == null) {
            i = coopDao.insert(coop);
            id = coop.getId();
        } else {
            i = coopDao.update(coop);
        }
        coopDao.deleteList(id, coop.getCreateUser());
        List<HotCoopProduct> prodList = coop.getProdList();
        if (prodList != null && prodList.size() > 0) {
            for (HotCoopProduct prod : prodList) {
                prod.setCoopId(id);
                prod.setCreateUser(coop.getCreateUser());
                prod.setUpdateUser(coop.getUpdateUser());
                String coopfee = prod.getCoopFee();
                if (coopfee == null || coopfee.length() == 0) {
                    prod.setCoopFee("0");
                }
                if (prod.getId() == null) {
                    coopDao.insertProduct(prod);
                } else {
                    coopDao.updateProduct(prod);
                }
                List<HotCoopSett> settList = prod.getSettList();
                if (settList != null && settList.size() > 0) {
                    for (HotCoopSett sett : settList) {
                        sett.setCoopId(prod.getId());
                        sett.setCreateUser(coop.getCreateUser());
                        sett.setUpdateUser(coop.getUpdateUser());
                        if (sett.getId() == null) {
                            coopDao.insertSett(sett);
                        } else {
                            coopDao.updateSett(sett);
                        }
                    }
                }
                List<HotCoopData> dataList = prod.getDataList();
                if (dataList != null && dataList.size() > 0) {
                    for (HotCoopData data : dataList) {
                        data.setCoopId(prod.getId());
                        data.setCreateUser(coop.getCreateUser());
                        data.setUpdateUser(coop.getUpdateUser());
                        if (data.getId() == null) {
                            coopDao.insertData(data);
                        } else {
                            coopDao.updateData(data);
                        }
                    }
                }
            }
        }
        //关闭已经合作的邀约单
        coopDao.closeInvite(coop.getInvitId(), "合作单号："+coop.getDocNum(), coop.getCreateUser());
        //修改红人的合作状态
        coopDao.closeHotState(coop.getId());
        return i;
    }

    public int delete(Integer id, String userid) {
        return coopDao.delete(id,userid);
    }

    public int close(String[] ids, String userid) {
        int i = coopDao.close(ids,userid);
        if (i > 0) {
            //查询关闭的合作单红人Id，判断该红人的所有合作单是否全部关闭，状态自动变为"历史合作"
            coopDao.updateHotState();
            for (String id : ids) {
                //计算内容评分，以及等级
                coopDao.sum_score_level(id);
            }
            //获取红人Id
            List<Integer> arr = coopDao.queryHotbId(ids);
            if (arr != null && arr.size() > 0) {
                for (Integer id : arr) {
                    //计算红人合作等级
                    coopDao.sum_hot_score_level(id);
                }
            }
        }
        return i;
    }

    public List<HotCoopReport> coop(HotCoopReport coop) {
        return coopDao.coop(coop);
    }

    public Map<String, Object> isExist(Integer invitId) {
        return coopDao.isExist(invitId);
    }

    public String queryCreativ(String[] ids) {
        return coopDao.queryCreativ(ids);
    }

    public List<Integer> queryprojectid(String[] ids) {
        return coopDao.queryprojectid(ids);
    }

    public List<HotCoopExp> listexp(HotCoop coop) {
        return coopDao.listexp(coop);
    }

    public List<HotCoopExp> coopexp(HotCoopReport coop) {
        return coopDao.coopexp(coop);
    }
}
