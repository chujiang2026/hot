package com.hot.modules.hot.controller;

import com.github.pagehelper.PageHelper;
import com.hot.common.page.PageInfo;
import com.hot.common.result.Result;
import com.hot.common.result.ResultCode;
import com.hot.common.util.UserUtils;
import com.hot.modules.hot.entity.Country;
import com.hot.modules.hot.service.HotCoopService;
import com.hot.modules.hot.service.CountryService;
import com.hot.modules.sys.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/country")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @PostMapping("/get")
    public Result<?> get(@RequestParam Integer id) {
        Country m = countryService.get(id);
        return m == null ? Result.of(ResultCode.FAIL) : Result.success(m);
    }

    @PostMapping("/list")
    public Result<?> list(@ModelAttribute Country country) {
        int p = country.getPageNum() == null || country.getPageNum() < 1 ? 1 : country.getPageNum();
        int s = country.getPageSize() == null || country.getPageSize() < 1 ? 10 : country.getPageSize();
        PageHelper.startPage(p, s);
        return Result.success(new PageInfo<>(countryService.list(country)));
    }

    @PostMapping("/save")
    public Result<?> save(@ModelAttribute Country country, HttpServletRequest request) {
        String uid = currentUserId(request);
        country.setCreateUser(uid);
        country.setUpdateUser(uid);
        Integer id = countryService.queryCountryName(country.getName());
        boolean b = true;
        if (id == null) {
            b = false;
        } else {
            if (country.getId() != null && id.equals(country.getId())) {
                b = false;
            }
        }
        if (b) {
            return Result.of(ResultCode.FAIL,"项目名称已存在");
        }
        return countryService.save(country) > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }

    /**
     * 校验项目名称，不允许重复
     * @param country
     * @param request
     * @return
     */
    @PostMapping("/checkName")
    public Result<?> checkName(@ModelAttribute Country country, HttpServletRequest request) {
        Integer id = countryService.queryCountryName(country.getName());
        boolean b = true;
        if (id == null) {
            b = false;
        } else {
            if (country.getId() != null && id.equals(country.getId())) {
                b = false;
            }
        }
        return Result.success(b);
    }

    @PostMapping("/delete")
    public Result<?> delete(@ModelAttribute Country country, HttpServletRequest request) {
        String uid = currentUserId(request);
        country.setCreateUser(uid);
        country.setUpdateUser(uid);
        return countryService.delete(country) > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }

    private String currentUserId(HttpServletRequest request) {
        SysUser user = UserUtils.getCurrentUser(request);
        return user == null ? null : user.getId();
    }
}
