package org.example.store.controller;

import org.assertj.core.api.Assertions;
import org.example.store.configuration.Application;
import org.example.store.service.StoreService;
import org.example.store.service.dto.*;
import org.example.store.service.exception.ResourceNotFoundException;
import org.example.store.util.TestUtil;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith({SpringExtension.class})
@WebMvcTest(controllers = StoreController.class)
@ContextConfiguration(classes = Application.class)
public class StoreControllerUnitTest {

    private final static String URI = "/stores";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StoreService service;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private StoreDto expectedDto;

    @BeforeEach
    void setUp() {
        Mockito.reset(service);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        AddressDto addressDto = new AddressDto();
        addressDto.setStreet("Novatorskaya");
        addressDto.setHouseNumber(154);
        addressDto.setPostalCode(220000);
        addressDto.setCity("Minsk");
        addressDto.setCountryCode("BY");
        addressDto.setId(1L);

        CompanyCodeDto companyCodeDto = new CompanyCodeDto();
        companyCodeDto.setCode("GA");
        companyCodeDto.setId(1L);

        GeoLocationDto geoLocationDto = new GeoLocationDto();
        geoLocationDto.setLatitude(new BigDecimal("53.908508"));
        geoLocationDto.setLongitude(new BigDecimal("27.432272"));
        geoLocationDto.setId(1L);

        expectedDto = new StoreDto();
        expectedDto.setName("Test shop");
        expectedDto.setPhoneNumber("+375291234567");
        expectedDto.setAddress(addressDto);
        expectedDto.setCompanyCode(companyCodeDto);
        expectedDto.setGeoLocation(geoLocationDto);
        expectedDto.setId(1L);
    }


