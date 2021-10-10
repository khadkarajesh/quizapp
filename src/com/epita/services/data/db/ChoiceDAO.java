package com.epita.services.data.db;

import com.epita.models.MCQChoice;
import com.epita.models.Question;
import com.epita.services.base.BaseDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ChoiceDAO extends BaseDAO implements IDAO<MCQChoice> {
    public ChoiceDAO(DatabaseManager manager) {
        super(manager);
    }

    @Override
    public MCQChoice findById(int id) throws SQLException {
        String sqlSelect = "select * from " + DatabaseManager.Table.CHOICE + " where id=?";
        PreparedStatement statement = manager.prepare(sqlSelect);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return toModel(resultSet);
    }

    @Override
    public int create(MCQChoice mcqChoice) throws SQLException {
        String sqlCreate = "INSERT INTO " + DatabaseManager.Table.CHOICE + "(CHOICE, VALID, QUESTION_ID) VALUES (?, ?, ?)";
        PreparedStatement statement = manager.getConnection().prepareStatement(sqlCreate, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, mcqChoice.getChoice());
        statement.setBoolean(2, mcqChoice.isValid());
        statement.setInt(3, mcqChoice.getQuestionId());
        statement.execute();
        ResultSet resultSet = statement.getGeneratedKeys();
        resultSet.next();
        return resultSet.getInt(1);
    }

    @Override
    public void update(MCQChoice mcqChoice) throws SQLException {
        String updateQuery = "update " + DatabaseManager.Table.CHOICE + " set choice=?, valid=? where " + DatabaseManager.ChoiceColumns.ID + "=?";
        PreparedStatement statement = manager.prepare(updateQuery);
        System.out.println("statement to be printed = " + statement);
        statement.setString(1, mcqChoice.getChoice());
        statement.setBoolean(2, mcqChoice.isValid());
        statement.setInt(3, mcqChoice.getId());
    }

    @Override
    public void delete(MCQChoice mcqChoice) throws SQLException {
        String deleteQuery = "delete from " + DatabaseManager.Table.CHOICE + " where " + DatabaseManager.ChoiceColumns.ID + "=?";
        PreparedStatement statement = manager.prepare(deleteQuery);
        statement.setInt(1, mcqChoice.getId());
        statement.execute();
    }

    @Override
    public ArrayList<MCQChoice> search(MCQChoice mcqChoice) throws SQLException {
        String sqlSearch = "select * from " + DatabaseManager.Table.CHOICE + " where " + DatabaseManager.ChoiceColumns.QUESTION_ID + "=?";
        PreparedStatement statement = manager.prepare(sqlSearch);
        statement.setInt(1, mcqChoice.getQuestionId());
        ResultSet resultSet = statement.executeQuery();
        ArrayList<MCQChoice> choices = new ArrayList<>();
        while (resultSet.next()) {
            choices.add(toModel(resultSet));
        }
        return choices;
    }

    @Override
    public ArrayList<MCQChoice> getAll() throws SQLException {
        String sqlChoices = "select * from " + DatabaseManager.Table.CHOICE;
        PreparedStatement statement = manager.prepare(sqlChoices);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<MCQChoice> choices = new ArrayList<>();
        while (resultSet.next()) {
            choices.add(toModel(resultSet));
        }
        return choices;
    }

    @Override
    public MCQChoice toModel(ResultSet resultSet) throws SQLException {
        MCQChoice mcqChoice = new MCQChoice();
        mcqChoice.setValid(resultSet.getBoolean(DatabaseManager.ChoiceColumns.VALID));
        mcqChoice.setId(resultSet.getInt(DatabaseManager.ChoiceColumns.ID));
        mcqChoice.setChoice(resultSet.getString(DatabaseManager.ChoiceColumns.CHOICE));
        mcqChoice.setQuestionId(resultSet.getInt(DatabaseManager.ChoiceColumns.QUESTION_ID));
        return mcqChoice;
    }
}
