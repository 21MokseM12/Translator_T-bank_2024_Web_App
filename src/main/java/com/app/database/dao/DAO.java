package com.app.database.dao;

import com.app.database.exceptions.DaoException;

import java.util.Optional;

public interface DAO <I, E> {
    E save(E entity) throws DaoException;
    boolean delete(I id) throws DaoException;
    Optional<E> findById(I id) throws DaoException;
    boolean update(E entity) throws DaoException;
}
