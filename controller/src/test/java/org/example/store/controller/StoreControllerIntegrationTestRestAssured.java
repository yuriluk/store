package org.example.store.controller;

import io.restassured.http.ContentType;
import io.restassured.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.specification.MockMvcRequestSpecBuilder;
import org.example.store.configuration.Application;
import org.example.store.exception.handler.DefaultExceptionHandler;
import org.example.store.service.dto.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class StoreControllerIntegrationTestRestAssured {

    public static final String STORES = "/stores/{id}";
    public static final String BASE_PATH = "/stores";
    public static final String BY_COMPANY_CODE = "/by-company-code";
    public static final String SORTED_BY_DISTANCE = "/sorted-by-distance";

    private AddressDto addressDto;
    private CompanyCodeDto companyCodeDto;
    private GeoLocationDto geoLocationDto;
    private StoreDto expectedDto;


    @Autowired
    private StoreController controller;
    @Autowired
    private DefaultExceptionHandler exceptionHandler;


    @Test
    public void testFindByIdWhenExists() {
        long id = 3L;

        given()
                .when()
                .get(STORES, id)
                .then()
                .log().ifError()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body("id", equalTo(3));
    }


    @Test
    public void testFindByIdWhenDoesNotExists() {
        long id = 99L;

        given()
                .when()
                .get(STORES, id)
                .then()
                .log().ifError()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }


    @Disabled
    @Test
    public void testAddStore_positive() {
        String newName = "Test_Shop888";
        expectedDto.setName(newName);

        RestAssuredMockMvc.requestSpecification = new MockMvcRequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBody(expectedDto)
                .build();


        StoreDto storeDto =
                given()
                        .accept(ContentType.JSON)
                        .contentType(ContentType.JSON)
                        .when()
                        .request("POST", BASE_PATH)
                        .then()
                        .log().ifError()
                        .assertThat()
                        .contentType(ContentType.JSON)
                        .statusCode(HttpStatus.CREATED.value())
                        .extract().body().as(StoreDto.class);


        assertThat(storeDto.getName()).isEqualTo(newName);
    }


    @Test
    public void testAddStore_negative() {
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(expectedDto)
                .when()
                .request("POST", BASE_PATH)
                .then()
                .log().ifError()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }


    @Test
    public void testUpdate_positive() {
        long id = 1L;
        expectedDto.setId(1L);
        expectedDto.setName("Testing update");
        companyCodeDto.setId(1L);
        addressDto.setId(1L);
        geoLocationDto.setId(1L);

        given().log().all()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(expectedDto)
                .when()
                .request("PUT", STORES, id)
                .then()
                .log().ifError()
                .assertThat()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo(expectedDto.getName()));

    }


    @Test
    public void testUpdate_negative() {
        long id = 100L;
        expectedDto.setId(1L);
        companyCodeDto.setId(1L);
        addressDto.setId(1L);
        geoLocationDto.setId(1L);

        RestAssuredMockMvc.requestSpecification = new MockMvcRequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBody(expectedDto)
                .build();

        given()
                .when()
                .request("PUT", STORES, id)
                .then()
                .log().ifError()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());

    }

    @Test
    public void testDeleteByIdWhenExist() {
        long id = 1L;

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when()
                .delete(STORES, id)
                .then()
                .log().ifError()
                .assertThat()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }


    @Test
    public void testDeleteByIdWhenDoesNotExist() {
        long id = 99L;

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when()
                .delete(STORES, id)
                .then()
                .log().ifError()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }


    @Test
    public void testFindByCompanyCodeWhenExists() {
        String code = "GA";
        RestAssuredMockMvc.requestSpecification = new MockMvcRequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addQueryParam("companyCode", code)
                .build();

        PageWrapper<StoreDto> storesWrapper =
                given()
                        .when()
                        .get(BASE_PATH + BY_COMPANY_CODE)
                        .then()
                        .log().ifError()
                        .assertThat().contentType(ContentType.JSON)
                        .statusCode(HttpStatus.OK.value())
                        .extract().body().as(new TypeRef<>() {
                });

        Assertions.assertNotNull(storesWrapper);
        assertThat(storesWrapper.getObjects().get(0).getCompanyCode().getCode()).isEqualTo(code);
    }


    @Test
    public void testFindByCompanyCodeWhenDoesNotExist() {
        String code = "tt";

        RestAssuredMockMvc.requestSpecification = new MockMvcRequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addQueryParam("companyCode", code)
                .build();

        PageWrapper<StoreDto> storesWrapper =
                given()
                        .when()
                        .get(BASE_PATH + BY_COMPANY_CODE)
                        .then()
                        .log().ifError()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .extract().body().as(new TypeRef<>() {
                });

        Assertions.assertNotNull(storesWrapper);
        assertThat(storesWrapper.getElementsCount()).isEqualTo(0);
    }


    @Test
    public void testFindByCompanyCodeAndSortedByDistanceWhenExists() {
        String code = "GA";
        double latitude = 53.908508;
        double longitude = 27.432272;
        String sortBy = "phoneNumber";


        RestAssuredMockMvc.requestSpecification = new MockMvcRequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addQueryParam("companyCode", code)
                .addQueryParam("latitude", latitude)
                .addQueryParam("longitude", longitude)
                .addQueryParam("sortBy", sortBy)
                .build();

        PageWrapper<StoreDto> storesWrapper =
                given()
                        .when()
                        .get(BASE_PATH + SORTED_BY_DISTANCE)
                        .then()
                        .log().ifError()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .extract().body().as(new TypeRef<>() {
                });

        Assertions.assertNotNull(storesWrapper);
        assertThat(storesWrapper.getObjects().get(0).getCompanyCode().getCode()).isEqualTo(code);
    }


    @Test
    public void testFindByCompanyCodeAndSortedByDistanceWhenDoesNotExist() {
        String code = "tt";
        double latitude = 53.908508;
        double longitude = 27.432272;
        String sortBy = "phoneNumber";

        RestAssuredMockMvc.requestSpecification = new MockMvcRequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addQueryParam("companyCode", code)
                .addQueryParam("latitude", latitude)
                .addQueryParam("longitude", longitude)
                .addQueryParam("sortBy", sortBy)
                .build();

        PageWrapper<StoreDto> storesWrapper =
                given()
                        .when()
                        .get(BASE_PATH + SORTED_BY_DISTANCE)
                        .then()
                        .log().ifError()
                        .assertThat()
                        .statusCode(HttpStatus.OK.value())
                        .extract().body().as(new TypeRef<>() {
                });

        Assertions.assertNotNull(storesWrapper);
        Assertions.assertEquals(0, storesWrapper.getTotalElements());
    }


    @BeforeEach
    public void setup() {

        RestAssuredMockMvc.reset();
        RestAssuredMockMvc.standaloneSetup(controller, exceptionHandler);


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

