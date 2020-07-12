package user.client;

import com.proto.greet.GreetRequest;
import com.proto.greet.GreetResponse;
import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.Greeting;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GreetingClient {
    public static void main(String[] args) {
        System.out.println("gRPC client");
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        System.out.println("creating greet stub");
        GreetServiceGrpc.GreetServiceBlockingStub syncClient = GreetServiceGrpc.newBlockingStub(managedChannel);

        Greeting greeting = Greeting.newBuilder()
                .setFirstName("Ash")
                .setLastName("B G")
                .build();
        GreetRequest greetRequest = GreetRequest.newBuilder()
                .setGreeting(greeting)
                .build();
        GreetResponse greetResponse = syncClient.greet(greetRequest);

        System.out.println(greetResponse.getResult());

        System.out.println("shutting down client");
        managedChannel.shutdown();
    }
}
