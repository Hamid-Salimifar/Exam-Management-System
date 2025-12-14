package com.example.exammanagementsystem.service.impl;

import com.example.exammanagementsystem.service.BaseService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class BaseServiceImpl<T> implements BaseService<T> {
    private final JpaRepository<T,Long> jpaRepository;

    public BaseServiceImpl(JpaRepository<T, Long> jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public T saveOrUpdate(T entity) {
        return jpaRepository.save(entity);
    }

    @Override
    public T findById(Long id) {
        return jpaRepository.findById(id).orElseThrow();
    }

    @Override
    public List<T> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}
