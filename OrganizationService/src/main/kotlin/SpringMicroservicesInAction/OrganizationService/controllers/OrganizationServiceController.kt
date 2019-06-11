package SpringMicroservicesInAction.OrganizationService.controllers

import SpringMicroservicesInAction.OrganizationService.models.Organization
import SpringMicroservicesInAction.OrganizationService.service.OrganizationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMethod.*

@RestController
@RequestMapping(value="v1/organizations")
class OrganizationServiceController {

    @Autowired
    lateinit var organizationService: OrganizationService

    @RequestMapping(value="/{organizationId}",method = arrayOf(GET))
    fun getOrganization(@PathVariable("organizationId") organizationId: String): Organization {
        return organizationService.getOrganization(organizationId)
    }

    @RequestMapping(value="/{organizationId}", method = arrayOf(PUT))
    fun updateOrganization(@PathVariable("organizationId") organizationId: String,
                           @RequestBody organization: Organization): Organization {
        return organizationService.updateOrganization(organizationId, organization)
    }

    @RequestMapping(value="/{organizationId}", method = arrayOf(POST))
    fun saveOrganization(@RequestBody organization: Organization): Organization {
        return organizationService.saveOrganization(organization)
    }

    @RequestMapping(value="/{organizationId}",method = arrayOf(DELETE))
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteOrganization(@PathVariable("organizationId") organizationId: String,
                           @RequestBody organization: Organization) =
            organizationService.deleteOrganization(organization)
}