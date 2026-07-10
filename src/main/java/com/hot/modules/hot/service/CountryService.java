package com.hot.modules.hot.service;

import com.hot.modules.hot.dao.CountryDao;
import com.hot.modules.hot.entity.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor = Exception.class)
@Service
public class CountryService {

    @Autowired
    private CountryDao countryDao;

    public Country get(Integer id) {
        return countryDao.get(id);
    }

    public List<Country> list(Country country) {
        return countryDao.list(country);
    }

    public int save(Country country) {
        if (country.getId() == null || country.getId().equals(0)) {
            return countryDao.insert(country);
        }
        return countryDao.update(country);
    }

    public int delete(Country country) {
        return countryDao.delete(country);
    }

    public Integer queryCountryName(String name) {
        return countryDao.queryCountryName(name);
    }
}
