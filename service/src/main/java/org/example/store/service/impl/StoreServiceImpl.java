package org.example.store.service.impl;

import org.example.store.model.Address;
import org.example.store.model.CompanyCode;
import org.example.store.model.GeoLocation;
import org.example.store.model.Store;
import org.example.store.repository.AddressRepository;
import org.example.store.repository.CompanyCodeRepository;
import org.example.store.repository.GeoLocationRepository;
import org.example.store.repository.StoreRepository;
import org.example.store.service.StoreService;
import org.example.store.service.dto.PageWrapper;
import org.example.store.service.dto.Paging;
import org.example.store.service.dto.StoreDto;
import org.example.store.service.dto.mapper.StoreMapper;
import org.example.store.service.exception.ResourceNotFoundException;
import org.example.store.service.exception.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static org.example.store.repository.specification.StoreSpecification.*;


@Service
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;
    private final AddressRepository addressRepository;
    private final CompanyCodeRepository companyCodeRepository;
    private final GeoLocationRepository geoLocationRepository;


    public StoreServiceImpl(StoreRepository storeRepository,
                            StoreMapper storeMapper,
                            AddressRepository addressRepository,
                            CompanyCodeRepository companyCodeRepository,
                            GeoLocationRepository geoLocationRepository) {
        this.storeRepository = storeRepository;
        this.storeMapper = storeMapper;
        this.addressRepository = addressRepository;
        this.companyCodeRepository = companyCodeRepository;
        this.geoLocationRepository = geoLocationRepository;
    }


    @Transactional
    @Override
    public StoreDto save(StoreDto storeDto) {

        storeRepository.findByName(storeDto.getName())
                .ifPresent(value -> {
                    throw new ServiceException("Sorry, but store with name="
                            + value.getName() + " is already present! Just modify previous version.");
                });

        Store store = storeMapper.toEntity(storeDto);
        store.setAddress(resolveAddress(store));
        store.setCompanyCode(getCompanyCodeOrThrowException(store));
        store.setGeoLocation(resolveLocation(store));

        return storeMapper.toDto(storeRepository.save(store));
    }


    @Transactional
    @Override
    public void delete(Long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        storeRepository.delete(store);
    }


    @Override
    public StoreDto findById(Long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return storeMapper.toDto(store);
    }


    @Override
    public List<StoreDto> findAll(Paging paging) {
        return storeMapper.toDtoList(storeRepository.findAll());
    }


    @Override
    public PageWrapper<StoreDto> findByCompanyCode(Paging paging, String companyCode) {
        Pageable pageable = getPageable(paging);
        Specification<Store> specification = Specification
                .where(findByCompanyCodeLike(companyCode));

        Page<Store> page = storeRepository.findAll(specification, pageable);

        List<Store> stores = page.toList();
        return
                PageWrapper.of(
                        storeMapper.toDtoList(stores),
                        page.getTotalPages(),
                        page.getTotalElements(),
                        paging.getPage(),
                        page.getNumberOfElements());
    }

    @Transactional
    @Override
    public StoreDto update(StoreDto storeDto) {
     return null;
    }



    private GeoLocation resolveLocation(Store store) {
        GeoLocation location = store.getGeoLocation();
        if (isObjectExists(location)) {
            if (Objects.isNull(location.getId())) {
                location = getExistingLocationByLatitudeAndLongitude(location);
            } else {
                location = getExistingLocationByIdOrThrowException(location);
            }
        }
        return location;
    }


    private boolean isObjectExists(Object object) {
        return Objects.nonNull(object);
    }


    private GeoLocation getExistingLocationByLatitudeAndLongitude(GeoLocation location) {
        return geoLocationRepository.findByLatitudeAndLongitude(location.getLatitude(), location.getLongitude())
                .orElseGet(() -> geoLocationRepository.save(location));
    }


    private GeoLocation getExistingLocationByIdOrThrowException(GeoLocation location) {
        return geoLocationRepository.findById(location.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Location with id=" + location.getId()
                        + " does not exist."));
    }


    private Address resolveAddress(Store store) {
        Address address = store.getAddress();
        if (isObjectExists(address)) {
            if (Objects.isNull(address.getId())) {
                address = getExistingAddress(address);
            } else {
                address = getExistingAddressByIdOrThrowException(address);
            }
        }
        return address;
    }


    private Address getExistingAddress(Address address) {
        return addressRepository.findByCityAndStreetAndHouseNumber(address.getCity(),
                address.getStreet(),
                address.getHouseNumber())
                .orElseGet(() -> addressRepository.save(address));
    }


    private Address getExistingAddressByIdOrThrowException(Address address) {
        return addressRepository.findById(address.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Address with id=" + address.getId()
                        + " does not exist."));
    }


    private CompanyCode getCompanyCodeOrThrowException(Store store) {
        CompanyCode code = store.getCompanyCode();
        return companyCodeRepository.findByCode(code.getCode())
                .orElseThrow(() -> new ResourceNotFoundException("Company Code =" + code.getCode()
                        + " is not allowed!"));
    }


    private Pageable getPageable(Paging paging) {
        return PageRequest.of(paging.getPage(), paging.getSize());
    }


    private Specification<Store> getStoreSpecification(String searchParam) {

        Specification<Store> specification =
                Specification
                        .where(findByStoreNameLike(searchParam))
                        .or(findByStorePhoneNumberLike(searchParam))
                        .or(findByCompanyCodeLike(searchParam));
        return specification;
    }
}
