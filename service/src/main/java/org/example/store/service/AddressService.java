package org.example.store.service;


import org.example.store.service.dto.AddressDto;

import java.util.List;

public interface AddressService extends CrudService<AddressDto> {
    List<AddressDto> findAll();
}
