package org.example.store.controller;

import org.example.store.service.StoreService;
import org.example.store.service.dto.StoreDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import java.net.URI;

import static org.example.store.controller.ControllerHelper.checkBindingResultAndThrowExceptionIfInvalid;


@Validated
@RestController
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreDto> findById(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(storeService.findById(id));
    }


    @PostMapping
    public ResponseEntity<StoreDto> add(@RequestBody @Valid StoreDto storeDto,
                                        BindingResult result) {
        checkBindingResultAndThrowExceptionIfInvalid(result);
        StoreDto store = storeService.save(storeDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(store.getId()).toUri();
        return ResponseEntity.created(location).body(store);
    }


    @PutMapping("/{id}")
    public ResponseEntity<StoreDto> update(@PathVariable @Positive Long id,
                                           @RequestBody @Valid StoreDto storeDto,
                                           BindingResult result) {
        checkBindingResultAndThrowExceptionIfInvalid(result);
        storeDto.setId(id);
        return ResponseEntity.ok(storeService.update(storeDto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive Long id) {
        storeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(storeService.findAll());
    }


    @GetMapping("/by-company-code")
    public ResponseEntity<?> findByCompanyCode(
            @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "companyCode", defaultValue = "") String companyCode) {

        return ResponseEntity.ok(storeService.findByCompanyCode(pageNo, pageSize, sortBy, companyCode));
    }


    @GetMapping("/sorted-by-distance")
    public ResponseEntity<?> findByCompanyCodeAndSortedByDistance(
            @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "companyCode", defaultValue = "") String companyCode,
            @RequestParam(value = "latitude", defaultValue = "0.0") Double latitude,
            @RequestParam(value = "longitude", defaultValue = "0.0") Double longitude) {

        return ResponseEntity.ok(storeService
                .findByCompanyCodeAndSortedByDistance(pageNo, pageSize, sortBy, companyCode, latitude, longitude));
    }
}
