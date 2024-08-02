package com.app.database.dao;

import com.app.database.exceptions.DaoException;

public interface DAO <I, E> {
    E save(E entity) throws DaoException;
    boolean delete(I id) throws DaoException;
    E findById(I id) throws DaoException;
    boolean update(E entity) throws DaoException;
}
