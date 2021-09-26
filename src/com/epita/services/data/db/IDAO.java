package com.epita.services.data.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface IDAO<T> {
    T findById(int id) throws SQLException;

    int create(T t) throws SQLException;

    void update(T t) throws SQLException;

    void delete(T t) throws SQLException;

    ArrayList<T> search(T t) throws SQLException;

    ArrayList<T> getAll() throws SQLException;

    T toModel(ResultSet resultSet) throws SQLException;
}
