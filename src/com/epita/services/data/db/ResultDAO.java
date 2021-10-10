package com.epita.services.data.db;

import com.epita.models.MCQChoice;
import com.epita.models.Question;
import com.epita.models.Quiz;
import com.epita.models.Result;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ResultDAO {
    DatabaseManager manager;
    ChoiceDAO choiceDAO;
    QuizDAO quizDAO;
    QuestionDAO questionDAO;

    public ResultDAO(DatabaseManager manager, ChoiceDAO choiceDAO, QuizDAO quizDAO, QuestionDAO questionDAO) {
        this.manager = manager;
        this.choiceDAO = choiceDAO;
        this.quizDAO = quizDAO;
        this.questionDAO = questionDAO;
    }

    public void create(Result result) throws SQLException {
        String createSql = "insert into " + DatabaseManager.Table.RESULT + "(student_id,quiz_id,question_id,choice_id)" + " values(?,?,?,?)";
        PreparedStatement statement = manager.prepare(createSql);
        statement.setInt(1, result.getStudentId());
        statement.setInt(2, result.getQuizId());
        statement.setInt(3, result.getQuestionId());
        statement.setInt(4, result.getChoiceId());
        statement.execute();
    }

    public String displayResult(int quizId, int studentId) throws SQLException {
        String selectSql = "select * from " + DatabaseManager.Table.RESULT + " where quiz_id=? and student_id=?";
        PreparedStatement statement = manager.prepare(selectSql);
        statement.setInt(1, quizId);
        statement.setInt(2, studentId);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Result> results = new ArrayList<>();
        while (resultSet.next()) {
            Result result = new Result();
            result.setId(resultSet.getInt(DatabaseManager.ResultColumns.ID));
            result.setQuizId(resultSet.getInt(DatabaseManager.ResultColumns.QUIZ_ID));
            result.setQuestionId(resultSet.getInt(DatabaseManager.ResultColumns.QUESTION_ID));
            result.setStudentId(resultSet.getInt(DatabaseManager.ResultColumns.STUDENT_ID));
            result.setChoiceId(resultSet.getInt(DatabaseManager.ResultColumns.CHOICE_ID));
            result.setId(resultSet.getInt(DatabaseManager.ResultColumns.ID));
            results.add(result);
        }
        int securedScore = 0;
        Question question = new Question();
        question.setQuizId(quizId);
        Quiz quiz = quizDAO.findById(quizId);
        int totalScore = questionDAO.search(question).size();

        for (Result result : results) {
            MCQChoice choice = choiceDAO.findById(result.getChoiceId());
            if (choice.isValid()) {
                securedScore += 1;
            }
        }
        return String.format("You scored %d out of %d in quiz: %s", securedScore, totalScore, quiz.getTitle());
    }
}
