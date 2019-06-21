package SpringMicroservicesInAction.OrganizationService

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
class OrganizationServiceApplication

fun main(args: Array<String>) {
	runApplication<OrganizationServiceApplication>(*args)
}
