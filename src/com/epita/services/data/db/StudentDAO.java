package com.epita.services.data.db;

import com.epita.models.Student;
import com.epita.services.base.BaseDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class StudentDAO extends BaseDAO implements IDAO<Student> {
    public StudentDAO(DatabaseManager manager) {
        super(manager);
    }

    @Override
    public Student findById(int id) throws SQLException {
        String selectSql = "select * from " + DatabaseManager.Table.STUDENT + " where id=?";
        PreparedStatement statement = manager.prepare(selectSql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return toModel(resultSet);
    }

    @Override
    public int create(Student student) throws SQLException {
        String insertStatement = "INSERT INTO " + DatabaseManager.Table.STUDENT + "(name) VALUES (?)";
        PreparedStatement preparedStatement = manager.getConnection().prepareStatement(insertStatement, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, student.getName());
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        resultSet.next();
        return resultSet.getInt(1);
    }

    @Override
    public void update(Student student) throws SQLException {
        String updateQuery = "update " + DatabaseManager.Table.STUDENT + " set title=? where " + DatabaseManager.StudentColumns.ID + "=?";
        PreparedStatement statement = manager.prepare(updateQuery);
        statement.setString(1, student.getName());
        statement.setInt(2, student.getId());
        statement.executeUpdate();
    }

    @Override
    public void delete(Student student) throws SQLException {
        String deleteQuery = "delete from " + DatabaseManager.Table.STUDENT + " where " + DatabaseManager.StudentColumns.ID + "=?";
        PreparedStatement statement = manager.prepare(deleteQuery);
        statement.setInt(1, student.getId());
        statement.execute();
    }

    @Override
    public ArrayList<Student> search(Student student) throws SQLException {
        String sqlSearch = "select * from " + DatabaseManager.Table.STUDENT + " where name=?";
        PreparedStatement statement = manager.prepare(sqlSearch);
        statement.setString(1, student.getName());
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Student> students = new ArrayList<>();
        while (resultSet.next()) {
            students.add(toModel(resultSet));
        }
        return students;
    }

    @Override
    public ArrayList<Student> getAll() throws SQLException {
        return null;
    }

    @Override
    public Student toModel(ResultSet resultSet) throws SQLException {
        Student student = new Student();
        student.setId(resultSet.getInt(DatabaseManager.StudentColumns.ID));
        student.setName(resultSet.getString(DatabaseManager.StudentColumns.NAME));
        return student;
    }
}
