package com.hot.modules.sys.controller;

import com.github.pagehelper.PageHelper;
import com.hot.common.page.PageInfo;
import com.hot.common.result.Result;
import com.hot.common.util.UserUtils;
import com.hot.modules.hot.entity.HotCoopReport;
import com.hot.modules.hot.entity.Product;
import com.hot.modules.sys.entity.BasicData;
import com.hot.modules.sys.entity.SysUser;
import com.hot.modules.sys.service.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 基础数据接口：树形、数据、菜单、模块、按钮。
 * 用户身份统一通过 token -> Redis（UserUtils）获取
 */
@RestController
@RequestMapping("/basic")
public class BasicController {

    @Autowired
    private BasicService basicService;

    /** 树形数据。 */
    @PostMapping("/tree")
    public Result<?> tree(HttpServletRequest request, @ModelAttribute BasicData bd) {
        bd.setUserid(currentUserId(request));
        return Result.success(basicService.tree(bd));
    }

    /** 数据获取。 */
    @PostMapping("/data")
    public Result<?> data(HttpServletRequest request, @ModelAttribute BasicData bd) {
        bd.setUserid(currentUserId(request));
        return Result.success(basicService.data(bd));
    }

    /** 当前用户菜单。 */
    @PostMapping("/menu")
    public Result<?> menu(HttpServletRequest request) {
        return Result.success(basicService.menu(currentUserId(request)));
    }

    /** 当前用户模块。 */
    @PostMapping("/modu")
    public Result<?> modu(HttpServletRequest request) {
        return Result.success(basicService.modu(currentUserId(request)));
    }

    /** 当前用户按钮。 */
    @PostMapping("/butt")
    public Result<?> butt(HttpServletRequest request) {
        return Result.success(basicService.butt(currentUserId(request)));
    }

    /** 当前用户字段*/
    @PostMapping("/field")
    public Result<?> field(HttpServletRequest request) {
        return Result.success(basicService.field(currentUserId(request)));
    }



    @PostMapping("/product")
    public Result<?> product(@ModelAttribute Product product) {
        int p = product.getPageNum() == null || product.getPageNum() < 1 ? 1 : product.getPageNum();
        int s = product.getPageSize() == null || product.getPageSize() < 1 ? 10 : product.getPageSize();
        PageHelper.startPage(p, s);
        return Result.success(new PageInfo<>(basicService.product(product)));
    }

    /** 取当前登录用户的 id。 */
    private String currentUserId(HttpServletRequest request) {
        SysUser user = UserUtils.getCurrentUser(request);
        return user == null ? null : user.getId();
    }
}
