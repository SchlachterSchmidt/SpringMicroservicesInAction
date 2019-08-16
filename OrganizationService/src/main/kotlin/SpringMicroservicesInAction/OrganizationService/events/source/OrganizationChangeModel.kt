package SpringMicroservicesInAction.OrganizationService.events.source

class OrganizationChangeModel(val type: String, val action: String, val organizationId: String, val correlationId: String) {


}