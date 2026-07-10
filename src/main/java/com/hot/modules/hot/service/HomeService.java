package com.hot.modules.hot.service;

import com.hot.modules.hot.dao.HomeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HomeService {

    @Autowired
    private HomeDao homeDao;

    public Map<String, Object> data(String date, String userid) {
        return homeDao.data(date,userid);
    }

    public List<Map<String, Object>> cotry(String date, String userid) {
        return homeDao.cotry(date,userid);
    }

    public List<Map<String, Object>> trend(String date, String country, String userid) {
        return homeDao.trend(date,country,userid);
    }
}
