package com.abin.lee.grpc.rpc.stub.common;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by abin on 2017/9/12 2017/9/12.
 * grpc-rpc2
 * com.abin.lee.grpc.rpc.stub.common
 */
@Component
public class GoogleRpcRemoteAddress implements InitializingBean,Closeable {

    //主机
    private String host;
    // 端口
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

    @Override
    public void close() throws IOException {
        System.out.println("GoogleRpcRemoteAddress----end : " + host +":" +port);

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("GoogleRpcRemoteAddress----start : " + host +":" +port);

    }
}
