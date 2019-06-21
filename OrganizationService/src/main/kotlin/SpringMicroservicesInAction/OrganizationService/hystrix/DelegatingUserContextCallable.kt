package SpringMicroservicesInAction.OrganizationService.hystrix

import SpringMicroservicesInAction.OrganizationService.utils.UserContext
import SpringMicroservicesInAction.OrganizationService.utils.UserContextHolder
import java.util.concurrent.Callable

class DelegatingUserContextCallable<T>(val delegate: Callable<T>, val userContext: UserContext) : Callable<T> {
    override fun call(): T {
        UserContextHolder.setContext(userContext)
        return delegate.call()
    }
}