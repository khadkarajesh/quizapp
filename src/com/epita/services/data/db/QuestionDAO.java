package com.epita.services.data.db;

import com.epita.models.Question;
import com.epita.services.base.BaseDAO;
import com.epita.utils.StringUtils;

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
    public Question findById(int id) throws SQLException {
        String selectSql = "select * from " + DatabaseManager.Table.QUESTION + " where id=?";
        PreparedStatement statement = manager.prepare(selectSql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return toModel(resultSet);
    }

    @Override
    public int create(Question question) throws SQLException {
        String insertStatement = "INSERT INTO " + DatabaseManager.Table.QUESTION + "(TITLE, DIFFICULTY,TOPICS, QUIZ_ID) VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = manager.getConnection().prepareStatement(insertStatement, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, question.getQuestion());
        preparedStatement.setInt(2, question.getDifficulty());
        preparedStatement.setString(3, question.mapToStr());
        preparedStatement.setInt(4, question.getQuizId());
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        resultSet.next();
        return resultSet.getInt(1);
    }

    @Override
    public void update(Question question) throws SQLException {
        String updateQuery = "update " + DatabaseManager.Table.QUESTION + " set title=?,difficulty=?,topics=? where " + DatabaseManager.QuestionColumns.ID + "=?";
        PreparedStatement statement = manager.prepare(updateQuery);
        statement.setString(1, question.getQuestion());
        statement.setInt(2, question.getDifficulty());
        statement.setString(3, question.mapToStr());
        statement.setInt(4, question.getId());
        statement.executeUpdate();
    }

    @Override
    public void delete(Question question) throws SQLException {
        Statement statement = manager.getConnection().createStatement();
        String deleteQuery = "delete from "
                + DatabaseManager.Table.QUESTION
                + " where "
                + DatabaseManager.QuestionColumns.ID
                + "="
                + question.getId();
        statement.execute(deleteQuery);
    }

    @Override
    public ArrayList<Question> search(Question question) throws SQLException {
        String sqlSearch = "select * from " + DatabaseManager.Table.QUESTION + " where " + DatabaseManager.QuestionColumns.QUIZ_ID + "=?";
        PreparedStatement statement = manager.prepare(sqlSearch);
        statement.setInt(1, question.getQuizId());
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Question> results = new ArrayList<>();
        while (resultSet.next()) {
            results.add(toModel(resultSet));
        }
        return results;
    }

    private ArrayList<Question> getQuestions(String sql) throws SQLException {
        Statement statement = manager.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        ArrayList<Question> results = new ArrayList<>();
        while (resultSet.next()) {
            results.add(toModel(resultSet));
        }
        return results;
    }

    @Override
    public ArrayList<Question> getAll() throws SQLException {
        String sqlQuestions = "select * from " + DatabaseManager.Table.QUESTION;
        return getQuestions(sqlQuestions);
    }

    @Override
    public Question toModel(ResultSet resultSet) throws SQLException {
        Question question = new Question();
        question.setId(resultSet.getInt(DatabaseManager.QuestionColumns.ID));
        question.setQuestion(resultSet.getString(DatabaseManager.QuestionColumns.TITLE));
        question.setDifficulty(resultSet.getInt(DatabaseManager.QuestionColumns.DIFFICULTY));
        question.setTopics(StringUtils.splitStr(resultSet.getString(DatabaseManager.QuestionColumns.TOPICS)));
        question.setQuizId(resultSet.getInt(DatabaseManager.QuestionColumns.QUIZ_ID));
        return question;
    }
}
