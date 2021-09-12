package com.epita.services.data;

import com.epita.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public enum DatabaseManager {
    INSTANCE;
    private Connection connection;
    private static final String SQL_CREATE = "CREATE TABLE EMPLOYEE"
            + "("
            + " ID serial,"
            + " NAME varchar(100) NOT NULL,"
            + " SALARY numeric(15, 2) NOT NULL,"
            + " CREATED_DATE timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,"
            + " PRIMARY KEY (ID)"
            + ")";

    public static final class Table {
        public static final String QUESTIONS = "QUESTIONS";
        public static final String STUDENTS = "STUDENTS";
    }

    public static final class QuestionColumns {
        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String DIFFICULTY = "difficulty";
    }


    public static final class StudentColumns {
        public static final String ID = "id";
        public static final String NAME = "name";
    }

    private static final String QUESTION_TABLE_CREATION_SQL =
            "CREATE TABLE IF NOT EXISTS "
                    + Table.QUESTIONS
                    + "("
                    + QuestionColumns.ID + " int auto_increment primary key,"
                    + QuestionColumns.TITLE + " VARCHAR(255),"
                    + QuestionColumns.DIFFICULTY + " INT" +
                    ")";

    private static final String STUDENT_TABLE_CREATION_SQL = "CREATE TABLE IF NOT EXISTS " +
            Table.STUDENTS
            + "(" + StudentColumns.ID + " int auto_increment primary key,"
            + StudentColumns.NAME + " varchar(255) "
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
            for (String table : Arrays.asList(QUESTION_TABLE_CREATION_SQL, STUDENT_TABLE_CREATION_SQL)) {
                PreparedStatement preparedStatement = connection.prepareStatement(table);
                preparedStatement.execute();
                System.out.println("created table successfully");
            }
        } catch (SQLException e) {
            System.out.println("e = " + e.getSQLState() + "message:" + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
