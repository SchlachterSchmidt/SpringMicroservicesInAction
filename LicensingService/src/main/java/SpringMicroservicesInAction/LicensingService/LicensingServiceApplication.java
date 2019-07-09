package SpringMicroservicesInAction.LicensingService;

import SpringMicroservicesInAction.LicensingService.utils.UserContextInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
@RefreshScope
public class LicensingServiceApplication {

	@LoadBalanced
	@Bean
	public RestTemplate getRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		List interceptors = restTemplate.getInterceptors();

		interceptors.add(new UserContextInterceptor());
		restTemplate.setInterceptors(interceptors);

		return restTemplate;
	}

	public static void main(String[] args) {
		SpringApplication.run(LicensingServiceApplication.class, args);
	}
}
