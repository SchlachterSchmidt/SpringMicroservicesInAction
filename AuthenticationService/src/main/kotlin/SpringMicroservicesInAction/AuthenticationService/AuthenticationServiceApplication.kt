package SpringMicroservicesInAction.AuthenticationService

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@RestController
@EnableResourceServer
@EnableAuthorizationServer
class AuthenticationServiceApplication {
	@RequestMapping(value = "/user", produces = arrayOf("application/json"))
	fun user(user: OAuth2Authentication): Map<String, Any> =
			mapOf("user" to user.userAuthentication.principal, "authorities" to user.userAuthentication.authorities)
}

fun main(args: Array<String>) {
	runApplication<AuthenticationServiceApplication>(*args)
}
