package org.example.store.service;


import org.example.store.service.dto.PageWrapper;
import org.example.store.service.dto.Paging;
import org.example.store.service.dto.StoreDto;

public interface StoreService extends CrudService<StoreDto> {

    PageWrapper<StoreDto> findByCompanyCode(Paging paging, String companyCode);
}
