package com.nayanzin.clientsideloadbalancer;

import com.netflix.loadbalancer.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class RibbonCLR implements CommandLineRunner {

    private final DiscoveryClient discoveryClient;

    private final Log logger = LogFactory.getLog(getClass());

    @Override
    public void run(String... args) {

        logger.info("RibbonCLR");

        String serviceId = "greeting-service";

        // Load the list of servers from the discovery client.
        List<Server> servers = discoveryClient.getInstances(serviceId)
                .stream()
                .map(si -> new Server(si.getHost(), si.getPort()))
                .collect(Collectors.toList());

        // Create load balancer with the round robin rule and fixed servers list.
        BaseLoadBalancer loadBalancer = LoadBalancerBuilder.newBuilder()
                .withRule(new RoundRobinRule())
                .buildFixedServerListLoadBalancer(servers);

        // Resolve servers 10 and log URIs.
        IntStream.range(0, 10).forEach(i -> {
            Server server = loadBalancer.chooseServer();
            URI uri = URI.create("http://" + server.getHost() + ":" + server.getPort() + "/");
            logger.info("resolved service: " + uri.toString());
        });
    }
}
