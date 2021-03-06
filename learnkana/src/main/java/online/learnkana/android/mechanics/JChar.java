package online.learnkana.android.mechanics;

import android.widget.Button;

/**
 * Created by Marc on 7/14/2016.
 */
public class JChar {
    private String romaji, hiragana, katakana;
    private double hScore, kScore;
    private double minChance, tmpChance;
    private JBlock parent;

    public JChar(String romaji, String hiragana, String katakana)
    {
        this.romaji         = romaji;
        this.hiragana       = hiragana;
        this.katakana       = katakana;
        this.hScore         = 0;
        this.kScore         = 0;
        this.minChance      = 0.05;
        this.tmpChance      = 1;
    }
    public double getScore()
    {
        return (this.hScore + this.kScore) / 2.0;
    }
    public double getChance()
    {
        double score = this.getScore();
        if(score < 10){return 1.0;}
        else
        {
            double change = Math.abs(Math.log10(score)-2);
            return change > this.minChance ? change : this.minChance;
        }
    }

    public void hCorrect(){this.hScore++;}

    public void kCorrect(){this.kScore++;}

    public void hFalse(){if(this.hScore > 0){this.hScore--;}}

    public void kFalse(){if(this.kScore > 0){this.kScore--;}}

    public void hBonus(){this.hScore += 0.5;}

    public void kBonus(){this.kScore += 0.5;}

    public String getRomaji(){return this.romaji;}

    public String getHiragana(){return this.hiragana;}

    public String getKatakana(){return this.katakana;}

    public JBlock getParent() {
        return this.parent;
    }

    public void setParent(JBlock parent) {
        this.parent = parent;
    }

    public double getHScore() {return this.hScore;}

    public void setHScore(double hScore) {this.hScore = hScore;}

    public double getKScore() {
        return this.kScore;
    }

    public void setKScore(double kScore) {this.kScore = kScore;}

    public boolean hasText(String s)
    {
        return this.romaji.equals(s) || this.hiragana.equals(s) || this.katakana.equals(s);
    }
    public boolean compareToButton(Button b)
    {
        String buttonText = b.getText().toString();
        return this.hasText(buttonText);
    }
}
