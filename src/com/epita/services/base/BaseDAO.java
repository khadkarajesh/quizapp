package com.epita.services.base;

import com.epita.services.data.db.DatabaseManager;

public class BaseDAO {
    protected DatabaseManager manager;

    public BaseDAO(DatabaseManager manager) {
        this.manager = manager;
    }
}
