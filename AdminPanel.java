import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AdminPanel extends JFrame {
    private JTextField questionField;
    private JTextField answerField;
    private JComboBox<String> typeCombo;
    private JButton addQuestionButton;
    private ArrayList<Question> questions = new ArrayList<>();

    public AdminPanel() {
        setTitle("Admin Panel");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        questionField = new JTextField();
        answerField = new JTextField();
        typeCombo = new JComboBox<>(new String[]{"MCQ", "True/False"});
        addQuestionButton = new JButton("Add Question");

        add(new JLabel("Question:"));
        add(questionField);
        add(new JLabel("Answer:"));
        add(answerField);
        add(new JLabel("Type:"));
        add(typeCombo);
        add(addQuestionButton);

        addQuestionButton.addActionListener(e -> {
            String question = questionField.getText();
            String answer = answerField.getText();
            String type = (String) typeCombo.getSelectedItem();

            if (type.equals("MCQ")) {
                questions.add(new MCQQuestion(question, new ArrayList<>(), answer));
            } else if (type.equals("True/False")) {
                questions.add(new TrueFalseQuestion(question, answer));
            }

            JOptionPane.showMessageDialog(this, "Question Added!");
        });

        setVisible(true);
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public static void main(String[] args) {
        new AdminPanel();
    }
}
