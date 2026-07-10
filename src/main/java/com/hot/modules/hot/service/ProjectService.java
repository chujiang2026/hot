package com.hot.modules.hot.service;

import com.hot.modules.hot.dao.HotCoopDao;
import com.hot.modules.hot.dao.ProjectDao;
import com.hot.modules.hot.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor = Exception.class)
@Service
public class ProjectService {

    @Autowired
    private ProjectDao projectDao;

    public Project get(Integer id) {
        return projectDao.get(id);
    }

    public List<Project> list(Project project) {
        return projectDao.list(project);
    }

    public int save(Project project) {
        if (project.getId() == null || project.getId().equals(0)) {
            project.setState(1);
            return projectDao.insert(project);
        }
        return projectDao.update(project);
    }

    public int delete(Project project) {
        return projectDao.delete(project);
    }

    @Autowired
    private HotCoopDao coopDao;

    public int close(String[] ids, String uid) {
        try {
            projectDao.close(ids, uid);
            //查询关闭的合作单红人Id，判断该红人的所有合作单是否全部关闭，状态自动变为"历史合作"
            coopDao.updateHotState();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Integer queryproject(Integer id) {
        return projectDao.queryproject(id);
    }

    public Integer queryProjectName(String name) {
        return projectDao.queryProjectName(name);
    }
}
