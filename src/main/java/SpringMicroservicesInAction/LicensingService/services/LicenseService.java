package SpringMicroservicesInAction.LicensingService.services;

import SpringMicroservicesInAction.LicensingService.clients.OrganizationDiscoveryClient;
import SpringMicroservicesInAction.LicensingService.config.ServiceConfig;
import SpringMicroservicesInAction.LicensingService.models.License;
import SpringMicroservicesInAction.LicensingService.models.Organization;
import SpringMicroservicesInAction.LicensingService.repository.LicenseRepository;
import SpringMicroservicesInAction.LicensingService.types.ClientType;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LicenseService {

    @Autowired
    private LicenseRepository repository;

    @Autowired
    ServiceConfig config;

    @Autowired
    private OrganizationDiscoveryClient organizationDiscoveryClient;

    public License getLicense(String organizationId, String licenseId, ClientType clientType) {

        Organization organization = retrieveOrgInfo(organizationId, clientType);

        return repository.findByOrganizationIdAndLicenseId(organizationId, licenseId)
                .withOrganizationName(organization.getName())
                .withContactEmail(organization.getContactEmail())
                .withContactName(organization.getContactName())
                .withContactPhone(organization.getContactPhone())
                .withComment(config.getExampleProperty());
    }

    private Organization retrieveOrgInfo(String organizationId, ClientType clientType) {

        Organization organization = null;
        switch(clientType) {
            case DISCOVERY:
                organization = organizationDiscoveryClient.getOrganization(organizationId);
                break;
            case REST:
                break;
            case FEIGN:
                break;
        }

        return organization;
    }

    public List<License> getLicensesByOrganization(String organizationId) {
        return repository.findByOrganizationId(organizationId);
    }

    public void saveLicense(License license) {
        license.withLicenseId(UUID.randomUUID().toString());
        repository.save(license);
    }

    public void updateLicense(License license) {
        repository.save(license);
    }

    public void deleteLicense (License license) {
        repository.delete(license);
    }

}
