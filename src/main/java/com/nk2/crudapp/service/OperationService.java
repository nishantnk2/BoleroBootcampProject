package com.nk2.crudapp.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface OperationService<T> {
    public T add(T obj);
    public T update(T obj);
    public void deleteById(int id);
    public List<T> getAll();
    public Optional<T> getById(int id);
}
