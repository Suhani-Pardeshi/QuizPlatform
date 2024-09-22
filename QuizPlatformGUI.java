import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class QuizPlatformGUI extends JFrame {
    public QuizPlatformGUI() {
        setTitle("Quiz Platform");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        JButton exitButton = new JButton("Exit");

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showLoginMenu();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showRegistrationMenu();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panel.add(loginButton);
        panel.add(registerButton);
        panel.add(exitButton);

        add(panel, BorderLayout.CENTER);
    }

    private void showLoginMenu() {
        String[] options = {"User", "Admin"};
        int choice = JOptionPane.showOptionDialog(null, "Login as", "Quiz Platform",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            userLogin();
        } else {
            adminLogin();
        }
    }

    private void showRegistrationMenu() {
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

    private void userLogin() {
        String username = JOptionPane.showInputDialog("Enter Username:");
        String password = JOptionPane.showInputDialog("Enter Password:");

        User user = UserDatabase.getUser(username);
        if (user != null && user.getPassword().equals(password) && !user.isAdmin()) {
            startQuiz(user);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid Credentials!");
        }
    }

    private void adminLogin() {
        String username = JOptionPane.showInputDialog("Enter Admin Username:");
        String password = JOptionPane.showInputDialog("Enter Admin Password:");

        User user = UserDatabase.getUser(username);
        if (user != null && user.getPassword().equals(password) && user.isAdmin()) {
            new AdminPanel();
        } else {
            JOptionPane.showMessageDialog(null, "Invalid Admin Credentials!");
        }
    }

    private void startQuiz(User user) {
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

    public static void main(String[] args) {
        UserDatabase.addUser(new User("admin", "admin", true));  // Pre-load an admin account

        EventQueue.invokeLater(() -> {
            QuizPlatformGUI frame = new QuizPlatformGUI();
            frame.setVisible(true);
        });
    }
}
