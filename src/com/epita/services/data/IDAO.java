package com.epita.services.data;

import com.epita.models.Question;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IDAO<T> {
    void create(T t) throws SQLException;

    void update(T t) throws SQLException;

    boolean delete(T t) throws SQLException;

    ArrayList<T> search(T t) throws SQLException;

    ArrayList<Question> getAll() throws SQLException;
}
