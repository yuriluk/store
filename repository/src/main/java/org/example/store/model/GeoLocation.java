package org.example.store.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "geo_locations")
public class GeoLocation extends AbstractEntity {

    @Column(name = "latitude", nullable = false, length = 17)
    private BigDecimal latitude;

    @Column(name = "longitude", nullable = false, length = 17)
    private BigDecimal longitude;


    public GeoLocation() {
    }

    public GeoLocation(Long id) {
        super(id);
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GeoLocation other = (GeoLocation) o;
        return Objects.equals(latitude, other.latitude) &&
                Objects.equals(longitude, other.longitude);
    }


    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), latitude, longitude);
    }


    @Override
    public String toString() {
        return "GeoLocation{" +
                super.toString() +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }
}
