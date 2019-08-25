package SpringMicroservicesInAction.LicensingService.events.handlers;

import SpringMicroservicesInAction.LicensingService.events.channels.CustomChannel;
import SpringMicroservicesInAction.LicensingService.events.model.OrganizationChangeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(CustomChannel.class)
public class OrganizationChangeHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @StreamListener("inboundOrgChanges")
    public void loggerSink(OrganizationChangeModel organizationChangeModel) {

        logger.debug("Received event for organization id: {} on new custom channel", organizationChangeModel.getOrganizationId());
    }
}
