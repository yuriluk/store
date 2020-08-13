package org.example.store.service;


import org.example.store.service.dto.GeoLocationDto;

import java.util.List;

public interface GeoLocationService extends CrudService<GeoLocationDto> {
    List<GeoLocationDto> findAll();
}
