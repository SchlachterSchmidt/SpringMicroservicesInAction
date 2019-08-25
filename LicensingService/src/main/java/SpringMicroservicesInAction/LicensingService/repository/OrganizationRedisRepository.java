package SpringMicroservicesInAction.LicensingService.repository;

import SpringMicroservicesInAction.LicensingService.models.Organization;

public interface OrganizationRedisRepository {
    void saveOrganization(Organization organization);

    void updateOrganization(Organization organization);

    void deleteOrganization(Organization organization);

    Organization findOrganization(String organizationId);
}
