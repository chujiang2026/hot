package com.hot.modules.hot.controller;

import com.github.pagehelper.PageHelper;
import com.hot.common.excel.ExportExcel;
import com.hot.common.excel.ImportExcel;
import com.hot.common.page.PageInfo;
import com.hot.common.result.Result;
import com.hot.common.result.ResultCode;
import com.hot.common.util.BasicUtils;
import com.hot.common.util.DateUtils;
import com.hot.common.util.UserUtils;
import com.hot.modules.hot.entity.Hot;
import com.hot.modules.hot.entity.HotBasic;
import com.hot.modules.hot.entity.HotCoop;
import com.hot.modules.hot.entity.HotInvit;
import com.hot.modules.hot.service.HotService;
import com.hot.modules.sys.entity.BasicData;
import com.hot.modules.sys.entity.SysUser;
import com.hot.modules.sys.service.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/hot")
public class HotController {

    @Autowired
    private HotService hotService;

    @PostMapping("/get")
    public Result<?> get(@RequestParam Integer id) {
        Hot m = hotService.get(id);
        return m == null ? Result.of(ResultCode.FAIL) : Result.success(m);
    }

    @PostMapping("/list")
    public Result<?> list(@ModelAttribute Hot hot, HttpServletRequest request) throws Exception {
        int p = hot.getPageNum() == null || hot.getPageNum() < 1 ? 1 : hot.getPageNum();
        int s = hot.getPageSize() == null || hot.getPageSize() < 1 ? 10 : hot.getPageSize();
        SysUser user = currentUser(request);
        hot.setUserid(user.getId());
        PageHelper.startPage(p, s);
        return Result.success(new PageInfo<>(hotService.list(hot)));
    }

    @PostMapping("/save")
    public Result<?> save(@RequestBody Hot hot, HttpServletRequest request) throws Exception {
        SysUser user = currentUser(request);
        hot.setCreateUser(user.getId());
        hot.setUpdateUser(user.getId());

        List<Map<String, Object>> data = BasicUtils.getDictList(user.getId(),"hot_type");

        HotBasic basicyt = hot.getBasicyt();
        if (basicyt != null) {
            basicyt.setIsHotp(2);
            String hotType = basicyt.getHotType();
            if (hotType != null || hotType.trim().length() > 0) {
                String[] ht = hotType.split(",");
                Integer[] hotTypeArr = new Integer[ht.length];
                for (int j = 0; j < ht.length; j++) {
                    String hott = ht[j];
                    for (Map<String, Object> d : data) {
                        String id = d.get("id") == null ? "" : d.get("id").toString();
                        String name = d.get("name") == null ? "" : d.get("name").toString();
                        if (id.equals(hott)) {
                            hotTypeArr[j] = Integer.parseInt(d.get("id").toString());
                            //hott 转小写
                            name = name.toLowerCase();
                            //判断hott是否包含这个字符串“tech”
                            if (name.contains("tech")) {
                                basicyt.setIsHotp(1);
                            }
                        }
                    }
                }
                basicyt.setHotTypeArr(hotTypeArr);
            }
        }
        HotBasic basicig = hot.getBasicig();
        if (basicig != null) {
            basicig.setIsHotp(2);
            String hotType = basicig.getHotType();
            if (hotType != null || hotType.trim().length() > 0) {
                String[] ht = hotType.split(",");
                Integer[] hotTypeArr = new Integer[ht.length];
                for (int j = 0; j < ht.length; j++) {
                    String hott = ht[j];
                    for (Map<String, Object> d : data) {
                        String id = d.get("id") == null ? "" : d.get("id").toString();
                        String name = d.get("name") == null ? "" : d.get("name").toString();
                        if (id.equals(hott)) {
                            hotTypeArr[j] = Integer.parseInt(d.get("id").toString());
                            //hott 转小写
                            name = name.toLowerCase();
                            //判断hott是否包含这个字符串“tech”
                            if (name.contains("tech")) {
                                basicig.setIsHotp(1);
                            }
                        }
                    }
                }
                basicig.setHotTypeArr(hotTypeArr);
            }
        }
        HotBasic basictk = hot.getBasictk();
        if (basictk != null) {
            basictk.setIsHotp(2);
            String hotType = basictk.getHotType();
            if (hotType != null || hotType.trim().length() > 0) {
                String[] ht = hotType.split(",");
                Integer[] hotTypeArr = new Integer[ht.length];
                for (int j = 0; j < ht.length; j++) {
                    String hott = ht[j];
                    for (Map<String, Object> d : data) {
                        String id = d.get("id") == null ? "" : d.get("id").toString();
                        String name = d.get("name") == null ? "" : d.get("name").toString();
                        if (id.equals(hott)) {
                            hotTypeArr[j] = Integer.parseInt(d.get("id").toString());
                            //hott 转小写
                            name = name.toLowerCase();
                            //判断hott是否包含这个字符串“tech”
                            if (name.contains("tech")) {
                                basictk.setIsHotp(1);
                            }
                        }
                    }
                }
                basictk.setHotTypeArr(hotTypeArr);
            }
        }

        return hotService.save(hot,user.getId()) > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }

