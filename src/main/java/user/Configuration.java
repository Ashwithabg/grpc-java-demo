package user;

import com.gojek.ApplicationConfiguration;
import com.gojek.Figaro;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

public class Configuration {
    public static ApplicationConfiguration get() {
        Set<String> requiredConfig = new HashSet<>(asList(
                "SERVICE_HOST",
                "SERVICE_PORT",
                "CONSUL_DISCOVERY_ENABLED",
                "CONSUL_HOST",
                "CONSUL_PORT"
        ));

        return Figaro.configure(requiredConfig);
    }

}
