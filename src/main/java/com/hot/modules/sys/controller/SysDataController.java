package com.hot.modules.sys.controller;

import com.github.pagehelper.PageHelper;
import com.hot.common.page.PageInfo;
import com.hot.common.result.Result;
import com.hot.common.result.ResultCode;
import com.hot.common.util.UserUtils;
import com.hot.modules.sys.entity.SysData;
import com.hot.modules.sys.entity.SysField;
import com.hot.modules.sys.entity.SysUser;
import com.hot.modules.sys.service.SysDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/sys/data")
public class SysDataController {

    @Autowired
    private SysDataService dataService;

    @PostMapping("/get")
    public Result<?> get(@RequestParam Integer id) {
        SysData m = dataService.get(id);
        return m == null ? Result.of(ResultCode.FAIL) : Result.success(m);
    }

    @PostMapping("/list")
    public Result<?> list(@ModelAttribute SysData data) {
        int p = data.getPageNum() == null || data.getPageNum() < 1 ? 1 : data.getPageNum();
        int s = data.getPageSize() == null || data.getPageSize() < 1 ? 10 : data.getPageSize();
        PageHelper.startPage(p, s);
        return Result.success(new PageInfo<>(dataService.list(data)));
    }
    
    @PostMapping("/save")
    public Result<?> save(HttpServletRequest request, @RequestBody SysData data) {
        data.setCreateUser(currentUserId(request));
        return dataService.save(data) > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }

    @PostMapping("/delete")
    public Result<?> delete(HttpServletRequest request, @RequestParam Integer id) {
        SysData data = new SysData();
        data.setId(id);
        data.setCreateUser(currentUserId(request));
        return dataService.delete(data) > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }

    @PostMapping("/field")
    public Result<?> field(@RequestParam String type) {
        Object field = dataService.field(type);
        return field == null ? Result.of(ResultCode.FAIL) : Result.success(field);
    }

    private String currentUserId(HttpServletRequest request) {
        SysUser user = UserUtils.getCurrentUser(request);
        return user == null ? null : user.getId();
    }
}
