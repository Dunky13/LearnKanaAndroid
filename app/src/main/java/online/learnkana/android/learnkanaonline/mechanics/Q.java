package online.learnkana.android.learnkanaonline.mechanics;

/**
 * Created by Marc on 7/14/2016.
 */
public class Q {
    private QA qa;
    private JChar jChar;

    public Q(QA qa, JChar jChar) {
        this.qa = qa;
        this.jChar = jChar;
    }
    public JChar answer(String chr)
    {
        if(this.qa.getQuestion().equals(chr))
            return null;
        JChar correct = this.jChar;
        if(this.jChar.getRomaji().equals(chr))
        {
            if(qa.getType() == JCharEnum.HIRAGANA){jChar.hCorrect();}
            else if(qa.getType() == JCharEnum.KATAKANA){jChar.kCorrect();}
        }
        else if(this.jChar.getHiragana().equals(chr))
        {
            if(qa.getType() == JCharEnum.ROMAJI){jChar.hCorrect();}
            else if(qa.getType() == JCharEnum.KATAKANA){jChar.kCorrect();jChar.hBonus();}
        }
        else if(this.jChar.getKatakana().equals(chr))
        {
            if(qa.getType() == JCharEnum.HIRAGANA){jChar.hCorrect();jChar.kBonus();}
            else if(qa.getType() == JCharEnum.ROMAJI){jChar.kCorrect();}
        }
        else if(qa.getType() == JCharEnum.HIRAGANA)
        {
            jChar.hFalse();
            correct = null;
        }
        else if(qa.getType() == JCharEnum.KATAKANA)
        {
            jChar.kFalse();
            correct = null;
        }
        else
        {
            correct = null;
        }


        return correct;
    }
}
