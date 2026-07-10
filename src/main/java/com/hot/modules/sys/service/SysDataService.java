package com.hot.modules.sys.service;

import com.hot.modules.sys.dao.SysDataDao;
import com.hot.modules.sys.entity.SysCoopField;
import com.hot.modules.sys.entity.SysData;
import com.hot.modules.sys.entity.SysField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysDataService {

    @Autowired
    private SysDataDao dataDao;

    public SysData get(Integer id) {
        SysData sys = dataDao.get(id);
        List<SysField> fields = dataDao.fieldList(sys.getId());
        String type = sys.getType();
        switch (type) {
            case "a_hot_coop":// 合作单
                SysCoopField coop = new SysCoopField();
                List<SysField> coopList = new ArrayList<>();
                List<SysField> dataList = new ArrayList<>();
                List<SysField> productList = new ArrayList<>();
                List<SysField> settList = new ArrayList<>();
                for (SysField field : fields) {
                    String table = field.getTable();
                    switch (table) {
                        case "a_hot_coop":
                            coopList.add(field);
                            break;
                        case "a_hot_coop_data":
                            dataList.add(field);
                            break;
                        case "a_hot_coop_product":
                            productList.add(field);
                            break;
                        case "a_hot_coop_sett":
                            settList.add(field);
                            break;
                    }
                }
                coop.setCoop(coopList);
                coop.setData(dataList);
                coop.setProduct(productList);
                coop.setSett(settList);
                sys.setField(coop);
                break;
        }
        return sys;
    }

    public List<SysData> list(SysData data) {
        return dataDao.list(data);
    }

    public int save(SysData data) {
        int i = 0;
        if (data.getId() == null) {
            i = dataDao.insert(data);
        } else {
            i = dataDao.update(data);
        }
        dataDao.deleteFields(data.getId());
        List<SysField> fields = data.getFields();
        if (fields != null && fields.size() > 0) {
            dataDao.insertFields(fields, data.getId());
        }
        return i;
    }

    public int delete(SysData data) {
        return dataDao.delete(data);
    }

    public Object field(String type) {
        switch (type) {
            case "a_hot_coop":// 合作单
                SysCoopField coop = new SysCoopField();
                List<SysField> fields = dataDao.fieldListType(type);
                List<SysField> coopList = fields.stream().filter(field -> field.getTable().equals("a_hot_coop")).collect(Collectors.toList());
                List<SysField> dataList = fields.stream().filter(field -> field.getTable().equals("a_hot_coop_data")).collect(Collectors.toList());
                List<SysField> productList = fields.stream().filter(field -> field.getTable().equals("a_hot_coop_product")).collect(Collectors.toList());
                List<SysField> settList = fields.stream().filter(field -> field.getTable().equals("a_hot_coop_sett")).collect(Collectors.toList());

                coop.setCoop(coopList);
                coop.setData(dataList);
                coop.setProduct(productList);
                coop.setSett(settList);
                return coop;
        }
        return null;
    }
}
