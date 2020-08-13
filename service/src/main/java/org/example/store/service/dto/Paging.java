package org.example.store.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Paging {

    @NotNull(message = "Page can`t be null!")
    @Min(value = 0, message = "Min value for page is 0")
    @Positive(message = "Page must be positive")
    private int page;

    @NotNull(message = "Size can`t be null!")
    @Min(value = 1, message = "Min value for number of things is 1")
    @Positive(message = "Size must be positive")
    private int size;

    public Paging(int size, int page) {
        this.page = page;
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int defineStartPosition() {
        return (page - 1) * size;
    }

    @Override
    public String toString() {
        return "Paging{" +
                "page=" + page +
                ", size=" + size +
                '}';
    }
}
