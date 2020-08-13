package org.example.store.service.dto.mapper;

import org.example.store.model.Store;
import org.example.store.service.dto.StoreDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StoreMapper extends AbstractMapper<Store, StoreDto> {

    @Autowired
    public StoreMapper() {
        super(Store.class, StoreDto.class);
    }

}
