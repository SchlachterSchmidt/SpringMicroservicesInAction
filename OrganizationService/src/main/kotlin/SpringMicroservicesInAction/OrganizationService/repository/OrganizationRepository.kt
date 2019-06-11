package SpringMicroservicesInAction.OrganizationService.repository

import SpringMicroservicesInAction.OrganizationService.models.Organization
import org.springframework.data.repository.CrudRepository

interface OrganizationRepository: CrudRepository<Organization, String> {
    fun getById(organizationId: String): Organization
}