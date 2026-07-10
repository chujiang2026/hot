package com.hot.modules.hot.dao;

import com.hot.modules.hot.entity.Country;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CountryDao {

    Country get(@Param("id") Integer id);

    List<Country> list(Country country);

    int insert(Country country);

    int update(Country country);

    int delete(Country country);

    Integer queryCountryName(@Param("name") String name);
}
