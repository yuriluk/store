package org.example.store.controller;

import org.example.store.configuration.Application;
import org.example.store.service.dto.*;
import org.example.store.util.TestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class StoreControllerIntegrationTest {


    @Autowired
    private TestRestTemplate restTemplate;

    private AddressDto addressDto;
    private CompanyCodeDto companyCodeDto;
    private GeoLocationDto geoLocationDto;
    private StoreDto expectedDto;


    @Test
    public void testFindByIdWhenExist() {
        ResponseEntity<StoreDto> responseEntity = restTemplate.getForEntity("/stores/1", StoreDto.class);

        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testFindByIdWhenDoesNotExist() {
        ResponseEntity<StoreDto> responseEntity = restTemplate.getForEntity("/stores/10", StoreDto.class);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testAddStore_positive() {
        expectedDto.setName("Test_Shop58");
        expectedDto.setId(1L);
        companyCodeDto.setId(1L);
        addressDto.setId(1L);
        geoLocationDto.setId(1L);
        HttpEntity<StoreDto> httpEntity = new HttpEntity<>(expectedDto);
        ResponseEntity<StoreDto> responseEntity = restTemplate.exchange("/stores", HttpMethod.POST, httpEntity, StoreDto.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(Objects.requireNonNull(responseEntity.getBody()).getName()).isEqualTo(expectedDto.getName());
    }

    @Test
    public void testAddStore_negative() throws Exception {
        String jsonString = TestUtil.convertObjectToJsonString(expectedDto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);

        ResponseEntity<String> response = restTemplate.exchange("/stores", HttpMethod.POST, entity, String.class);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testUpdate_positive() {
        expectedDto.setId(1L);
        expectedDto.setName("Testing update");
        companyCodeDto.setId(1L);
        addressDto.setId(1L);
        geoLocationDto.setId(1L);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<StoreDto> requestUpdate = new HttpEntity<>(expectedDto, headers);
        ResponseEntity<StoreDto> responseEntity = restTemplate.exchange("/stores/1", HttpMethod.PUT, requestUpdate, StoreDto.class);

        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testUpdate_negative() {
        expectedDto.setId(1L);
        companyCodeDto.setId(1L);
        addressDto.setId(1L);
        geoLocationDto.setId(1L);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<StoreDto> requestUpdate = new HttpEntity<>(expectedDto, headers);
        ResponseEntity<StoreDto> responseEntity = restTemplate.exchange("/stores/1", HttpMethod.PUT, requestUpdate, StoreDto.class);

        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testDeleteByIdWhenExist() {
        restTemplate.delete("/stores/", 1);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String jsonPayload = "";
        HttpEntity<String> entity = new HttpEntity<String>(jsonPayload, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange("/stores/1", HttpMethod.DELETE, entity, String.class);

        Assertions.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }


    @Test
    public void testDeleteByIdWhenDoesNotExist() {
        restTemplate.delete("/stores/100");

        ResponseEntity<StoreDto> responseEntity = restTemplate.getForEntity("/stores/100", StoreDto.class);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }


    @Test
    public void testFindByCompanyCodeWhenExists() {

        ResponseEntity<PageWrapper> responseEntity = restTemplate
                .getForEntity("/stores/by-company-code?companyCode=ga", PageWrapper.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(responseEntity.getBody().getObjects().get(0)).equals(expectedDto));
    }


    @Test
    public void testFindByCompanyCodeWhenDoesNotExist() {

        ResponseEntity<PageWrapper> responseEntity = restTemplate
                .getForEntity("/stores/by-company-code?companyCode=tt", PageWrapper.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertEquals(0, Objects.requireNonNull(responseEntity.getBody()).getTotalElements());
    }


    @Test
    public void testFindByCompanyCodeAndSortedByDistanceWhenExists() {

        ResponseEntity<PageWrapper> responseEntity = restTemplate
                .getForEntity("/stores/sorted-by-distance?companyCode=ga&latitude=53.908508&" +
                        "longitude=27.432272&sortBy=phoneNumber", PageWrapper.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(responseEntity.getBody().getObjects().get(0)).equals(expectedDto));
    }


    @Test
    public void testFindByCompanyCodeAndSortedByDistanceWhenDoesNotExist() {

        ResponseEntity<PageWrapper> responseEntity = restTemplate
                .getForEntity("/stores/sorted-by-distance?companyCode=tt&latitude=53.908508&" +
                        "longitude=27.432272&sortBy=phoneNumber", PageWrapper.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertEquals(0, Objects.requireNonNull(responseEntity.getBody()).getTotalElements());
    }


    @BeforeEach
    public void setup() {

        addressDto = new AddressDto();
        addressDto.setStreet("Novatorskaya");
        addressDto.setHouseNumber(154);
        addressDto.setPostalCode(220000);
        addressDto.setCity("Minsk");
        addressDto.setCountryCode("BY");

        companyCodeDto = new CompanyCodeDto();
        companyCodeDto.setCode("GA");

        geoLocationDto = new GeoLocationDto();
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
