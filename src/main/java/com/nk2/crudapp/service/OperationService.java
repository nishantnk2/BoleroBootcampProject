package com.nk2.crudapp.service;

import java.util.List;

public interface OperationService<T> {
    T save(T obj) throws Exception;
    T update(T obj) throws Exception;
    void deleteById(Integer id) throws Exception;
    List<T> getAll();
    T getById(Integer id);
}
