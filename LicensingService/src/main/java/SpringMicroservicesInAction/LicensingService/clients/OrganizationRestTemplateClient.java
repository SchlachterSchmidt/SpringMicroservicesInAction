package SpringMicroservicesInAction.LicensingService.clients;

import SpringMicroservicesInAction.LicensingService.models.Organization;
import SpringMicroservicesInAction.LicensingService.repository.OrganizationRedisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrganizationRestTemplateClient {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    OrganizationRedisRepository redisRepository;

    private static final Logger logger = LoggerFactory.getLogger(OrganizationRestTemplateClient.class);

    public Organization getOrganization(String organizationId) {
        logger.debug("Getting Organization data");

        Organization organization = checkRedisForOrganization(organizationId);

        if (organization != null) {
            logger.debug("Retrieved organization data from Redis cache");
            return organization;
        }

        logger.debug("Unable to retrieve organization data from Redis cache");
        ResponseEntity<Organization> restExchange = restTemplate.exchange(
                "http://organizationservice/v1/organizations/{organizationId}",
                HttpMethod.GET,
                null, Organization.class, organizationId);

        organization = restExchange.getBody();

        if (organization != null) {
            cacheOrganizationObject(organization);
        }
        return organization;
     }

    private Organization checkRedisForOrganization(String organizationId) {
        try {
            return redisRepository.findOrganization(organizationId);
        } catch (Exception ex) {
            logger.debug("Error retrieving organization {} from cache: {}", organizationId, ex.getMessage());
            return null;
        }
    }

    private void cacheOrganizationObject(Organization organization) {
        try {
            redisRepository.saveOrganization(organization);
            logger.debug("Written organization {} to cache", organization.getId());
        } catch (Exception ex) {
            logger.debug("Error writing organization {} to cache: {}", organization.getId(), ex.getMessage());
        }
    }
}
