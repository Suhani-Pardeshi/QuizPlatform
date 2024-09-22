import javax.swing.JOptionPane;  // Import the JOptionPane class

public class QuizSession {
    private User user;
    private Quiz quiz;
    private int score = 0;

    public QuizSession(User user, Quiz quiz) {
        this.user = user;
        this.quiz = quiz;
    }

    public void checkAnswer(Question question, String userAnswer) {
        if (question.isCorrect(userAnswer)) {
            score++;
        }
    }

    public void showResult() {
        JOptionPane.showMessageDialog(null, "Your score: " + score + "/" + quiz.getQuestions().size());
    }
}
