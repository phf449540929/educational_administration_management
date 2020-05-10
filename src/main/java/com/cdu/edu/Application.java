package com.cdu.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * description: SpringBoot项目的启动程序
 *
 * @author haifeng
 * @version 1.0
 * @date 2018/9/29 0029 下午 17:23
 * @since jdk 10.0.1
 */
@SpringBootApplication
public class Application {

    /**
     * description: SpringBoot项目的main函数
     *
     * @param args main函数的启动参数
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
