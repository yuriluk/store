package org.example.store.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreDto extends AbstractDto {

    @NotBlank(message = "Name can`t be null and spaces!")
    @Pattern(regexp = ".{2,58}", message = "Name must be more then 2 symbols, but smaller than 58")
    private String name;

    @NotBlank(message = "Phone number can`t be null and spaces!")
    @Pattern(regexp = ".{11,15}", message = "Phone number  must be more then 11 symbols, but smaller than 15")
    private String phoneNumber;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Valid
    private AddressDto address;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Valid
    private CompanyCodeDto companyCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Valid
    private GeoLocationDto geoLocation;


    public StoreDto() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public CompanyCodeDto getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(CompanyCodeDto companyCode) {
        this.companyCode = companyCode;
    }

    public GeoLocationDto getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(GeoLocationDto geoLocation) {
        this.geoLocation = geoLocation;
    }
}
