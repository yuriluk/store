package org.example.store.service.impl;

import org.example.store.service.StoreService;
import org.example.store.service.dto.AddressDto;
import org.example.store.service.dto.CompanyCodeDto;
import org.example.store.service.dto.GeoLocationDto;
import org.example.store.service.dto.StoreDto;
import org.example.store.service.exception.ResourceNotFoundException;
import org.example.store.service.exception.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
@ContextConfiguration(classes = AppConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StoreServiceIntegrationTest {

    @Autowired
    private StoreService service;

    private StoreDto expectedDto;


    @Test
    public void testFindById_correctId() {
        StoreDto storeDto = service.findById(1L);
        expectedDto.setId(1L);
        assertNotNull(storeDto);
        Assertions.assertEquals(expectedDto.getId(), storeDto.getId());
    }


    @Test
    public void testFindById_wrongId() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.findById(99L));
    }


    @Test
    public void testAdd_correct() {
        expectedDto.setName("Test_Shop_2");
        StoreDto actual = service.save(expectedDto);
        assertNotNull(actual);
        assertEquals(expectedDto.getName(), actual.getName());
    }


    @Test
    public void testAdd_existStore() {
        Assertions.assertThrows(ServiceException.class, () -> service.save(expectedDto));
    }


    @Test
    public void testDelete_correctId() {
        Long givenId = 1L;
        service.delete(givenId);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.findById(givenId));
    }


    @Test
    public void testDelete_wrongId() {
        Long givenId = 777L;

        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.delete(givenId));

    }


    @Test
    public void testUpdate_correct() {
        expectedDto.setId(1L);
        expectedDto.setName("Shop_2");

        StoreDto actual = service.update(expectedDto);

        assertNotNull(actual);
        assertEquals(expectedDto.getName(), actual.getName());
    }


    @Test
    public void testUpdate_badId() {
        expectedDto.setId(99L);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.update(expectedDto));
    }


    @Test
    public void testFindAll_correct() {
        List<StoreDto> storeList = service.findAll();

        assertNotNull(storeList);
        Assertions.assertTrue(storeList.size() > 0);
    }


    @BeforeEach
    public void setUp() {
        AddressDto addressDto = new AddressDto();
        addressDto.setStreet("Novatorskaya");
        addressDto.setHouseNumber(154);
        addressDto.setPostalCode(220000);
        addressDto.setCity("Minsk");
        addressDto.setCountryCode("BY");

        CompanyCodeDto companyCodeDto = new CompanyCodeDto();
        companyCodeDto.setCode("GA");

        GeoLocationDto geoLocationDto = new GeoLocationDto();
        geoLocationDto.setLatitude(new BigDecimal("53.908508"));
        geoLocationDto.setLongitude(new BigDecimal("27.432272"));

        expectedDto = new StoreDto();
        expectedDto.setName("Test shop");
        expectedDto.setPhoneNumber("+375291234567");
        expectedDto.setAddress(addressDto);
        expectedDto.setCompanyCode(companyCodeDto);
        expectedDto.setGeoLocation(geoLocationDto);
    }
}
