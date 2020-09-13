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
    public static final String SERVER_HOST = "localhost";
    public static final int SERVER_PORT = 50051;
    public static final String FIRST_NAME = "Ash";
    public static final String LAST_NAME = "B G";

    public static void main(String[] args) {
        logger.info("Starting gRPC client");
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        logger.info("Creating greet stub");
        GreetServiceGrpc.GreetServiceBlockingStub syncClient = GreetServiceGrpc.newBlockingStub(managedChannel);

        Greeting greeting = Greeting.newBuilder()
                .setFirstName(FIRST_NAME)
                .setLastName(LAST_NAME)
                .build();
        GreetRequest greetRequest = GreetRequest.newBuilder()
                .setGreeting(greeting)
                .build();
        GreetResponse greetResponse = syncClient.greet(greetRequest);

        logger.info(greetResponse.getResult());

        logger.info("Shutting down client");
        managedChannel.shutdown();
    }
}
