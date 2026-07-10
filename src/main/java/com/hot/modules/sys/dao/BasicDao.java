package com.hot.modules.sys.dao;

import com.hot.modules.hot.entity.Product;
import com.hot.modules.sys.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface BasicDao {

    List<BasicTree> tree(BasicData data);

    List<Map<String, Object>> data(BasicData data);

    SysUser user(@Param("id") String id);

    List<SysMenu> menu(@Param("userid") String userid);

    List<SysMenu> modu(@Param("userid") String userid);

    List<SysMenu> butt(@Param("userid") String userid);

    List<Product> product(Product product);

    List<SysField> field(@Param("userid")String userid);

    String getserial(@Param("type")String type);

    void insertFsLog(@Param("type")String type, @Param("json")String json);
}
