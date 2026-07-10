package com.hot.modules.hot.service;

import com.hot.common.exception.BizException;
import com.hot.common.result.ResultCode;
import com.hot.common.util.BasicUtils;
import com.hot.modules.hot.dao.HotDao;
import com.hot.modules.hot.entity.Hot;
import com.hot.modules.hot.entity.HotBasic;
import com.hot.modules.hot.entity.HotCoop;
import com.hot.modules.hot.entity.HotInvit;
import com.hot.modules.sys.entity.BasicData;
import com.hot.modules.sys.service.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional(rollbackFor = Exception.class)
@Service
public class HotService {

    @Autowired
    private HotDao hotDao;

    public Hot get(Integer id) {
        Hot hot = hotDao.get(id);
        if (hot != null) {
            List<HotBasic> basicList = hotDao.getBasicList(hot.getId());
            if (basicList != null && basicList.size() > 0) {
                for (HotBasic hb : basicList) {
                    switch (hb.getChannel()) {
                        case "YouTube":
                            hot.setBasicyt(hb);
                            break;
                        case "Instagram":
                            hot.setBasicig(hb);
                            break;
                        case "TikTok":
                            hot.setBasictk(hb);
                            break;
                    }
                }
            }
        }
        return hot;
    }

    public List<Hot> list(Hot hot) {
        return hotDao.list(hot);
    }

    @Autowired
    private BasicService basicService;

    public int save(Hot hot, String userid) throws Exception {
        int i = 0;
        List<HotBasic> blist = new ArrayList<>();
        Integer id = hot.getId();
        HotBasic basicyt = hot.getBasicyt();
        if (basicyt != null) {
            basicyt.setCreateUser(userid);
            basicyt.setUpdateUser(userid);
            if (basicyt.getHotsId() == null || basicyt.getHotsId().equals("")) {
                throw new Exception("YouTube渠道红人ID不能为空");
            } else {
                HotBasic hb = hotDao.getBasic(basicyt.getHotsId(), "YouTube");
                if (hb != null) {
                    if (id == null || !id.equals(hb.getHotId())) throw new BizException(ResultCode.FAIL,"YouTube渠道红人ID已存在");
                    basicyt.setId(hb.getId());
                }
                if (hot.getName() == null || hot.getName().equals("")) {
                    hot.setName(basicyt.getHotsId());
                }
                basicyt.setChannel("YouTube");
                blist.add(basicyt);
            }
        }
        HotBasic basicig = hot.getBasicig();
        if (basicig != null) {
            basicig.setCreateUser(userid);
            basicig.setUpdateUser(userid);
            if (basicig.getHotsId()== null || basicig.getHotsId().equals("")) {
                throw new Exception("Instagram渠道红人ID不能为空");
            } else {
                HotBasic hb = hotDao.getBasic(basicig.getHotsId(), "Instagram");
                if (hb != null) {
                    if (id != null && !id.equals(hb.getHotId())) throw new BizException(ResultCode.FAIL,"Instagram渠道红人ID已存在");
                    basicig.setId(hb.getId());
                }
                if (hot.getName() == null || hot.getName().equals("")) {
                    hot.setName(basicig.getHotsId());
                }
                basicig.setChannel("Instagram");
                blist.add(basicig);
            }
        }
        HotBasic basictk = hot.getBasictk();
        if (basictk != null) {
            basictk.setCreateUser(userid);
            basictk.setUpdateUser(userid);
            if (basictk.getHotsId()== null || basictk.getHotsId().equals("")) {
                throw new Exception("TikTok渠道红人ID不能为空");
            } else {
                HotBasic hb = hotDao.getBasic(basictk.getHotsId(), "TikTok");
                if (hb != null) {
                    if (id != null && !id.equals(hb.getHotId())) throw new BizException(ResultCode.FAIL,"TikTok渠道红人ID已存在");
                    basictk.setId(hb.getId());
                }
                if (hot.getName() == null || hot.getName().equals("")) {
                    hot.setName(basictk.getHotsId());
                }
                basictk.setChannel("TikTok");
                blist.add(basictk);
            }
        }
        if (id == null) {
            hot.setState("3");
            hot.setArchName("HOT"+basicService.getserial("a_hot"));
            i = hotDao.insert(hot);
            id = hot.getId();
        }
        for (HotBasic hb : blist) {
            if (hb.getId() == null) {
                hb.setHotId(id);
                insertBasic(hb,userid);
            } else {
                updateBasic(hb,userid);
            }
        }
        hotDao.updateLevel();
        if (blist.size() > 0) i = 1;
        return i;
    }



    public int insert(Hot hot) {
        return hotDao.insert(hot);
    }

    public int state(Hot hot) {
        return hotDao.state(hot);
    }

    public HotBasic getBasic(String hotsId, String channel) {
        return hotDao.getBasic(hotsId, channel);
    }

    public List<HotInvit> invit(HotInvit invit) {
        return hotDao.invit(invit);
    }

    public List<HotCoop> coop(HotCoop coop) {
        return hotDao.coop(coop);
    }

    private int insertBasic(HotBasic hb, String userid) {
        int i = hotDao.insertBasic(hb);
        hotDao.deleteHotType(hb.getId());
        if (hb.getHotTypeArr() != null && hb.getHotTypeArr().length > 0) {
            hotDao.insertHotType(hb.getId(),hb.getHotTypeArr());
        }
        return i;
    }
    public int updateBasic(HotBasic hb, String userid) {
        int i = hotDao.updateBasic(hb);
        hotDao.deleteHotType(hb.getId());
        if (hb.getHotTypeArr() != null && hb.getHotTypeArr().length > 0) {
            hotDao.insertHotType(hb.getId(),hb.getHotTypeArr());
        }
        return i;
    }

    public List<HotBasic> expList(Hot coop) {
        return hotDao.expList(coop);
    }

    public Integer queryHotsIdState(Integer hotbId) {
        return hotDao.queryHotsIdState(hotbId);
    }
}
