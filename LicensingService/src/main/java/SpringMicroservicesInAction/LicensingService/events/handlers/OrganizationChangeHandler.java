package SpringMicroservicesInAction.LicensingService.events.handlers;

import SpringMicroservicesInAction.LicensingService.events.channels.CustomChannel;
import SpringMicroservicesInAction.LicensingService.events.model.OrganizationChangeModel;
import SpringMicroservicesInAction.LicensingService.repository.OrganizationRedisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(CustomChannel.class)
public class OrganizationChangeHandler {

    @Autowired
    private OrganizationRedisRepository organizationRedisRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @StreamListener("inboundOrgChanges")
    public void loggerSink(OrganizationChangeModel organizationChangeModel) {
        switch (organizationChangeModel.getAction()) {
            case "SAVE":
                logger.debug("Received a UPDATE event from the organization service for organization id {}", organizationChangeModel.getOrganizationId());
                break;
            case "UPDATE":
                logger.debug("Received a UPDATE event from the organization service for organization id {}", organizationChangeModel.getOrganizationId());
                organizationRedisRepository.deleteOrganization(organizationChangeModel.getOrganizationId());
                break;
            case "DELETE":
                logger.debug("Received a DELETE event from the organization service for organization id {}", organizationChangeModel.getOrganizationId());
                organizationRedisRepository.deleteOrganization(organizationChangeModel.getOrganizationId());
                break;
            default:
                logger.error("Received an UNKNOWN event from the organization service of type {}", organizationChangeModel.getType());
                break;
        }
    }
}
