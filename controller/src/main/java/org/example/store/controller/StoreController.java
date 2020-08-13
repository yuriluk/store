package org.example.store.controller;

import org.example.store.service.StoreService;
import org.example.store.service.dto.Paging;
import org.example.store.service.dto.StoreDto;
import org.example.store.service.validation.CustomNullableNotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import static org.example.store.controller.ControllerHelper.checkBindingResultAndThrowExceptionIfInvalid;


@Validated
@RestController
@RequestMapping("/stores")
@CrossOrigin(origins = "http://localhost:3000")
public class StoreController {

    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreDto> findById(@PathVariable @Positive Long id) {
        return new ResponseEntity<>(storeService.findById(id), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<StoreDto> add(@RequestBody @Valid StoreDto storeDto,
                                        BindingResult result) {
        checkBindingResultAndThrowExceptionIfInvalid(result);
        StoreDto store = storeService.save(storeDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(store.getId()).toUri());
        return new ResponseEntity<>(store, httpHeaders, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<StoreDto> update(@PathVariable @Positive Long id,
                                            @RequestBody @Valid StoreDto storeDto,
                                            BindingResult result) {
        checkBindingResultAndThrowExceptionIfInvalid(result);
        storeDto.setId(id);
        return new ResponseEntity<>(storeService.update(storeDto), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive Long id) {
        storeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/byCompanyCode")
    public ResponseEntity<?> findByCompanyCode(
            @RequestParam(defaultValue = "10", value = "size") Integer size,
            @RequestParam(defaultValue = "0", value = "page") Integer page,
            @RequestParam(defaultValue = "", value = "companyCode") String companyCode) {

        Paging paging = new Paging(size, page);

        return new ResponseEntity<>(storeService.findByCompanyCode(paging, companyCode), HttpStatus.OK);
    }


    @GetMapping("/sortedByDistance")
    public ResponseEntity<?> findByCompanyCodeAndSortedByDistance(
            @RequestParam(defaultValue = "0", value = "pageNo") Integer pageNo,
            @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize,
            @RequestParam(defaultValue = "id", value = "sortBy") String sortBy,
            @RequestParam(defaultValue = "", value = "companyCode") String companyCode,
            @RequestParam(defaultValue = "0.0", value = "latitude") Double latitude,
            @RequestParam(defaultValue = "0.0", value = "longitude") Double longitude) {

        return new ResponseEntity<>(
                storeService
                        .findByCompanyCodeAndSortedByDistance(pageNo, pageSize, sortBy, companyCode, latitude, longitude),
                HttpStatus.OK);
    }
}
