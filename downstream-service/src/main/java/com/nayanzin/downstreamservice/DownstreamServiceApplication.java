package com.nayanzin.downstreamservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@EnableDiscoveryClient
@SpringBootApplication
public class DownstreamServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DownstreamServiceApplication.class, args);
    }

}

@RestController
class GreetingRestController {
    @GetMapping("/hi/{name}")
    Map<String, String> hi(
            @PathVariable String name,
            @RequestHeader(value = "X-CNJ-Name", required = false) Optional<String> cn) {
        String resolvedName = cn.orElse(name);
        return Collections.singletonMap("greeting", "Hello, " + resolvedName + "!");
    }
}
