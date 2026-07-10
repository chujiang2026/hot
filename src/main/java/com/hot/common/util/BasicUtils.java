package com.hot.common.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hot.modules.sys.dao.BasicDao;
import com.hot.modules.sys.entity.BasicData;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BasicUtils {

    private static BasicDao dao = SpringContextHolder.getBean(BasicDao.class);

    public static List<Map<String, Object>> getDictList(String userid, String type){
        BasicData bd = new BasicData();
        bd.setName(type);
        bd.setUserid(userid);
        return dao.data(bd);
    }

    public static String getDictLabel(String userid, String value, String type, String defaultValue){
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)){
            List<Map<String, Object>> list = getDictList(userid,type);
            if (list == null){
                return defaultValue;
            } else {
                for (Map<String, Object> map : list){
                    String id = map.get("id") == null ? null : map.get("id").toString();
                    String name = map.get("name") == null ? "" : map.get("name").toString();
                    if (id != null && id.equals(value)) {
                        return name;
                    }
                }
            }
        }
        return defaultValue;
    }

    public static String getDictValue(String userid, String label, String type, String defaultLabel){
        if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(label)){
            List<Map<String, Object>> list = getDictList(userid,type);
            if (list == null){
                return defaultLabel;
            } else {
                for (Map<String, Object> map : list){
                    String id = map.get("id") == null ? null : map.get("id").toString();
                    String name = map.get("name") == null ? "" : map.get("name").toString();
                    if (name != null && name.equals(label)) {
                        return id;
                    }
                }
            }
        }
        return defaultLabel;
    }

    public static String[] dropList(String userid, String type) {
        if (type == null || type.length() == 0) {
            return new String[0];
        }
        List<String> arr = new ArrayList<>();
        List<Map<String, Object>> list = getDictList(userid,type);
        if (list != null) {
            for (Map<String, Object> map : list) {
                if (map.get("name") != null) {
                    arr.add(map.get("name").toString());
                }
            }
        }
        return arr.toArray(new String[arr.size()]);
    }
}
