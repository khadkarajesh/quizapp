package com.epita.luncher;

import com.epita.models.MCQChoice;
import com.epita.models.Question;
import com.epita.models.Quiz;
import com.epita.services.data.db.ChoiceDAO;
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

    private static void displayMenu() throws SQLException, NumberFormatException {
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
            int questionId = questionDAO.create(q);
            createChoice(questionId, scanner, id);
        }
        System.out.println();
        displayMenu();
    }

    private static void createChoice(int questionId, Scanner scanner, int quizId) throws SQLException {
        System.out.println("Create choices for the question");
        ChoiceDAO choiceDAO = new ChoiceDAO(DatabaseManager.INSTANCE);
        String addChoice = "";
        do {
            System.out.println("title of choice:");
            String choice = scanner.next();
            MCQChoice mcqChoice = new MCQChoice();
            mcqChoice.setChoice(choice);
            scanner.nextLine();

            System.out.println("is it valid answer: (yes|no)");
            String valid = scanner.next();
            scanner.nextLine();

            mcqChoice.setValid(valid.equalsIgnoreCase("yes"));
            mcqChoice.setQuestionId(questionId);
            choiceDAO.create(mcqChoice);

            System.out.println("Do you want to add more? (yes|no)");
            addChoice = scanner.next();
            scanner.nextLine();
        } while (addChoice.equalsIgnoreCase("yes"));
        displayChoicesMenu(choiceDAO, scanner, questionId, quizId);
    }

    private static void displayChoicesMenu(ChoiceDAO dao, Scanner scanner, int questionId, int quizId) throws SQLException {
        System.out.println("1. Insert Choice");
        System.out.println("2. List All Choices");
        System.out.println("3. Update Choice");
        System.out.println("4. Delete Choice");
        System.out.println("5. Back");
        System.out.println();
        System.out.println("Choose your options....");
        System.out.println();

        int i = Integer.parseInt(scanner.next());
        switch (i) {
            case 1:
                createChoice(questionId, scanner, quizId);
                break;
            case 2:
                listAllChoices(dao, questionId, scanner, quizId);
                break;
            case 3:
                updateChoice(dao, scanner, questionId, quizId);
                break;
            case 4:
                deleteChoice(dao, scanner, questionId, quizId);
                break;
            case 5:
                displayQuestionsMenu(scanner, quizId);
                break;
            default:
                System.out.println();
                System.out.println("Please enter correct input");
                System.out.println();
                break;
        }
    }

    private static void listAllQuestions(QuestionDAO dao, int quizId, Scanner scanner) throws SQLException {
        Question q = new Question();
        q.setQuizId(quizId);

        ArrayList<Question> questions = dao.search(q);
        if (questions.size() > 0) {
            System.out.println("----------------List of Questions---------------");
            for (Question question : questions) {
                System.out.println(String.format("%d) title: %s difficulty:%d topics:%s", question.getId(), question.getQuestion(), question.getDifficulty(), question.getJoinedTopics()));
            }
            System.out.println("----------------End of Questions-----------------");
        } else {
            System.out.println();
            System.out.println("You haven't inserted any questions yet");
        }
        System.out.println();
        displayQuestionsMenu(scanner, quizId);
    }

    private static void displayQuestionsMenu(Scanner scanner, int quizId) throws SQLException {
        System.out.println("1. Add Question");
        System.out.println("2. List All Questions");
        System.out.println("3. Update Question");
        System.out.println("4. Delete Question");
        System.out.println("5. Back");
        System.out.println();
        System.out.println("Choose your options....");
        System.out.println();

        int i = Integer.parseInt(scanner.next());
        QuestionDAO dao = new QuestionDAO(DatabaseManager.INSTANCE);

        switch (i) {
            case 1:
                createQuestion(dao, quizId, scanner);
                break;
            case 2:
                listAllQuestions(dao, quizId, scanner);
                break;
            case 3:
                updateQuestion(dao, scanner, quizId);
                break;
            case 4:
                deleteQuestion(dao, scanner, quizId);
                break;
            case 5:
                displayMenu();
                break;
            default:
                System.out.println();
                System.out.println("Please enter correct input");
                System.out.println();
                break;
        }
    }

    private static void deleteQuestion(QuestionDAO dao, Scanner scanner, int quizId) throws SQLException {
        System.out.println();
        System.out.println("Enter the id of question to delete.");

        int id = scanner.nextInt();

        Question question = dao.findById(id);

        if (question == null) {
            System.out.println("Choice with given id doesn't exist in database");
        } else {
            dao.delete(question);
            System.out.println("Deleted Successfully");
            System.out.println();
        }
        displayQuestionsMenu(scanner, quizId);
    }

    private static void updateQuestion(QuestionDAO dao, Scanner scanner, int quizId) throws SQLException {
        System.out.println();
        System.out.println("Enter the id of question to edit.");
        int id = scanner.nextInt();

        Question question = dao.findById(id);
        if (question == null) {
            System.out.println("Choice with given id doesn't exist in database");
        } else {
            System.out.println("Enter question title:");
            String title = scanner.next();
            title += scanner.nextLine();

            System.out.println("Enter question difficulty(1:Easy, 2:Medium, 3:Hard):");
            int difficulty = Integer.parseInt(scanner.next());

            System.out.println("Enter labels with format: java,kotlin");
            String labels = scanner.next();
            String[] topics = labels.split(",");

            question.setQuestion(title);
            question.setDifficulty(difficulty);
            question.setTopics(topics);
            System.out.println("Updated Successfully");
            System.out.println();
        }
        displayQuestionsMenu(scanner, quizId);
    }

    private static void createQuestion(QuestionDAO dao, int quizId, Scanner scanner) throws SQLException {
//        String addQuestion = "yes";
//        do {
        System.out.println("Enter question title:");
        String question = scanner.next();
        question += scanner.nextLine();

        System.out.println("Enter question difficulty(1:Easy, 2:Medium, 3:Hard):");
        int difficulty = Integer.parseInt(scanner.next());

        System.out.println("Enter labels with format: java,kotlin");
        String labels = scanner.next();
        String[] topics = labels.split(",");

        Question q = new Question();
        q.setQuizId(quizId);
        q.setQuestion(question);
        q.setDifficulty(difficulty);
        q.setTopics(topics);
        int questionId = dao.create(q);
        createChoice(questionId, scanner, quizId);
//        } while (addQuestion.equalsIgnoreCase("yes"));
    }

    private static void deleteChoice(ChoiceDAO dao, Scanner scanner, int questionId, int quizId) throws SQLException {
        System.out.println();
        System.out.println("Enter the id of choice to delete.");

        int id = scanner.nextInt();

        MCQChoice mcqChoice = dao.findById(id);

        if (mcqChoice == null) {
            System.out.println("Choice with given id doesn't exist in database");
        } else {
            dao.delete(mcqChoice);
            System.out.println("Deleted Successfully");
            System.out.println();
        }
        displayChoicesMenu(dao, scanner, questionId, quizId);
    }

    private static void updateChoice(ChoiceDAO dao, Scanner scanner, int questionId, int quizId) throws SQLException {
        System.out.println();
        System.out.println("Enter the id of choice to edit.");
        int id = scanner.nextInt();

        MCQChoice mcqChoice = dao.findById(id);
        if (mcqChoice == null) {
            System.out.println("Choice with given id doesn't exist in database");
        } else {
            System.out.println();
            System.out.println("Enter the title of choice:");
            String choice = scanner.next();

            System.out.println("Is this valid choice:(yes|no)");
            String validChoice = scanner.next();

            mcqChoice.setChoice(choice);
            mcqChoice.setValid(validChoice.equalsIgnoreCase("yes"));

            dao.update(mcqChoice);

            System.out.println("Updated Successfully");
            System.out.println();
        }
        displayChoicesMenu(dao, scanner, questionId, quizId);
    }

    private static void listAllChoices(ChoiceDAO dao, int questionId, Scanner scanner, int quizId) throws SQLException {
        MCQChoice choice = new MCQChoice();
        choice.setQuestionId(questionId);
        dao.search(choice);

        ArrayList<MCQChoice> choices = dao.search(choice);
        if (choices.size() > 0) {
            System.out.println("----------------List of Choices---------------");
            for (MCQChoice mcqChoice : choices) {
                System.out.println(String.format("%d) %s %b", mcqChoice.getId(), mcqChoice.getChoice(), mcqChoice.isValid()));
            }
            System.out.println("----------------End of Choices-----------------");
        } else {
            System.out.println();
            System.out.println("You haven't inserted any choices yet");
        }
        System.out.println();
        displayChoicesMenu(dao, scanner, questionId, quizId);
    }

}
