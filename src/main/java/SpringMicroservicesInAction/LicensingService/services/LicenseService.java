package SpringMicroservicesInAction.LicensingService.services;

import SpringMicroservicesInAction.LicensingService.models.License;
import SpringMicroservicesInAction.LicensingService.repository.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LicenseService {

    @Autowired
    private LicenseRepository repository;

    public License getLicense(String organizationId, String licenseId) {
        return repository.findByOrganizationIdAndLicenseId(organizationId, licenseId);
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
