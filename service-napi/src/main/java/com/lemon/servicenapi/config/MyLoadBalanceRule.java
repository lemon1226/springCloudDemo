package com.lemon.servicenapi.config;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * description: 负载均衡-轮询规则
 *
 * @author lemon
 * @date 2019-07-09 10:38:06 创建
 */
public class MyLoadBalanceRule implements IRule {

    private ILoadBalancer lb;

    private AtomicInteger counter;

    public MyLoadBalanceRule() {
        counter = new AtomicInteger(0);
    }

    @Override
    public Server choose(Object key) {
        List<Server> servers = getLoadBalancer().getAllServers();
        int retryCount = 0;
        int serverSize = servers.size();
        while(retryCount++ <= 20 && serverSize > 0) {
            int count = counter.incrementAndGet();
            Server server = servers.get(count % serverSize);
            if (server.isAlive() && server.isReadyToServe()) {
                return server;
            }
        }
        return null;
    }

    @Override
    public void setLoadBalancer(ILoadBalancer lb) {
        this.lb = lb;
    }

    @Override
    public ILoadBalancer getLoadBalancer() {
        return this.lb;
    }
}
