package user.server;

import com.proto.health.HealthCheckRequest;
import com.proto.health.HealthCheckResponse;
import com.proto.health.HealthGrpc;
import io.grpc.stub.StreamObserver;

public class HealthCheckImplementation extends HealthGrpc.HealthImplBase {
    @Override
    public void check(HealthCheckRequest request, StreamObserver<HealthCheckResponse> responseObserver) {
        HealthCheckResponse healthCheckResponse = HealthCheckResponse.newBuilder()
                .setStatus(HealthCheckResponse.ServingStatus.SERVING)
                .build();
        responseObserver.onNext(healthCheckResponse);
        responseObserver.onCompleted();
    }
}
