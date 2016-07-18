package online.learnkana.android;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import online.learnkana.android.mechanics.FileStorage;
import online.learnkana.android.mechanics.JAll;
import online.learnkana.android.mechanics.JChar;
import online.learnkana.android.mechanics.Q;
import online.learnkana.android.mechanics.Statics;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String LOGTAG = "LearnKanaLogTag";
    private final Object load = new Object();
    private JAll jAll;
    private Context context;
    private Q question;
    private TextView questionView;
    private List<Button> answerButtons;
    private Button next;

    private void primaryInit()
    {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run()
            {
                jAll = Statics.loadCharacters();
            }
        });
        t.start();
        Log.d(LOGTAG, "Thread started");
        try {
            t.join();
            Log.d(LOGTAG,"Threads should be joined");
            synchronized (load)
            {
                load.notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        primaryInit();
//        this.jAll = this.loadCharacters();
        this.context = getApplicationContext();

        this.questionView = (TextView) findViewById(R.id.Q);
        this.answerButtons = new ArrayList<Button>(10);
        this.answerButtons.add((Button) findViewById(R.id.A1));
        this.answerButtons.add((Button) findViewById(R.id.A2));
        this.answerButtons.add((Button) findViewById(R.id.A3));
        this.answerButtons.add((Button) findViewById(R.id.A4));
        this.answerButtons.add((Button) findViewById(R.id.A5));
        this.answerButtons.add((Button) findViewById(R.id.A6));
        this.answerButtons.add((Button) findViewById(R.id.A7));
        this.answerButtons.add((Button) findViewById(R.id.A8));
        this.answerButtons.add((Button) findViewById(R.id.A9));
        this.answerButtons.add((Button) findViewById(R.id.A10));

        for(Button b : this.answerButtons)
        {
            b.setOnClickListener(this);
        }

        this.next = (Button) findViewById(R.id.next);
        this.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                Boolean disabled = (Boolean) b.getTag(R.integer.disabledTag);
                if (disabled == null || disabled) return;
                showNextQuestion();
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        AnalyticsTrackers.initialize(this.context);
        this.loadData();
        showNextQuestion();
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        this.saveData();
    }
    private void showNextQuestion()
    {
        while(jAll == null) {
            try {
                synchronized (load) {
                    load.wait(500);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                resetTags();
                question = jAll.getQuestion();
                final List<String> answers = question.getQA().getAnswers();
                updateScore();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        questionView.setText(question.getQA().getQuestion());
                        for(int i = 0; i < answerButtons.size(); i++)
                        {
                            answerButtons.get(i).setText(answers.get(i));
                        }
                    }
                });
            }
        }).start();

    }
    private void loadData()
    {
        //TODO: Load from Google Drive
        this.jAll.loadJSON(FileStorage.load(this.context));
    }
    private void saveData()
    {
        //TODO: Save to Google Drive
        FileStorage.save(this.context, jAll.getJSON());
    }

    private void resetTags()
    {
        List<Button> toReset = new ArrayList<Button>();
        toReset.addAll(this.answerButtons);
        toReset.add(this.next);
        for(Button b : toReset)
        {
            b.setTag(R.integer.incorrectTag, null);
            b.setTag(R.integer.alternativeTag, null);
            b.setTag(R.integer.correctTag, null);
            b.setTag(R.integer.disabledTag, null);
            final Button tmpB = b;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tmpB.setBackgroundResource(android.R.drawable.btn_default);
                }
            });

        }
    }

    private void colorButtons(Button correct, JChar alternative)
    {
        for(Button b : this.answerButtons)
        {
            b.setTag(R.integer.disabledTag, Boolean.valueOf(true));
            if(b.equals(correct))
            {
                b.setBackgroundColor(ContextCompat.getColor(this.context, R.color.greenButton));
            }
            else if(alternative.compareToButton(b))
            {
                b.setBackgroundColor(ContextCompat.getColor(this.context, R.color.yellowButton));
            }
        }
    }
    private void clickAnswer(Button b)
    {
        JChar correct = this.question.answer(b);
        if (b.getTag(R.integer.disabledTag) != null && ((Boolean) b.getTag(R.integer.disabledTag)))
            return;
        this.next.setTag(R.integer.disabledTag, Boolean.valueOf(false));
        if(correct != null)
        {
            colorButtons(b, correct);
            this.next.setTag(R.integer.disabledTag, Boolean.valueOf(false));
        }
        else
        {
            setButtonFalse(b);
        }
        this.updateScore();
    }

    private void updateScore() {

        final int hScore = Double.valueOf(this.question.getQuestion().getHScore() * 10).intValue();
        final int kScore = Double.valueOf(this.question.getQuestion().getKScore() * 10).intValue();
        final int cScore = Double.valueOf(this.question.getQuestion().getScore() * 10).intValue();
        final int bScore = Double.valueOf(this.question.getQuestion().getParent().getScore()).intValue();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ProgressBar hiragana = (ProgressBar) findViewById(R.id.hiraganaProgressbar);
                ProgressBar katakana = (ProgressBar) findViewById(R.id.katakanaProgressbar);
                ProgressBar charProgress = (ProgressBar) findViewById(R.id.charProgressbar);
                ProgressBar blockProgress = (ProgressBar) findViewById(R.id.blockProgressbar);

                hiragana.setProgress(hScore);
                katakana.setProgress(kScore);
                charProgress.setProgress(cScore);
                blockProgress.setProgress(bScore);

            }
        });
    }

    @Override
    public void onClick(View view) {
        Button b = (Button)view;
        clickAnswer(b);

    }

    public void setButtonFalse(Button b) {
        b.setTag(R.integer.incorrectTag, new Boolean(true));
        b.setTag(R.integer.disabledTag, new Boolean(true));
        b.setBackgroundColor(ContextCompat.getColor(this.context, R.color.redButton));
    }
}
