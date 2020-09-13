package user.server;

import com.gojek.ApplicationConfiguration;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class GreetingServer {
    private static final Logger logger = LoggerFactory.getLogger(GreetingServer.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        ApplicationConfiguration config = Configuration.get();
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
