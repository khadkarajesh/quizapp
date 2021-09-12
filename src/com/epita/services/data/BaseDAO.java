package com.epita.services.data;

public class BaseDAO {
    protected DatabaseManager manager;

    public BaseDAO(DatabaseManager manager) {
        this.manager = manager;
    }
}
