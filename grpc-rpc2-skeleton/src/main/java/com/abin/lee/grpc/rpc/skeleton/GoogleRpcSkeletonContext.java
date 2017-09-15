package com.abin.lee.grpc.rpc.skeleton;


import com.abin.lee.grpc.rpc.common.context.SpringContextUtils;
import com.abin.lee.grpc.rpc.skeleton.common.GoogleRpcRemoteAddress;
import com.abin.lee.grpc.rpc.skeleton.proxy.GoogleRpcSkeletonProxyFactory;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.netty.NettyServerBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Iterator;
import java.util.Map;

/**
 * 服务端注册服务工厂
 */
@Component
public class GoogleRpcSkeletonContext {

    @Resource
    GoogleRpcRemoteAddress googleRpcRemoteAddress;

    @PostConstruct
    public void init() throws Exception {
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


}