    @Test
    @DisplayName("Should find store and return")
    public void testFindById_Positive() throws Exception {
        given(service.findById(expectedDto.getId())).willReturn(expectedDto);
        this.mockMvc
                .perform(get("/stores/{id}", 1)
                        .accept(TestUtil.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(content().json(TestUtil.convertObjectToJsonString(expectedDto)));

        verify(service, times(1)).findById(1L);
        verifyNoMoreInteractions(service);
    }


    @Test
    @DisplayName("Should not find store and throw exception")
    public void testFindById_Negative() throws Exception {
        given(service.findById(99L)).willThrow(ResourceNotFoundException.class);
        this.mockMvc
                .perform(get("/stores/{id}", 99)
                        .accept(TestUtil.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$..*", Matchers.contains("Resource not found! ")));

        verify(service, times(1)).findById(99L);
        verifyNoMoreInteractions(service);
    }


    @Test
    @DisplayName("Should add Store and return added entry")
    public void testAdd_Store_Positive() throws Exception {
        when(service.save(any(StoreDto.class))).thenReturn(expectedDto);

        this.mockMvc
                .perform(post(URI)
                        .contentType(TestUtil.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(expectedDto))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Test shop")))
                .andExpect(jsonPath("$.phoneNumber", is("+375291234567")))
                .andReturn();

        ArgumentCaptor<StoreDto> dtoCaptor = ArgumentCaptor.forClass(StoreDto.class);
        verify(service, times(1)).save(dtoCaptor.capture());
        verifyNoMoreInteractions(service);

        StoreDto dtoArgument = dtoCaptor.getValue();
        assertEquals(1L, dtoArgument.getId());
        assertThat(dtoArgument.getName(), is("Test shop"));
        assertThat(dtoArgument.getPhoneNumber(), is("+375291234567"));
    }


    @Test
    @DisplayName("test Add Store - Name and Phone Number are too long -Should Return Validation Errors")
    public void testAdd_NameAndPhoneNumberAreTooLong() throws Exception {
        String name = TestUtil.createStringWithLength(59);
        String phoneNumber = TestUtil.createStringWithLength(16);

        expectedDto.setName(name);
        expectedDto.setPhoneNumber(phoneNumber);

        MvcResult mvcResult = mockMvc.perform(post(URI)
                .contentType(TestUtil.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(expectedDto))
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(TestUtil.APPLICATION_JSON))
                .andReturn();
        String resultString = mvcResult.getResponse().getContentAsString();

        assertTrue(resultString.contains("Name must be more then 2 symbols, and less or equal 58"));
        assertTrue(resultString.contains("Phone number must be more then 11 symbols, and less or equal 15"));
        verifyNoMoreInteractions(service);
    }


    @Test
    @DisplayName("Should update Store and return updated entry")
    public void testUpdate_Store_Positive() throws Exception {
        when(service.update(any(StoreDto.class))).thenReturn(expectedDto);

        this.mockMvc
                .perform(put("/stores/{id}", 1)
                        .contentType(TestUtil.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(expectedDto))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Test shop")))
                .andExpect(jsonPath("$.phoneNumber", is("+375291234567")));

        ArgumentCaptor<StoreDto> dtoCaptor = ArgumentCaptor.forClass(StoreDto.class);
        verify(service, times(1)).update(dtoCaptor.capture());
        verifyNoMoreInteractions(service);

        StoreDto dtoArgument = dtoCaptor.getValue();
        assertEquals(1L, dtoArgument.getId());
        assertThat(dtoArgument.getName(), is("Test shop"));
        assertThat(dtoArgument.getPhoneNumber(), is("+375291234567"));
    }


    @Test
    @DisplayName("Try to update store with not exists Id throw exception")
    public void testUpdate_Store_Negative() throws Exception {
        expectedDto.setId(99L);
        when(service.update(any(StoreDto.class))).thenThrow(ResourceNotFoundException.class);

        MvcResult mvcResult = mockMvc
                .perform(put("/stores/{id}", 999)
                        .contentType(TestUtil.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(expectedDto)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON))
                .andExpect(jsonPath("$..*", Matchers.contains("Resource not found! ")))
                .andReturn();
        String resultString = mvcResult.getResponse().getContentAsString();
        assertTrue(resultString.contains("Resource not found!"));
    }


    @Test
    @DisplayName("Should delete store and return status No_content")
    public void testDelete_Positive() throws Exception {
        doNothing().when(service).delete(1L);
        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/stores/{id}", 1))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(service, times(1)).delete(1L);
        verifyNoMoreInteractions(service);
    }

    @Test
    @DisplayName("Should not delete store and throw exception")
    public void testDelete_Negative() throws Exception {
        doThrow(ResourceNotFoundException.class).when(service).delete(99L);

        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/stores/{id}", 99))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$..*", Matchers.contains("Resource not found! ")));

        verify(service, times(1)).delete(99L);
        verifyNoMoreInteractions(service);
    }


    @Test
    void testFindAllStores() throws Exception {
        List<StoreDto> stores = Collections.singletonList(expectedDto);
        given(service.findAll()).willReturn(stores);

        this.mockMvc
                .perform(get(URI)
                        .contentType(TestUtil.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Test shop")))
                .andExpect(content().string("[" + TestUtil.convertObjectToJsonString(expectedDto) + "]"));


        verify(service, times(1)).findAll();
        verifyNoMoreInteractions(service);
    }


    @Test
    void testFindAllStores_WhenNothingPassedAsParam() throws Exception {
        int pageNo = 0;
        int pageSize = 10;
        String sortBy = "id";
        String companyCode = "";
        List<StoreDto> stores = Collections.singletonList(expectedDto);
        PageWrapper<StoreDto> pageWrapper = PageWrapper.of(stores, 1, 1, pageNo, 1);

        when(service.findByCompanyCode(anyInt(), anyInt(), anyString(), anyString())).thenReturn(pageWrapper);

        this.mockMvc
                .perform(get(URI + "/by-company-code")
                        .contentType(TestUtil.APPLICATION_JSON)
                        .param("pageNo", String.valueOf(pageNo))
                        .param("pageSize", String.valueOf(pageSize))
                        .param("sortBy", sortBy)
                        .param("companyCode", companyCode)
                )
                .andDo(print())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(TestUtil.convertObjectToJsonString(pageWrapper)))
                .andReturn();

        verify(service, times(1)).findByCompanyCode(pageNo, pageSize, sortBy, companyCode);
        verifyNoMoreInteractions(service);
    }


    @Test
    public void testFindStoreByCompanyCode() throws Exception {
        int pageNo = 0;
        int pageSize = 10;
        String sortBy = "id";
        String companyCode = "GA";

        List<StoreDto> stores = Collections.singletonList(expectedDto);
        PageWrapper<StoreDto> expectedResponseBody = PageWrapper.of(stores, 1, 1, pageNo, 1);

        when(service.findByCompanyCode(anyInt(), anyInt(), anyString(), anyString())).thenReturn(expectedResponseBody);

        MvcResult mvcResult = mockMvc
                .perform(get(URI + "/by-company-code")
                        .contentType(TestUtil.APPLICATION_JSON)
                        .param("pageNo", String.valueOf(pageNo))
                        .param("pageSize", String.valueOf(pageSize))
                        .param("sortBy", sortBy)
                        .param("companyCode", companyCode)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON))
                .andReturn();

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBodyString = TestUtil.convertObjectToJsonString(expectedResponseBody);

        Assertions.assertThat(actualResponseBody).isEqualToIgnoringWhitespace(expectedResponseBodyString);

        verify(service, times(1)).findByCompanyCode(pageNo, pageSize, sortBy, companyCode);
        verifyNoMoreInteractions(service);
    }


    @Test
    public void findByCompanyCodeAndSortedByDistance() throws Exception {
        int pageNo = 0;
        int pageSize = 10;
        String sortBy = "id";
        String companyCode = "GA";
        double latitude = 53.908508;
        double longitude = 27.432272;
        List<StoreDto> stores = Collections.singletonList(expectedDto);
        PageWrapper<StoreDto> pageWrapper = PageWrapper.of(stores, 1, 1, pageNo, 1);

        when(service
                .findByCompanyCodeAndSortedByDistance(anyInt(), anyInt(), anyString(), anyString(), anyDouble(), anyDouble()))
                .thenReturn(pageWrapper);

        this.mockMvc
                .perform(get(URI + "/sorted-by-distance")
                        .contentType(TestUtil.APPLICATION_JSON)
                        .param("pageNo", String.valueOf(pageNo))
                        .param("pageSize", String.valueOf(pageSize))
                        .param("sortBy", sortBy)
                        .param("companyCode", companyCode)
                        .param("latitude", String.valueOf(53.908508))
                        .param("longitude", String.valueOf(27.432272))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON))
                .andExpect(content().string(TestUtil.convertObjectToJsonString(pageWrapper)))
                .andReturn();

        verify(service, times(1)).findByCompanyCodeAndSortedByDistance(pageNo, pageSize, sortBy,
                companyCode, latitude, longitude);
        verifyNoMoreInteractions(service);
    }
}
