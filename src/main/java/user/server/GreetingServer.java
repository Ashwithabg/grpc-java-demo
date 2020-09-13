package user.server;

import com.gojek.ApplicationConfiguration;
import com.gojek.Figaro;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.Arrays.asList;

public class GreetingServer {
    private static final Logger logger = LoggerFactory.getLogger(GreetingServer.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        Set<String> requiredConfig = new HashSet<>(asList("SERVER_PORT"));
        ApplicationConfiguration config = Figaro.configure(requiredConfig);

        Server server = ServerBuilder
                .forPort(config.getValueAsInt("SERVER_PORT"))
                .addService(new GreetServiceImplementation())
                .build();

        server.start();
        logger.info("Server started");

        server.awaitTermination();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Received shutdown request");
            server.shutdown();
            logger.info("Server has shutdown");
        }));

    }
}
