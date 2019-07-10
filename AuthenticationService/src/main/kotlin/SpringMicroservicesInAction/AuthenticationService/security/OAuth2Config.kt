package SpringMicroservicesInAction.AuthenticationService.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer

@Configuration
class OAuth2Config : AuthorizationServerConfigurerAdapter() {

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var userDetailsService: UserDetailsService

    override fun configure(clients: ClientDetailsServiceConfigurer?) {
        clients?.inMemory()
                ?.withClient("eagleeye")
                ?.secret("{noop}secret")
                ?.authorizedGrantTypes("password", "client_credentials", "refresh_token")
                ?.scopes("webclient", "mobileclient")
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer?) {
        endpoints?.authenticationManager(authenticationManager)
                ?.userDetailsService(userDetailsService)
    }
}
