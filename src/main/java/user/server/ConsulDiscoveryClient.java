package user.server;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.agent.model.NewService;
import com.gojek.ApplicationConfiguration;

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
}
