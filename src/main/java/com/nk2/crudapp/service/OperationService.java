package com.nk2.crudapp.service;

import org.apache.coyote.BadRequestException;

import java.util.List;

public interface OperationService<T> {
    T save(T obj);
    T update(T obj) throws BadRequestException;
    void deleteById(Integer id) throws BadRequestException;
    List<T> getAll();
    T getById(Integer id);
}
