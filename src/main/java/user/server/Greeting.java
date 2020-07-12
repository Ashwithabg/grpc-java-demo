package user.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class Greeting {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder
                .forPort(50051)
                .addService(new GreetServiceImplementation())
                .build();

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