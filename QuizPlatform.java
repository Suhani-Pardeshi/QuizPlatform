import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class QuizPlatform {
    public static void main(String[] args) {
        // Pre-load an admin account for testing
        UserDatabase.addUser(new User("admin", "admin", true));

        String[] options = {"Login", "Register"};
        int choice = JOptionPane.showOptionDialog(null, "Welcome to the Quiz Platform", "Quiz Platform",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            showLoginMenu();
        } else if (choice == 1) {
            showRegistrationMenu();
        }
    }

    private static void showLoginMenu() {
        String[] options = {"User", "Admin"};
        int choice = JOptionPane.showOptionDialog(null, "Login as", "Quiz Platform",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            userLogin();
        } else {
            adminLogin();
        }
    }

    private static void showRegistrationMenu() {
        String[] options = {"User", "Admin"};
        int choice = JOptionPane.showOptionDialog(null, "Register as", "Quiz Platform",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        String username = JOptionPane.showInputDialog("Enter Username:");
        String password = JOptionPane.showInputDialog("Enter Password:");

        if (UserDatabase.userExists(username)) {
            JOptionPane.showMessageDialog(null, "User already exists!");
            return;
        }

        if (choice == 0) {
            UserDatabase.addUser(new User(username, password, false));
            JOptionPane.showMessageDialog(null, "User registered successfully!");
        } else if (choice == 1) {
            UserDatabase.addUser(new User(username, password, true));
            JOptionPane.showMessageDialog(null, "Admin registered successfully!");
        }

        showLoginMenu();  // Go back to the login menu after registration
    }

    private static void userLogin() {
        String username = JOptionPane.showInputDialog("Enter Username:");
        String password = JOptionPane.showInputDialog("Enter Password:");

        User user = UserDatabase.getUser(username);
        if (user != null && user.getPassword().equals(password) && !user.isAdmin()) {
            startQuiz(user);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid Credentials!");
        }
    }

    private static void adminLogin() {
        String username = JOptionPane.showInputDialog("Enter Admin Username:");
        String password = JOptionPane.showInputDialog("Enter Admin Password:");

        User user = UserDatabase.getUser(username);
        if (user != null && user.getPassword().equals(password) && user.isAdmin()) {
            new AdminPanel();
        } else {
            JOptionPane.showMessageDialog(null, "Invalid Admin Credentials!");
        }
    }

    private static void startQuiz(User user) {
        ArrayList<Question> questions = new ArrayList<>(Arrays.asList(
                new MCQQuestion("What is the capital of France?", Arrays.asList("Paris", "London", "Berlin"), "Paris"),
                new TrueFalseQuestion("Java is a programming language.", "True")
        ));

        Quiz quiz = new Quiz("General Knowledge", questions);
        QuizSession session = new QuizSession(user, quiz);

        for (Question question : quiz.getQuestions()) {
            String userAnswer = JOptionPane.showInputDialog(question.getQuestionText());
            session.checkAnswer(question, userAnswer);
        }

        session.showResult();
    }
}
