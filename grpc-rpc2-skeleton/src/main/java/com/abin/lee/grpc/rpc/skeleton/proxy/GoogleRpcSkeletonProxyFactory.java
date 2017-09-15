package com.abin.lee.grpc.rpc.skeleton.proxy;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 服务端注册服务工厂
 */
@Getter
@Setter
@ToString
public class GoogleRpcSkeletonProxyFactory {

    // 服务实现类
    private Object service;// serice实现类


    public Object getService() {
        return service;
    }

    public void setService(Object service) {
        this.service = service;
    }

}
