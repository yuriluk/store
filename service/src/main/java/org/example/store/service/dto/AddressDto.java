package org.example.store.service.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressDto extends AbstractDto {

    @NotBlank(message = "Street can`t be null and spaces!")
    @Pattern(regexp = ".{2,85}", message = "Street must be more then 2 symbols, but smaller than 85")
    private String street;

    @NotNull(message = "House number can`t be null!")
    @Min(value = 1, message = "Min value for House number of things is 1")
    @Positive(message = "House number must be positive")
    private int houseNumber;

    @NotNull(message = "Postal code can`t be null!")
    @Min(value = 0, message = "Min value for postal code of things is 0")
    @Positive(message = "Postal code must be positive")
    private int postalCode;

    @NotBlank(message = "City can`t be null and spaces!")
    @Pattern(regexp = ".{2,85}", message = "City must be more then 2 symbols, but smaller than 85")
    private String city;

    @NotBlank(message = "Country code  can`t be null and spaces!")
    @Pattern(regexp = ".{2,3}", message = "Country code  must be more then 2 symbols, but smaller than 3")
    private String countryCode;

    public AddressDto() {
    }


    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
