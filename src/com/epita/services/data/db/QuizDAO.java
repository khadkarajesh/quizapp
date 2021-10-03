package com.epita.services.data.db;

import com.epita.models.Quiz;
import com.epita.services.base.BaseDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class QuizDAO extends BaseDAO implements IDAO<Quiz> {
    public QuizDAO(DatabaseManager manager) {
        super(manager);
    }

    @Override
    public Quiz findById(int id) throws SQLException {
        String selectSql = "select * from " + DatabaseManager.Table.QUIZ + " where id=?";
        PreparedStatement statement = manager.prepare(selectSql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return toModel(resultSet);
    }

    @Override
    public int create(Quiz quiz) throws SQLException {
        String insertQuery = "insert into " + DatabaseManager.Table.QUIZ + "(title) values(?)";
        PreparedStatement statement = manager.getConnection().prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, quiz.getTitle());
        statement.execute();
        ResultSet resultSet = statement.getGeneratedKeys();
        resultSet.next();
        return resultSet.getInt(1);
    }

    @Override
    public void update(Quiz quiz) throws SQLException {
        String updateQuery = "update " + DatabaseManager.Table.QUIZ + " set " + DatabaseManager.QuizColumns.TITLE + "=?" + "where id=?";
        PreparedStatement statement = manager.prepare(updateQuery);
        statement.setString(1, quiz.getTitle());
        statement.setInt(2, quiz.getId());
        statement.executeUpdate();
    }

    @Override
    public void delete(Quiz quiz) throws SQLException {
        String deleteQuery = "delete from " + DatabaseManager.Table.QUIZ + " where id=?";
        PreparedStatement statement = manager.prepare(deleteQuery);
        statement.setInt(1, quiz.getId());
        statement.execute();
    }

    @Override
    public ArrayList<Quiz> search(Quiz quiz) throws SQLException {
        String searchQuery = "select * from " + DatabaseManager.Table.QUIZ + " where " + DatabaseManager.QuizColumns.TITLE + " ilike=?";
        PreparedStatement statement = manager.prepare(searchQuery);
        statement.setString(1, "%" + quiz.getTitle() + "%");
        ResultSet resultSet = manager.prepare(searchQuery).executeQuery();
        return getQuizzes(resultSet, searchQuery);
    }

    private ArrayList<Quiz> getQuizzes(ResultSet resultSet, String searchQuery) throws SQLException {
        ArrayList<Quiz> results = new ArrayList<>();
        while (resultSet.next()) {
            Quiz dbQuiz = new Quiz();
            dbQuiz.setId(resultSet.getInt(DatabaseManager.QuizColumns.ID));
            dbQuiz.setTitle(resultSet.getString(DatabaseManager.QuizColumns.TITLE));
            results.add(dbQuiz);
        }
        return results;
    }

    @Override
    public ArrayList<Quiz> getAll() throws SQLException {
        String selectQuery = "select * from " + DatabaseManager.Table.QUIZ;
        ResultSet resultSet = manager.prepare(selectQuery).executeQuery();
        return getQuizzes(resultSet, selectQuery);
    }

    @Override
    public Quiz toModel(ResultSet resultSet) throws SQLException {
        Quiz quiz = new Quiz();
        quiz.setId(resultSet.getInt(DatabaseManager.QuizColumns.ID));
        quiz.setTitle(resultSet.getString(DatabaseManager.QuizColumns.TITLE));
        return quiz;
    }
}
