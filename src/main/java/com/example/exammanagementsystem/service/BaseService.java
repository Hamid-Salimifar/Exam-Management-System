package com.example.exammanagementsystem.service;

import java.util.List;

public interface BaseService <T>{
    T saveOrUpdate(T entity);
    T findById(Long id);
    List<T> findAll();
    void deleteById(Long id);
}
