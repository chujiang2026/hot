package com.hot.modules.hot.controller;

import com.github.pagehelper.PageHelper;
import com.hot.common.excel.ExportExcel;
import com.hot.common.page.PageInfo;
import com.hot.common.result.Result;
import com.hot.common.result.ResultCode;
import com.hot.common.util.UserUtils;
import com.hot.modules.hot.entity.HotBasic;
import com.hot.modules.hot.entity.HotCoop;
import com.hot.modules.hot.entity.HotCoopExp;
import com.hot.modules.hot.entity.HotCoopReport;
import com.hot.modules.hot.service.HotCoopService;
import com.hot.modules.hot.service.HotService;
import com.hot.modules.sys.entity.SysUser;
import com.hot.modules.sys.service.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 合作邀约单
 */
@RestController
@RequestMapping("/hot/coop")
public class HotCoopController {

    @Autowired
    private HotCoopService coopService;

    @PostMapping("/get")
    public Result<?> get(@RequestParam Integer id) {
        HotCoop m = coopService.get(id);
        return m == null ? Result.of(ResultCode.FAIL) : Result.success(m);
    }

    @PostMapping("/list")
    public Result<?> list(@ModelAttribute HotCoop coop, HttpServletRequest request) throws Exception {
        int p = coop.getPageNum() == null || coop.getPageNum() < 1 ? 1 : coop.getPageNum();
        int s = coop.getPageSize() == null || coop.getPageSize() < 1 ? 10 : coop.getPageSize();
        SysUser user = currentUser(request);
        coop.setUserid(user.getId());
        PageHelper.startPage(p, s);
        return Result.success(new PageInfo<>(coopService.list(coop)));
    }

    @Autowired
    private BasicService basicService;

    @Autowired
    private HotService hotService;

    @PostMapping("/save")
    public Result<?> save(@RequestBody HotCoop coop, HttpServletRequest request) throws Exception {
        SysUser user = currentUser(request);
        coop.setCreateUser(user.getId());
        coop.setUpdateUser(user.getId());
        coop.setBdName(user.getName());
        Integer states = hotService.queryHotsIdState(coop.getHotbId());
        if (states.equals(2)) {
            return Result.of(ResultCode.FAIL, "该红人是黑名单，不能发起合作");
        }
        Integer invitId = coop.getInvitId();
        if (invitId != null) {
            Integer id = coop.getId();
            Map<String, Object> invit = coopService.isExist(invitId);
            if (invit == null) {
                return Result.of(ResultCode.FAIL,"该邀约单不存在");
            } else {
                String state = invit.get("state").toString();
                if ("2".equals(state)) {
                    return Result.of(ResultCode.FAIL,"该邀约单为系统自动关闭，无法引用");
                } else {
                    Integer coopId = invit.get("id") == null ? null : Integer.parseInt(invit.get("id").toString());
                    if (coopId != null) {
                        if (id == null || !coopId.equals(id)) {
                            return Result.of(ResultCode.FAIL,"该邀约单已引用生成合作单");
                        }
                    }
                }
            }
        }
        if (coop.getDocNum() == null || coop.getDocNum().trim().length() == 0) {
            coop.setDocNum("COOP"+basicService.getserial("hot_coop"));
        }
        return coopService.save(coop) > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }

    /**
     * 校验邀约单是否存在合作单
     * 1.缴约单已关闭，不能被引用生成合作单。
     * 2.同一个邀约单，只能被引用一次，生成合作单，不能被多次引用生成合作单
     * @return
     */

    @PostMapping("/isExist")
    public Result<?> isExist(Integer id, @RequestParam Integer invitId) {
        Map<String, Object> map = new HashMap<>();
        map.put("isExist", false);
        Map<String, Object> invit = coopService.isExist(invitId);
        if (invit == null) {
            map.put("isExist", true);
            map.put("msg", "该邀约单不存在");
        } else {
            String state = invit.get("state").toString();
            if ("2".equals(state)) {
                map.put("isExist", true);
                map.put("msg", "该邀约单为系统自动关闭，无法引用");
            } else {
                Integer coopId = invit.get("id") == null ? null : Integer.parseInt(invit.get("id").toString());
                if (coopId != null) {
                    if (id == null || !coopId.equals(id)) {
                        map.put("isExist", true);
                        map.put("msg", "该邀约单已引用生成合作单");
                    }
                }
            }
        }
        return Result.success(map);
    }

    @PostMapping("/delete")
    public Result<?> delete(@RequestParam Integer id, HttpServletRequest request) throws Exception {
        SysUser user = currentUser(request);
        return coopService.delete(id, user.getId()) > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }

    @PostMapping("/close")
    public Result<?> close(@RequestParam String[] ids, HttpServletRequest request) throws Exception {
        SysUser user = currentUser(request);
        String msg = coopService.queryCreativ(ids);
        if (msg != null) {
            return Result.of(ResultCode.FAIL, "合作单：" + msg + " 的Content Creativity Score字段未填写，单据不允许关闭");
        }
        return coopService.close(ids, user.getId()) > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }

    @RequestMapping(value = "exp")
    public void exp(HttpServletResponse response, HttpServletRequest request, @ModelAttribute HotCoop coop) throws Exception {
        try {
            SysUser user = currentUser(request);
            coop.setUserid(user.getId());
            String fileName = "红人合作.xlsx";
            List<HotCoopExp> list = coopService.listexp(coop);
            new ExportExcel(user.getId(),null, HotCoopExp.class, 2).setDataList(list).write(response, fileName).dispose();
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write("{\"code\":300,\"msg\":\"导出失败\"}");
        }
    }

    //合作单表报
    @PostMapping("/coop")
    public Result<?> coop(@ModelAttribute HotCoopReport coop, HttpServletRequest request) throws Exception {
        int p = coop.getPageNum() == null || coop.getPageNum() < 1 ? 1 : coop.getPageNum();
        int s = coop.getPageSize() == null || coop.getPageSize() < 1 ? 10 : coop.getPageSize();
        SysUser user = currentUser(request);
        coop.setUserid(user.getId());
        PageHelper.startPage(p, s);
        return Result.success(new PageInfo<>(coopService.coop(coop)));
    }

    @RequestMapping(value = "coopexp")
    public void coopexp(HttpServletResponse response, HttpServletRequest request, @ModelAttribute HotCoopReport coop) throws Exception {
        try {
            SysUser user = currentUser(request);
            coop.setUserid(user.getId());
            String fileName = "合作单表报.xlsx";
            List<HotCoopExp> list = coopService.coopexp(coop);
            new ExportExcel(user.getId(),null, HotCoopExp.class, 2).setDataList(list).write(response, fileName).dispose();
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
