package org.example.store.service.dto.mapper;

import org.example.store.model.Address;
import org.example.store.service.dto.AddressDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper extends AbstractMapper<Address, AddressDto> {

    @Autowired
    public AddressMapper() {
        super(Address.class, AddressDto.class);
    }

}
