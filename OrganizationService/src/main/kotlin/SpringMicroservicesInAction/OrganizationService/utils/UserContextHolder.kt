package SpringMicroservicesInAction.OrganizationService.utils

object UserContextHolder {

    val userContext: ThreadLocal<UserContext> = ThreadLocal()

    fun getContext(): UserContext {
        if (userContext.get() == null) {
            val context = createEmptyContext()
            setContext(context)
        }
        return userContext.get()
    }

    private fun createEmptyContext() = UserContext

    private fun setContext(context: UserContext) {
        userContext.set(context)
    }
}