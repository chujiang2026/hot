package com.hot.modules.hot.service;

import com.hot.modules.hot.dao.KolDao;
import com.hot.modules.hot.entity.Kol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor = Exception.class)
@Service
public class KolService {

    @Autowired
    private KolDao kolDao;

    public Kol get(Integer id) {
        return kolDao.get(id);
    }

    public List<Kol> list(Kol kol) {
        return kolDao.list(kol);
    }

    public int save(Kol kol) {
        if (kol.getId() == null || kol.getId().equals(0)) {
            return kolDao.insert(kol);
        }
        return kolDao.update(kol);
    }

    public int delete(Kol kol) {
        return kolDao.delete(kol);
    }

    public Integer queryKolName(String name) {
        return kolDao.queryKolName(name);
    }
}
