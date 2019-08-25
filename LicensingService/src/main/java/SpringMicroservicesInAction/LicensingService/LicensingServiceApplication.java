package SpringMicroservicesInAction.LicensingService;

import SpringMicroservicesInAction.LicensingService.config.ServiceConfig;
import SpringMicroservicesInAction.LicensingService.events.model.OrganizationChangeModel;
import SpringMicroservicesInAction.LicensingService.utils.UserContextInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
@RefreshScope
//@EnableBinding(Sink.class)
public class LicensingServiceApplication {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ServiceConfig serviceConfig;

	@LoadBalanced
	@Bean
	public RestTemplate getRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		List interceptors = restTemplate.getInterceptors();

		interceptors.add(new UserContextInterceptor());
		restTemplate.setInterceptors(interceptors);

		return restTemplate;
	}

	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration =
				new RedisStandaloneConfiguration(serviceConfig.getRedisServer(), serviceConfig.getRedisPort());
		return new JedisConnectionFactory(redisStandaloneConfiguration);
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(jedisConnectionFactory());
		return template;
	}

//	@StreamListener(Sink.INPUT)
//	public void loggerSink(OrganizationChangeModel organizationChangeModel) {
//		logger.debug("Received event for organization id: {}", organizationChangeModel.getOrganizationId());
//	}

	public static void main(String[] args) {
		SpringApplication.run(LicensingServiceApplication.class, args);
	}
}
