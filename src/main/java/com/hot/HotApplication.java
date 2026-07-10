package com.hot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableScheduling
@SpringBootApplication
@EnableTransactionManagement
public class HotApplication extends SpringBootServletInitializer {

    static {
        // 服务器（Linux 无 X11 显示）导出 Excel 时，POI 的 autoSizeColumn 会触发 AWT 字体测量，
        // 若未开启 headless 会尝试初始化 sun.awt.X11FontManager 而报错。必须在任何 AWT 类加载前设置。
        System.setProperty("java.awt.headless", "true");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(HotApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(HotApplication.class, args);
    }
}
