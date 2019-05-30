package SpringMicroservicesInAction.LicensingService.controllers;

import SpringMicroservicesInAction.LicensingService.models.License;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value="/v1/organizations/{organizationId}/licenses")
public class LicensingServiceController {

    @RequestMapping(value="/{licenseId}", method = GET)
    public License getLicense(@PathVariable("organizationId") String organizationId,
                              @PathVariable("licenseId") String licenseId) {

        // initial stump. this will be replaced with call to the service layer akin to:
        // return service.getLicenseByLicenseIdAndOrganizationId(licenseId, organizationId)
        return new License()
                .withLicenseId(licenseId)
                .withOrganizationId(organizationId)
                .withProductName("Telco")
                .withLicenseType("Seat");
    }
}
