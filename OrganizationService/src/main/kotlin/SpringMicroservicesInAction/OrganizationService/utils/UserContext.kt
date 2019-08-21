package SpringMicroservicesInAction.OrganizationService.utils

object UserContext {
    const val CORRELATION_ID = "tmx-correlation-id"
    const val AUTH_TOKEN = "tmx-auth-token"
    const val USER_ID = "tmx-user-id"
    const val ORG_ID = "tmx-org-id"

    var correlationId: String? = null
    var authToken: String? = null
    var userId: String? = null
    var orgId: String? = null
}