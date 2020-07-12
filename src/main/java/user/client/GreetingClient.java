package user.client;

import com.proto.user_greeting.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GreetingClient {
    public static void main(String[] args) {
        System.out.println("gRPC client");
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .build();
        System.out.println("creating stub");
        UserServiceGrpc.UserServiceBlockingStub syncClient = UserServiceGrpc.newBlockingStub(managedChannel);

        //do something

        System.out.println("shutting client");
        managedChannel.shutdown();
    }
}
