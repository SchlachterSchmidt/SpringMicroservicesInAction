package SpringMicroservicesInAction.OrganizationService

import SpringMicroservicesInAction.OrganizationService.utils.UserContextInterceptor
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.Bean
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.web.client.RestTemplate

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
class OrganizationServiceApplication {

	@Bean
	@LoadBalanced
	fun getRestTemplate(): RestTemplate {
		val restTemplate = RestTemplate()
		val interceptors = restTemplate.interceptors

		interceptors.add(UserContextInterceptor())
		restTemplate.interceptors = interceptors

		return restTemplate
	}
}

fun main(args: Array<String>) {
	runApplication<OrganizationServiceApplication>(*args)
}
