import java.util.List;

public class MCQQuestion extends Question {
    private List<String> options;
    private String correctAnswer;

    public MCQQuestion(String questionText, List<String> options, String correctAnswer) {
        super(questionText);
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean isCorrect(String answer) {
        return correctAnswer.equalsIgnoreCase(answer);
    }
}
