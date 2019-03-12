package com.nayanzin.clientsideloadbalancer;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

import static java.util.Objects.nonNull;

@Component
public class LoadBalancedRestTemplateCLR implements CommandLineRunner {

    private final RestTemplate restTemplate;

    private Log logger = LogFactory.getLog(getClass());

    // We use the same @LoadBalanced qualifier annotation at both the bean producer and injection site.
    @Autowired
    public LoadBalancedRestTemplateCLR(@LoadBalanced RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void run(String... args) {

        logger.info("LoadBalancedRestTemplateCLR");

        // Generate test data.
        Map<String, String> testData = Collections.singletonMap("name", "Cloud Natives!");

        ResponseEntity<JsonNode> response = restTemplate.getForEntity(
                "//greeting-service/hi/{name}", JsonNode.class, testData);

        JsonNode body = response.getBody();
        JsonNode greetingNode = body.get("greeting");
        if (nonNull(greetingNode)) {
            logger.info("greeting: " + greetingNode.asText());
        }

    }
}
