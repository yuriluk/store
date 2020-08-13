package org.example.store.service.dto;

import javax.validation.constraints.NotNull;

public class GeoLocationDto extends AbstractDto {

    @NotNull(message = "Latitude can`t be null!")
    private double latitude;

    @NotNull(message = "Longitude can`t be null!")
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
