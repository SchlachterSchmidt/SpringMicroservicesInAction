//package SpringMicroservicesInAction.OrganizationService.security
//
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
//
//@Configuration
//class ResourceServerConfiguration : ResourceServerConfigurerAdapter() {
//
//    override fun configure(http: HttpSecurity) {
//        http
//                .authorizeRequests()
//                .anyRequest()
//                .authenticated()
//    }
//}