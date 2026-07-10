package com.hot.modules.sys.task;

import com.hot.modules.hot.service.HotInvitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 */
@Component
public class HotTask {

    @Autowired
    private HotInvitService invitService;
    /**
     * 关闭已建联未回复的邀约单
     */
    @Scheduled(cron="0 30 0 * * ? ")//每天0点30分执行一次
    public void closeInvit() {
        invitService.closeInvit();
    }
}
