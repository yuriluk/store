package org.example.store.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyCodeDto extends AbstractDto {

    @NotBlank(message = "Company code can`t be null and spaces!")
    @Pattern(regexp = ".{2}", message = "Company code must be 2 symbols")
    private String code;


    public CompanyCodeDto() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
