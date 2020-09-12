package user.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GreetingServer {
    private static final Logger logger = LoggerFactory.getLogger(GreetingServer.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder
                .forPort(50051)
                .addService(new GreetServiceImplementation())
                .build();

        server.start();
        logger.info("server started");

        server.awaitTermination();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("recieved shutdown request");
            server.shutdown();
            logger.info("server has shutdown");
        }));

    }
}
