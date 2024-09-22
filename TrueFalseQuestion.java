public class TrueFalseQuestion extends Question {
    private String correctAnswer;

    public TrueFalseQuestion(String questionText, String correctAnswer) {
        super(questionText);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean isCorrect(String answer) {
        return correctAnswer.equalsIgnoreCase(answer);
    }
}
