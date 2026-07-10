package com.hot.modules.hot.service;

import com.hot.modules.hot.dao.HotInvitDao;
import com.hot.modules.hot.entity.HotInvit;
import com.hot.modules.hot.entity.HotInvitDema;
import com.hot.modules.hot.entity.HotInvitReport;
import com.hot.modules.sys.service.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor = Exception.class)
@Service
public class HotInvitService {

    @Autowired
    private HotInvitDao invitDao;

    public HotInvit get(Integer id) {
        return invitDao.get(id);
    }

    public List<HotInvit> list(HotInvit invit) {
        return invitDao.list(invit);
    }

    public List<HotInvitReport> listexp(HotInvit invit) {
        return invitDao.listexp(invit);
    }

    @Autowired
    private BasicService basicService;

    public int save(HotInvit invit, String userid) {
        int i = 0;
        Integer id = invit.getId();

        List<HotInvitDema> demaList = invit.getDemaList();
        List<HotInvit> list = invit.getHotsList();
        if (id == null) {
            for (HotInvit hots : list) {
                hots.setDocNum("INVIT"+basicService.getserial("hot_invit"));
                hots.setBdName(invit.getBdName());
                hots.setCreateUser(userid);
                hots.setUpdateUser(userid);
                i = invitDao.insert(hots);
                id = hots.getId();
                if (demaList != null && demaList.size() > 0) {
                    invitDao.insertDemaList(id, userid, demaList);
                }
            }
        } else {
            HotInvit hots = list.get(0);
            invit.setHotbId(hots.getHotbId());
            invit.setHotsId(hots.getHotsId());
            invit.setChannel(hots.getChannel());
            invit.setCountry(hots.getCountry());
            i = invitDao.update(invit);
            invitDao.deleteDema(id,userid);
            if (demaList != null && demaList.size() > 0) {
                for (HotInvitDema dema : demaList) {
                    dema.setInvitId(id);
                    dema.setCreateUser(userid);
                    dema.setUpdateUser(userid);
                    if (dema.getId() == null) {
                        invitDao.insertDema(dema);
                    } else {
                        invitDao.updateDema(dema);
                    }
                }
            }
        }
        return i;
    }

    public int delete(Integer id, String userid) {
        return invitDao.delete(id,userid);
    }

    public int close(String[] ids, String userid, String reason) {
        return invitDao.close(ids,userid,reason);
    }

    public List<HotInvitReport> invit(HotInvitReport invit) {
        return invitDao.invit(invit);
    }

    public List<String> isExist(String[] hotsIds) {
        return invitDao.isExist(hotsIds);
    }

    public List<HotInvitReport> invitexp(HotInvitReport invit) {
        return invitDao.invitexp(invit);
    }

    public void closeInvit() {
        invitDao.closeInvit();
    }
}
