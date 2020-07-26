package com.search.controller;

import com.search.model.LeaderResult;
import com.search.service.LeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.integration.zookeeper.config.LeaderInitiatorFactoryBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class LeaderController {

    @Autowired
    private LeaderInitiatorFactoryBean leaderInitiatorFactoryBean;

    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    LeaderService leaderService;

    @RequestMapping("/is_leader")
    public boolean isLeader() {
        try {
            return leaderInitiatorFactoryBean.getObject().getContext().isLeader();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping("/search")
    public LeaderResult getResult(@RequestParam String term) {
        LeaderResult result = leaderService.search(term);
        return result;
    }

    @RequestMapping("/clients")
    public String serviceUrl() {
        List<ServiceInstance> list = discoveryClient.getInstances("application");
        if(list == null) return null;
        List<String> services = list.stream().map(instance -> instance.getUri().toString()).collect(Collectors.toList());
        return services.toString();
    }





}
