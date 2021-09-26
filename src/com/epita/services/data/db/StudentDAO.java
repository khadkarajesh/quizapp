package com.epita.services.data.db;

import com.epita.models.Question;
import com.epita.models.Student;
import com.epita.services.base.BaseDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentDAO extends BaseDAO implements IDAO<Student> {
    public StudentDAO(DatabaseManager manager) {
        super(manager);
    }

    @Override
    public Student findById(int id) {
        return null;
    }

    @Override
    public int create(Student student) throws SQLException {
//        manager.getConnection()
        return 0;
    }

    @Override
    public void update(Student student) throws SQLException {

    }

    @Override
    public void delete(Student student) throws SQLException {
    }

    @Override
    public ArrayList<Student> search(Student student) throws SQLException {
        return null;
    }

    @Override
    public ArrayList<Student> getAll() throws SQLException {
        return null;
    }

    @Override
    public Student toModel(ResultSet resultSet) {
        return null;
    }
}
