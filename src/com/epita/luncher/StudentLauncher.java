package com.epita.luncher;

import com.epita.models.*;
import com.epita.services.data.db.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class StudentLauncher {
    public static void main(String[] args) throws SQLException {
        StudentDAO studentDAO = new StudentDAO(DatabaseManager.INSTANCE);
        QuizDAO quizDAO = new QuizDAO(DatabaseManager.INSTANCE);
        QuestionDAO questionDAO = new QuestionDAO(DatabaseManager.INSTANCE);
        ChoiceDAO choiceDAO = new ChoiceDAO(DatabaseManager.INSTANCE);
        ResultDAO resultDAO = new ResultDAO(DatabaseManager.INSTANCE, choiceDAO, quizDAO, questionDAO);

        displayMenu(studentDAO, quizDAO, questionDAO, choiceDAO, resultDAO);
    }

    private static void displayMenu(StudentDAO studentDAO, QuizDAO quizDAO, QuestionDAO questionDAO, ChoiceDAO choiceDAO, ResultDAO resultDAO) throws SQLException {
        System.out.println("1. Signup");
        System.out.println("2. Play Quiz");
        Scanner scanner = new Scanner(System.in);
        switch (Integer.parseInt(scanner.next())) {
            case 1:
                signup(studentDAO, questionDAO, quizDAO, choiceDAO, resultDAO, scanner);
                break;
            case 2:
                System.out.println("Enter your name:");
                String name = scanner.next();
                Student student = new Student();
                student.setName(name);
                ArrayList<Student> students = studentDAO.search(student);
                if (students.get(0) == null) {
                    System.out.println("Student with following name does not exist");
                } else {
                    playQuiz(quizDAO, questionDAO, choiceDAO, resultDAO, students.get(0), scanner);
                }
                break;
            default:
                System.out.println("Make valid selection");
                break;
        }
    }

    private static void playQuiz(QuizDAO quizDAO, QuestionDAO questionDAO, ChoiceDAO choiceDAO, ResultDAO resultDAO, Student student, Scanner scanner) throws SQLException {
        ArrayList<Quiz> quizzes = quizDAO.getAll();
        if (quizzes.size() > 0) {
            System.out.println("----------------List of Quiz---------------");
            for (Quiz quiz : quizzes) {
                System.out.println(String.format("%d) %s", quiz.getId(), quiz.getTitle()));
            }
            System.out.println("----------------End of Quiz-----------------");
        } else {
            System.out.println();
            System.out.println("You haven't inserted any quiz yet");
        }
        System.out.println();
        System.out.println("Enter id of quiz you want to play:");
        int quizId = scanner.nextInt();

        Quiz quiz = quizDAO.findById(quizId);
        if (quiz == null) {
            System.out.println("Quiz with provided id doesn't exist");
        } else {
            Question question = new Question();
            question.setQuizId(quizId);
            ArrayList<Question> questions = questionDAO.search(question);
            int size = 0;
            do {
                System.out.println(questions.get(size).getQuestion());
                MCQChoice choice = new MCQChoice();
                choice.setQuestionId(questions.get(size).getId());
                for (MCQChoice c : choiceDAO.search(choice)) {
                    System.out.println(String.format("%d) %s", c.getId(), c.getChoice()));
                }
                System.out.println();
                System.out.println("Chose the id of answer:");
                int answerId = scanner.nextInt();

                Result result = new Result();
                result.setQuizId(quizId);
                result.setChoiceId(answerId);
                result.setQuestionId(questions.get(size).getId());
                result.setStudentId(student.getId());
                resultDAO.create(result);
                size++;
            }
            while (size < questions.size());

            System.out.println(resultDAO.displayResult(quizId, student.getId()));
        }
    }

    private static void signup(StudentDAO dao, QuestionDAO questionDAO, QuizDAO quizDAO, ChoiceDAO choiceDAO, ResultDAO resultDAO, Scanner scanner) throws SQLException {
        System.out.println();
        System.out.println("Enter name of student:");
        String name = scanner.next();

        Student student = new Student();
        student.setName(name);
        dao.create(student);

        System.out.println("Successfully created user");
        System.out.println();
        displayMenu(dao, quizDAO, questionDAO, choiceDAO, resultDAO);
    }
}
