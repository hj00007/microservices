package com.edureka.ecomm.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edureka.ecomm.service.SubService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/subservice")
public class SubServiceController {

    @Autowired
    private SubService orderService;

    public SubServiceController(SubService orderService) {
        this.orderService = orderService;
    }
    
    @GetMapping("/dashboard/{userId}")
    public Mono<ResponseEntity<Map<String, Object>>> getDashboard(@PathVariable String userId) {
        return orderService.getDashboardData(userId)
                .map(response -> ResponseEntity.ok().body(response));
    }


   
}
