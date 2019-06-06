package SpringMicroservicesInAction.LicensingService.controllers;

import SpringMicroservicesInAction.LicensingService.models.License;
import SpringMicroservicesInAction.LicensingService.services.LicenseService;
import SpringMicroservicesInAction.LicensingService.types.ClientType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value="/v1/organizations/{organizationId}/licenses")
public class LicensingServiceController {

    @Autowired
    LicenseService service;

    @RequestMapping(value="/{licenseId}/{clientType}", method = GET)
    public License getLicense(@PathVariable("organizationId") String organizationId,
                              @PathVariable("licenseId") String licenseId,
                              @PathVariable("clientType") ClientType clientType) {
        return service.getLicense(organizationId, licenseId, clientType);
    }
}
