package com.abin.lee.grpc.rpc.common.proxy;


import com.abin.lee.grpc.rpc.common.context.SpringContextUtils;
import com.abin.lee.grpc.rpc.common.rpc.GoogleRpcRemoteAddress;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.netty.NettyServerBuilder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;

import javax.annotation.Resource;
import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * 服务端注册服务工厂
 */
@Getter
@Setter
@ToString
public class GoogleRpcSkeletonProxyFactory implements InitializingBean, Closeable, Ordered {

    // 服务实现类
    private Object service;// serice实现类
    @Resource
    GoogleRpcRemoteAddress googleRpcRemoteAddress;
    private static Server server = null;

    @Override
    public void close() throws IOException {
        if(!server.isShutdown()){
            server.shutdown();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        NettyServerBuilder builder = NettyServerBuilder.forPort(googleRpcRemoteAddress.getPort());

        Map<String, GoogleRpcSkeletonProxyFactory> handlers = SpringContextUtils.getBeansOfType(GoogleRpcSkeletonProxyFactory.class);
        for (Iterator<Map.Entry<String, GoogleRpcSkeletonProxyFactory>> iterator = handlers.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, GoogleRpcSkeletonProxyFactory> entry = iterator.next();
            String beanName = entry.getKey();
            GoogleRpcSkeletonProxyFactory instance = entry.getValue();
            Object service = instance.getService();
            Class<?> serviceClass = service.getClass();
            builder.addService((BindableService) service);
        }

        Server server = builder.build();
        server.start();

        System.out.println("............................................................................................................................");
        System.out.println(".................................................GRPC Server is Running now!................................................");
        System.out.println("............................................................................................................................");

//        System.in.read(); // 按任意键退出
        server.awaitTermination();
    }


    public Object getService() {
        return service;
    }

    public void setService(Object service) {
        this.service = service;
    }



    @Override
    public int getOrder() {
        return 1;
    }
}
