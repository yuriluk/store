package org.example.store.repository;

import org.example.store.model.Address;
import org.example.store.model.CompanyCode;
import org.example.store.model.GeoLocation;
import org.example.store.model.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long>, JpaSpecificationExecutor<Store> {

    Optional<Store> findByName(String name);

    Optional<Store> findByNameIgnoreCase(String name);

    Optional<Store> findByPhoneNumber(String phoneNumber);

    Optional<Store> findByAddress(Address address);

    Page<Store> findByCompanyCode(CompanyCode companyCode, Pageable pageable);

    Optional<Store> findByGeoLocation(GeoLocation geoLocation);

}
