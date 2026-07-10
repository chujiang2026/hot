package com.hot.modules.hot.controller;

import com.github.pagehelper.PageHelper;
import com.hot.common.excel.ExportExcel;
import com.hot.common.page.PageInfo;
import com.hot.common.result.Result;
import com.hot.common.result.ResultCode;
import com.hot.common.util.UserUtils;
import com.hot.modules.hot.entity.HotInvit;
import com.hot.modules.hot.entity.HotInvitReport;
import com.hot.modules.hot.service.HotInvitService;
import com.hot.modules.hot.service.HotService;
import com.hot.modules.sys.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 红人邀约单
 */
@RestController
@RequestMapping("/hot/invit")
public class HotInvitController {

    @Autowired
    private HotInvitService invitService;

    @PostMapping("/get")
    public Result<?> get(@RequestParam Integer id) {
        HotInvit m = invitService.get(id);
        return m == null ? Result.of(ResultCode.FAIL) : Result.success(m);
    }

    @PostMapping("/list")
    public Result<?> list(@ModelAttribute HotInvit invit, HttpServletRequest request) throws Exception {
        int p = invit.getPageNum() == null || invit.getPageNum() < 1 ? 1 : invit.getPageNum();
        int s = invit.getPageSize() == null || invit.getPageSize() < 1 ? 10 : invit.getPageSize();
        SysUser user = currentUser(request);
        invit.setUserid(user.getId());
        PageHelper.startPage(p, s);
        return Result.success(new PageInfo<>(invitService.list(invit)));
    }

    //校验红人Id是否存在邀约单
    @PostMapping("/isExist")
    public Result<?> isExist(@RequestParam String[] hotsIds ) {
        Map<String, Object> map = new HashMap<>();
        map.put("isExist", false);
        List<String> hotsId = invitService.isExist(hotsIds);
        if (hotsId.size() > 0) {
            map.put("isExist", true);
            map.put("hotsIds", hotsId);
        }
        return Result.success(map);
    }

    @Autowired
    private HotService hotService;

    @PostMapping("/save")
    public Result<?> save(@RequestBody HotInvit invit, HttpServletRequest request) throws Exception {
        SysUser user = currentUser(request);
        List<HotInvit> list = invit.getHotsList();
        if (list == null || list.size() < 1) {
            throw new Exception("红人ID不能为空");
        }
        invit.setCreateUser(user.getId());
        invit.setUpdateUser(user.getId());
        invit.setBdName(user.getName());
        for (HotInvit hots : list) {
            Integer state = hotService.queryHotsIdState(hots.getHotbId());
            if (state.equals(2)) {
                return Result.of(ResultCode.FAIL, "该红人是黑名单，不能发起邀约");
            }
        }
        return invitService.save(invit,user.getId()) > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }

    @PostMapping("/delete")
    public Result<?> delete(@RequestParam Integer id, HttpServletRequest request) throws Exception {
        SysUser user = currentUser(request);
        return invitService.delete(id,user.getId()) > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }

    @PostMapping("/close")
    public Result<?> close(@RequestParam String[] ids, String reason, HttpServletRequest request) throws Exception {
        SysUser user = currentUser(request);
        return invitService.close(ids, user.getId(), reason) > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }

    @RequestMapping(value = "exp")
    public void exp(HttpServletResponse response,HttpServletRequest request,@ModelAttribute HotInvit invit) throws IOException {
        try {
            SysUser user = currentUser(request);
            String fileName = "红人邀约单.xlsx";
            List<HotInvitReport> list = invitService.listexp(invit);
            new ExportExcel(user.getId(),null, HotInvitReport.class, 2).setDataList(list).write(response, fileName).dispose();
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write("{\"code\":300,\"msg\":\"导出失败\"}");
        }
    }


    // 邀约单报表
    @PostMapping("/invit")
    public Result<?> invit(@ModelAttribute HotInvitReport invit, HttpServletRequest request) throws Exception {
        int p = invit.getPageNum() == null || invit.getPageNum() < 1 ? 1 : invit.getPageNum();
        int s = invit.getPageSize() == null || invit.getPageSize() < 1 ? 10 : invit.getPageSize();
        SysUser user = currentUser(request);
        invit.setUserid(user.getId());
        PageHelper.startPage(p, s);
        return Result.success(new PageInfo<>(invitService.invit(invit)));
    }

    @RequestMapping(value = "invitexp")
    public void invitexp(HttpServletResponse response, HttpServletRequest request, @ModelAttribute HotInvitReport invit) throws IOException {
        try {
            SysUser user = currentUser(request);
            invit.setUserid(user.getId());
            String fileName = "邀约单表报.xlsx";
            List<HotInvitReport> list = invitService.invitexp(invit);
            new ExportExcel(user.getId(),null, HotInvitReport.class, 2).setDataList(list).write(response, fileName).dispose();
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write("{\"code\":300,\"msg\":\"导出失败\"}");
        }
    }

    private SysUser currentUser(HttpServletRequest request) throws Exception {
        SysUser user = UserUtils.getCurrentUser(request);
        if (user == null) {
            throw new Exception("用户未登录");
        }
        return user;
    }


}
