package com.hot.modules.hot.dao;

import com.hot.modules.hot.entity.HotInvit;
import com.hot.modules.hot.entity.HotInvitDema;
import com.hot.modules.hot.entity.HotInvitReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HotInvitDao {

    HotInvit get(@Param("id") Integer id);

    List<HotInvit> list(HotInvit invit);

    List<HotInvitReport> listexp(HotInvit invit);

    int insert(HotInvit invit);

    int update(HotInvit invit);

    int delete(@Param("id") Integer id,@Param("userid") String userid);

    void insertDemaList(@Param("id")Integer id, @Param("userid")String userid, @Param("demaList")List<HotInvitDema> demaList);

    void deleteDema(@Param("id")Integer id, @Param("userid")String userid);

    void insertDema(HotInvitDema dema);

    void updateDema(HotInvitDema dema);

    int close(@Param("ids")String[] ids, @Param("userid")String userid, @Param("reason")String reason);

    List<HotInvitReport> invit(HotInvitReport invit);

    List<HotInvitReport> invitexp(HotInvitReport invit);

    List<String> isExist(@Param("ids")String[] hotsIds);

    void closeInvit();
}
