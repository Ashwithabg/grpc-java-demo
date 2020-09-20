package user.client;

import com.gojek.ApplicationConfiguration;
import com.proto.greet.GreetRequest;
import com.proto.greet.GreetResponse;
import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.Greeting;
import io.grpc.ManagedChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import user.Configuration;
import user.service_discovery.ConsulDiscoveryClient;

public class GreetingClient {
    private static final Logger logger = LoggerFactory.getLogger(GreetingClient.class);
    public static final String FIRST_NAME = "Ash";
    public static final String LAST_NAME = "B G";

    public static void main(String[] args) {
        logger.info("Starting gRPC client");
        ApplicationConfiguration config = Configuration.get();
        ConsulDiscoveryClient consulDiscoveryClient = new ConsulDiscoveryClient(config);
        ManagedChannel managedChannel = consulDiscoveryClient.getHealthyNodes(config.getValueAsString("SERVICE_NAME"));

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
