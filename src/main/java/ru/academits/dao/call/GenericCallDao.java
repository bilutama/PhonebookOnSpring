package ru.academits.dao.call;

import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

public interface GenericCallDao<T, C extends Serializable> {
    @Transactional
    void create(T obj);

    @Transactional
    void remove(T obj);

    T getById(C id);

    @Transactional
    List<T> findAll();
}