package org.example.store.service;


import org.example.store.service.dto.PageWrapper;
import org.example.store.service.dto.StoreDto;

public interface StoreService extends CrudService<StoreDto> {

    PageWrapper<StoreDto> findByCompanyCode(Integer pageNo, Integer pageSize, String sortBy,String companyCode);

    PageWrapper<StoreDto> findByCompanyCodeAndSortedByDistance(Integer pageNo, Integer pageSize, String sortBy,
                                                               String companyCode, Double latitude, Double longitude);
}
