package org.example.store.repository;

import org.example.store.model.Address;
import org.example.store.model.CompanyCode;
import org.example.store.model.GeoLocation;
import org.example.store.model.Store;
import org.example.store.repository.configuration.AppConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@ContextConfiguration(classes = AppConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StoreRepositoryTest {

    @Autowired
    private StoreRepository storeRepository;

    @Test
    void testFindByNameIgnoreCase_correct() {
        String name = "Test shop";
        Store expected = new Store(1L);

        Optional<Store> actual = storeRepository.findByNameIgnoreCase(name);

        assertTrue(actual.isPresent());
        assertEquals(expected.getId(), actual.get().getId());
    }


    @Test
    public void testFindByNameIgnoreCase_wrong() {
        String wrongName = "wrong";
        Optional<Store> actual = storeRepository.findByNameIgnoreCase(wrongName);

        assertFalse(actual.isPresent());
    }

    @Test
    public void testAdd_correct() {
        Address address = new Address();
        address.setStreet("Novatorskaya");
        address.setHouseNumber(154);
        address.setPostalCode(220000);
        address.setCity("Minsk");
        address.setCountryCode("BY");

        CompanyCode companyCode = new CompanyCode();
        companyCode.setCode("GA");

        GeoLocation location = new GeoLocation();
        location.setLatitude(new BigDecimal("53.908508"));
        location.setLongitude(new BigDecimal("27.432272"));

        Store given = new Store();
        given.setName("Test shop");
        given.setPhoneNumber("+375291234567");
        given.setAddress(address);
        given.setCompanyCode(companyCode);
        given.setGeoLocation(location);

        Store saved = storeRepository.save(given);
        assertNotNull(saved);
        assertEquals(given, saved);
    }


    @Test
    public void testDelete_correctId() {
        Long givenId = 1L;
        storeRepository.findById(givenId).ifPresent(store -> storeRepository.delete(store));

        assertFalse(storeRepository.findById(givenId).isPresent());
    }


    @Test
    public void testDelete_wrongId() {
        Store given = new Store(777L);

        storeRepository.delete(given);
    }

    @Test
    public void testFindById_correctId() {
        Address address = new Address();
        address.setStreet("Novatorskaya");
        address.setHouseNumber(154);
        address.setPostalCode(220000);
        address.setCity("Minsk");
        address.setCountryCode("BY");

        CompanyCode companyCode = new CompanyCode();
        companyCode.setCode("GA");

        GeoLocation location = new GeoLocation();
        location.setLatitude(new BigDecimal("53.908508"));
        location.setLongitude(new BigDecimal("27.432272"));

        Store expected = new Store(1L);
        expected.setName("Test shop");
        expected.setPhoneNumber("+375291234567");
        expected.setAddress(address);
        expected.setCompanyCode(companyCode);
        expected.setGeoLocation(location);


        Optional<Store> actual = storeRepository.findById(expected.getId());

        assertTrue(actual.isPresent());
        assertEquals(expected.getId(), actual.get().getId());
    }


    @Test
    public void testFindById_wrongId() {
        Store given = new Store(777L);
        Optional<Store> actual = storeRepository.findById(given.getId());

        assertFalse(actual.isPresent());
    }

    @Test
    public void testUpdate_correct() {
        Address address = new Address(1L);
        address.setStreet("Novatorskaya");
        address.setHouseNumber(154);
        address.setPostalCode(220000);
        address.setCity("Minsk");
        address.setCountryCode("BY");

        CompanyCode companyCode = new CompanyCode(1L);
        companyCode.setCode("GA");

        GeoLocation location = new GeoLocation(1L);
        location.setLatitude(new BigDecimal("53.908508"));
        location.setLongitude(new BigDecimal("27.432272"));

        Store given = new Store(1L);
        given.setName("Test shop");
        given.setPhoneNumber("+375291234567");
        given.setAddress(address);
        given.setCompanyCode(companyCode);
        given.setGeoLocation(location);

        Store expected = new Store(1L);
        expected.setName("Test shop");
        expected.setPhoneNumber("+375291234567");
        expected.setAddress(address);
        expected.setCompanyCode(companyCode);
        expected.setGeoLocation(location);

        Store actual = storeRepository.save(given);

        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
    }


    @Test
    public void testUpdate_badId() {
        Address address = new Address(1L);
        address.setStreet("Novatorskaya");
        address.setHouseNumber(154);
        address.setPostalCode(220000);
        address.setCity("Minsk");
        address.setCountryCode("BY");

        CompanyCode companyCode = new CompanyCode(1L);
        companyCode.setCode("GA");

        GeoLocation location = new GeoLocation(1L);
        location.setLatitude(new BigDecimal("53.908508"));
        location.setLongitude(new BigDecimal("27.432272"));

        Store given = new Store(999L);
        given.setName("Test shop");
        given.setPhoneNumber("+375291234567");
        given.setAddress(address);
        given.setCompanyCode(companyCode);
        given.setGeoLocation(location);

        Store actual = storeRepository.save(given);

        assertNotNull(actual);
    }


    @Test
    public void testFindAll_correct() {
        List<Store> storeList = storeRepository.findAll();

        assertNotNull(storeList);
        assertTrue(storeList.size() > 0);
    }
}
