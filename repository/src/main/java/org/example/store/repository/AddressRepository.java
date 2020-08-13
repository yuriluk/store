package org.example.store.repository;


import org.example.store.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByStreet(String street);

    Optional<Address> findByHouseNumber(Integer houseNumber);

    Optional<Address> findByPostalCode(Integer postalCode);

    Optional<Address> findByCity(String city);

    Optional<Address> findByCountryCode(String countryCode);

    Optional<Address> findByCityAndStreetAndHouseNumber(String city, String street, Integer houseNumber);

}
