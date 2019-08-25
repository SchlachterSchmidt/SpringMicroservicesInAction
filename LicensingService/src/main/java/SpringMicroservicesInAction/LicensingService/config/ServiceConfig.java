package SpringMicroservicesInAction.LicensingService.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ServiceConfig {
    @Value("${example.property}")
    private String exampleProperty = "";

    @Value("${spring.redis.server}")
    private String redisServer = "";

    @Value("${spring.redis.port}")
    private String redisPort = "";

    public String getExampleProperty() {
        return exampleProperty;
    }

    public String getRedisServer() {
        return redisServer;
    }

    public int getRedisPort() {
        return new Integer(redisPort).intValue();
    }
}
