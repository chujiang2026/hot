package com.hot.modules.hot.controller;

import com.github.pagehelper.PageHelper;
import com.hot.common.page.PageInfo;
import com.hot.common.result.Result;
import com.hot.common.result.ResultCode;
import com.hot.common.util.UserUtils;
import com.hot.modules.hot.entity.Kol;
import com.hot.modules.hot.service.KolService;
import com.hot.modules.sys.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/kol")
public class KolController {

    @Autowired
    private KolService kolService;

    @PostMapping("/get")
    public Result<?> get(@RequestParam Integer id) {
        Kol m = kolService.get(id);
        return m == null ? Result.of(ResultCode.FAIL) : Result.success(m);
    }

    @PostMapping("/list")
    public Result<?> list(@ModelAttribute Kol kol) {
        int p = kol.getPageNum() == null || kol.getPageNum() < 1 ? 1 : kol.getPageNum();
        int s = kol.getPageSize() == null || kol.getPageSize() < 1 ? 10 : kol.getPageSize();
        PageHelper.startPage(p, s);
        return Result.success(new PageInfo<>(kolService.list(kol)));
    }

    @PostMapping("/save")
    public Result<?> save(@ModelAttribute Kol kol, HttpServletRequest request) {
        String uid = currentUserId(request);
        kol.setCreateUser(uid);
        kol.setUpdateUser(uid);
        Integer id = kolService.queryKolName(kol.getName());
        boolean b = true;
        if (id == null) {
            b = false;
        } else {
            if (kol.getId() != null && id.equals(kol.getId())) {
                b = false;
            }
        }
        if (b) {
            return Result.of(ResultCode.FAIL,"项目名称已存在");
        }
        return kolService.save(kol) > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }

    /**
     * 校验名称，不允许重复
     * @param kol
     * @param request
     * @return
     */
    @PostMapping("/checkName")
    public Result<?> checkName(@ModelAttribute Kol kol, HttpServletRequest request) {
        Integer id = kolService.queryKolName(kol.getName());
        boolean b = true;
        if (id == null) {
            b = false;
        } else {
            if (kol.getId() != null && id.equals(kol.getId())) {
                b = false;
            }
        }
        return Result.success(b);
    }

    @PostMapping("/delete")
    public Result<?> delete(@ModelAttribute Kol kol, HttpServletRequest request) {
        String uid = currentUserId(request);
        kol.setCreateUser(uid);
        kol.setUpdateUser(uid);
        return kolService.delete(kol) > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }

    private String currentUserId(HttpServletRequest request) {
        SysUser user = UserUtils.getCurrentUser(request);
        return user == null ? null : user.getId();
    }
}
