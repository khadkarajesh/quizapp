package com.epita;

import com.epita.models.MCQChoice;
import com.epita.models.MCQQuestion;
import com.epita.models.Question;
import com.epita.models.Quiz;
import com.epita.services.data.db.DatabaseManager;
import com.epita.services.data.db.QuestionDAO;
import com.epita.services.data.db.QuizDAO;

import java.sql.SQLException;

public class Launcher {
    public static void main(String[] args) throws SQLException {
        Quiz quiz = new Quiz();
        quiz.setTitle("Java Language Basics Quiz Online Test – 1");

        QuizDAO quizDAO = new QuizDAO(DatabaseManager.INSTANCE);
        int insertedId = quizDAO.create(quiz);

        Question question = new MCQQuestion();
        question.setQuestion("Who is prime minister of France?");
        question.setQuizId(insertedId);
        String[] labels = {"general", "politics"};
        question.setTopics(labels);

        QuestionDAO questionDAO = new QuestionDAO(DatabaseManager.INSTANCE);
        int savedId = questionDAO.create(question);
        question = questionDAO.findById(savedId);
        question.setQuestion("Who is not prime minster of France?");
        questionDAO.update(question);

        for(Question q: questionDAO.getAll()){
            System.out.println("q = " + q);
        }


//        MCQQuestion question = new MCQQuestion();
//        question.setQuestion("Who is prime minister of France");
//        String[] topics = {"france", "minister"};
//        question.setTopics(topics);
//        question.setDifficulty(1);
//
//        MCQChoice mcqChoice1 = new MCQChoice();
//        mcqChoice1.setMcqQuestion(question);
//        mcqChoice1.setChoice("Emanuel Macron");
//        mcqChoice1.setValid(true);
//
//        MCQChoice mcqChoice2 = new MCQChoice();
//        mcqChoice2.setMcqQuestion(question);
//        mcqChoice2.setChoice("Barak Obama");
//
//        MCQChoice mcqChoice3 = new MCQChoice();
//        mcqChoice3.setMcqQuestion(question);
//        mcqChoice3.setChoice("Si-Xing Ping");
//
//        MCQChoice mcqChoice4 = new MCQChoice();
//        mcqChoice4.setMcqQuestion(question);
//        mcqChoice4.setChoice("Nancy Pawel");
    }
}
