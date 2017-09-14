package com.abin.lee.grpc.rpc.skeleton.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by abin on 2017/9/12 2017/9/12.
 * grpc-rpc2
 * com.abin.lee.grpc.rpc.stub.common
 */
@Component
public class GoogleRpcRemoteAddress  {

    //主机
    @Value("${grpc.rpc.host}")
    private String host;
    // 端口
    @Value("${grpc.rpc.port}")
    private Integer port;


    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }


}
