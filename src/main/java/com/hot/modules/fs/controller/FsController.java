package com.hot.modules.fs.controller;

import com.hot.modules.fs.service.FsService;
import com.hot.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/fs")
public class FsController {

    @Autowired
    private FsService fsService;

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("/init")
    public String init(Model model){
        String url = fsService.init();
        model.addAttribute("fsUrl", url);
        return "jump";
    }

    @RequestMapping("/user")
    public String user(Model model,String code) throws Exception {
        String userToken = fsService.getAccessToken(code);
        String phone = fsService.getPhone(userToken);
        if (phone != null) {
            phone = phone.replaceAll("\\+86", "");
        }
        String token = sysUserService.fslogin(phone);
        model.addAttribute("token", token);
        return "index";
    }

}
