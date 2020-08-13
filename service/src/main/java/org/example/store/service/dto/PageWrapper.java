package org.example.store.service.dto;

import java.util.List;

public class PageWrapper<T> {

    private List<T> objects;
    private int totalPages;
    private long totalElements;
    private int page;
    private int elementsCount;


    public PageWrapper(List<T> objects,
                       int totalPages,
                       long totalElements,
                       int page,
                       int elementsCount) {
        this.objects = objects;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.page = page;
        this.elementsCount = elementsCount;
    }

    public static <T> PageWrapper<T> of(List<T> objects,
                                        int totalPages,
                                        long totalElements,
                                        int page,
                                        int elementsCount) {
        return new PageWrapper<>(objects, totalPages, totalElements, page, elementsCount);
    }

    public List<T> getObjects() {
        return objects;
    }

    public void setObjects(List<T> objects) {
        this.objects = objects;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getElementsCount() {
        return elementsCount;
    }

    public void setElementsCount(int elementsCount) {
        this.elementsCount = elementsCount;
    }

    @Override
    public String toString() {
        return "PageWrapper{" +
                ", totalPages=" + totalPages +
                ", totalElements=" + totalElements +
                ", page=" + page +
                ", elementsCount=" + elementsCount +
                '}';
    }
}