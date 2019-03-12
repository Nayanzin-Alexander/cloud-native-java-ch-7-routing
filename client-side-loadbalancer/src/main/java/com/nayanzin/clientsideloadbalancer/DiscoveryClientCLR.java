package com.nayanzin.clientsideloadbalancer;

import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiscoveryClientCLR implements CommandLineRunner {

    private final DiscoveryClient discoveryClient;

    private Log logger = LogFactory.getLog(getClass());

    @Override
    public void run(String... args) {

        logger.info("DiscoveryClientCLR");

        logger.info("localServiceInstance");
        discoveryClient.getInstances("greeting-service")
                .forEach(this::logServiceInstance);
    }

    private void logServiceInstance(ServiceInstance si) {
        String msg  = String.format("host = %s, port = %s, service ID = %s",
                si.getHost(), si.getPort(), si.getServiceId());
        logger.info(msg);
    }

}
