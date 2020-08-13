package org.example.store.repository.specification;

import org.example.store.model.Store;
import org.springframework.data.jpa.domain.Specification;

public final class StoreSpecification {

    private StoreSpecification() {
    }

    public static Specification<Store> findByCompanyCodeLike(String companyCode) {
        return (Specification<Store>) (root, query, criteriaBuilder) ->
                criteriaBuilder
                        .like(root.get("companyCode").get("code"), '%' + companyCode.toUpperCase() + '%');
    }


    public static Specification<Store> findByGeoLocationLatitude(Double latitude) {
        return (Specification<Store>) (root, query, criteriaBuilder) ->
                criteriaBuilder
                        .equal(root.get("geoLocation").get("latitude"), latitude);
    }


    public static Specification<Store> findByGeoLocationLongitude(Double longitude) {
        return (Specification<Store>) (root, query, criteriaBuilder) ->
                criteriaBuilder
                        .equal(root.get("geoLocation").get("longitude"), longitude);
    }
}