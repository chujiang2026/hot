package com.hot.modules.hot.dao;

import com.hot.modules.hot.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface HotCoopDao {

    HotCoop get(@Param("id") Integer id);

    List<HotCoop> list(HotCoop coop);

    List<HotCoopExp> listexp(HotCoop coop);

    int insert(HotCoop coop);

    int update(HotCoop coop);

    int delete(@Param("id") Integer id, @Param("userid") String userid);

    int close(@Param("ids") String[] ids, @Param("userid")String userid);

    void deleteList(@Param("id")Integer id, @Param("userid") String userid);

    void insertProduct(HotCoopProduct prod);

    void updateProduct(HotCoopProduct prod);

    void insertSett(HotCoopSett sett);

    void updateSett(HotCoopSett sett);

    void insertData(HotCoopData data);

    void updateData(HotCoopData data);

    List<HotCoopReport> coop(HotCoopReport coop);

    List<HotCoopExp> coopexp(HotCoopReport coop);

    Map<String, Object> isExist(@Param("id")Integer id);

    void closeInvite(@Param("id") Integer id, @Param("reason") String reason, @Param("userid") String userid);

    void updateHotState();

    void closeHotState(@Param("id")Integer id);

    String queryCreativ(@Param("ids")String[] ids);

    List<Integer> queryprojectid(@Param("ids")String[] ids);

    void sum_score_level(@Param("id") String id);

    void sum_hot_score_level(@Param("id") Integer id);

    List<Integer> queryHotbId(@Param("ids")String[] ids);


}
