package com.epita.services.data.db;

import com.epita.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public enum DatabaseManager {
    INSTANCE;
    private Connection connection;

    public static final class Table {
        public static final String QUESTION = "QUESTION";
        public static final String STUDENT = "STUDENT";
        public static final String QUIZ = "QUIZ";
        public static final String CHOICE = "CHOICE";
        public static final String RESULT = "RESULT";
    }

    public static final class QuestionColumns {
        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String DIFFICULTY = "difficulty";
        public static final String QUIZ_ID = "quiz_id";
        public static final String TOPICS = "topics";
    }


    public static final class StudentColumns {
        public static final String ID = "id";
        public static final String NAME = "name";
    }

    public static final class QuizColumns {
        public static final String ID = "id";
        public static final String TITLE = "title";
    }

    public static final class ChoiceColumns {
        public static final String ID = "id";
        public static final String CHOICE = "choice";
        public static final String VALID = "valid";
        public static final String QUESTION_ID = "question_id";
    }

    public static final class ResultColumns {
        public static final String ID = "id";
        public static final String STUDENT_ID = "student_id";
        public static final String QUIZ_ID = "quiz_id";
        public static final String QUESTION_ID = "question_id";
        public static final String CHOICE_ID = "choice_id";
    }

    private static final String QUESTION_TABLE_CREATION_SQL =
            "CREATE TABLE IF NOT EXISTS "
                    + Table.QUESTION
                    + "("
                    + QuestionColumns.ID + " int auto_increment primary key,"
                    + QuestionColumns.TITLE + " VARCHAR(255),"
                    + QuestionColumns.DIFFICULTY + " INT,"
                    + QuestionColumns.QUIZ_ID + " INT,"
                    + QuestionColumns.TOPICS + " VARCHAR(255),"
                    + "foreign key (" + QuestionColumns.QUIZ_ID + ") references " + Table.QUIZ + "(" + QuizColumns.ID + ")"
                    + ")";

    private static final String STUDENT_TABLE_CREATION_SQL = "CREATE TABLE IF NOT EXISTS " +
            Table.STUDENT
            + "(" + StudentColumns.ID + " int auto_increment primary key,"
            + StudentColumns.NAME + " varchar(255) "
            + ")";

    private static final String QUIZ_TABLE_CREATION_SQL = "CREATE TABLE IF NOT EXISTS " + Table.QUIZ
            + "(" + StudentColumns.ID + " int auto_increment primary key,"
            + QuizColumns.TITLE + " varchar(255) "
            + ")";

    private static final String MCQ_CHOICE_CREATION_SQL = "CREATE TABLE IF NOT EXISTS " + Table.CHOICE
            + "(" + ChoiceColumns.ID + " int auto_increment primary key,"
            + ChoiceColumns.CHOICE + " varchar(255),"
            + ChoiceColumns.VALID + " boolean,"
            + ChoiceColumns.QUESTION_ID + " int not null,"
            + " foreign key (" + ChoiceColumns.QUESTION_ID + ")" + " references " + Table.QUESTION + "(" + QuestionColumns.ID + ")" + " on delete cascade"
            + " )";

    private static final String RESULT_CREATION_SQL = "CREATE TABLE IF NOT EXISTS " + Table.RESULT
            + "(" + ResultColumns.ID + " int auto_increment primary key,"
            + ResultColumns.STUDENT_ID + " int,"
            + ResultColumns.QUIZ_ID + " int,"
            + ResultColumns.QUESTION_ID + " int,"
            + ResultColumns.CHOICE_ID + " int"
            + ")";


    DatabaseManager() {
        Configuration conf = Configuration.INSTANCE;
        try {
            connection = DriverManager.getConnection(conf.getHost(),
                    conf.getUser(),
                    conf.getPassword());
            String schema = connection.getSchema();
            if (!"PUBLIC".equals(schema)) {
                throw new RuntimeException("connection was not successful");
            }
            createTables();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    public void createTables() throws SQLException {
        try {
            for (String table : Arrays.asList(
                    QUIZ_TABLE_CREATION_SQL,
                    QUESTION_TABLE_CREATION_SQL,
                    STUDENT_TABLE_CREATION_SQL,
                    MCQ_CHOICE_CREATION_SQL,
                    RESULT_CREATION_SQL)) {
                PreparedStatement preparedStatement = connection.prepareStatement(table);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            System.out.println("e = " + e.getSQLState() + "message:" + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public PreparedStatement prepare(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }
}
