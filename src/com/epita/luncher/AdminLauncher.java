package com.epita.luncher;

import com.epita.models.Question;
import com.epita.models.Quiz;
import com.epita.services.data.db.DatabaseManager;
import com.epita.services.data.db.QuestionDAO;
import com.epita.services.data.db.QuizDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class AdminLauncher {
    public static void main(String[] args) throws SQLException {
        displayMenu();
    }

    private static void displayMenu() throws SQLException {
        System.out.println("1. Create Quiz");
        System.out.println("2. List All Quiz");
        System.out.println("3. Update Quiz");
        System.out.println("4. Delete Quiz");
        System.out.println("5. Exit");
        System.out.println();
        System.out.println("Choose your options....");
        System.out.println();

        QuizDAO quizDAO = new QuizDAO(DatabaseManager.INSTANCE);

        Scanner scanner = new Scanner(System.in);
        int i = Integer.parseInt(scanner.next());
        switch (i) {
            case 1:
                createQuiz(quizDAO, scanner);
                break;
            case 2:
                listAllQuiz(quizDAO);
                break;
            case 3:
                updateQuiz(quizDAO, scanner);
                break;
            case 4:
                deleteQuiz(quizDAO, scanner);
                break;
            default:
                System.out.println();
                System.out.println("Please enter correct input");
                System.out.println();
                break;
        }
    }

    private static void deleteQuiz(QuizDAO quizDAO, Scanner scanner) throws SQLException {
        System.out.println();
        System.out.println("Enter the id of quiz to be deleted:");
        System.out.println();
        int id = Integer.parseInt(scanner.next());
        Quiz quiz = quizDAO.findById(id);
        if (quiz == null) {
            System.out.println("Quiz doesn't exist with given id");
        } else {
            quizDAO.delete(quiz);
            System.out.println("Successfully deleted quiz");
        }
        System.out.println();
        displayMenu();
    }

    private static void updateQuiz(QuizDAO dao, Scanner scanner) throws SQLException {
        System.out.println("------------- Update -----------------");
        System.out.println();
        System.out.println("Enter id of quiz to edit:");
        int quizId = Integer.parseInt(scanner.next());
        Quiz quiz = dao.findById(quizId);
        if (quiz == null) {
            System.out.println("Quiz with given id doesn't exist");
        } else {
            System.out.println("Enter title of:");
            quiz.setTitle(scanner.next());
            dao.update(quiz);
            System.out.println("Update Successfully");
        }
        System.out.println();
        displayMenu();
    }

    private static void listAllQuiz(QuizDAO dao) throws SQLException {
        ArrayList<Quiz> quizzes = dao.getAll();
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
        displayMenu();
    }

    private static void createQuiz(QuizDAO dao, Scanner scanner) throws SQLException {
        System.out.println("Enter quiz name");
        String input = scanner.next();

        Quiz quiz = new Quiz();
        quiz.setTitle(input);
        int id = dao.create(quiz);

        System.out.println("Do you want to insert questions? (yes|no)");
        QuestionDAO questionDAO = new QuestionDAO(DatabaseManager.INSTANCE);

        while (scanner.next().equalsIgnoreCase("yes")) {
            System.out.println("Enter question title:");
            String question = scanner.next();
            question += scanner.nextLine();
            System.out.println("Enter question difficulty(1:Easy, 2:Medium, 3:Hard):");
            int difficulty = Integer.parseInt(scanner.next());
            System.out.println("Enter labels with format: java,kotlin");
            String labels = scanner.next();
            String[] topics = labels.split(",");
            Question q = new Question();
            q.setQuizId(id);
            q.setQuestion(question);
            q.setDifficulty(difficulty);
            q.setTopics(topics);
            questionDAO.create(q);
            System.out.println("Do you want to add more question?(yes|no)");
        }
        System.out.println();
        displayMenu();
    }
}
