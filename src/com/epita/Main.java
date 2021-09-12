package com.epita;

import com.epita.models.Question;
import com.epita.services.data.DatabaseManager;
import com.epita.services.data.QuestionDAO;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        QuestionDAO questionDAO = new QuestionDAO(DatabaseManager.INSTANCE);
        for (int i = 0; i < 10; i++) {
            Question question = new Question();
            question.setQuestion("What is your name" + i + "?");
            question.setDifficulty(i);
            questionDAO.create(question);
        }

        for (Question q : questionDAO.getAll()) {
            System.out.println("q = " + q);
        }

        Question question8 = new Question();
        question8.setId(8);
        question8.setQuestion("What is static void main in java?");
        question8.setDifficulty(3);
        questionDAO.update(question8);

//            questionDAO.delete(question8);
        for (Question q : questionDAO.getAll()) {
            System.out.println("q = " + q);
        }

        question8.setQuestion("name");
        for (Question q : questionDAO.search(question8)) {
            System.out.println("searched question = " + q);
        }
    }
}
