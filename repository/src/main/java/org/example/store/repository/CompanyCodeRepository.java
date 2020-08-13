package org.example.store.repository;

import org.example.store.model.CompanyCode;
import org.example.store.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyCodeRepository extends JpaRepository<CompanyCode, Long> {

    Optional<CompanyCode> findByCode(String code);

    Optional<CompanyCode> findByCodeIgnoreCase(String code);
}
