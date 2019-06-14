package SpringMicroservicesInAction.LicensingService.controllers;

import SpringMicroservicesInAction.LicensingService.models.License;
import SpringMicroservicesInAction.LicensingService.services.LicenseService;
import SpringMicroservicesInAction.LicensingService.types.ClientType;
import SpringMicroservicesInAction.LicensingService.utils.UserContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value="/v1/organizations/{organizationId}/licenses")
public class LicensingServiceController {

    private static final Logger logger = LoggerFactory.getLogger(LicensingServiceController.class);

    @Autowired
    LicenseService service;

    @RequestMapping(method = GET)
    public List<License> getLicensesByOrganization(@PathVariable("organizationId") String organizationId) {
        logger.debug("LicenseServiceController Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
        return service.getLicensesByOrganization(organizationId);
    }

    @RequestMapping(value="/{licenseId}/{clientType}", method = GET)
    public License getLicense(@PathVariable("organizationId") String organizationId,
                              @PathVariable("licenseId") String licenseId,
                              @PathVariable("clientType") ClientType clientType) {
        return service.getLicense(organizationId, licenseId, clientType);
    }
}
