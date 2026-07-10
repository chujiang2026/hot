package com.hot.modules.hot.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface HomeDao {

    Map<String, Object> data(@Param("date") String date, @Param("userid") String userid);

    List<Map<String, Object>> cotry(@Param("date") String date, @Param("userid") String userid);

    List<Map<String, Object>> trend(@Param("date") String date, @Param("country") String country, @Param("userid") String userid);

}
