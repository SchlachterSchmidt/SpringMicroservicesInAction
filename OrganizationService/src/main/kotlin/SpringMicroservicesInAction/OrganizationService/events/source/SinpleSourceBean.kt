package SpringMicroservicesInAction.OrganizationService.events.source

import SpringMicroservicesInAction.OrganizationService.utils.UserContext
import org.slf4j.LoggerFactory
import org.springframework.cloud.stream.messaging.Source
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component

@Component
class SimpleSourceBean(val source: Source) {

    val logger = LoggerFactory.getLogger(this.javaClass)

    fun publishOrgChange(action: String, orgId: String) {
        logger.debug("Sending Kafka message $action for Organization $orgId")
        val change = OrganizationChangeModel(
                OrganizationChangeModel::class.java.typeName,
                action,
                orgId,
                UserContext.correlationId!!)

        source.output()
                .send(MessageBuilder
                        .withPayload(change)
                        .build())

        logger.debug("Sent Kafka message $action for Organization $orgId successfully")
    }
}