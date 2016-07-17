package online.learnkana.android.learnkanaonline;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import online.learnkana.android.learnkanaonline.mechanics.FileStorage;
import online.learnkana.android.learnkanaonline.mechanics.JAll;
import online.learnkana.android.learnkanaonline.mechanics.JBlock;
import online.learnkana.android.learnkanaonline.mechanics.JChar;
import online.learnkana.android.learnkanaonline.mechanics.Q;
import online.learnkana.android.learnkanaonline.mechanics.Statics;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String LOGTAG = "LearnKanaLogTag";
    private JAll jAll;
    private Context context;
    private Q question;

    private TextView questionView;
    private List<Button> answerButtons;
    private Button next;

    private Object load;
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

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.load = new Object();
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
                if(disabled == null || disabled.booleanValue() == true) return;
                showNextQuestion();
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        //this.loadData();
        showNextQuestion();
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        //this.saveData();
    }
    private void showNextQuestion()
    {
        while(jAll == null) {
            try {
                synchronized (load) {
                    load.wait(500);
                }
            } catch (InterruptedException e) {

            }
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                resetTags();
                question = jAll.getQuestion();
                final List<String> answers = question.getQA().getAnswers();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        question = jAll.getQuestion();
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
            b.setBackgroundResource(android.R.drawable.btn_default); //TODO: Run on UI Thread!
        }
    }

    private void colorButtons(Button correct, JChar alternative)
    {
        for(Button b : this.answerButtons)
        {
            b.setTag(R.integer.disabledTag, new Boolean(true));
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
        JChar correct = this.jAll.getQuestion().answer(b);
        if(b.getTag(R.integer.disabledTag) != null && ((Boolean)b.getTag(R.integer.disabledTag)).booleanValue() == true) return;
        this.next.setTag(R.integer.disabledTag, new Boolean(false));
        if(correct != null)
        {
            colorButtons(b, correct);
            this.next.setTag(R.integer.disabledTag, new Boolean(false));
        }
        else
        {
            setButtonFalse(b);
        }
        this.updateScore();
    }

    private void updateScore() {
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
