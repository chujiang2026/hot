package com.hot.modules.hot.controller;

import com.github.pagehelper.PageHelper;
import com.hot.common.page.PageInfo;
import com.hot.common.result.Result;
import com.hot.common.result.ResultCode;
import com.hot.common.util.UserUtils;
import com.hot.modules.hot.entity.Project;
import com.hot.modules.hot.service.HotCoopService;
import com.hot.modules.hot.service.ProjectService;
import com.hot.modules.sys.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/get")
    public Result<?> get(@RequestParam Integer id) {
        Project m = projectService.get(id);
        return m == null ? Result.of(ResultCode.FAIL) : Result.success(m);
    }

    @PostMapping("/list")
    public Result<?> list(@ModelAttribute Project project) {
        int p = project.getPageNum() == null || project.getPageNum() < 1 ? 1 : project.getPageNum();
        int s = project.getPageSize() == null || project.getPageSize() < 1 ? 10 : project.getPageSize();
        PageHelper.startPage(p, s);
        return Result.success(new PageInfo<>(projectService.list(project)));
    }

    @PostMapping("/save")
    public Result<?> save(@ModelAttribute Project project, HttpServletRequest request) {
        String uid = currentUserId(request);
        project.setCreateUser(uid);
        project.setUpdateUser(uid);
        Integer id = projectService.queryProjectName(project.getName());
        boolean b = true;
        if (id == null) {
            b = false;
        } else {
            if (project.getId() != null && id.equals(project.getId())) {
                b = false;
            }
        }
        if (b) {
            return Result.of(ResultCode.FAIL,"项目名称已存在");
        }
        return projectService.save(project) > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }

    /**
     * 校验项目名称，不允许重复
     * @param project
     * @param request
     * @return
     */
    @PostMapping("/checkName")
    public Result<?> checkName(@ModelAttribute Project project, HttpServletRequest request) {
        Integer id = projectService.queryProjectName(project.getName());
        boolean b = true;
        if (id == null) {
            b = false;
        } else {
            if (project.getId() != null && id.equals(project.getId())) {
                b = false;
            }
        }
        return Result.success(b);
    }

    @PostMapping("/delete")
    public Result<?> delete(@ModelAttribute Project project, HttpServletRequest request) {
        String uid = currentUserId(request);
        project.setCreateUser(uid);
        project.setUpdateUser(uid);
        int i = projectService.queryproject(project.getId());
        if (i > 0) {
            return Result.of(ResultCode.EXIS_PROJECT);
        }
        return projectService.delete(project) > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }

    @Autowired
    private HotCoopService coopService;

    //批量关闭
    @PostMapping("/close")
    public Result<?> close(@RequestParam String[] ids, HttpServletRequest request) {
        String uid = currentUserId(request);
        List<Integer> arr = coopService.queryprojectid(ids);
        if (arr != null && arr.size() > 0) {
            String[] idsArr = new String[arr.size()];
            for (int i = 0; i < arr.size(); i++) {
                idsArr[i] = arr.get(i).toString();
            }
            String msg = coopService.queryCreativ(idsArr);
            if (msg != null) {
                return Result.of(ResultCode.FAIL, "合作单：" + msg + " 的Content Creativity Score字段未填写，项目不允许关闭");
            }
            coopService.close(idsArr, uid);
        }
        return projectService.close(ids, uid) > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }

    private String currentUserId(HttpServletRequest request) {
        SysUser user = UserUtils.getCurrentUser(request);
        return user == null ? null : user.getId();
    }
}
