package com.cx;

import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;
import com.ruoyi.common.swagger.annotation.EnableCustomSwagger2;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *  系统模块
 *
 * @author ruoyi
 */
@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
public class CxOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(CxOrderApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  创鑫WMS订单模块启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                "_________ ____  ___\n" +
                "\\_   ___ \\\\   \\/  /\n" +
                "/    \\  \\/ \\     / \n" +
                "\\     \\____/     \\ \n" +
                " \\______  /___/\\  \\\n" +
                "        \\/      \\_/\n" +
                "Have fun  :)\n");
    }
}