    @PostMapping("/state")
    public Result<?> state(@ModelAttribute Hot hot, HttpServletRequest request) throws Exception {
        SysUser user = currentUser(request);
        hot.setCreateUser(user.getId());
        hot.setUpdateUser(user.getId());
        return hotService.state(hot) > 0 ? Result.success() : Result.of(ResultCode.FAIL);
    }

    @PostMapping("/check")
    public Result<?> check(@ModelAttribute Hot hot) {
        Boolean b = true;
        Integer id = hot.getId();
        String hotsid = hot.getHotsId();
        String channel = hot.getChannel();
        HotBasic m = hotService.getBasic(hotsid,channel);
        if (m == null) {
            b = false;
        } else {
            if (id != null && id > 0) {
                if (!id.equals(m.getId())) {
                    b = false;
                }
            } else {
                b = false;
            }
        }
        return Result.success(b);
    }

    @RequestMapping(value = "exp")
    public void exp(HttpServletResponse response, HttpServletRequest request, @ModelAttribute Hot coop) throws IOException {
        try {
            SysUser user = currentUser(request);
            coop.setUserid(user.getId());
            String fileName = "红人库列表.xlsx";
            List<HotBasic> list = hotService.expList(coop);
            new ExportExcel(user.getId(),null, HotBasic.class, 2).setDataList(list).write(response, fileName).dispose();
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
            SysUser user = currentUser(request);
            String fileName = "红人库-红人信息导入模板.xlsx";
            List<HotBasic> list = new ArrayList<>();
            new ExportExcel(user.getId(),null, HotBasic.class, 2).setDataList(list).write(response, fileName).dispose();
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

        SysUser user = currentUser(request);
        int successNum = 0;
        StringBuilder failureMsg = new StringBuilder();
        ImportExcel ei = new ImportExcel(user.getId(),file, 1, 0);
        List<HotBasic> list = ei.getDataList(HotBasic.class);
        List<HotBasic> iBasic = new ArrayList<>();
        List<Map<String, Object>> data = BasicUtils.getDictList(user.getId(),"hot_type");
        for (int i = 0; i < list.size(); i++) {
            HotBasic h = list.get(i);
            h.setHotType(h.getHotTypes());
            String channel = h.getChannel();
            if (channel == null || channel.trim().length() == 0) {
                failureMsg.append("第" + (i + 2) + "行，渠道不能为空;");
                continue;
            } else if (!channel.equals("YouTube") && !channel.equals("Instagram") && !channel.equals("TikTok")) {
                failureMsg.append("第" + (i + 2) + "行，渠道填写不正确。渠道只能为YouTube、Instagram、TikTok;");
                continue;
            }
            String hotsId = h.getHotsId();
            if (hotsId == null || hotsId.trim().length() == 0) {
                failureMsg.append("第" + (i + 2) + "行，红人ID不能为空;");
                continue;
            }

            h.setIsHotp(2);
            String hotTypes = h.getHotTypes();
            if (hotTypes != null || hotTypes.trim().length() > 0) {
                String[] hotType = hotTypes.split(",");
                Integer[] hotTypeArr = new Integer[hotType.length];
                for (int j = 0; j < hotType.length; j++) {
                    String hott = hotType[j];
                    for (Map<String, Object> d : data) {
                        String name = d.get("name") == null ? "" : d.get("name").toString();
                        if (name.equals(hott)) {
                            hotTypeArr[j] = Integer.parseInt(d.get("id").toString());
                        }
                    }
                    //hott 转小写
                    hott = hott.toLowerCase();
                    //判断hott是否包含这个字符串“tech”
                    if (hott.contains("tech")) {
                        h.setIsHotp(1);
                    }
                }
                h.setHotTypeArr(hotTypeArr);
            }

            HotBasic m = hotService.getBasic(hotsId,channel);
            if (m != null && m.getId() > 0) {
                h.setId(m.getId());
                h.setUpdateUser(user.getId());
                int u = hotService.updateBasic(h,user.getId());
                successNum = successNum + u;
            } else {
                h.setCreateUser(user.getId());
                h.setUpdateUser(user.getId());
                iBasic.add(h);
            }
        }

        if (iBasic.size() > 0) {
            //通过hotsId,分组iBasic
            Map<String, List<HotBasic>> map = iBasic.stream().collect(Collectors.groupingBy(HotBasic::getHotsId));
            for (String key : map.keySet()) {
                Hot t = new Hot();
                t.setName(key);
                t.setArchName(key);
                t.setCreateUser(user.getId());
                t.setUpdateUser(user.getId());
                List<HotBasic> list1 = map.get(key);
                int num = 0;
                for (HotBasic hb : list1) {
                    switch (hb.getChannel()) {
                        case "YouTube":
                            t.setBasicyt(hb);
                            num++;
                            break;
                        case "Instagram":
                            t.setBasicig(hb);
                            num++;
                            break;
                        case "TikTok":
                            t.setBasictk(hb);
                            num++;
                            break;
                    }
                }
                int i = hotService.save(t, user.getId());
                if (i > 0) {
                    successNum = successNum + num;
                }
            }
        }
        if (successNum > 0) {
            failureMsg.insert(0, "共" + list.size() + "条数据，成功" + successNum + "条，失败" + (list.size() - successNum) + "条。");
        } else {
            failureMsg.insert(0, "共" + list.size() + "条数据，全部失败。");
        }
        return Result.success(failureMsg);
    }

    @PostMapping("/invit")
    public Result<?> invit(@ModelAttribute HotInvit invit, HttpServletRequest request) throws Exception {
        int p = invit.getPageNum() == null || invit.getPageNum() < 1 ? 1 : invit.getPageNum();
        int s = invit.getPageSize() == null || invit.getPageSize() < 1 ? 10 : invit.getPageSize();
        SysUser user = currentUser(request);
        invit.setUserid(user.getId());
        PageHelper.startPage(p, s);
        return Result.success(new PageInfo<>(hotService.invit(invit)));
    }

    @PostMapping("/coop")
    public Result<?> coop(@ModelAttribute HotCoop coop, HttpServletRequest request) throws Exception {
        int p = coop.getPageNum() == null || coop.getPageNum() < 1 ? 1 : coop.getPageNum();
        int s = coop.getPageSize() == null || coop.getPageSize() < 1 ? 10 : coop.getPageSize();
        SysUser user = currentUser(request);
        coop.setUserid(user.getId());
        PageHelper.startPage(p, s);
        return Result.success(new PageInfo<>(hotService.coop(coop)));
    }

    private SysUser currentUser(HttpServletRequest request) throws Exception {
        SysUser user = UserUtils.getCurrentUser(request);
        if (user == null) {
            throw new Exception("用户未登录");
        }
        return user;
    }
}
