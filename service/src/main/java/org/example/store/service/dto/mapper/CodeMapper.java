package org.example.store.service.dto.mapper;

import org.example.store.model.CompanyCode;
import org.example.store.service.dto.CompanyCodeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CodeMapper extends AbstractMapper<CompanyCode, CompanyCodeDto> {

    @Autowired
    public CodeMapper() {
        super(CompanyCode.class, CompanyCodeDto.class);
    }

}
