package org.example.store.service;


import org.example.store.service.dto.PageWrapper;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CrudService<E> {

    E save(E e);

    E update(E e);

    void delete(Long id);

    E findById(Long id);

    PageWrapper<E> findAll(Pageable pageable);

    List<E> findAll();

}