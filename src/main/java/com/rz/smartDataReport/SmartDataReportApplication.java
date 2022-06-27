package com.rz.smartDataReport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.config.annotation.web.server.EnableSpringWebSession;

@SpringBootApplication
public class SmartDataReportApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartDataReportApplication.class, args);
    }

}
