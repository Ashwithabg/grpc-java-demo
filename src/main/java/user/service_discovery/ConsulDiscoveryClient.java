package user.service_discovery;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.agent.model.NewService;
import com.ecwid.consul.v1.health.HealthServicesRequest;
import com.ecwid.consul.v1.health.model.HealthService;
import com.gojek.ApplicationConfiguration;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.List;

public class ConsulDiscoveryClient {
    private ConsulClient client;
    private ApplicationConfiguration config;

    public ConsulDiscoveryClient(ApplicationConfiguration config) {
        this.client = new ConsulClient(config.getValueAsString("CONSUL_HOST"));
        this.config = config;
    }

    public void register() {
        NewService newService = new NewService();
        newService.setId(config.getValueAsString("SERVICE_ID"));
        newService.setName(config.getValueAsString("SERVICE_NAME"));
        newService.setPort(config.getValueAsInt("SERVICE_PORT"));
        client.agentServiceRegister(newService);
    }

    public void deregister() {
        client.agentServiceDeregister(config.getValueAsString("SERVICE_ID"));
    }

    public ManagedChannel getHealthyNodes(String serviceName){
        HealthServicesRequest servicesRequest = HealthServicesRequest.newBuilder().build();
        Response<List<HealthService>> healthServicesResponse = client.getHealthServices(serviceName, servicesRequest);
        List<HealthService> healthServices = healthServicesResponse.getValue();
        HealthService healthService = healthServices.get(0);
        String address = healthService.getNode().getAddress();
        int port = healthService.getService().getPort();

        return ManagedChannelBuilder
                .forAddress(address, port)
                .usePlaintext()
                .build();
    }
}
