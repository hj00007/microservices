package com.edureka.ecomm.service;


import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import reactor.core.publisher.Mono;

@Service
public class SubService {

    private RestTemplate restTemplate;

    public Logger logger = LogManager.getLogger(SubService.class);

    private final WebClient webClient = WebClient.create();
    
    @CircuitBreaker(name = "dashboardService", fallbackMethod = "dashboardServiceFallback")
	public  Mono<Map<String, Object>> getDashboardData(String userId) {

        Mono<String> orderHistory = webClient.get()
                .uri("http://localhost:8092/api/v1/orders/customer/" + userId)
                .retrieve()
                .bodyToMono(String.class);

        Mono<String> userDetails = webClient.get()
                .uri("http://localhost:8222/api/v1/customers/" + userId)
                .retrieve()
                .bodyToMono(String.class);

        return Mono.zip( orderHistory, userDetails)
                .map(tuple -> {
                    Map<String, Object> aggregatedResult = new HashMap<>();
                    aggregatedResult.put("orderHistory", tuple.getT1());
                    aggregatedResult.put("userDetails", tuple.getT2());
                    return aggregatedResult;
                });
    
	}

    // Fallback method
    public String dashboardServiceFallback(String userId, Throwable throwable) {
        return "Dashboard service is currently unavailable for user ID: " + userId;
    }

	    

  
}
