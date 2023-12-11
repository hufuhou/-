package com.cx.ledger;

import com.ruoyi.common.security.annotation.EnableCustomConfig;
import com.ruoyi.common.security.annotation.EnableRyFeignClients;
import com.ruoyi.common.swagger.annotation.EnableCustomSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@EnableCustomConfig
@EnableCustomSwagger2
@EnableRyFeignClients
@SpringBootApplication
public class CxLedgerApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(CxLedgerApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  台账模块启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                "_________ ____  ___\n" +
                "\\_   ___ \\\\   \\/  /\n" +
                "/    \\  \\/ \\     / \n" +
                "\\     \\____/     \\ \n" +
                " \\______  /___/\\  \\\n" +
                "        \\/      \\_/\n" +
                "Have fun. :)\n");
    }
}
