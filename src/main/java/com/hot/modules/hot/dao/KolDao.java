package com.hot.modules.hot.dao;

import com.hot.modules.hot.entity.Kol;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface KolDao {

    Kol get(@Param("id") Integer id);

    List<Kol> list(Kol country);

    int insert(Kol country);

    int update(Kol country);

    int delete(Kol country);

    Integer queryKolName(@Param("name") String name);
}
