/*
 * Copyright 2015, Google Inc. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *    * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *    * Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following disclaimer
 * in the documentation and/or other materials provided with the
 * distribution.
 *
 *    * Neither the name of Google Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.abin.lee.grpc.rpc.stub.proxy;


import com.abin.lee.grpc.rpc.common.context.SpringContextUtils;
import com.abin.lee.grpc.rpc.stub.common.GoogleRpcRemoteAddress;
import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

/**
 */
public class GoogleRpcStubProxyClient implements FactoryBean, InitializingBean, Closeable, Ordered {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    // 服务实现类
    private Object service;// serice实现类

    private GoogleRpcRemoteAddress googleRpcRemoteAddress;
    private Object proxyClient;
    private Class<?> objectClass;
    ManagedChannel channel = null;

    @Override
    public void close() throws IOException {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        channel = NettyChannelBuilder.forAddress(googleRpcRemoteAddress.getHost(), googleRpcRemoteAddress.getPort()).usePlaintext(true).build();
        Object service = null;
        Map<String, GoogleRpcStubProxyClient> handlers = SpringContextUtils.getBeansOfType(GoogleRpcStubProxyClient.class);
        for (Iterator<Map.Entry<String, GoogleRpcStubProxyClient>> iterator = handlers.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, GoogleRpcStubProxyClient> entry = iterator.next();
            String beanName = entry.getKey();
            GoogleRpcStubProxyClient instance = entry.getValue();
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Class<?> externalClass = classLoader.loadClass((String) instance.getService());
            System.out.println("externalClass=" + externalClass);
            Method newBlockingStub = externalClass.getMethod("newBlockingStub", io.grpc.Channel.class);
            Object[] object = {channel};
            proxyClient = newBlockingStub.invoke(externalClass, object);
//            proxyClient = newBlockingStub.invoke(externalClass, channel);

            LOGGER.info("proxyClient = " + proxyClient);
        }
    }


    public GoogleRpcRemoteAddress getGoogleRpcRemoteAddress() {
        return googleRpcRemoteAddress;
    }

    public void setGoogleRpcRemoteAddress(GoogleRpcRemoteAddress googleRpcRemoteAddress) {
        this.googleRpcRemoteAddress = googleRpcRemoteAddress;
    }

    @Override
    public Object getObject() throws Exception {
        return proxyClient;
    }

    @Override
    public Class<?> getObjectType() {
        return objectClass;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public int getOrder() {
        return 1;
    }

    public Object getProxyClient() {
        return proxyClient;
    }

    public void setProxyClient(Object proxyClient) {
        this.proxyClient = proxyClient;
    }

    public Class<?> getObjectClass() {
        return objectClass;
    }

    public void setObjectClass(Class<?> objectClass) {
        this.objectClass = objectClass;
    }

    public Object getService() {
        return service;
    }

    public void setService(Object service) {
        this.service = service;
    }
}
