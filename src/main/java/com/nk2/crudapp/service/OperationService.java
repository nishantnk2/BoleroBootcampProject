package com.nk2.crudapp.service;

import java.util.List;

public interface OperationService<T> {
    public T save(T obj) throws Exception;
    public T update(T obj) throws Exception;
    public void deleteById(Integer id) throws Exception;
    public List<T> getAll();
    public T getById(Integer id);
}
