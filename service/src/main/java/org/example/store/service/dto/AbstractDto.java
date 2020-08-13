package org.example.store.service.dto;

import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;


public abstract class AbstractDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @PositiveOrZero(message = "Id can`t be smaller than 0 ")
    private Long id;

    AbstractDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "AbstractDto{" +
                "id=" + id +
                '}';
    }
}

