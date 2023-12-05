package com.cx.wms;

import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;
import com.ruoyi.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
public class CxChartApplication {

    public static void main(String[] args) {
        SpringApplication.run(CxChartApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  Cx统计报表系统模块启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                "_________ ____  ___\n" +
                "\\_   ___ \\\\   \\/  /\n" +
                "/    \\  \\/ \\     / \n" +
                "\\     \\____/     \\ \n" +
                " \\______  /___/\\  \\\n" +
                "        \\/      \\_/\n" +
                "Have fun. :)\n");
    }
}

