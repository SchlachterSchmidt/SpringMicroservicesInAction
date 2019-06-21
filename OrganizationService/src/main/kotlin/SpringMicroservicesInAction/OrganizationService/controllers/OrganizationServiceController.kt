package SpringMicroservicesInAction.OrganizationService.controllers

import SpringMicroservicesInAction.OrganizationService.models.Organization
import SpringMicroservicesInAction.OrganizationService.service.OrganizationService
import SpringMicroservicesInAction.OrganizationService.utils.UserContextHolder
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMethod.*

@RestController
@RequestMapping("v1/organizations")
class OrganizationServiceController {

    @Autowired
    lateinit var organizationService: OrganizationService

    val logger = LoggerFactory.getLogger(OrganizationServiceController::class.java)

    @RequestMapping("/{organizationId}",method = arrayOf(GET))
    fun getOrganization(@PathVariable("organizationId") organizationId: String): Organization {
        logger.debug("OrganizationServiceController Correlation id: ${UserContextHolder.getContext().correlationId}")
        return organizationService.getOrganization(organizationId)
    }

    @RequestMapping("/{organizationId}", method = arrayOf(PUT))
    fun updateOrganization(@PathVariable("organizationId") organizationId: String,
                           @RequestBody organization: Organization): Organization {
        return organizationService.updateOrganization(organizationId, organization)
    }

    @RequestMapping("/{organizationId}", method = arrayOf(POST))
    fun saveOrganization(@RequestBody organization: Organization): Organization {
        return organizationService.saveOrganization(organization)
    }

    @RequestMapping("/{organizationId}",method = arrayOf(DELETE))
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteOrganization(@PathVariable("organizationId") organizationId: String,
                           @RequestBody organization: Organization) =
            organizationService.deleteOrganization(organization)
}