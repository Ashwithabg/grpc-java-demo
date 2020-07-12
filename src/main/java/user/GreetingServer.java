package user;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GreetingServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(50051).build();

        server.start();
        System.out.println("server started");
        server.awaitTermination();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("recieved shutdown request");
            server.shutdown();
            System.out.println("server has shutdown");
        }));

    }
}
