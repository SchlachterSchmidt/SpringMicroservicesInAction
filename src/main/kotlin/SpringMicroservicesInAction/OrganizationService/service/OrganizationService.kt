package SpringMicroservicesInAction.OrganizationService.service

import SpringMicroservicesInAction.OrganizationService.models.Organization
import SpringMicroservicesInAction.OrganizationService.repository.OrganizationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class OrganizationService {

    @Autowired
    lateinit var organizationRepository: OrganizationRepository

    fun getOrganization(organizationId: String): Organization = organizationRepository.getById(organizationId)

    fun updateOrganization(organizationId: String, organization: Organization): Organization = organizationRepository.save(organization)

    fun saveOrganization(organization: Organization): Organization = organizationRepository.save(organization)

    fun deleteOrganization(organization: Organization) = organizationRepository.delete(organization)
}