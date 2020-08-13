package org.example.store.service.dto.mapper;

import org.example.store.model.GeoLocation;
import org.example.store.service.dto.GeoLocationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GeoLocationMapper extends AbstractMapper<GeoLocation, GeoLocationDto> {

    @Autowired
    public GeoLocationMapper() {
        super(GeoLocation.class, GeoLocationDto.class);
    }

}
