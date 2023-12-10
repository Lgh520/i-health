package com.project.ihealth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;

@ComponentScan("com.project.ihealth.*")
@MapperScan("com.project.ihealth.dao")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class IHealthApplicaiton {

    @RequestMapping
    public static void main(String[] args) {
        SpringApplication.run(IHealthApplicaiton.class, args);
    }

}
