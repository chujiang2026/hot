package com.hot.modules.hot.controller;

import com.github.pagehelper.PageHelper;
import com.hot.common.excel.ExportExcel;
import com.hot.common.excel.ImportExcel;
import com.hot.common.page.PageInfo;
import com.hot.common.result.Result;
import com.hot.common.result.ResultCode;
import com.hot.common.util.UserUtils;
import com.hot.modules.hot.entity.Hot;
import com.hot.modules.hot.entity.HotBasic;
import com.hot.modules.hot.entity.HotTarget;
import com.hot.modules.hot.service.HotTargetService;
import com.hot.modules.sys.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 红人合作目标控制器
 */
@RestController
@RequestMapping("/hot/target")
public class HotTargetController {

    @Autowired
    private HotTargetService hotTargetService;

    /**
     * 根据ID查询单条记录
     * @param id 主键ID
     * @return 查询结果
     */
    @PostMapping("/get")
    public Result<?> get(@RequestParam Integer id) {
        HotTarget target = hotTargetService.get(id);
        return target == null ? Result.of(ResultCode.FAIL) : Result.success(target);
    }

    /**
     * 分页查询列表
     * @param hotTarget 查询条件
     * @return 分页结果
     */
    @PostMapping("/list")
    public Result<?> list(@ModelAttribute HotTarget hotTarget, HttpServletRequest request) throws Exception {
        int pageNum = hotTarget.getPageNum() == null || hotTarget.getPageNum() < 1 ? 1 : hotTarget.getPageNum();
        int pageSize = hotTarget.getPageSize() == null || hotTarget.getPageSize() < 1 ? 10 : hotTarget.getPageSize();
        String userid = currentUser(request);
        hotTarget.setUserid(userid);
        PageHelper.startPage(pageNum, pageSize);
        return Result.success(new PageInfo<>(hotTargetService.list(hotTarget)));
    }

    /**
     * 新增或更新记录
     * @param hotTarget 红人合作目标对象
     * @param request HTTP请求对象
     * @return 操作结果
     */
    @PostMapping("/save")
    public Result<?> save(@RequestBody HotTarget hotTarget, HttpServletRequest request) throws Exception {
        String userid = currentUser(request);
        hotTarget.setUserid(userid);
        return hotTargetService.save(hotTarget) > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }

    /**
     * 删除记录
     * @param id 主键ID
     * @param request HTTP请求对象
     * @return 操作结果
     */
    @PostMapping("/delete")
    public Result<?> delete(@RequestParam Integer id, HttpServletRequest request) throws Exception {
        String userid = currentUser(request);
        return hotTargetService.delete(id, userid) > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }

    @RequestMapping(value = "exp")
    public void exp(HttpServletResponse response, HttpServletRequest request, @ModelAttribute HotTarget hotTarget) throws IOException {
        try {
            String userid = currentUser(request);
            hotTarget.setUserid(userid);
            String fileName = "红人合作目标.xlsx";
            List<HotTarget> list = hotTargetService.list(hotTarget);
            new ExportExcel(userid,null, HotTarget.class, 2).setDataList(list).write(response, fileName).dispose();
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write("{\"code\":300,\"msg\":\"导出失败\"}");
        }
    }

    @RequestMapping(value = "temp")
    public void temp(HttpServletResponse response,HttpServletRequest request) throws IOException {
        try {
            String userid = currentUser(request);
            String fileName = "红人合作目标导入模板.xlsx";
            List<HotTarget> list = new ArrayList<>();
            new ExportExcel(userid,null, HotTarget.class, 2).setDataList(list).write(response, fileName).dispose();
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write("{\"code\":300,\"msg\":\"导出失败\"}");
        }
    }

    @RequestMapping(value = "imp")
    public Result<?> imp(HttpServletResponse response, HttpServletRequest request, MultipartFile file) throws Exception {

        response.reset();
        response.addHeader("Access-Control-Allow-Origin", "*");// 解决ajax 跨域
        response.addHeader("Access-Control-Allow-Methods", "POST,GET");
        response.addHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
        response.setCharacterEncoding("utf-8");

        String userid = currentUser(request);
        int successNum = 0;
        StringBuilder failureMsg = new StringBuilder();
        ImportExcel ei = new ImportExcel(userid, file, 1, 0);
        List<HotTarget> list = ei.getDataList(HotTarget.class);
        for (int i = 0; i < list.size(); i++) {
            HotTarget h = list.get(i);
            String channel = h.getChannel();
            if (channel == null || channel.trim().length() == 0) {
                failureMsg.append("第" + (i + 2) + "行，渠道不能为空;");
                continue;
            } else if (!channel.equals("YouTube") && !channel.equals("Instagram") && !channel.equals("TikTok")) {
                failureMsg.append("第" + (i + 2) + "行，渠道填写不正确。渠道只能为YouTube、Instagram、TikTok;");
                continue;
            }
            // 国家
            if (h.getCountry() == null || h.getCountry().trim().length() == 0) {
                failureMsg.append("第" + (i + 2) + "行，国家不能为空");
                continue;
            }
            // 红人类型
            if (h.getHotType() == null) {
                failureMsg.append("第" + (i + 2) + "行，红人类型为空或填写错误");
                continue;
            }
            // 红人量级
            if (h.getHotLevel() == null) {
                failureMsg.append("第" + (i + 2) + "行，红人量级为空或填写错误");
                continue;
            }
            // 视频类型
            if (h.getVideoType() == null) {
                failureMsg.append("第" + (i + 2) + "行，视频类型为空或填写错误");
                continue;
            }
            //判断是否已存在
            Integer id = hotTargetService.getTarget(h);
            if (id != null) {
                h.setId(id);
            }
            h.setCreateUser(userid);
            h.setUpdateUser(userid);
            int count = hotTargetService.save(h);
            if (count > 0) {
                successNum++;
            }
        }
        if (successNum > 0) {
            failureMsg.insert(0, "共" + list.size() + "条数据，成功" + successNum + "条，失败" + (list.size() - successNum) + "条。");
        } else {
            failureMsg.insert(0, "共" + list.size() + "条数据，全部失败。");
        }
        return Result.success(failureMsg);
    }

    /**
     * 获取当前登录用户信息
     * @param request HTTP请求对象
     * @return 当前用户对象
     * @throws Exception 用户未登录异常
     */
    private String currentUser(HttpServletRequest request) throws Exception {
        SysUser user = UserUtils.getCurrentUser(request);
        if (user == null) {
            throw new Exception("用户未登录");
        }
        return user.getId();
    }

}
