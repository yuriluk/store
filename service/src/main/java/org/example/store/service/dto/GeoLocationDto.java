package org.example.store.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.example.store.service.validation.BigDecimalRange;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoLocationDto extends AbstractDto {

    @NotNull(message = "Latitude can`t be null!")
    @BigDecimalRange(minPrecision = 2, maxPrecision = 17, scale = 15)
    private BigDecimal latitude;

    @NotNull(message = "Longitude can`t be null!")
    @BigDecimalRange(minPrecision = 2, maxPrecision = 17, scale = 15)
    private BigDecimal longitude;


    public GeoLocationDto() {
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
}
