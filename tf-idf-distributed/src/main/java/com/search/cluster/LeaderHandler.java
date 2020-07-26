package com.search.cluster;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.integration.leader.event.OnFailedToAcquireMutexEvent;
import org.springframework.integration.leader.event.OnGrantedEvent;
import org.springframework.integration.leader.event.OnRevokedEvent;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class LeaderHandler implements Watcher {

    @Autowired
    Environment env;

    @Autowired
    CuratorFramework client;

    String LEADER_REGISTRY = "/leader";


    @EventListener(OnGrantedEvent.class)
    public void start() throws UnknownHostException {

        String address = String.format("http://%s:%s", InetAddress.getLocalHost().getCanonicalHostName(), env.getProperty("server.port"));
        System.out.println("I'm the leader: " + address);

        try {
            if(client.checkExists().forPath(LEADER_REGISTRY) == null) {
                String result = client.create().forPath(LEADER_REGISTRY);
            }
            client.setData().forPath(LEADER_REGISTRY,address.getBytes());
        } catch (Exception e) {
            System.out.println("Error when create leader znode: " + e.getMessage());
        }
    }


    @EventListener(OnRevokedEvent.class)
    public void stop() throws UnknownHostException {
        String address = String.format("http://%s:%s", InetAddress.getLocalHost().getCanonicalHostName(), env.getProperty("server.port"));
        System.out.println("revoke leadership: " + address);

    }

    @Override
    public void process(WatchedEvent event) {
        System.out.println("Watch: " + event.toString());
    }
}
