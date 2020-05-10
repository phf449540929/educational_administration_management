package com.cdu.edu;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * description: 使SpringBoot项目可以以WAR形式进行部署
 *
 * @author haifeng
 * @version 1.0
 * @date 2018/9/29 0029 下午 17:30
 * @since jdk 10.0.1
 */
public class ServletInitializer extends SpringBootServletInitializer {

    /**
     *
     * @param application 程序的入口类
     * @return org.springframework.boot.builder.SpringApplicationBuilder 用以构建war文件
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

}
