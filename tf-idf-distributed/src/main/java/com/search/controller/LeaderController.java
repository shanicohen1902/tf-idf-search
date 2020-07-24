package com.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.zookeeper.config.LeaderInitiatorFactoryBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LeaderController {

    @Autowired
    private LeaderInitiatorFactoryBean leaderInitiatorFactoryBean;

    @RequestMapping("/is_leader")
    public boolean isLeader() {
        try {
            return leaderInitiatorFactoryBean.getObject().getContext().isLeader();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
