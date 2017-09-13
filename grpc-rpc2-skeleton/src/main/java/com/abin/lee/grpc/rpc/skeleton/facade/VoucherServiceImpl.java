package com.abin.lee.grpc.rpc.skeleton.facade;

import com.abin.lee.grpc.rpc.service.*;
import com.google.protobuf.Any;
import io.grpc.ServerServiceDefinition;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

/**
 * Created by abin on 2017/9/12 0:21.
 * grpc-rpc1
 * com.abin.lee.grpc.rpc.skeleton.facade
 */
@Service
public class VoucherServiceImpl extends VoucherServiceGrpc.VoucherServiceImplBase {

    @Override
    public void createVoucher(VoucherRequest request, StreamObserver<VoucherResponse> responseObserver) {
        System.out.println("request= " + request.toString());
        VoucherResponse.Builder builder = VoucherResponse.newBuilder();
        builder.setStatus(VoucherEnum.APPLY_SUCCESS);
        builder.setMsg("Operate Success");
        Any.Builder any = Any.newBuilder();
        any.setTypeUrl("SOME");
        builder.addDetails(any.build());
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();

    }

    @Override
    public void findById(VoucherQueryRequest request, StreamObserver<VoucherResponse> responseObserver) {
        System.out.println("request= " + request.toString());
        VoucherResponse.Builder builder = VoucherResponse.newBuilder();
        builder.setStatus(VoucherEnum.APPLY_SUCCESS);
        builder.setMsg("Operate Success");
        Any.Builder any = Any.newBuilder();
        any.setTypeUrl("SOME");
        builder.addDetails(any.build());
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void findAll(emptyRequest request, StreamObserver<VoucherResponse> responseObserver) {
        System.out.println("request= " + request.toString());
        VoucherResponse.Builder builder = VoucherResponse.newBuilder();
        builder.setStatus(VoucherEnum.APPLY_SUCCESS);
        builder.setMsg("Operate Success");
        Any.Builder any = Any.newBuilder();
        any.setTypeUrl("SOME");
        builder.addDetails(any.build());
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public ServerServiceDefinition bindService() {
        return super.bindService();
    }
}
