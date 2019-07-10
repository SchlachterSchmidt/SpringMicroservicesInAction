package SpringMicroservicesInAction.AuthenticationService.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService

@Configuration
class WebSecurityConfigurer : WebSecurityConfigurerAdapter() {

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    override fun userDetailsServiceBean(): UserDetailsService {
        return super.userDetailsServiceBean()
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.inMemoryAuthentication()
                .withUser("user").password("{noop}password").roles(Roles.USER.role)
                .and()
                .withUser("pete.tester").password("{noop}password2").roles(Roles.USER.role, Roles.ADMIN.role)
    }
}

enum class Roles(var role: String) {
    USER("USER"),
    ADMIN("ADMIN")
}