package com.nayanzin.routingeurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class RoutingEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoutingEurekaServerApplication.class, args);
    }

}
