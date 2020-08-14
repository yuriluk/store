package org.example.store.repository;

import org.example.store.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long>, JpaSpecificationExecutor<Store> {

    Optional<Store> findByNameIgnoreCase(String name);
}
