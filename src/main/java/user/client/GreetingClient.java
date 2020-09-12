package user.client;

import com.proto.greet.GreetRequest;
import com.proto.greet.GreetResponse;
import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.Greeting;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GreetingClient {
    private static final Logger logger = LoggerFactory.getLogger(GreetingClient.class);

    public static void main(String[] args) {
        logger.info("gRPC client");
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        logger.info("creating greet stub");
        GreetServiceGrpc.GreetServiceBlockingStub syncClient = GreetServiceGrpc.newBlockingStub(managedChannel);

        Greeting greeting = Greeting.newBuilder()
                .setFirstName("Ash")
                .setLastName("B G")
                .build();
        GreetRequest greetRequest = GreetRequest.newBuilder()
                .setGreeting(greeting)
                .build();
        GreetResponse greetResponse = syncClient.greet(greetRequest);

        logger.info(greetResponse.getResult());

        logger.info("shutting down client");
        managedChannel.shutdown();
    }
}
