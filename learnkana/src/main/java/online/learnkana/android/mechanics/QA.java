package online.learnkana.android.mechanics;

import java.util.List;

public class QA{
    private String question;
    private List<String> answers;
    private JCharEnum type;

    public QA(String question, List<String> answers, JCharEnum type)
    {
        this.question = question;
        this.answers = answers;
        this.type = type;
    }

    public String getQuestion() {
        return question;
    }
    public List<String> getAnswers(){return  answers;}
    public JCharEnum getType() {
        return type;
    }
}
