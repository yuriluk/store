package org.example.store.service.dto.mapper;


import org.example.store.model.AbstractEntity;
import org.example.store.service.dto.AbstractDto;

import java.util.List;

public interface Mapper<E extends AbstractEntity, D extends AbstractDto> {

    E toEntity(D d);

    D toDto(E e);

    List<D> toDtoList(List<E> eList);
}