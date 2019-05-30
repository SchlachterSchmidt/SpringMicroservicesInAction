package SpringMicroservicesInAction.LicensingService.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "licenses")
public class License {

    @Id
    @Column(name = "license_id")
    private String licenseId;

    @Column(name = "organization_id")
    private String organizationId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "license_type")
    private String licenseType;

    public String getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(String licenseId) {
        this.licenseId = licenseId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public License withOrganizationId(String organizationId) {
        this.organizationId = organizationId;
        return this;
    }

    public License withLicenseId(String licenseId) {
        this.licenseId = licenseId;
        return this;
    }

    public License withProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public License withLicenseType(String licenseType) {
        this.licenseType = licenseType;
        return this;
    }
}
