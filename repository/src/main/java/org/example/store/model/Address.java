package org.example.store.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "addresses", schema = "public")
public class Address extends AbstractEntity {

    @Column(name = "street", nullable = false, length = 85)
    private String street;

    @Column(name = "houseNumber", nullable = false, length = 10)
    private Integer houseNumber;

    @Column(name = "postalCode", nullable = false, length = 10)
    private Integer postalCode;

    @Column(name = "city", nullable = false, length = 85)
    private String city;

    @Column(name = "countryCode", nullable = false, length = 3)
    private String countryCode;


    public Address() {
    }

    public Address(Long id) {
        super(id);
    }


    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }

    public Integer getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Integer postalCode) {
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Address other = (Address) o;
        return Objects.equals(street, other.street) &&
                Objects.equals(houseNumber, other.houseNumber) &&
                Objects.equals(postalCode, other.postalCode) &&
                Objects.equals(city, other.city) &&
                Objects.equals(countryCode, other.countryCode);
    }


    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), street, houseNumber, postalCode, city, countryCode);
    }


    @Override
    public String toString() {
        return "Address{" +
                super.toString() +
                ", street='" + street + '\'' +
                ", houseNumber=" + houseNumber +
                ", postalCode=" + postalCode +
                ", city='" + city + '\'' +
                ", countryCode=" + countryCode +
                '}';
    }
}
