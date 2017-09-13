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
public class TeamServiceImpl extends TeamServiceGrpc.TeamServiceImplBase {

    @Override
    public void createTeam(TeamRequest request, StreamObserver<TeamResponse> responseObserver) {
        System.out.println("request= " + request.toString());
        TeamResponse teamResponse = TeamResponse.newBuilder().setCode(1).setMsg("Operate Success").build();
        responseObserver.onNext(teamResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void findById(TeamRequest request, StreamObserver<TeamResponse> responseObserver) {
        System.out.println("request= " + request.toString());
        TeamResponse teamResponse = TeamResponse.newBuilder().setCode(1).setMsg("Operate Success").build();

        responseObserver.onNext(teamResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void findListById(TeamRequest request, StreamObserver<RootResponse> responseObserver) {
        System.out.println("request= " + request.toString());
        RootResponse.Builder builder = RootResponse.newBuilder();
        builder.setStatus(BaseEnum.EXCHANGE_SUCCESS);
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
