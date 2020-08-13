package org.example.store.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoLocationDto extends AbstractDto {

    @NotNull(message = "Latitude can`t be null!")
    @Digits(integer = 2, fraction = 15, message = "Latitude could be in format (+/-)[xx].[up to 15 digits]")
    private double latitude;

    @NotNull(message = "Longitude can`t be null!")
    @Digits(integer = 2, fraction = 15, message = "Longitude could be in format (+/-)[xx].[up to 15 digits]")
    private double longitude;

    public GeoLocationDto() {
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
