package com.epita.services.data;

import com.epita.models.Question;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class QuestionDAO extends BaseDAO implements IDAO<Question> {

    public QuestionDAO(DatabaseManager manager) {
        super(manager);
    }

    @Override
    public void create(Question question) throws SQLException {
        String insertStatement = "INSERT INTO QUESTIONS(TITLE, DIFFICULTY) VALUES (?,?)";
        PreparedStatement preparedStatement = manager.getConnection().prepareStatement(insertStatement, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, question.getQuestion());
        preparedStatement.setInt(2, question.getDifficulty());
        preparedStatement.execute();
    }

    @Override
    public void update(Question question) throws SQLException {
        Statement statement = manager.getConnection().createStatement();
        String updateQuery = "update " + DatabaseManager.Table.QUESTIONS
                + " set "
                + DatabaseManager.QuestionColumns.TITLE + "=" + "'" + question.getQuestion() + "',"
                + DatabaseManager.QuestionColumns.DIFFICULTY + "=" + question.getDifficulty()
                + " where "
                + DatabaseManager.QuestionColumns.ID + "=" + question.getId();
        statement.executeUpdate(updateQuery);
    }

    @Override
    public boolean delete(Question question) throws SQLException {
        Statement statement = manager.getConnection().createStatement();
        String deleteQuery = "delete from "
                + DatabaseManager.Table.QUESTIONS
                + " where "
                + DatabaseManager.QuestionColumns.ID
                + "="
                + question.getId();
        return statement.execute(deleteQuery);
    }

    @Override
    public ArrayList<Question> search(Question question) throws SQLException {
        String sqlSearch = "select * from " + DatabaseManager.Table.QUESTIONS + " where " + DatabaseManager.QuestionColumns.TITLE + " like" + "'" + "%" + question.getQuestion() + "%" + "'";
        return getQuestions(sqlSearch);
    }

    private ArrayList<Question> getQuestions(String sql) throws SQLException {
        Statement statement = manager.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        ArrayList<Question> results = new ArrayList<>();
        while (resultSet.next()) {
            Question dbQuestion = new Question();
            dbQuestion.setId(resultSet.getInt(DatabaseManager.QuestionColumns.ID));
            dbQuestion.setQuestion(resultSet.getString(DatabaseManager.QuestionColumns.TITLE));
            dbQuestion.setDifficulty(resultSet.getInt(DatabaseManager.QuestionColumns.DIFFICULTY));
            results.add(dbQuestion);
        }
        return results;
    }

    @Override
    public ArrayList<Question> getAll() throws SQLException {
        String sqlQuestions = "select * from " + DatabaseManager.Table.QUESTIONS;
        return getQuestions(sqlQuestions);
    }
}
