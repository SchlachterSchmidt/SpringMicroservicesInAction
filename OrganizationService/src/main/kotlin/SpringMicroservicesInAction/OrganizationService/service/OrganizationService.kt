package SpringMicroservicesInAction.OrganizationService.service

import SpringMicroservicesInAction.OrganizationService.events.source.SimpleSourceBean
import SpringMicroservicesInAction.OrganizationService.models.Organization
import SpringMicroservicesInAction.OrganizationService.repository.OrganizationRepository
import SpringMicroservicesInAction.OrganizationService.utils.UserContextHolder
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class OrganizationService {

    @Autowired
    lateinit var organizationRepository: OrganizationRepository

    @Autowired
    lateinit var simpleSourceBean: SimpleSourceBean

    val logger = LoggerFactory.getLogger(OrganizationService::class.java)

    @HystrixCommand(threadPoolKey = "getOrganizationThreadPool")
    fun getOrganization(organizationId: String): Organization {
        randomTimeout()
        logger.debug("OrganizationService.getOrganization Correlation id: ${UserContextHolder.getContext().correlationId}")
        return organizationRepository.getById(organizationId)
    }

    @HystrixCommand(
            fallbackMethod = "fallbackUpdate",
            commandKey = "updateOrganizationCommandKey",
            threadPoolKey = "updateOrganizationThreadPool")
    fun updateOrganization(organizationId: String, organization: Organization): Organization {
        val updatedOrg = organizationRepository.save(organization)
        simpleSourceBean.publishOrgChange("UPDATE", organizationId)
        return updatedOrg
    }

    private fun fallbackUpdate(organizationId: String, organization: Organization) =
            Organization(id = "000-000-00", name = "update not saved", contactEmail = "", contactName = "", contactPhone = "")

    @HystrixCommand
    fun saveOrganization(organization: Organization): Organization {
        val orgToSave = organization.copy(id = UUID.randomUUID().toString())
        val savedOrg = organizationRepository.save(orgToSave)
        simpleSourceBean.publishOrgChange("SAVE", savedOrg.id)
        return savedOrg
    }

    @HystrixCommand
    fun deleteOrganization(organization: Organization) = organizationRepository.delete(organization)
}

fun randomTimeout() {
    val randomVal = Random().nextInt((3 - 1) + 1) + 1
    if (randomVal == 3) sleep()
}

fun sleep() {
    Thread.sleep(4000)
}