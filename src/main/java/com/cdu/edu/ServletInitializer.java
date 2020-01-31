package com.cdu.edu;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * description: the ServletInitializer of SpringBoot
 *
 * @author haifeng
 * @version 1.0
 * @date 2018/9/29 0029 下午 17:30
 * @since jdk
 */
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

}
