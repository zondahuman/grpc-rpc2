march action

grpc RPC:

1、grpc stub

2、grpc skeleton


# Language Guide
https://developers.google.com/protocol-buffers/docs/proto?hl=zh-cn

# Language Guide (proto3)
https://developers.google.com/protocol-buffers/docs/proto3?hl=zh-cn


# Protocol Buffers Version 3 Language Specification
https://developers.google.com/protocol-buffers/docs/reference/proto3-spec

# GrpcClientFactory
https://www.javatips.net/api/armeria-master/grpc/src/main/java/com/linecorp/armeria/client/grpc/GrpcClientFactory.java


# Grpc Proxy Integration Spring :

grpc stub proxy : GoogleRpcStubProxyFactory

grpc skeleton proxy : GoogleRpcSkeletonProxyFactory



# armeria
https://github.com/line/armeria



# tcpcpoy pressure test

线上机：132执行
/usr/local/tcpcopy/sbin/tcpcopy -x 80-172.16.2.133:80 -s 172.16.2.134 -n 5 -c 10.10.10.x -d -C 4 -l tcpcopy.log  -P /var/run/tcpcopy.pid

关闭response，intercept机器：134执行
/usr/local/intercept/sbin/intercept -i eth0 -l intercept.log -P /var/run/intercept.pid -F 'tcp and src port 80' -d

133执行：
route add -net 10.10.10.0 netmask 255.255.255.0 gw 172.16.2.134







