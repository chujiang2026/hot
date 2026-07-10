package com.hot.modules.hot.controller;

import com.hot.common.result.Result;
import com.hot.common.result.ResultCode;
import com.hot.common.util.DateUtils;
import com.hot.common.util.UserUtils;
import com.hot.modules.hot.service.HomeService;
import com.hot.modules.sys.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @PostMapping("/data")
    public Result<?> data(String date, HttpServletRequest request) throws Exception {
        if (date == null) {
            date = DateUtils.getDate("yyyy");
        }
        String userId = currentUser(request);
        Map<String, Object> m = homeService.data(date,userId);
        return m == null ? Result.of(ResultCode.FAIL) : Result.success(m);
    }

    @PostMapping("/cotry")
    public Result<?> cotry(String date, HttpServletRequest request) throws Exception {
        if (date == null) {
            date = DateUtils.getDate("yyyy");
        }
        String userid = currentUser(request);
        List<Map<String, Object>> m = homeService.cotry(date,userid);
        return m == null ? Result.of(ResultCode.FAIL) : Result.success(m);
    }

    @PostMapping("/trend")
    public Result<?> trend(String date, String country, HttpServletRequest request) throws Exception {
        if (date == null) {
            date = DateUtils.getDate("yyyy");
        }
        String userid = currentUser(request);
        List<Map<String, Object>> m = homeService.trend(date,country,userid);
        return m == null ? Result.of(ResultCode.FAIL) : Result.success(m);
    }

    private String currentUser(HttpServletRequest request) throws Exception {
        SysUser user = UserUtils.getCurrentUser(request);
        if (user == null) {
            throw new Exception("用户未登录");
        }
        return user.getId();
    }
}
