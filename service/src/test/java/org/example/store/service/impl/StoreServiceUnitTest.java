package org.example.store.service.impl;

import org.example.store.model.Address;
import org.example.store.model.CompanyCode;
import org.example.store.model.GeoLocation;
import org.example.store.model.Store;
import org.example.store.repository.AddressRepository;
import org.example.store.repository.CompanyCodeRepository;
import org.example.store.repository.GeoLocationRepository;
import org.example.store.repository.StoreRepository;
import org.example.store.service.dto.*;
import org.example.store.service.dto.mapper.StoreMapper;
import org.example.store.service.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StoreServiceUnitTest {

    @Mock
    private StoreMapper storeMapper;
    @Mock
    private StoreRepository storeRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private CompanyCodeRepository companyCodeRepository;
    @Mock
    private GeoLocationRepository geoLocationRepository;

    @InjectMocks
    private StoreServiceImpl service;


    private Store store;
    private StoreDto expectedDto;
    private Address address;
    private CompanyCode companyCode;
    private GeoLocation location;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        address = new Address();
        address.setStreet("Novatorskaya");
        address.setHouseNumber(154);
        address.setPostalCode(220000);
        address.setCity("Minsk");
        address.setCountryCode("BY");

        companyCode = new CompanyCode();
        companyCode.setCode("GA");

        location = new GeoLocation();
        location.setLatitude(new BigDecimal("53.908508"));
        location.setLongitude(new BigDecimal("27.432272"));

        store = new Store(1L);
        store.setName("Test shop");
        store.setPhoneNumber("+375291234567");
        store.setAddress(address);
        store.setCompanyCode(companyCode);
        store.setGeoLocation(location);

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


    @Test
    @DisplayName("Test findById Success")
    public void testFindByIdPositive() {
        expectedDto.setId(1L);

        when(storeRepository.findById(1L)).thenReturn(Optional.of(store));
        when(storeMapper.toDto(store)).thenReturn(expectedDto);

        StoreDto actualDto = service.findById(1L);
        expectedDto.setId(1L);

        Assertions.assertSame(expectedDto, actualDto, "The Store returned was not the same as the mock");
        verify(storeRepository, times(1)).findById(any(Long.class));
    }


    @Test
    @DisplayName("Test findById Not Found")
    public void testFindByIdNotFound() {
        when(storeRepository.findById(99L)).thenThrow(ResourceNotFoundException.class);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.findById(99L));
    }


    @Test
    @DisplayName("Test delete store Success")
    public void testDeleteStorePositive() {
        when(storeRepository.findById(anyLong())).thenReturn(Optional.of(store));
        doNothing().when(storeRepository).delete(store);

        service.delete(store.getId());
        verify(storeRepository, times(1)).delete(any(Store.class));
    }


    @Test
    @DisplayName("Test delete store Throws Exception")
    public void testDeleteStoreThrows() {
        when(storeRepository.findById(anyLong())).thenThrow(ResourceNotFoundException.class);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.findById(1L));
    }


    @Test
    @DisplayName("Test save store")
    public void testSavePositive() {
        expectedDto.setId(1L);

        when(storeMapper.toEntity(any(StoreDto.class))).thenReturn(store);
        when(storeRepository.findByNameIgnoreCase(anyString())).thenReturn(Optional.empty());
        when(addressRepository.findById(anyLong())).thenReturn(Optional.of(address));
        when(companyCodeRepository.findByCodeIgnoreCase(anyString())).thenReturn(Optional.of(companyCode));
        when(geoLocationRepository.findById(anyLong())).thenReturn(Optional.of(location));
        when(storeRepository.save(any(Store.class))).thenReturn(store);
        when(storeMapper.toDto(any(Store.class))).thenReturn(expectedDto);

        StoreDto returnedStore = service.save(expectedDto);
        Assertions.assertNotNull(returnedStore, "The saved store should not be null");
        assertEquals(expectedDto.getId(), returnedStore.getId(), "The id should be 1");
    }


    @Test
    public void testUpdateStorePositive() {
        expectedDto.setId(1L);
        when(storeRepository.findById(anyLong())).thenReturn(Optional.of(store));
        when(storeMapper.toEntity(any(StoreDto.class))).thenReturn(store);
        when(storeRepository.findByNameIgnoreCase(anyString())).thenReturn(Optional.of(store));
        when(addressRepository.findById(anyLong())).thenReturn(Optional.of(address));
        when(companyCodeRepository.findByCodeIgnoreCase(anyString())).thenReturn(Optional.of(companyCode));
        when(geoLocationRepository.findById(anyLong())).thenReturn(Optional.of(location));
        when(storeRepository.save(any(Store.class))).thenReturn(store);
        when(storeMapper.toDto(any(Store.class))).thenReturn(expectedDto);

        service.update(expectedDto);
    }

    @Test
    public void testUpdateStoreNegative() {
        when(storeRepository.findById(anyLong())).thenThrow(ResourceNotFoundException.class);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.findById(99L));
    }

    @Test
    @DisplayName("Test findAll")
    public void testFindAll() {
        when(storeRepository.findAll()).thenReturn(Collections.singletonList(store));
        when(storeMapper.toDtoList(anyList())).thenReturn(Collections.singletonList(expectedDto));

        List<StoreDto> stores = service.findAll();

        Assertions.assertEquals(1, stores.size(), "findAll should return 1 store");
    }

    @Test
    void findByCompanyCode() {
        int pageNo = 0;
        int pageSize = 10;
        String sortBy = "id";
        String companyCode = "GA";
        long total = 1;
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        Page<Store> page = new PageImpl<>(Collections.singletonList(store), pageable, total);

        when(storeRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        when(storeMapper.toDtoList(anyList())).thenReturn(Collections.singletonList(expectedDto));

        PageWrapper<StoreDto> stores = service.findByCompanyCode(pageNo, pageSize, sortBy, companyCode);

        Assertions.assertEquals(1, stores.getTotalElements(), "findAll should return 1 store");
    }


    @Test
    void findByCompanyCodeAndSortedByDistance() {
        int pageNo = 0;
        int pageSize = 10;
        String sortBy = "id";
        String companyCode = "GA";
        double latitude = 53.908508;
        double longitude = 27.432272;
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        long total = 1;
        Page<Store> page = new PageImpl<>(Collections.singletonList(store), pageable, total);

        when(storeRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        when(storeMapper.toDtoList(anyList())).thenReturn(Collections.singletonList(expectedDto));

        PageWrapper<StoreDto> stores = service.findByCompanyCodeAndSortedByDistance(pageNo, pageSize, sortBy,
                companyCode, latitude, longitude);

        Assertions.assertEquals(1, stores.getTotalElements(), "findAll should return 1 store");
    }
}
