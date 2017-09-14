package com.abin.lee.grpc.rpc.stub.common;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * 服务端注册服务工厂
 */

public class GoogleRpcStubFactory {

    // 服务实现类
    private Object service;// serice实现类

    private GoogleRpcRemoteAddress googleRpcRemoteAddress;

    public Object getService() {
        return service;
    }

    public void setService(Object service) {
        this.service = service;
    }

    public GoogleRpcRemoteAddress getGoogleRpcRemoteAddress() {
        return googleRpcRemoteAddress;
    }

    public void setGoogleRpcRemoteAddress(GoogleRpcRemoteAddress googleRpcRemoteAddress) {
        this.googleRpcRemoteAddress = googleRpcRemoteAddress;
    }
}
