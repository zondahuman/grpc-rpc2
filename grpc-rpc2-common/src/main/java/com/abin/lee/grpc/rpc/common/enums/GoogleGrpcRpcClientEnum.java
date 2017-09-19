package com.abin.lee.grpc.rpc.common.enums;

/**
 * Created by abin on 2017/9/19 0:32.
 * grpc-rpc2
 * com.abin.lee.grpc.rpc.common.enums
 */
public enum GoogleGrpcRpcClientEnum {
    STUB("newStub"), //OrderServiceStub
    BLOCKING_STUB("newBlockingStub"), //OrderServiceBlockingStub
    FUTURE_STUB("newFutureStub"),  //OrderServiceFutureStub

            ;
    private String param;

    GoogleGrpcRpcClientEnum(String param) {
        this.param = param;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
