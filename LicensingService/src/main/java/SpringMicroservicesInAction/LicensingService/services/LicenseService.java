package SpringMicroservicesInAction.LicensingService.services;

import SpringMicroservicesInAction.LicensingService.clients.OrganizationDiscoveryClient;
import SpringMicroservicesInAction.LicensingService.clients.OrganizationFeignClient;
import SpringMicroservicesInAction.LicensingService.clients.OrganizationRestTemplateClient;
import SpringMicroservicesInAction.LicensingService.config.ServiceConfig;
import SpringMicroservicesInAction.LicensingService.models.License;
import SpringMicroservicesInAction.LicensingService.models.Organization;
import SpringMicroservicesInAction.LicensingService.repository.LicenseRepository;
import SpringMicroservicesInAction.LicensingService.types.ClientType;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class LicenseService {

    @Autowired
    private LicenseRepository repository;

    @Autowired
    ServiceConfig config;

    @Autowired
    private OrganizationDiscoveryClient organizationDiscoveryClient;

    @Autowired
    private OrganizationRestTemplateClient organizationRestTemplateClient;

    @Autowired
    private OrganizationFeignClient organizationFeignClient;

    @HystrixCommand(commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "700")})
    public License getLicense(String organizationId, String licenseId, ClientType clientType) {
        // simulating a slow call to the database with a 1 in 3 chance of the call taking more than 1 second
        randomSleep();

        Organization organization = getOrganizationByIdWithClient(organizationId, clientType);

        return repository.findByOrganizationIdAndLicenseId(organizationId, licenseId)
                .withOrganizationName(organization.getName())
                .withContactEmail(organization.getContactEmail())
                .withContactName(organization.getContactName())
                .withContactPhone(organization.getContactPhone())
                .withComment(config.getExampleProperty());
    }

    private Organization getOrganizationByIdWithClient(String organizationId, ClientType clientType) {
        // demonstrating calling the OrganizationService using 3 different strategies
        Organization organization = null;
        switch(clientType) {
            case DISCOVERY:
                organization = organizationDiscoveryClient.getOrganization(organizationId);
                break;
            case REST:
                organization = organizationRestTemplateClient.getOrganization(organizationId);
                break;
            case FEIGN:
                organization = organizationFeignClient.getOrganization(organizationId);
                break;
        }
        return organization;
    }

    @HystrixCommand(fallbackMethod = "buildFallbackLicenseList")
    public List<License> getLicensesByOrganization(String organizationId) {
        randomSleep();
        return repository.findByOrganizationId(organizationId);
    }

    private List<License> buildFallbackLicenseList(String organizationId) {
        List<License> fallbackLicenseList = new ArrayList<License>();
        License license = new License()
                .withLicenseId("00-0000")
                .withOrganizationId(organizationId)
                .withOrganizationName("No License information is currently available");
        fallbackLicenseList.add(license);
        return fallbackLicenseList;
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

    private void randomSleep() {
        Random random =new Random();
        int randomInt = random.nextInt((3 - 1) + 1) + 1;
        if (randomInt == 3) sleep();
    }

    private void sleep() {
        try {
            Thread.sleep(11000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
