package SpringMicroservicesInAction.OrganizationService.models

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "organizations")
data class Organization(
        @Id @Column(name = "organization_id", nullable = false) val id: String,
        @Column(name = "name") val name: String,
        @Column(name = "contact_name") val contactName: String,
        @Column(name = "contact_email") val contactEmail: String,
        @Column(name = "contact_phone") val contactPhone: String)